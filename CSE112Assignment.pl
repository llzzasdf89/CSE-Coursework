jug(a).
jug(b).
maxgallon(jug(a),4).
maxgallon(jug(b),3).
state(x,y).
initalstate(jug(a),0).
initalstate(jug(b),0).
empty(jug(X)):- state(x,y), y>0,y<=3.
fillup(jug(Y)) :- state(x,y),x>=0,x<=4.
goalstate(jug(a),2).