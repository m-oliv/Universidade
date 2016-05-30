run_pi :- pesquisa(trabalho).

pesquisa(Problema):-
  consult(Problema),
  estado_inicial(S0),
  init_a([no(S0,[],[],0,1,0)],Solucao),
  write(Solucao),
  nl.

/*Expande os n�s na pesquisa A* */

expande_i(no(EA,Pai,OpA,CA,HA,PA),Lista):-
	findall(no(ES,no(EA,Pai,OpA,CA,HA,PA),OpS,CTotal,HTotal,PS),
		   ( op(EA,OpS,ES,CS),
		   PS is PA+1, /*Calcula a profundidade seguinte.*/
		   CTotal is CA+CS, /*Calcula o custo real final.*/
		   distancia_m(EA,HS), /*Utiliza a heur�stica.*/
		   HTotal is HA+HS), /*Calcula o valor total da heur�stica.*/
	           Lista).

/*Se o estado actual � uma solu�ao, o algoritmo termina a sua execu��o. */

a([no(E,Pai,Op,C,HC,P)|_],no(E,Pai,Op,C,HC,P)):- estado_final(E).

a([EstA|Restantes],Sol):-
	expande_i(EstA,Seguintes),
	ordem(Seguintes,Restantes,Ordenados),
	a(Ordenados,Sol).

/*Os auxiliares seguintes permitem inserir n�s ordenados numa lista. */

ordem([],No,No).
ordem([],No,No).
ordem([No|Rest],L1,L2):-
	nos_ordem(No,L1,L3),
	ordem(Rest,L3,L2).


/*Se a lista est� vazia, insere o n� � cabe�a da mesma.*/
nos_ordem(No,[],[No]).

/*Se a lista n�o est� vazia e o n� dado � menor que o n� que se encontra � cabe�a da lista, insere-se o n� dado na lista e este passa a ser o elemento que se encontra � cabe�a da mesma. A lista retornada ir� conter os n�s ordenados.*/

nos_ordem(No,[X|Xs],[No,X|Xs]):-
	verifica_menor(No,X),
	!.

/*Se o n� que se encontra � cabe�a da lista � menor que o n� dado, insere-se o n� na ordem correcta no corpo da lista. A lista retornada ir� conter os n�s ordenados.*/

nos_ordem(No,[X|Xs],[X|Ys]):-
	nos_ordem(No,Xs,Ys).

/*Permite verificar qual � o menor n� em termos de heur�stica.*/

verifica_menor(e(_,_,_,_,H1,_),e(_,_,_,H2,_)):- H1 < H2.


/*Heuristica 1: distancia em linha recta (dist�ncia euclidiana) entre duas salas*/

distancia((X,Y),D) :-
	estado_final((W,Z)),
	D is round(sqrt(abs(X-W)^2+abs(Y-Z)^2)).

/*Heur�stica 2: dist�ncia de manhattan entre duas salas da caverna.*/
distancia_m((X,Y),D) :-
	estado_final((W,Z)),
	D is abs(X-W)+abs(Y-Z).

/* Inicializa a pesquisa A*. */
init_a(E,S):- a(E,S).

