run_pi :- pesquisa(trabalho).

pesquisa(Problema):-
  consult(Problema),
  estado_inicial(S0),
  init_a([no(S0,[],[],0,1,0)],Solucao),
  write(Solucao),
  nl.

/*Expande os nós na pesquisa A* */

expande_i(no(EA,Pai,OpA,CA,HA,PA),Lista):-
	findall(no(ES,no(EA,Pai,OpA,CA,HA,PA),OpS,CTotal,HTotal,PS),
		   ( op(EA,OpS,ES,CS),
		   PS is PA+1, /*Calcula a profundidade seguinte.*/
		   CTotal is CA+CS, /*Calcula o custo real final.*/
		   distancia_m(EA,HS), /*Utiliza a heurística.*/
		   HTotal is HA+HS), /*Calcula o valor total da heurística.*/
	           Lista).

/*Se o estado actual é uma soluçao, o algoritmo termina a sua execução. */

a([no(E,Pai,Op,C,HC,P)|_],no(E,Pai,Op,C,HC,P)):- estado_final(E).

a([EstA|Restantes],Sol):-
	expande_i(EstA,Seguintes),
	ordem(Seguintes,Restantes,Ordenados),
	a(Ordenados,Sol).

/*Os auxiliares seguintes permitem inserir nós ordenados numa lista. */

ordem([],No,No).
ordem([],No,No).
ordem([No|Rest],L1,L2):-
	nos_ordem(No,L1,L3),
	ordem(Rest,L3,L2).


/*Se a lista está vazia, insere o nó à cabeça da mesma.*/
nos_ordem(No,[],[No]).

/*Se a lista não está vazia e o nó dado é menor que o nó que se encontra à cabeça da lista, insere-se o nó dado na lista e este passa a ser o elemento que se encontra à cabeça da mesma. A lista retornada irá conter os nós ordenados.*/

nos_ordem(No,[X|Xs],[No,X|Xs]):-
	verifica_menor(No,X),
	!.

/*Se o nó que se encontra à cabeça da lista é menor que o nó dado, insere-se o nó na ordem correcta no corpo da lista. A lista retornada irá conter os nós ordenados.*/

nos_ordem(No,[X|Xs],[X|Ys]):-
	nos_ordem(No,Xs,Ys).

/*Permite verificar qual é o menor nó em termos de heurística.*/

verifica_menor(e(_,_,_,_,H1,_),e(_,_,_,H2,_)):- H1 < H2.


/*Heuristica 1: distancia em linha recta (distância euclidiana) entre duas salas*/

distancia((X,Y),D) :-
	estado_final((W,Z)),
	D is round(sqrt(abs(X-W)^2+abs(Y-Z)^2)).

/*Heurística 2: distância de manhattan entre duas salas da caverna.*/
distancia_m((X,Y),D) :-
	estado_final((W,Z)),
	D is abs(X-W)+abs(Y-Z).

/* Inicializa a pesquisa A*. */
init_a(E,S):- a(E,S).

