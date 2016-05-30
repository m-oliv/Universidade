%Descrição do Problema:
%
%Representação dos Estados:
%
%estado_inicial(Estado).
%
%estado_final(Estado).
%
%Representação dos Operadores de Transição:
%
%op(EstadoActual,Movimento,EstadoSeguinte,Custo).
%
%Representação das Restrições Para o Caso das Portas Bloqueadas:
%
%blocked(EstadoActual,Movimento,EstadoSeguinte,Custo).
%
%Representação dos Nós:
%
%no(Estado,No_Pai,Operador,Custo,Profundidade).


estado_inicial((2,2)).

% estado_inicial((18,18)).

%estado_final((26,26)).

%estado_final((8,8)).

estado_final((4,4)).

/*Restrições. (Evitam que o agente utilize portas que estão bloqueadas)*/

blocked((1,2),e,(1,3),1):- fail.
blocked((2,3),w,(2,2),1):- fail.
blocked((3,4),n,(4,4),1):- fail.
blocked((4,5),w,(3,5),1):- fail.

/*As acções possíveis serão: N,S,E,W (as salas a norte, sul,etc. para as quais o agente pode mover-se)*/

op((X,Y),n,(X,W),1):-
	W is Y+1,

	\+ blocked((X,Y),n,(X,W),_).

op((X,Y),s,(X,W),1):-
	W is Y-1,
	\+ blocked((X,Y),s,(X,W),_).

op((X,Y),e,(Z,Y),1):-
	Z is X+1,
	\+ blocked((X,Y),e,(Z,Y),_).

op((X,Y),w,(Z,Y),1):-
	Z is X-1,
	\+ blocked((X,Y),w,(Z,Y),_).


/* Local Search */
%Represetação dos Estados
%estado = [posicao_inicial,.......,posicao_final] - caminhos

%Vizinho

%Indica onde o caminho está "partido"
partido([(X,Y),(Z,W),(A,B)|Xs],(C,D)) :-
	op((X,Y),_,(Z,W),1),
	partido([(Z,W),(A,B)|Xs],(C,D)).
partido([(Z,W),(A,B)|Xs],(Z,W)).

%Auxiliar para encontrar vizinhos.
aux_viz((X,Y),Lista) :-
	findall((Z,W),op((X,Y),_,(Z,W),_),Lista).

%Adicionar a lista
insere_casa((X,Y),L1,[],[[L2]]).
insere_casa((X,Y),[(X,Y)|Xs],[(A,B)|Xs],[[(X,Y),(A,B)|Xs]|Zs]) :-
	insere_casa((X,Y),[(X,Y)|Xs],[(A,B)|Xs],[Zs]).


/*lista((X,Y),[(X,Y)|Xs],(A,B),[(X,Y)|Xs]) :-
	lista((X,Y),[(X,Y)|Xs],[],[(X,Y),(A,B)|Xs]). */
lista((X,Y),[(A,B),(A1,B1)|Xs],(C,D),[(A,B),(A1,B1)|Xs]) :-
	lista((X,Y),[(A1,B1)|Xs],(C,D),[(A1,B1)|Xs]).
lista((X,Y),[(X,Y)|Xs],(A,B),[(X,Y),(A,B)|Xs]).
