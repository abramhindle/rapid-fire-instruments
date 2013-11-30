CSOUND=/usr/bin/csound -dm6 -+rtaudio=alsa -dm6 -o devaudio -L stdin
CSOUNDJACK=/usr/bin/csound -dm6 -+rtaudio=jack -dm6 -o devaudio -L stdin -B 2048
OPENCV=-lopencv_calib3d -lopencv_contrib -lopencv_core -lopencv_features2d -lopencv_flann -lopencv_gpu -lopencv_highgui -lopencv_imgproc -lopencv_legacy -lopencv_ml -lopencv_objdetect -lopencv_video 
SHAREDFLAGS=-DSDL=1 -lGL -lglut -lSDL -lfreenect -lm `pkg-config --cflags sdl` `pkg-config --cflags libfreenect` `pkg-config --libs sdl` `pkg-config --libs libfreenect` `pkg-config --cflags opencv` `pkg-config --libs opencv`
CC=gcc -std=c99 -O3 
CPP=g++ -O3 -DSDL=1 -lGL -lglut -lSDL  ${SHAREDFLAGS}

default: playgoop

binaries:	goop


goop:	goop-sdl.c	
	$(CC)  goop-sdl.c -o goop $(SHAREDFLAGS) -lpthread

playgoop:	goop energy.orc
	./goop |  $(CSOUND) energy.orc energy.sco
playgoopjack:	goop energy.orc
	./goop |  $(CSOUNDJACK) energy441.orc energy.sco



