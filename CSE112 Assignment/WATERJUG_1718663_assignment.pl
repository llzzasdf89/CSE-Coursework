
state(s(X,Y),s(0,Z)) :- Z is X + Y, Z=< 3. %pour A into B (A will be empty)
state(s(X,Y),s(Z,0)) :- Z is X + Y, Z=< 4. %pour B into A (B will be empty)
state(s(X,Y),s(Z,3)) :- Z is X + Y -3, Z >= 0. %pour A into B (B will be full)
state(s(X,Y),s(4,Z)) :- Z is Y + X -4, Z >= 0. %pour B into A (A will be full)
state(s(0,Y),s(4,Y)). % fill up A
state(s(X,0),s(X,3)). % fill up B
state(s(X,Y),s(0,Y)) :- X > 0. % Empty A
state(s(X,Y),s(X,0)) :- Y > 0. % Empty B

path(Xs) :- write('from A and B empty begin : (0,0)\n'),path([s(0,0)],Xs). % from A and B empty begin
path(Xs) :- write('from A jug full , B empty begin : (4,0)\n'),path([s(4,0)],Xs). % from A jug full , B empty begin
path(Xs) :- write('from B jug full, A empty begin: (0,3)\n'),path([s(0,3)],Xs). % from B jug full, A empty begin
path(Xs) :- write('from both jugs full begin: (4,3)\n'),path([s(4,3)],Xs).% from both jugs full begin

path([s(X0,Y0)|T],[s(2,X1),s(X0,Y0)|T]) :- state(s(X0,Y0),s(2,X1)), !. % when it met with A==2 , end the program

path([s(X0,Y0)|T],Xs) :- % use state to define the recursive process of path
state(s(X0,Y0),s(X1,Y1)),
not(member(s(X1,Y1),[s(X0,Y0)|T])), % filter out the repeated recursive result
path([s(X1,Y1),s(X0,Y0)|T],Xs).
solve :- write('the prccess shows from right to left\n'),path(Xs), write(Xs), nl,fail.