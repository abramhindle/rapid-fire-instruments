s.options.memSize = 650000;
s.boot;
s.scope;

~bal = {
	var y = 1.0.rand;
	[1.0 - y, y]
};
SynthDef(\splayer, {
	arg buf, out=0, rate=1.0, looping=0, amp=1.0, myvol=[0.5,0.5];
	var myrate, trigger, frames,y;
	Out.ar(0,
		myvol * [amp,amp] * PlayBuf.ar(1, buf, [rate,rate], 1, 0, looping, 2)
	);
}).add;
~crinkles = "./wavs/crinkle*wav".pathMatch.collect({arg path; Buffer.read(s,path)});
~pops = "./wavs/pop*wav".pathMatch.collect({arg path; Buffer.read(s,path)});
~scratches = "./wavs/scratch-*wav".pathMatch.collect({arg path; Buffer.read(s,path)});
~silences = "./wavs/silence*wav".pathMatch.collect({arg path; Buffer.read(s,path)});
~ones = "./wavs/1.*wav".pathMatch.collect({arg path; Buffer.read(s,path)});




~splayer = {
	arg buf, rate=1.0, looping=0, amp=1.0;
	{
		var myrate, trigger, frames, myvol;
		myvol = ~bal.(); 
		amp * myvol * PlayBuf.ar(1, buf, rate, 1, 0, looping, 2); 
	}.play();
};

~since = {
	arg since=10000.0;
	var amp = log(1.0+(abs(1000.0.min(since))))/log(1.0+10000.0);
	amp
};



~popf = {
	var myvol = ~bal.();
	Synth(\splayer,[\buf,~pops.choose,\myvol,myvol]);
};
~scratchf = {
	arg since=1000;
	var amp = 1.0.min(2.0 * (~since.(since))), myvol = ~bal.();
	Synth(\splayer,[buf: ~scratches.choose, rate: 0.9 + (0.3.rand), amp: amp, myvol: myvol ]);
};
~onef = {
	arg since=1000;
	var amp = 1.0.min(2.0 * (~since.(since))), myvol = ~bal.();
	Synth(\splayer,[buf: ~ones.choose, rate: 0.9 + (0.3.rand), amp: amp, myvol: myvol ]);
};
//20.do{~onef.()}
// ~scratchf.();
// ~scratchf.(since: 10);
// ~scratchf.(since: 50);
// ~scratchf.(since: 100);
// ~scratchf.(since: 500);
// ~scratchf.(since: 1000);
// ~scratchf.(since: 1500);
// ~scratchf.(since: 2000);
// ~scratchf.(since: 5000);
// ~scratchf.(since: 10000);

~crinklef = {
	arg since=1000;
	var amp = ~since.(since), myvol = ~bal.();
	//myvol.postln;
	Synth(\splayer,[buf: ~crinkles.choose, rate: 0.9 + (0.5.rand), amp: 0.5*amp, myvol: myvol]);
};

~popexplosion = {
	Routine {
		fork {
			1000.do{
				Synth(\splayer,[\buf, ~pops.choose, \myvol: (~bal.())]); 0.05.wait;
			};
		};
	}.play();
};
// ~popexplosion.();

/*

Synth(\splayer,[\buf,~pops.choose]);


Synth(\splayer,[\buf, ~crinkles.choose]);
fork {
	1000.do{Synth(\splayer,[\buf, ~pops.choose]); 0.05.wait;};
};


Synth(\splayer,[buf: ~scratches.choose, rate: 0.9 + (0.5.rand)]);
Synth(\splayer,[buf: ~scratches.choose, looping: 1, rate: 0.9 + (0.5.rand)]);
Synth(\splayer,[buf: ~crinkles.choose, looping: 1, rate: 0.9 + (0.5.rand)]);

~silences.do{|silence| 
	Synth(\splayer,[buf: silence, rate: 0.3+1.0.rand, looping: 1]);
};
*/

~loopall = {
	arg vals, f, waittime=0.1;
	Routine {
		waittime.rand.wait;
		loop {
			f.(vals.choose).waitForFree;
		}
	}.play;
};

/*
~loopall.( ~crinkles, {|x| Synth(\splayer,[buf: x, rate: 0.3+1.0.rand]) });
~loopall.( ~scratches, {|x| Synth(\splayer,[buf: x, rate: 0.3+1.0.rand]) });
~loopall.( ~pops, {|x| Synth(\splayer,[buf: x, rate: 0.3+1.0.rand]) });
*/

/* it's all good brah */

/* pop osc responder */
o = ();
o.n = NetAddr("127.0.0.1", 57120); 
o.o = OSCresponderNode(n, '/chat', { |t, r, msg| ("time:" + t).postln; msg[1].postln }).add;
o.m = NetAddr("127.0.0.1", 57120); // the url should be the one of computer of app 1
o.m.sendMsg("/chat", "Hello App 1");
o.m.sendBundle(2.0, ["/chat", "Hello App 1"], ["/chat", "Hallo Wurld"]);
o.m.sendBundle(0.0, ["/chat", "Hello App 1"], ["/chat", "Hallo Wurld"]);
o.pops = OSCresponderNode(n, '/pop', 
	{ arg t, r, msg;		
		~popf.();
	}
).add;
o.scratch = OSCresponderNode(n, '/scratch',
	{ arg t, r, msg;
		var since = msg[3];
		since.postln;
		~scratchf.(since: since);
	}
).add;
o.crinkle = OSCresponderNode(n, '/crinkle',
	{ arg t, r, msg;
		var since = msg[3];
		since.postln;
		~crinklef.(since: since);
	}
).add;
o.one = OSCresponderNode(n, '/one',
	{ arg t, r, msg;
		var since = msg[3];
		since.postln;
		"One".postln;
		~onef.(since: since);
	}
).add;

o.m.sendBundle(0.0, ["/scratch"], ["/scratch"], ["/scratch"]  );
o.m.sendBundle(0.0, ["/crinkle"], ["/crinkle"], ["/crinkle"]  );
o.m.sendBundle(0.0, ["/pop"], ["/pop"], ["/pop"]  );
o.m.sendBundle(0.0, ["/one",10,20,100], ["/one",10,20,100], ["/one",10,20,100]  );
o.m.sendBundle(0.0, ["/scratch",10,20,100]);

/* o.m.remove; */

/*

o.scratch.remove;
o.crinkle.remove;
o.pops.remove;


*/



/* eval this at the start of the performance */




4.do {
  ~loopall.( ~silences, {|x| Synth(\splayer,[buf: x, rate: 0.3+1.0.rand, myvol: ~bal.()]); });
};



// Change this to 840
r = Routine {
    560.0.wait;
    "Splotion".postln;
    ~popexplosion.().yield;
}.play;

fork {
    loop {
        30.0.rand.wait;
		~crinklef.();
	}
};

fork {
    loop {
        0.1.rand.wait;
		~onef.();
	}
};


/* [X] make osc webservice */
/* [X] make UI */
/* [X] improve graphics */
/* [ ] make Video */
/* [ ] Burn DVD */
/* [ ] Test Network */
/* */



