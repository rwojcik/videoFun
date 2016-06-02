import sys
from video_streaming_core import *
from frame_merge import *
from frame_editor import *
from frame_source import *
from frame_destination import *

# default values
editor = 'FrameEditorEmpty'
merge = 'FrameMergerFirst'
editorparams = ''
mergerparams = ''
framesrcparams = 'localhost:5005'
framedstparams = 'localhost:5005'
framesource = 'CameraFrameGenerator'
framesdestination = 'FrameSinkShower'

try:

    if '-framesource' in sys.argv:
        framesource = sys.argv[sys.argv.index('-framesource') + 1]
        if len(sys.argv) > sys.argv.index('-framesource') + 2 \
                and sys.argv[sys.argv.index('-framesource') + 2][0] != '-':
            framesrcparams = sys.argv[sys.argv.index('-framesource') + 2]

    if '-framedestination' in sys.argv:
        framesdestination = sys.argv[sys.argv.index('-framedestination') + 1]
        if len(sys.argv) > sys.argv.index('-framedestination') + 2 \
                and sys.argv[sys.argv.index('-framedestination') + 2][0] != '-':
            framedstparams = sys.argv[sys.argv.index('-framedestination') + 2]

    if '-editor' in sys.argv:
        editor = sys.argv[sys.argv.index('-editor') + 1]

    if '-merge' in sys.argv:
        merge = sys.argv[sys.argv.index('-merge') + 1]

    if '-editorparams' in sys.argv:
        editorparams = sys.argv[sys.argv.index('-editorparams') + 1]

    if '-mergerparams' in sys.argv:
        mergerparams = sys.argv[sys.argv.index('-mergerparams') + 1]

    # print "From %s:%s to %s:%s, edit by %s" % (fromhost, _from, tohost, _to, editor)

    frameEditor = eval(editor)(editorparams)
    frameMerger = eval(merge)(mergerparams)
    framesSrc = eval(framesource)(framesrcparams)
    framesDst = eval(framesdestination)(framedstparams)

    receive_and_sink_video(framesSrc=framesSrc, framesDst=framesDst, frameEditor=frameEditor, frameMerger=frameMerger)
except ValueError, e:
    print e



