from UserString import MutableString
from itertools import takewhile

import struct

from core import *
import sys


class CameraFrameGenerator:
    """
    Fetches image from camera.

    :param src_host: ignored.
    """
    def __init__(self, src_host):
        self.camera = cv2.VideoCapture(0)

    def gen_frame(self):
        """
        Reads frame from camera and packs it in list (size - one).

        Packing is necessary for merger.

        :return: list with frame read from camera.
        :rtype: list
        """
        _, frame = self.camera.read()
        return [frame]

    def generator_finish(self):
        """
        Releases camera.
        """
        self.camera.release()
        pass

    def __del__(self):
        self.generator_finish()

def recv_udp_data(si):
    """
    Receives UDP messages and converts them to frame.
    In case of error retries until connection will be available.
    :param si: SocketInfo, information about UDP connection.
    :return: Frame received from UDP.
    """
    img = []
    frame_str = ''
    read = False
    while not read:
        try:
            frame_str = ''
            last = False
            while not last:
                if len(frame_str) > 0 and not frame_str[8] == '*':
                    # if frame_str doesn't start with digits then we have lost frame
                    print >> sys.stderr, 'frame_str doesn\'t begin with packet length, frame(s) lost'
                    frame_str = ''
                # read as much as UDP allows
                frame_part, _ = si.s.recvfrom(DATAGRAM_MAX_SIZE)
                frame_str += frame_part
                last = len(frame_part) != DATAGRAM_MAX_SIZE
            if not frame_str[8] == '*':
                print >> sys.stderr, 'frame_str doesn\'t begin with packet length, frame(s) lost'
                continue
            data_len = struct.unpack('!Q', frame_str[:8])[0]  # decode as ull in network (b.endian) byte order
            frame = frame_str[9:]
            if len(frame) == data_len:
                nparr = np.fromstring(frame, np.uint8)
                img = cv2.imdecode(nparr, cv2.IMREAD_COLOR)
                read = True
            else:
                print >> sys.stderr, 'length data do not match, expected: {}, got: {}, diff: {}'.format(data_len, len(frame), len(frame) - data_len)
        except Exception as e:
            print >> sys.stderr, '{}error reading history, retry'.format(str(e))
    print 'read image, size: {}'.format(len(frame_str))
    return img


class DatagramFrameGenerator:
    """
    Generator which receives frames from UDP stream.

    :param src_host: comma separated source host and port pairs.
    """
    def __init__(self, src_host):
        self.socketInfos = list()
        for hostport in map(lambda x: (x.split(':')[0], int(x.split(':')[1])), src_host.split(',')):
            s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
            ip = hostport[0]
            port = hostport[1]
            s.bind((ip, port))
            si = SocketInfo(ip, port, s)
            self.socketInfos.append(si)

    def gen_frame(self):
        """
        Receives frames from UDP connections.

        :return: list with frames received from UDP connections.
        :rtype: list
        """
        frames = list()
        try:
            for si in self.socketInfos:
                frame_data = recv_udp_data(si)
                frames.append(frame_data)
        except socket.error as e:
            print >> sys.stderr, "{}, frame(s) was lost".format(str(e))
        return frames

    def generator_finish(self):
        """
        Closes socket's binds.
        """
        for si in self.socketInfos:
            si.s.close()

    def __del__(self):
        self.generator_finish()


def recv_tcp_data(si):
    """
    Receives TCP packets and converts them to frame.
    In case of error retries until connection will be available.
    :param si: SocketInfo, information about TCP connection.
    :return: Frame send over TCP.
    """
    frame_str = ''
    last = False
    while not last:
        received = False
        while not received:
            try:
                frame_part, _ = si.s.recvfrom(1024*100)
                received = True
            except socket.error as e:
                print >> sys.stderr, '{}, retrying receive frame'.format(e)
                frame_str = ''
                time.sleep(1)
                si.s.close()
                connected = False
                while not connected:
                    try:
                        si.s.close()
                        s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
                        s.connect((si.ip, si.port))
                        si.s = s
                        connected = True
                        print 'successfully reconnected'
                    except socket.error as e:
                        print >> sys.stderr, '{}, retrying connection'.format(e)
                        time.sleep(1)
        frame_str += frame_part
        last = frame_part[len(frame_part)-4:] == '*end'
    data_length = int(frame_str[:8])
    frame = frame_str[9:len(frame_str)-4]
    nparr = np.fromstring(frame, np.uint8)
    img = cv2.imdecode(nparr, cv2.IMREAD_COLOR)
    return img


def connect_tcp(ip, port):
    """
    Tries TCP connection until successful. Can lock program execution.

    :param ip: destination ip.
    :param port: destination port.
    :return: SocketInfo with TCP connection data.
    """
    while 1:
        try:
            s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
            s.connect((ip, port))
            return SocketInfo(ip, port, s)
        except socket.error as e:
            print >> sys.stderr, '{}, sleep and retry'.format(str(e))
            time.sleep(0.5)


class TransmissionControlFrameGenerator:
    """
    Generator which receives frames from TCP stream.

    :param src_host: comma separated source host and port pairs. Locks execution until connects to all servers.
    """
    def __init__(self, src_host):
        self.socketInfos = list()
        for hostport in map(lambda x: (x.split(':')[0], int(x.split(':')[1])), src_host.split(',')):
            ip = hostport[0]
            port = hostport[1]
            si = connect_tcp(ip, port)
            # si = SocketInfo(ip, port, s)
            self.socketInfos.append(si)

    def gen_frame(self):
        """
        Generates list of frames send over TCP.

        :return: list with frames from all source connections.
        :rtype: list
        """
        frames = list()
        for si in self.socketInfos:
            # si.s.send('o')
            frame_data = recv_tcp_data(si)
            frames.append(frame_data)
        return frames

    def generator_finish(self):
        """
        Closes all sockets opened by this generator.
        """
        for s in self.socketInfos:
            s.s.close()