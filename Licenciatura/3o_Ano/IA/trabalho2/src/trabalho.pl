%:- use_module(library(clpfd)). %importa a biblioteca que contém all_different
:- use_module(library('clp/bounds')).

/*Estado inicial do problema.

estado_incial([variaveis não instanciadas],[variaveis instanciadas])*/

estado_inicial(e([
		   v(n(1),[1,2,3,4,5,6,7,8,9],_),
		   v(op(1),['+','-','/','*'],_),
		   v(n(2),[1,2,3,4,5,6,7,8,9],_),
		   v(op(2),['+','-','/','*'],_),
		   v(n(3),[1,2,3,4,5,6,7,8,9],_),
		   v(n(4),[1,2,3,4,5,6,7,8,9],_),
		   v(op(3),['+','-','/','*'],_),
		   v(n(5),[1,2,3,4,5,6,7,8,9],_),
		   v(op(4),['+','-','/','*'],_),
		   v(n(6),[1,2,3,4,5,6,7,8,9],_),
		   v(n(7),[1,2,3,4,5,6,7,8,9],_),
		   v(op(5),['+','-','/','*'],_),
		   v(n(8),[1,2,3,4,5,6,7,8,9],_),
		   v(op(6),['+','-','/','*'],_),
		   v(n(9),[1,2,3,4,5,6,7,8,9],_)
		 ],[])).

%Restrições:

restricoes(e(_,A)):-
	   rest(e(_,A)),
	   varToVal(A,R),
	   all_diff(R),
	   !.

/*Note-se que para o caso do forward check já não é necessário verificar se os valores são todos diferentes, porque o valor utilizado é removido dos domínios de todas as outras variáveis, garantindo assim que os valores são todos diferentes.*/


restricoes_fc(e(_,A)):-
	rest(e(_,A)),
	!.

/*Auxiliar que evita que o algoritmo termine quando existe um número demasiado baixo de variáveis instanciadas.*/

rest(e(_,A)):-
	length(A, Res),
	Res\=5,
	Res\=10,
	Res\=15.

/*Auxiliar que verifica a restrição da terceira linha.
Este auxiliar verifica ainda as restrições das colunas.*/

rest(e(_,A)):-
	              (
		      (
		       length(A,15),
		       member(v(n(1),_,X1),A),
		       member(v(n(2),_,X2),A),
		       member(v(n(3),_,X3),A),
		       member(v(n(4),_,X4),A),
		       member(v(n(5),_,X5),A),
		       member(v(n(6),_,X6),A),
		       member(v(n(7),_,X),A),
		       member(v(op(5),_,Op1),A),
		       member(v(n(8),_,Y),A),
		       member(v(op(6),_,Op2),A),
		       member(v(n(9),_,Z),A),
		       !,
		       alldiff(X,Y,Z),
		       linha(X,Y,Z,Op1,Op2,24),
		       coluna(X1, X4, X,12),
		       coluna(X2, X5, Y,15),
		       coluna(X3, X6, Z,18))).

/*Auxiliar que verifica a restrição da segunda linha do quadrado.*/

rest(e(_,A)):-
	              (
		      (length(A,10),
		       member(v(n(4),_,X),A),
		       member(v(op(3),_,Op1),A),
		       member(v(n(5),_,Y),A),
		       member(v(op(4),_,Op2),A),
		       member(v(n(6),_,Z),A),
		       alldiff(X,Y,Z),
		(      linha(X,Y,Z,Op1,Op2,15)))).

/*Auxiliar que verifica a restrição da primeira linha do quadrado.*/

rest(e(_,A)):-  ((length(A,5),
		       member(v(n(1),_,X),A),
		       member(v(op(1),_,Op1),A),
		       member(v(n(2),_,Y),A),
		       member(v(op(2),_,Op2),A),
		       member(v(n(3),_,Z),A),
		       alldiff(X,Y,Z),
		(	   linha(X,Y,Z,Op1,Op2,6)))).


/*Auxiliar que verifica se os elementos de uma lista são todos diferentes.*/

all_diff(Result):-
	all_different(Result).


/*Auxiliar que coloca as variaveis correspondentes a valores numericos numa lista*/

varToVal([A|R],Vs):-
	member(v(op(_),_,_),[A]), /*Se a variável for um operador, ignora.*/
	!,
	varToVal(R,Vs).

varToVal([], []).

varToVal([A|R], [V| Vs]):- /*Senão, guarda o valor da variável numa lista.*/
	member(v(n(_),_,V),[A]),
	varToVal(R, Vs).

/*Auxiliar que verifica se três valores são diferentes.*/

alldiff(X,Y,Z):-
	X\=Y,
	X\=Z,
	Y\=Z.

/*Auxiliar que efectua as operações aritméticas necessárias e retorna o resultado das mesmas numa dada linhas do quadrado.*/

linha(X,Y,Z,Op1,Op2,Res):-
	calcula(X,Y,Op1,R1),
	calcula(R1,Z,Op2,Res).

/*Auxiliar que efectua as operações aritméticas necessárias e retorna o resultado das mesmas numa dada linhas do quadrado.*/

coluna(X,Y,Z,W):-
	W is X+Y+Z.

/*Auxiliares que efectuam operações aritméticas.*/

calcula(X,Y,'+',Res):-
	Res is X+Y.
calcula(X,Y,'-',Res):-
	Res is X-Y.
calcula(X,Y,'*',Res):-
	Res is X*Y.
calcula(X,Y,'/',Res):-
	Res is X/Y.

/*Predicado que calcula o nó sucessor.*/

sucessor(e([v(N,D,V)|R],E),e(R,[v(N,D,V)|E])):-
	member(V,D).

pb:- estado_inicial(EstI),
	/*time(backtracking(EstI,A)),*//*Descomentar esta linha para que seja possível verificar o tempo que o algoritmo demorou a encontrar uma resposta.*/
        backtracking(EstI,A),
	write('Solucao: '),
	nl,nl,
	reverse(A,Sol),
	imprime(Sol),
	nl,nl,
	write(Sol),
	nl.

fc:- estado_inicial(EstI),
	/*ime(forward_check(EstI,A)),*//*Descomentar esta linha para que seja possível verificar o tempo que o algoritmo demorou a encontrar uma resposta.*/
	forward_check(EstI,A),
	write('Solucao: '),
	nl,nl,
	reverse(A,Sol),
	imprime(Sol),
	nl,nl,
	write(Sol),
	nl.


/*Pesquisa Backtracking*/

/*Se o estado é solução, termina.*/

backtracking(e([],Solucao),Solucao).

/*Caso contrário, calcula o estado sucessor e volta a verifcar se este constitui uma solução. O processo repete-se tantas vezes quantas as necessárias até que se encontre uma soluçao ou não existam mais estados.*/

backtracking(EstAct,Solucao):-
	sucessor(EstAct,EstSeg),
	restricoes(EstSeg),
	backtracking(EstSeg,Solucao).

/*Forward Checking.*/

forward_check(e([],Solucao),Solucao).

forward_check(EstAct,Solucao):-
	sucessor(EstAct,EstSeg),
	restricoes_fc(EstSeg),
	fc_aux1(EstSeg, EstSegFC),
	backtracking(EstSegFC,Solucao).


/*Auxiliar que permite remover um valor de uma lista.*/

remove(_,[],[]):-!.
remove(X,[X|Ys],Ys):-!.
remove(X,[Y|Ys],[Y|Zs]):-
	X\=Y,
	remove(X,Ys,Zs).

/*Auxiliar do forward checking.*/

fc_aux1(e(NAf,[Af|Res]),e(NAf,[Af|Res])):-
	member(v(op(_),_,_),[Af]).

fc_aux1(e(NAf,[Af|Res]),e(NAfForwardChecked,[Af|Res])):-
	member(v(n(_),_,V),[Af]),
	fc_aux2(NAf, V, NAfForwardChecked).

/*Auxiliar que remove um valor do domínio e actualiza a lista de variáveis.*/

fc_aux2([],_,[]).

fc_aux2([X|Xs], V, [Y|Ys]):-
	((member(v(op(_),_,_),[X]),
	  Y = X); /*Caso X seja um operador, não ocorrem alterações.*/
	member(v(n(I),D,_),[X]),
	remove(V,D,NewD), /*Caso X seja um número, este é removido do domínio.*/
        Y=v(n(I), NewD,_)), /*Altera-se o domínio na variável depois de ter ocorrido a remoção.*/
	fc_aux2(Xs, V, Ys). /*Aplica-se o mesmo método ao resto da lista de variáveis.*/

/*Imprimir Solucao*/
imprime([]).
imprime([v(X1,Y1,Z1),v(X2,Y2,Z2),v(X3,Y3,Z3),v(X4,Y4,Z4),v(X5,Y5,Z5)|Ws]) :-
	write(Z1),write(Z2),write(Z3),write(Z4),write(Z5),
	nl,
	imprime(Ws).
