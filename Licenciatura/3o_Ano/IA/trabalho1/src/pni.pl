%no(Estado,No_Pai,Operador,Custo,Profundidade).
%op(EstadoActual,Movimento,EstadoSeguinte,Custo).

pesquisa_it(Problema):-
      consult(Problema),
      estado_inicial(S0),
      inic_it([no(S0,[],[],0,0)], Solucao),
      write(Solucao),
      nl.

run_pni :- pesquisa_it(trabalho).


% Se a profundidade do nó é superior à profundidade a que estamos a
% limitar o algoritmo.

expande_pni(no(_,_,_,_,Prof),[],Pl):- Pl =< Prof, !.

% Após expandir os nós que estão a uma dada profundidade, irá expandir
% os nós que se encontram à profundidade seguinte (P+1).

expande_pni(no(EstAct,Pai,Opera,Cust,Prof),Lista,_):-
       findall(no(EstSeg,no(EstAct,Pai,Opera,Cust,Prof),OperaSeg,CustoTotal,P1),
	       (op(EstAct,OperaSeg,EstSeg,CustSeg),
		 P1 is Prof+1,
		 CustoTotal is CustSeg+Cust),Lista).

 /*Inserir elementos no final da lista que irá conter os estados visitados.*/

inserir([],X,X).
inserir(X,[],X).
inserir(Z,[X|Y],[X|W]):- inserir(Z,Y,W).

/*Se o estado visitado é estado final, então encontrou-se uma solução*/

limitada([no(E,Pai,Opera,Cust,Prof)|_],no(E,Pai,Opera,Cust,Prof),_):-
	estado_final(E).

/*Senão, continua a procurar.*/

limitada([E|R],S,Pl):-
	expande_pni(E,Next,Pl),
	inserir(R,Next,Resto),
	limitada(Resto,S,Pl).

/*Aplica algoritmo numa profundidade P.*/

iterativa(Ln,Sol,P):- limitada(Ln,Sol,P).

/*Aplica o algoritmo na profundidade seguinte (P+1).*/

iterativa(Ln,Sol,P):- P1 is P+1, iterativa(Ln,Sol,P1).

/*Inicializa a pesquisa iterativa.*/
inic_it(Ln,Sol):- iterativa(Ln,Sol,1).


