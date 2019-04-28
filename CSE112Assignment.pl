state(JugAhasWater,JugBhasWater):- JugAhasWater=0, JugBhasWater=0.
fillupjuga(state(4,JugBhasWater)):- JugAhasWater<4,JugAhasWater>=0.
emptyjugb(state(JugAhasWater,0)) :- JugBhasWater>0, JugBhasWater=<3.
jugbhasenoughwater(JugAhasWater,JugBhasWater):- JugAhasWater<4, JugAhasWater>=0, 4-JugAhasWater=<JugBhasWater.
fillupjugawithb(state(4,JugBhasWater)) :- jugbhasenoughwater(JugAhasWater,JugBhasWater).
fillupbwitha(JugAhasWater,3) :-JugBhasWater