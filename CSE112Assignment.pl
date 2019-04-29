move(s(X,Y),s(0,Z)) :- Z is X + Y, Z=< 4. % A的水倒进B中(A空)
move(s(X,Y),s(Z,0)) :- Z is X + Y, Z=< 7. % B的水倒进A中(B空)
move(s(X,Y),s(Z,4)) :- Z is X + Y -4, Z >= 0. % A的水倒进B中(B满)
move(s(X,Y),s(7,Z)) :- Z is Y + X -7, Z >= 0. % B的水倒进A中(A满)
move(s(0,Y),s(7,Y)). % A全部倒满
move(s(X,0),s(X,4)). % B全部倒满
move(s(X,Y),s(0,Y)) :- X > 0. % A全部倒空
move(s(X,Y),s(X,0)) :- Y > 0. % B全部倒空
path(Xs) :- path([s(0,0)],Xs). % 从(0,0)开始递归
path([s(X0,Y0)|T],[s(5,X1),s(X0,Y0)|T])
:- move(s(X0,Y0),s(5,X1)), !. % 遇到5就终止递归
path([s(X0,Y0)|T],Xs) :- % 用move来定义path的递归
move(s(X0,Y0),s(X1,Y1)),
not(member(s(X1,Y1),[s(X0,Y0)|T])), % 排除重复的move
path([s(X1,Y1),s(X0,Y0)|T],Xs).
solve :- path(Xs), write(Xs), nl, fail.