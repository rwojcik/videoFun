from video_streaming_core import *

recive_and_sink_video('127.0.0.1', 5005, FrameEditorEllipse(), FrameSinkServer('127.0.0.1', 5006))