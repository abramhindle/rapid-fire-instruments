Rapid Fire Threatre Kimprov

Artist: SkruntSkrunt (abram hindle) with Rapid Fire Theatre
Title: Kinemprov
Timing: 10 minutes, +5/10 for setup (pretty straightforward)
Video: My VGA out

Using a Kinect depth sensor video camera I will monitor improv participants and project the video of their participation (maybe back on to them if they don't mind).

Their actions will be either directly or indirectly interpreted by the software. Thus the liveness of the performance will be evident. The Kinect camera can detect actors in space and thus we can use their position and shape to determine some aspects of the music.

The sounds will be semi-ambient with awkward sample triggers. It won't be too loud as to drown out any discussion but quick motions should cause immediate auditory response. This means that the improv actors not only will improv their scene but they will have to improv their interaction with the audio system. Some general guidelines will be given to the actors but they will be expected to experiment on the spot (but it won't be that complicated and the visual feedback will probably give them hints).

I'm going 10 minutes since I don't want to change the palette of the piece during the performance.

Equipment I provide: Laptop, Kinect Camera, Maybe a projector, 3.5mm to 1/4" TRS
Equipment I need: audio hook up (anything) and projector

If the projector is not around/purchased by then, I can offer my personal projector which is the kind found in many office meeting rooms.

Abram

== Implementation ==

goop-sdl.c makes goop a Kinect monitoring program that output the differences across 3 regions of the screen.

goop-osc.pl oscifies the output of goop-sdl as it was in a default csound format.

rapidfire.sc is the supercollider program that dictates the logic of the scene. It mostly thresholds the kinect output and triggers samples.

To run start up jack

start goop | perl goop-osc.pl
Then eval the regions of the rapidfire.sc in supercollider

== Licenses ==

goop-sdl.c is licensed under Apache 2.0

All csound and super collider is licensed under Apache 2.0 (c) Abram Hindle
All perl code is licensed under the same license as perl (Artistic and GPL2+).
All shell code is assumed to be under Apache 2.0

All samples are licensed under CC-BY-SA 4.0 (C) Abram Hindle

When in doubt assume Apache 2.0 (C) Abram Hindle 2013 (you could always email me you know that)
