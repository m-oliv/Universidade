
estado_inicial([' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ']).

terminal([A, A, A,
	  _, _, _,
	  _, _, _]):- A\=' ',!.
terminal([_, _, _,
	  A, A, A,
	  _, _, _]):- A\=' ',!.
terminal([_, _, _,
	  _, _, _,
	  A, A, A]):- A\=' ',!.
terminal([A, _, _,
	  A, _, _,
	  A, _, _]):- A\=' ',!.
terminal([_, A, _,
	  _, A, _,
	  _, A, _]):- A\=' ',!.
terminal([_, _, A,
	  _, _, A,
	  _, _, A]):- A\=' ',!.
terminal([A, _, _,
	  _, A, _,
	  _, _, A]):- A\=' ',!.
terminal([_, _, A,
	  _, A, _,
	  A, _, _]):- A\=' ',!.
terminal([A, B, C,
	  D, E, F,
	  G, H, I]):-
			A\=' ',
			B\=' ',
			C\=' ',
	                D\=' ',
	                E\=' ',
			F\=' ',
			G\=' ',
			H\=' ',
	                I\=' '.

% função de utilidade, retorna o valor dos estados terminais, 1 ganha -1
% perde 0 empata

valor([A, A, A,
       _, _, _,
       _, _, _],1,P):- X is P mod 2, X=1, A='X',!.

valor([_, _, _,
       A, A, A,
       _, _, _],1,P):- X is P mod 2, X=1, A='X',!.

valor([_, _, _,
       _, _, _,
       A, A, A],1,P):- X is P mod 2, X=1, A='X',!.

valor([A, _, _,
       A, _, _,
       A, _, _],1,P):- X is P mod 2, X=1, A='X',!.

valor([_, A, _,
       _, A, _,
       _, A, _],1,P):- X is P mod 2, X=1, A='X',!.

valor([_, _, A,
       _, _, A,
       _, _, A],1,P):- X is P mod 2, X=1, A='X',!.

valor([A, _, _,
       _, A, _,
       _, _, A],1,P):- X is P mod 2, X=1, A='X',!.

valor([_, _, A,
       _, A, _,
       A, _, _],1,P):- X is P mod 2, X=1, A='X',!.

valor([A, A, A,
       _, _, _,
       _, _, _],-1,P):- X is P mod 2, X=0, A='O',!.

valor([_, _, _,
       A, A, A,
       _, _, _],-1,P):- X is P mod 2, X=0, A='O',!.

valor([_, _, _,
       _, _, _,
       A, A, A],-1,P):- X is P mod 2, X=0, A='O',!.

valor([A, _, _,
       A, _, _,
       A, _, _],-1,P):- X is P mod 2, X=0, A='O',!.

valor([_, A, _,
       _, A, _,
       _, A, _],-1,P):- X is P mod 2, X=0, A='O',!.

valor([_, _, A,
       _, _, A,
       _, _, A],-1,P):- X is P mod 2, X=0, A='O',!.

valor([A, _, _,
       _, A, _,
       _, _, A],-1,P):- X is P mod 2, X=0, A='O',!.

valor([_, _, A,
       _, A, _,
       A, _, _],-1,P):- X is P mod 2, X=0, A='O',!.


valor([A, B, C,
       D, E, F,
       G, H, I],0,_):-
			A\=' ',
			B\=' ',
			C\=' ',
	                D\=' ',
	                E\=' ',
			F\=' ',
			G\=' ',
			H\=' ',
	                I\=' '.


% op1(estado,jogada,estado seguinte)
op1(
    [A,B,C,
     D,E,F,
     G,H,I],placeXTopLeft,[An, Bn, Cn,
		    Dn, En, Fn,
		    Gn, Hn, In],x):-
	A=' ', An='X', Bn=B, Cn=C,
	Dn=D, En=E, Fn=F,
	Gn=G, Hn=H, In=I.

op1(
    [A,B,C,
     D,E,F,
     G,H,I],placeXTopMid,[An, Bn, Cn,
		    Dn, En, Fn,
		    Gn, Hn, In],x):-
	B=' ', An=A, Bn='X', Cn=C,
	Dn=D, En=E, Fn=F,
	Gn=G, Hn=H, In=I.

op1(
    [A,B,C,
     D,E,F,
     G,H,I],placeXTopRight,[An, Bn, Cn,
		    Dn, En, Fn,
		    Gn, Hn, In],x):-
	C=' ', An=A, Bn=B, Cn='X',
	Dn=D, En=E, Fn=F,
	Gn=G, Hn=H, In=I.

op1(
    [A,B,C,
     D,E,F,
     G,H,I],placeXMidLeft,[An, Bn, Cn,
		    Dn, En, Fn,
		    Gn, Hn, In],x):-
	D=' ', An=A, Bn=B, Cn=C,
	Dn='X', En=E, Fn=F,
	Gn=G, Hn=H, In=I.

op1(
    [A,B,C,
     D,E,F,
     G,H,I],placeXMid,[An, Bn, Cn,
		    Dn, En, Fn,
		    Gn, Hn, In],x):-
	E=' ', An=A, Bn=B, Cn=C,
	Dn=D, En='X', Fn=F,
	Gn=G, Hn=H, In=I.

op1(
    [A,B,C,
     D,E,F,
     G,H,I],placeXMidRight,[An, Bn, Cn,
		    Dn, En, Fn,
		    Gn, Hn, In],x):-
	F=' ', An=A, Bn=B, Cn=C,
	Dn=D, En=E, Fn='X',
	Gn=G, Hn=H, In=I.

op1(
    [A,B,C,
     D,E,F,
     G,H,I],placeXBotLeft,[An, Bn, Cn,
		    Dn, En, Fn,
		    Gn, Hn, In],x):-

	G=' ', An=A, Bn=B, Cn=C,
	Dn=D, En=E, Fn=F,
	Gn='X', Hn=H, In=I.

op1(
    [A,B,C,
     D,E,F,
     G,H,I],placeXBotMid,[An, Bn, Cn,
		    Dn, En, Fn,
		    Gn, Hn, In],x):-
	H=' ', An=A, Bn=B, Cn=C,
	Dn=D, En=E, Fn=F,
	Gn=G, Hn='X', In=I.

op1(
    [A,B,C,
     D,E,F,
     G,H,I],placeXBotRight,[An, Bn, Cn,
		    Dn, En, Fn,
		    Gn, Hn, In],x):-
	I=' ', An=A, Bn=B, Cn=C,
	Dn=D, En=E, Fn=F,
	Gn=G, Hn=H, In='X'.


op1(
    [A,B,C,
     D,E,F,
     G,H,I],1,[An, Bn, Cn,
		    Dn, En, Fn,
		    Gn, Hn, In],o):-
	A=' ', An='O', Bn=B, Cn=C,
	Dn=D, En=E, Fn=F,
	Gn=G, Hn=H, In=I.


op1(
    [A,B,C,
     D,E,F,
     G,H,I],2,[An, Bn, Cn,
		    Dn, En, Fn,
		    Gn, Hn, In],o):-
	B=' ', An=A, Bn='O', Cn=C,
	Dn=D, En=E, Fn=F,
	Gn=G, Hn=H, In=I.


op1(
    [A,B,C,
     D,E,F,
     G,H,I],3,[An, Bn, Cn,
		    Dn, En, Fn,
		    Gn, Hn, In],o):-
	C=' ', An=A, Bn=B, Cn='O',
	Dn=D, En=E, Fn=F,
	Gn=G, Hn=H, In=I.


op1(
    [A,B,C,
     D,E,F,
     G,H,I],4,[An, Bn, Cn,
		    Dn, En, Fn,
		    Gn, Hn, In],o):-

	D=' ', An=A, Bn=B, Cn=C,
	Dn='O', En=E, Fn=F,
	Gn=G, Hn=H, In=I.


op1(
    [A,B,C,
     D,E,F,
     G,H,I],5,[An, Bn, Cn,
		    Dn, En, Fn,
		    Gn, Hn, In],o):-

	E=' ', An=A, Bn=B, Cn=C,
	Dn=D, En='O', Fn=F,
	Gn=G, Hn=H, In=I.


op1(
    [A,B,C,
     D,E,F,
     G,H,I],6,[An, Bn, Cn,
		    Dn, En, Fn,
		    Gn, Hn, In],o):-
	F=' ', An=A, Bn=B, Cn=C,
	Dn=D, En=E, Fn='O',
	Gn=G, Hn=H, In=I.


op1(
    [A,B,C,
     D,E,F,
     G,H,I],7,[An, Bn, Cn,
		    Dn, En, Fn,
		    Gn, Hn, In],o):-
	G=' ', An=A, Bn=B, Cn=C,
	Dn=D, En=E, Fn=F,
	Gn='O', Hn=H, In=I.


op1(
    [A,B,C,
     D,E,F,
     G,H,I],8,[An, Bn, Cn,
		    Dn, En, Fn,
		    Gn, Hn, In],o):-

	H=' ', An=A, Bn=B, Cn=C,
	Dn=D, En=E, Fn=F,
	Gn=G, Hn='O', In=I.


op1(
    [A,B,C,
     D,E,F,
     G,H,I],9,[An, Bn, Cn,
		    Dn, En, Fn,
		    Gn, Hn, In],o):-

	I=' ', An=A, Bn=B, Cn=C,
	Dn=D, En=E, Fn=F,
	Gn=G, Hn=H, In='O'.

/*Função de Avaliação que não usa minimax*/

avalia(Estado, Valor, Base):-
	avaliaCentro(Estado, V1, Base),
	avaliaCantoTopLeft(Estado, V2, V1),
	avaliaCantoTopRight(Estado, V3, V2),
	avaliaCantoBotRight(Estado, V4, V3),
	avaliaCantoBotLeft(Estado, Valor, V4).

avaliaCentro([_,_,_,
	_,'X',_,
	_,_,_], Valor, Base):-
	!,
	Valor is Base + 5. /*jogar ao meio tem valor 5.*/

avaliaCentro(_,Valor,Valor).

avaliaCantoTopLeft(['X',_,_,
	_,_,_,
	_,_,_], Valor, Base):-
	!,
	Valor is Base + 3. /*jogar aos cantos tem valor 3.*/
avaliaCantoTopLeft(_,Valor,Valor).

avaliaCantoTopRight([_,_,'X',
	_,_,_,
	_,_,_], Valor, Base):-
	!,
	Valor is Base + 3.
avaliaCantoTopRight(_,Valor,Valor).

avaliaCantoBotLeft([_,_,_,
	_,_,_,
	'X',_,_], Valor, Base):-
	!,
	Valor is Base + 3.
avaliaCantoBotLeft(_,Valor,Valor).

avaliaCantoBotRight([_,_,_,
	_,_,_,
	_,_,'X'], Valor, Base):-
	!,
	Valor is Base + 3.
avaliaCantoBotRight(_,Valor,Valor).


avaliacao_melhor(Eact,Eseg):-
	findall(Es-Opr,op1(Eact,Opr,Es,x),Estad),
	findall(Opre-Valor,(member(X-Opre,Estad), avalia(X, Valor,0)),Vs),
	maximoaval(Vs,Op),
	op1(Eact, Op, Eseg,x).


run:- estado_inicial(E), agente(E).

agente(Eactual):-
consult('Minimax_completo.pl'),
/* consult('alphabeta.pl'), */
avaliacao_melhor(Eactual,Eseguinte),
escrever_tab(Eseguinte),
jogadas(Eseguinte).


jogadas(Estado):-
	(   (
	    terminal(Estado),!,
	valor(Estado, Valor,1),
	victoria(Valor))
	;
	(
	write('Por favor, introduza a sua jogada:\n'),
	write('1 | 2 | 3 \n'),
	write('4 | 5 | 6 \n'),
	write('7 | 8 | 9 \n'),
	read(X),
	op1(Estado,X,Eseguinte,o),
	escrever_tab(Eseguinte),
	(terminal(Eseguinte),valor(Estado, Valor,2),victoria(V),!;
	agente(Eseguinte))
	;
	write('Jogada inválida. /n'),jogadas(Estado))).

victoria(V):-
	V==1,!,write('O computador ganhou.\n');
	V== -1,!,write('O humano ganhou.\n');
	V==0,write('É um empate.\n').


escrever_tab([A,B,C,
              D,E,F,
	      G,H,I]):-
	write(A),write(' | '), write(B),write(' | '),write(C),write('\n'),
	write(D),write(' | '), write(E),write(' | '),write(F),write('\n'),
	write(G),write(' | '), write(H),write(' | '),write(I),write('\n').








maximoaval([E|R], Resultado):-
	maximoaval(R, E, Resultado).

maximoaval([], Resultado-_, Resultado).


maximoaval([E-Val|Resto], T-Valor, Res):-
	Val>Valor,
	!,
	maximoaval(Resto, E-Val, Res).


maximoaval([E-Val|Resto], T-Valor, Res):-
	Val=<Valor,
	!,
	maximoaval(Resto, T-Valor, Res).










