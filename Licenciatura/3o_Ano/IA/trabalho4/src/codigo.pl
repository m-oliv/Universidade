:-dynamic(h/2).

go:- andar(0).

%Input: Set de valores
andar(S):-
	write(' o que? '), read(P),!,
	Sn is S+1,
	tell(kb,P,Sn),
	ask(kb,accao(A,Sn)),nl,write('Faz '),write(A),nl,
	(
	(h(ganhou,A), !, abort);
	go).

% Guarda percepcoes
tell(kb,A,S):- asserta(h(valor([a,b,c,d,e],A),S)).
tell(kb,A,S):- asserta(h(valor([a,b,c,d,e],A),S)).

%Interpretação do ask
% codifica a escolha da melhor accao: ordem dos predicados

ask(kb,accao(afectar_r(P),S)):-
	var(P),
	h(valor(X,Y),S),
	escolhejogada(X,Y,A1,B1,afect),
	nonvar(A1),
	nonvar(B1),
	!,
	val(A1,B1,P,afect,S).

ask(kb,accao(afectar_r(P),S)):-
	nonvar(P),
	P\=afectar_r(_),
	h(valor(X,Y),S),
	escolhejogada(X,Y,A1,B1,afect),
	nonvar(A1),
	nonvar(B1),
	!,
	val(A1,B1,P,afect,S).

ask(kb,accao(afectar_r(P),S)):-
	nonvar(P),
	ask(kb, accao(P,S)).
ask(kb,accao(somar_r(P),S)):-
	h(valor(X,Y),S),
	escolhejogada(X,Y,A1,B1,somar),
	nonvar(A1),
	nonvar(B1),
	!,
	val(A1,B1,P,somar,S).


%Descrição do problema

%estado inicial
h(valor([a,b,c,d,e],[va,vb,vc,vd,ve]),0).
%efinal
h(valor([a,b,c,d,e],[vb,va,vb,vd,ve]),s2).

%%%% Consequencias das acções
h(valor(X,Y),r(somar_r(valor(U,W)),S)):-
	h(valor(U,W),S),
	h(valor(X,Y),S),
	escolhejogada(X,Y,A1,B1,afect),
	val(A1,B1,W,somar,S).

h(valor(X,Y),r(afectar_r(valor(U,W)),S)):-
	h(valor(U,W),S),
	h(valor(X,Y),S),
	escolhejogada(X,Y,A1,B1,somar),
	val(A1,B1,W,somar,S).

h(ganhou,somar_r(Y)):-
	h(valor(_,Y),s2).
h(ganhou,afectar_r(Y)):-
	h(valor(_,Y),s2).

h(valor(X,Y),r(afectar_r(_),S)):-
	h(valor(X,Y),S).

h(valor(X,Y),r(somar_r(_),S)):-
	h(valor(X,Y),S).


val(Var1,Var2,R,afect,S):-
	h(valor(X,Y),S),
	valaux(X,Y,Var1,Var2,R,afect).

val(Var1,Var2,R,somar,S):-
	h(valor(X,Y),S),
	valaux(X,Y,Var1,Var2,R,somar).

escolhejogada([a,b,c,d,e], [A,B,C,D,E], Arg1,Arg2,Jogada):-
	valaux([a,b,c,d,e],[A,B,C,D,E],X,Y,[An,Bn,Cn,Dn,En],Jogada),
	h(valor(_,[Q,W,T,I,O]),s2), %estado final
	(   (Y=a, An=Q, A\=An, !, Arg1=X, Arg2=Y);
	 (Y=b, Bn=W, B\=Bn, !, Arg1=X, Arg2=Y);
	 (Y=c, Cn=T, C\=Cn, !, Arg1=X, Arg2=Y);
	 (Y=d, Dn=I, D\=Dn, !, Arg1=X, Arg2=Y);
	 (Y=e, En=O, E\=En, !, Arg1=X, Arg2=Y)
	).

escolhejogada(_,_,_,_,_).

valaux([a,b,c,d,e], [A,B,C,D,E], a,a, [A,B,C,D,E],afect).
valaux([a,b,c,d,e], [A,_,C,D,E], a,b, [A,A,C,D,E],afect).
valaux([a,b,c,d,e], [A,B,_,D,E], a,c, [A,B,A,D,E],afect).
valaux([a,b,c,d,e], [A,B,C,_,E], a,d, [A,B,C,A,E],afect).
valaux([a,b,c,d,e], [A,B,C,D,_], a,e, [A,B,C,D,A],afect).
valaux([a,b,c,d,e], [_,B,C,D,E], b,a, [B,B,C,D,E],afect).
valaux([a,b,c,d,e], [A,B,C,D,E], b,a, [A,B,C,D,E],afect).
valaux([a,b,c,d,e], [A,B,_,D,E], b,a, [A,B,B,D,E],afect).
valaux([a,b,c,d,e], [A,B,C,_,E], b,a, [A,B,C,B,E],afect).
valaux([a,b,c,d,e], [A,B,C,D,_], b,a, [A,B,C,D,B],afect).
valaux([a,b,c,d,e], [_,B,C,D,E], c,a, [C,B,C,D,E],afect).
valaux([a,b,c,d,e], [A,_,C,D,E], c,b, [A,C,C,D,E],afect).
valaux([a,b,c,d,e], [A,B,C,D,E], c,c, [A,B,C,D,E],afect).
valaux([a,b,c,d,e], [A,B,C,_,E], c,d, [A,B,C,C,E],afect).
valaux([a,b,c,d,e], [A,B,C,D,_], c,e, [A,B,C,D,C],afect).
valaux([a,b,c,d,e], [_,B,C,D,E], d,a, [D,B,C,D,E],afect).
valaux([a,b,c,d,e], [A,_,C,D,E], d,b, [A,D,C,D,E],afect).
valaux([a,b,c,d,e], [A,B,_,D,E], d,c, [A,B,D,D,E],afect).
valaux([a,b,c,d,e], [A,B,C,D,E], d,d, [A,B,C,D,E],afect).
valaux([a,b,c,d,e], [A,B,C,D,_], d,e, [A,B,C,D,D],afect).
valaux([a,b,c,d,e], [_,B,C,D,E], e,a, [E,B,C,D,E],afect).
valaux([a,b,c,d,e], [A,_,C,D,E], e,b, [A,E,C,D,E],afect).
valaux([a,b,c,d,e], [A,B,_,D,E], e,c, [A,B,E,D,E],afect).
valaux([a,b,c,d,e], [A,B,C,_,E], e,d, [A,B,C,E,E],afect).
valaux([a,b,c,d,e], [A,B,C,D,E], e,e, [A,B,C,D,E],afect).

valaux([a,b,c,d,e], [A,B,C,D,E], a,a, [A+A,B,C,D,E],somar).
valaux([a,b,c,d,e], [A,B,C,D,E], a,b, [A,B+A,C,D,E],somar).
valaux([a,b,c,d,e], [A,B,C,D,E], a,c, [A,B,C+A,D,E],somar).
valaux([a,b,c,d,e], [A,B,C,D,E], a,d, [A,B,C,D+A,E],somar).
valaux([a,b,c,d,e], [A,B,C,D,E], a,e, [A,B,C,D,E+A],somar).
valaux([a,b,c,d,e], [A,B,C,D,E], b,a, [A+B,B,C,D,E],somar).
valaux([a,b,c,d,e], [A,B,C,D,E], b,a, [A,B+B,C,D,E],somar).
valaux([a,b,c,d,e], [A,B,C,D,E], b,a, [A,B,C+B,D,E],somar).
valaux([a,b,c,d,e], [A,B,C,D,E], b,a, [A,B,C,D+B,E],somar).
valaux([a,b,c,d,e], [A,B,C,D,E], b,a, [A,B,C,D,E+B],somar).
valaux([a,b,c,d,e], [A,B,C,D,E], c,a, [A+C,B,C,D,E],somar).
valaux([a,b,c,d,e], [A,B,C,D,E], c,b, [A,B+C,C,D,E],somar).
valaux([a,b,c,d,e], [A,B,C,D,E], c,c, [A,B,C+C,D,E],somar).
valaux([a,b,c,d,e], [A,B,C,D,E], c,d, [A,B,C,D+C,E],somar).
valaux([a,b,c,d,e], [A,B,C,D,E], c,e, [A,B,C,D,E+C],somar).
valaux([a,b,c,d,e], [A,B,C,D,E], d,a, [A+D,B,C,D,E],somar).
valaux([a,b,c,d,e], [A,B,C,D,E], d,b, [A,B+D,C,D,E],somar).
valaux([a,b,c,d,e], [A,B,C,D,E], d,c, [A,B,C+D,D,E],somar).
valaux([a,b,c,d,e], [A,B,C,D,E], d,d, [A,B,C,D+D,E],somar).
valaux([a,b,c,d,e], [A,B,C,D,E], d,e, [A,B,C,D,E+D],somar).
valaux([a,b,c,d,e], [A,B,C,D,E], e,a, [A+E,B,C,D,E],somar).
valaux([a,b,c,d,e], [A,B,C,D,E], e,b, [A,B+E,C,D,E],somar).
valaux([a,b,c,d,e], [A,B,C,D,E], e,c, [A,B,C+E,D,E],somar).
valaux([a,b,c,d,e], [A,B,C,D,E], e,d, [A,B,C,D+E,E],somar).
valaux([a,b,c,d,e], [A,B,C,D,E], e,e, [A,B,C,D,E+E],somar).



















