
/*Verifica se o jogador usou uma letra v�lida (a,b,c,d,e,f).*/
valid(X,_,_,_,_,_):-
	(X=='a'; X=='b';X=='c';X=='d';X=='e';X=='f').

/*Qualquer situa��o em que uma pe�a seja mal jogada, esta fun��o � chamada.
*/

valid(_,TabJ1,TabJ2,Pontos1,Pontos2,Jogador):-
	Temp is Pontos1+Pontos2,
	Temp<48,
	write('\nJogada inv�lida!\n\n'),
	jogarHumano(TabJ1, TabJ2,Pontos1,Pontos2,Jogador).
validVariante(X,_,_,_,_,_):-
	(X=='a'; X=='b';X=='c';X=='d';X=='e';X=='f').

validVariante(_,TabJ1,TabJ2,Pontos1,Pontos2,Jogador):-
	Temp is Pontos1+Pontos2,
	Temp<48,
	write('\nJogada inv�lida!\n\n'),
	jogarVariante(TabJ1, TabJ2,Pontos1,Pontos2,Jogador).

validAI(X,_,_,_,_,_):-
	(X=='a'; X=='b';X=='c';X=='d';X=='e';X=='f').

validAI(_,TabJ1,TabJ2,Pontos1,Pontos2,Jogador):-
	Temp is Pontos1+Pontos2,
	Temp<48,
	write('\nJogada inv�lida!\n\n'),
	jogarAI(TabJ1, TabJ2,Pontos1,Pontos2,Jogador).
/*
As regras do ouri s�o aplicadas nestas fun��es.
validInfCycles trata do caso especial do tabuleiro de ciclos infinito,
em que nenhum dos jogadores poderia terminar. Dado que o jogo termina
aos 25 ou mais pontos, esta situa��o causa um empate autom�tico.

	*/

validInfCycles([1,0,0,0,0,0],[1,0,0,0,0,0]):-
	!,
	gameOver(24,24).
/*Qualquer outro tabuleiro valida a regra.*/
validInfCycles(_,_).


/* A fun��o validRules trata da regra do ouri da "casa com 1 semente".
Esta regra dita que � pro�bido jogar em casas com uma semente
caso existam casas com mais.
�, no entanto, imperativo o uso de duas flags.
Flag activa ou desactiva a regra, sendo esta regra automaticamente
validada com a flag a 1. Tal torna-se necess�rio devido � situa��o de
bloqueio:
000000
400101
O jogador de baixo � o jogador que efectua a jogada seguinte.
Por esta regra, o jogador era for�ado a jogar 'a',
no entanto, tento o oponente um tabuleiro de zeros, esta regra ter� de
ser ignorada. Flag colmata este problema.

FlagWrites determina se � imprimido no output a mensagem com o erro,
sendo esta uma necessidade devido � simula��o do AI tamb�m usar esta
fun��o.


*/

/*Se tenta jogar numa casa com 1 semente
	quando ainda existem casas com mais sementes*/

validRules([],1,_,Y,FlagWrites):-
	Y>1,
	!,
	(
	(   FlagWrites==0,!,
	write('H� casas com mais de 1 semente!')
	)
	),
	fail.
/*chegados ao fim da lista e com Y = 1 (zero implicava n�o existirem
jogadas, nessa situa��o nem � poss�vel chegar a esta fun��o. Outro valor
seria tratado na fun��o acima, que tem um cut vermelho para impedir
que ambas sejam lidas), a regra valida a jogada.*/

validRules([],_,_,_,_).

/*Y guarda a casa do tabuleiro com maior n�mero de sementes.
Se esta for 1 ou 0, � garantido n�o existirem casas com mais
.*/
validRules([X|Xs],NPecas,Flag,Y,FlagWrites):-
	X=<Y,
	validRules(Xs,NPecas,Flag,Y,FlagWrites).

validRules([X|Xs],NPecas,Flag,Y,FlagWrites):-
	X>Y,
	Temp is X,
	validRules(Xs,NPecas,Flag,Temp,FlagWrites).

/*Chama um auxiliar de aridade 5.*/

validRules(Tab,NPecas, Flag,FlagWrites):-
	Flag==0,
	validRules(Tab, NPecas, Flag, 0,FlagWrites).
/*aceita autom�ticamente a regra com flag a 1*/

validRules(_,_, Flag,_):-
	Flag==1.

/*Regra que pro�be jogar em casas sem pe�as.
FlagWrites a 1 implica que o write � ignorado,
necess�rio devido � possibilidade de esta ser uma simula��o do AI.*/
validRules0([X|_],0,FlagWrites):-
	X=0,
	!,
	(
	(   FlagWrites==0,!,write('Jogou num zero!')
	)
	),
	fail.
/*aceita jogadas em casas com mais de 0 pe�as*/
validRules0([X|_],0,_):-
	X>0,
	!.


/*procura a posi��o onde se jogou*/
validRules0([_|Xs], Posicao,FlagWrites):-
	Temp is Posicao - 1,
	validRules0(Xs, Temp,FlagWrites).


/*ValidRulesSupp/7 e as suas auxiliares, SumPontos, ValidRulesSupp/3 e validate,
	s�o chamados para tratar das regras suplementares do Ouri.
	Verifica-se primeiro se existe jogada poss�vel.
N�o existindo, soma � pontua��o do jogador os pontos de todas as pe�as
no seu campo. Existindo, verifica se a dada � poss�vel. Sabemos que o
oponente tem um tabuleiro de zeros neste ponto.
*/


/*Soma todos os valores do tabuleiro*/
sumPontos([A,B,C,D,E,F],Result):-
	Result is A+B+C+D+E+F.


validate(NPecas,Pos,FlagWrites):-
	NPecas=<5-Pos,
	(
	(   FlagWrites==0,!,write('H� uma jogada dispon�vel
	para o jogo prosseguir, mas n�o a seleccionou.\n'))),
	fail.


validate(NPecas, Pos,_):-
	NPecas>5-Pos.

/*validRulesSupp/3
Verifica a exist�ncia de jogada v�lida.
6 � o n�mero de pe�as necess�rio ter na casa 'a' para
atingir o tabuleiro oponente, sendo dado como argumento.*/

/*1 h� jogada dispon�vel. 0 n�o h�.*/
validRulesSupp(-1,_,1):-!.

validRulesSupp([],_,0):-!.

validRulesSupp([X|_], Pos,  IsAv):-
	X>=Pos, /*jogada dispon�vel!*/
	!, /*for�a o predicado validRulesSupp(-1,_,1). dando 1 ao valor IsAv.*/
	validRulesSupp(-1,_,IsAv).

/*Decrementa o segundo argumento, j� que em 'b' s�o
5 as pe�as necess�rias para chegar ao tabuleiro oponente,
4 em 'c', e assim sucessivamente.*/
validRulesSupp([X|Xs], Pos, IsAv):-
	X=<Pos,
	Temp is Pos-1,
	validRulesSupp(Xs, Temp, IsAv).



/*com o primeiro argumento a 0, o tabuleiro oponente n�o
est� vazio, e a regra � desnecess�ria, aceitando autom�ticamente.*/
validRulesSupp(0, _,_, _, 0,_,_):-!.
/*Sem jogada dispon�vel e o tabuleiro oponente vazio,
os pontos ficam do jogador actual.*/
validRulesSupp(1, Tab,_, _, ResultPts,_,_):-
	validRulesSupp(Tab,6, IsAvailable),
	IsAvailable==0,
	!,
	sumPontos(Tab, ResultPts).
/*� indicado que existe uma jogada poss�vel para o jogo n�o terminar de imediato,
antes de esta ser feita, por esta fun��o*/
validRulesSupp(1, Tab,_, _, 0,0,FlagWrites):-
	validRulesSupp(Tab,6, IsAvailable),
	IsAvailable==1,
	!,
	(
	(FlagWrites==1,
	 !,
	 write('')
	);
	(FlagWrites==0,
	 !,
	 write('H� uma jogada dispon�vel para o jogo	prosseguir\n')
	)
	)
	.

/*O jogador escolheu a jogada errada nesta fun��o. � dado erro atrav�s de
validade/3. */
validRulesSupp(1, Tab,NPecas, Posicao, ResultPts,1,FlagWrites):-

	validRulesSupp(Tab,6, IsAvailable),
	IsAvailable>0,
	!,
	validate(NPecas, Posicao,FlagWrites),
	ResultPts=0.




/*verifica se o oponente tem um tabuleiro
s� com zeros. caso tenha, activa as regras suplement.
IsEmpty � 1 em tal situa��o.*/


verifyZeros(-1,0):-!.
verifyZeros([],1):-!.
verifyZeros([X|_],IsEmpty):-

	X>0,
	!,
	verifyZeros(-1,IsEmpty).

verifyZeros([X|[]], IsEmpty):-
	X>0,
	verifyZeros(-1,IsEmpty).

verifyZeros([0|Xs], IsEmpty):-

	verifyZeros(Xs,IsEmpty).



sair:-
	write('**O programa fechou/terminou**\n').

/*gameOver o jogo, mostrando o vencedor e o score.*/
gameOver(J1, _):-
	J1>=25,
	!,
	write('O Jogador 1 ganhou com '),
	write(J1),
	write(' pontos.'),
	break.

gameOver(_, J2):-
	J2>=25,
	!,
	write('O Jogador 2 ganhou com '),
	write(J2),
	write(' pontos.'),
	break.

gameOver(24, 24):-
	!,
	write('Empate.'),
	break.

/*Mesmo que gameOver, mas ganha quem tem menor pontua��o.*/

gameOverVariante(J1, J2):-
	J1>=25,
	!,
	write('O Jogador 2 ganhou com '),
	write(J2),
	write(' pontos.'),
	break.

gameOverVariante(J1, J2):-
	J2>=25,
	!,
	write('O Jogador 1 ganhou com '),
	write(J1),
	write(' pontos.'),
	break.

gameOverVariante(24, 24):-
	!,
	write('Empate.'),
	break.

/*Verifica a posi��o da lista que corresponde � jogada feita*/
verPos(a, 0).
verPos(b, 1).
verPos(c, 2).
verPos(d, 3).
verPos(e, 4).
verPos(f, 5).

/*jogCommit efectua a jogada. Quando o �ltimo argumento
� 1, a jogada � a do jogador que est� a jogar
actualmente; quando o �ltimo argumento � 2, � a do
oponente. Qual � qual vai depender dos argumentos
(se � dado tab1,tab2 ou tab2,tab1.
									    */

/*jogCommit para o player 1
 o primeiro argumento � o n�mero de pe�as � esquerda
 que vai ser incrementado. Esta esquerda significa
 "antes da posi��o onde o jogador jogou", ou seja,
 s� � diferente de 0 se o n�mero de pe�as for grande o
 suficiente para partir do s�tio onde se joga,
 atravessar o tabuleiro oponente e voltar a entrar
 no tabuleiro do jogador.

 Exemplo:

 000000
 000080

 Jogando em 8, NPecasEsq ser� igual a 1, j� que
 111111
 1000X1

 No tabuleiro acima, X foi o local de onde se come�ou
 a jogada (ap�s efectuada, X=0). H� 1 casa no tabuleiro
 de baixo � esquerda de X que recebeu uma pe�a.

 O segundo argumento � o n�mero de pe�as � direita. �
 a mesma l�gica das da esquerda mas conta o n� �
 direita de X.

 Estes 2 argumentos s�o calculados antes de chamar
 esta fun��o!

 O terceiro argumento � a posi��o onde o jogador
 jogou na lista.

 O quarto (Multiplier) � para o caso de uma jogada dar
 uma volta completa ao campo, em que todas as casas
 s�o incrementadas 1 pe�a. Quanto mais voltas completas
 maior o Multiplier.

 o 5� argumento � o tabuleiro recebido do jogador.
 o 6� argumento � o reultado a devolver.
 o �ltimo � para distinguir o jogador que est� a
 jogar (1) do advers�rio (2).

 */
 /*Aqui toda a lista foi corrida e n�o h� mais pe�as
 a inserir.*/
jogCommit(0,0, _, _, [], [],1).

/*Aqui a lista ainda n�o foi toda corrida, mas como
temos de devolver Y|Ys como o resultado perfeito de
inserir as pe�as, continuamos a correr a lista, mesmo
sem mais pe�as a inserir. S� altera os valores
se houve uma volta completa (Multiplier!=0).*/
jogCommit(0,0, -1, Multiplier, [X|Xs], [Y|Ys],1):-
	Y is X+Multiplier,
	jogCommit(0,0,-1,Multiplier,Xs,Ys,1).

/*A fun��o chega aqui quando j� leu as pe�as �
esquerda e est� na posi��o onde o jogador jogou.
interessa que nessa posi��o fiquem 0 pe�as
e que se continue a inserir as pe�as � direita desta.
o 3� argumento a -1 for�a esta fun��o a ir para as de
cima depois de ficar o Y a 0.*/
jogCommit(0,NPecasDir, 0,
	  Multiplier, [_|Xs], [Y|Ys],1):-
	Y is 0,
	Temp is NPecasDir,
	jogCommit(0,Temp, -1, Multiplier, Xs, Ys,1).

/*Insere as pe�as � direita da posi��o onde se jogou.*/
jogCommit(0,NPecasDir, -1,Multiplier,
	  [X|Xs],[Y|Ys],1):-
	Temp is NPecasDir-1,
	Y is X+Multiplier+1,
	jogCommit(0,Temp, -1,Multiplier,Xs,Ys,1)

	.
/*Procura a posi��o onde se jogou. S� muda o valor
da casa se Multiplier!=0*/
jogCommit(0, NPecasDir, Posicao, Multiplier,
	  [X|Xs], [Y|Ys],1):-
	T1 is Posicao-1,
	Y is X+Multiplier,
	jogCommit(0, NPecasDir,T1,
		  Multiplier,Xs,Ys, 1).


/*Vai adicionando pe�as nas posi��es iniciais da lista
se se verificou que depois da inser��o de pe�as �
direita e no campo advers�rio ainda h� sobras.
Este valor � calculado antes da chamada da fun��o.*/
jogCommit(NPecasEsq, NPecasDir, Posicao,
	  Multiplier, [X|Xs], [Y|Ys],1):-
	T1 is Posicao-1,
	Temp is NPecasEsq-1,
	Y is X+Multiplier+1,
	jogCommit(Temp, NPecasDir,T1,
		  Multiplier,Xs,Ys, 1).

/*Para o oponente, apenas interessam as pe�as que
sobram depois de inserir � direita da posi��o onde
se jogou.*/

/*N�o h� mais pe�as para incrementar.
Como o tabuleiro oponente � no sentido oposto, aqui
chamo recursivamente antes de incrementar as casas.*/
jogCommit(0, Pos, Multiplier, [X|Xs], [Y|Ys], 2):-
	!,
	T is Pos-1,
	jogCommit(0, T, Multiplier, Xs, Ys,2),
	Y is X+Multiplier.

jogCommit(_, _, _, [], [],_).

/*Havendo pe�as a jogar, coloca-as.*/
jogCommit(NPecas, Posicao, Multiplier,
	  [X|Xs], [Y|Ys],2):-

	T1 is Posicao-1,
	T2 is NPecas -1,
	Y is X+Multiplier+1,
	jogCommit(T2,T1,Multiplier,Xs,Ys, 2).


/*verSobras � um auxiliar para o tabuleiro do oponente.
Verifica se h� pe�as suficientes para passar do
tabuleiro do jogador ao do oponente.*/
verSobras2(NPecas,Posicao,Resultado):-
	Temp is NPecas mod 11,
	Temp =< 5,
	Temp3 is Temp-(5-Posicao),
	Temp3>=0,
	!,
	Resultado=Temp3.

verSobras2(NPecas,Posicao,Resultado):-
	Temp is NPecas mod 11,
	Temp > 5,
	Temp2 is Temp-(5-Posicao),
	Temp2>=0,
	!,
	Resultado=Temp2.

verSobras2(_,_,Resultado):-
	Resultado=0.

/*verEsq e verDir calculam as pe�as a incrementar
� esquerda e � direita da posi��o onde o jogador
jogou, respectivamente.*/
verEsq(T,Pos,Result):-
	T>6+(5-Pos),
	Result=T-6-(5-Pos).

verEsq(T,Pos,Result):-
	T=<6+(5-Pos),
	Result=0.

verDir(T,Pos,Result):-
	T=<(5-Pos),
	Result=T.

verDir(T,Pos,Result):-
	T>(5-Pos),

	Result=(5-Pos).



/*efectua uma jogada calculando os valores das pe�as
a incrementar (REsq, RDir, Resultado) nos tabuleiros
do jogador e do oponente.
Multiplier � o n�mero de voltas completas ao tabuleiro.*/

efectuarJogadaAux(TabJ1, TabJ2, NPecas,
		  TabJ1Result, TabJ2Result,
		  Posicao, Pontos, PontosNovos):-
	/*Posicao � a pos a saltar*/

	Multiplier is NPecas div 11,
	Temp is NPecas mod 11,
	verEsq(Temp, Posicao, REsq),
	verDir(Temp, Posicao, RDir),
	jogCommit(REsq,RDir, Posicao,
		  Multiplier, TabJ1, TabJ1Result,1),
	verSobras2(NPecas,Posicao,Resultado),
	jogCommit(Resultado, -1, Multiplier,
		  TabJ2, TabJ2ResultTemp,2),
	T is Resultado-1,
	captura(TabJ2ResultTemp, T, Pontos,
		TabJ2Result, PontosNovos).


/*encontra o n�mero total de pe�as a jogar na posi��o
dada pelo jogador.*/
efectuarJogadaAux([X|_], 0, X):-!.

efectuarJogadaAux([_|Xs], Posicao, NPecas):-
	T is Posicao-1,
	efectuarJogadaAux(Xs, T, NPecas).


/*Valida a jogada (validRules) e efectua-a se sucede.
0 corresponde ao jogador1, 1 ao jogador2; os
argumentos TabJ1 e TabJ2 s�o invertidos nas fun��es
seguintes para espelhar qual o tabuleiro de onde
a jogada partiu.
verifica ciclos infinitos primeiro.
Em seguida, obt�m o n�mero de pe�as na casa jogada.
Verifica se o oponente tem um tabuleiro de zeros, activando as regras
suplementares se tal se aplica.
Verifica se o jogador jogou num 0,
valida a regra da 1 semente (pro�bido jogar em casas com 1 semente tendo
casas com mais), e apenas validadas todas as condi��es anteriores,
efectua	a jogada.
Ap�s tal, valida novamente os ciclos infinitos, se o oponente tem um
tabuleiro de zeros, calcula as novas pontua��es, e verifica o jogador
seguinte atrav�s do obtido em IsEmpty2 - pode ser poss�vel ocorrer uma
captura que deixe o oponente com um tabuleiro de zeros, situa��o na qual
o seu turno � saltado.
o oitavo argumento dita quem est� a jogar: 0 o jogador 1, 1 o jogador 2.
*/
efectuarJogada(TabJ1, TabJ2, Jogada, TabJ1Res, TabJ2Res, PtJ1,
	       PtJ2, 0, PtJ1New, PtJ2New, NextPlay,FlagWrites):-

	validInfCycles(TabJ1,TabJ2),

	verPos(Jogada,Posicao),
	efectuarJogadaAux(TabJ1, Posicao, NPecas),
	verifyZeros(TabJ2, IsEmpty),
	validRulesSupp(IsEmpty, TabJ1,NPecas,
		       Posicao, PtJ1NewTemp,1,FlagWrites),
	validRules0(TabJ1, Posicao,FlagWrites),
	validRules(TabJ1, NPecas, IsEmpty,FlagWrites),
	efectuarJogadaAux(TabJ1, TabJ2, NPecas,
			  TabJ1Res, TabJ2Res,
			  Posicao,PtJ1,PtJ1NewTemp2),

	validInfCycles(TabJ1Res,TabJ2Res),

	verifyZeros(TabJ2Res,IsEmpty2),
	validRulesSupp(IsEmpty2, TabJ1Res,0,_, PtJ1NewTemp3,0,FlagWrites),
	PtJ1New is PtJ1NewTemp3+PtJ1NewTemp2+PtJ1NewTemp,

	verifyZeros(TabJ1Res,IsEmpty3),
	validRulesSupp(IsEmpty3, TabJ2Res,0,_, PtJ2NewTemp,0,FlagWrites),
	PtJ2New is PtJ2+PtJ2NewTemp,

	NextPlay is (1+IsEmpty2) mod 2.


efectuarJogada(TabJ1, TabJ2, Jogada, TabJ1Res, TabJ2Res,
	       PtJ1,PtJ2, 1,PtJ1New,PtJ2New, NextPlay,FlagWrites):-

	validInfCycles(TabJ1,TabJ2),
	verPos(Jogada,Posicao),
	efectuarJogadaAux(TabJ2, Posicao, NPecas),
	verifyZeros(TabJ1, IsEmpty),
	validRulesSupp(IsEmpty, TabJ2,NPecas,
		       Posicao, PtJ2NewTemp,1,FlagWrites),
	validRules0(TabJ2, Posicao,FlagWrites),
	validRules(TabJ2, NPecas, IsEmpty,FlagWrites),
	efectuarJogadaAux(TabJ2, TabJ1, NPecas,
			  TabJ2Res, TabJ1Res,
			  Posicao,PtJ2,PtJ2NewTemp2),

	validInfCycles(TabJ1Res,TabJ2Res),

	verifyZeros(TabJ1Res,IsEmpty2),
	validRulesSupp(IsEmpty2, TabJ2Res,0,_,
		       PtJ2NewTemp3,0,FlagWrites),
	PtJ2New is PtJ2NewTemp3+PtJ2NewTemp2+PtJ2NewTemp,

	verifyZeros(TabJ2Res,IsEmpty3),
	validRulesSupp(IsEmpty3, TabJ1Res,0,_,
		       PtJ1NewTemp,0,FlagWrites),
	PtJ1New is PtJ1+PtJ1NewTemp,



	NextPlay is IsEmpty2 mod 2.


/*Imprime no output os tabuleiros.*/

printTabuleiroAux2([]).

printTabuleiroAux2([X|Xs]):-
	printTabuleiroAux2(Xs),
	write(X),
	write('          ').

printTabuleiroAux([]):-!,
	write('\n').

printTabuleiroAux([X|Xs]):-
	write(X),
	write('          '),
	printTabuleiroAux(Xs).


printTabuleiro(TabJ1, TabJ2, Pts1, Pts2):-
	write('Pontua��o \nJogador 2: '),
	write(Pts2),
	write('\nJogador 1: '),
	write(Pts1),
	write('\n\nTabuleiro:\n'),
	write('_________________________________________________________________\n'),
	write('                           Jogador 2                             |\n'),
	write('_________________________________________________________________| \n'),
	write('     F    |     E    |     D    |     C    |     B    |    A     |\n'),
	write('__________|__________|__________|__________|__________|__________|\n '),
	printTabuleiroAux2(TabJ2),
	write('\n_________________________________________________________________\n '),
	printTabuleiroAux(TabJ1),
	write('_________________________________________________________________\n'),
	write('     A    |     B    |     C    |     D    |     E    |    F     |\n'),
	write('__________|__________|__________|__________|__________|__________|\n'),
	write('                           Jogador 1                             |\n'),
	write('_________________________________________________________________|\n'),
	write('\n\n').

/*L� jogadas. Este � um loop at� que uma letra v�lida seja inserida,
	e caso mesmo assim a jogada seja inv�lida, valid volta a chamar
	esta fun��o com o mesmo jogador. Termina com as pontua��es
	pre definidas (mais que 25 pontos). jogarVariante �
	equivalente, mudando o caso do gameOver chamado.*/
jogarHumano(_, _,Pts1,Pts2,_) :-
	gameOver(Pts1, Pts2),
	!.

jogarHumano(TabJ1, TabJ2,Pts1,Pts2,Jogador) :-
	Temp is Pts1+Pts2,
	Temp<48,
	printTabuleiro(TabJ1, TabJ2,Pts1,Pts2),

	write('Jogador '),
	T is Jogador + 1,
	write(T),
	write(' (a,b,c,d,e,f)? \n'),
	read(Jogada),
	valid(Jogada,TabJ1, TabJ2,Pts1,Pts2,Jogador),
	efectuarJogada(TabJ1, TabJ2, Jogada, ResultJ1,
		       ResultJ2, Pts1, Pts2,Jogador,
		       NewPts1, NewPts2,NextPlayer,0),
	jogarHumano(ResultJ1, ResultJ2,NewPts1,NewPts2,
	      NextPlayer).


jogarVariante(_, _,Pts1,Pts2,_) :-
	gameOverVariante(Pts1, Pts2),
	!.

jogarVariante(TabJ1, TabJ2,Pts1,Pts2,Jogador) :-
	Temp is Pts1+Pts2,
	Temp<48,
	printTabuleiro(TabJ1, TabJ2,Pts1,Pts2),

	write('Jogador '),
	T is Jogador + 1,
	write(T),
	write(' (a,b,c,d,e,f)? \n'),
	read(Jogada),
	validVariante(Jogada,TabJ1, TabJ2,Pts1,Pts2,Jogador),
	efectuarJogada(TabJ1, TabJ2, Jogada, ResultJ1,
		       ResultJ2, Pts1, Pts2,Jogador,
		       NewPts1, NewPts2,NextPlayer,0),
	jogarVariante(ResultJ1, ResultJ2,NewPts1,NewPts2,
	      NextPlayer).


/*Cria dois tabuleiros com 4 pe�as em cada
casa de tamanho Size. � aqui dado 6 pelo menu.*/
inicializarTabuleiro(_, _, 0):-!.

inicializarTabuleiro([Y|Ys], [Y|Ys], Size):-
	S is Size-1,
	Y=4,
	inicializarTabuleiro(Ys, Ys, S).



changelist([X|Xs],0,Value,[Z|Zs]):-
	length([X|Xs],Size),
	Size>0,
	Z=Value,
	Zs=Xs,!
	.

changelist([X|Xs],Position,Value,[Z|Zs]):-
	length([X|Xs],Size),
	Size>0,
	Position<Size,
	Z=X,
	P is Position-1,
	changelist(Xs,P,Value,Zs).

captura(Tabuleiro, PosicaoUltimaPe�a,
	PontosJogador, TabuleiroResultante,
	PontosResultantes):-
	Z=0,
	PosicaoUltimaPe�a>=0,
	PosicaoUltimaPe�a<6,
	captura_valida(Tabuleiro,
		       PosicaoUltimaPe�a,Z,Peca),
	!,
	Pontos is PontosJogador+Peca,
	changelist(Tabuleiro,PosicaoUltimaPe�a,0,T),
	PUP is PosicaoUltimaPe�a-1,
	captura(T,PUP,Pontos,TabuleiroResultante,
		PontosResultantes).

captura(Tabuleiro, _, PontosJogador,
	TabuleiroResultante, PontosResultantes):-
	TabuleiroResultante=Tabuleiro,
	PontosResultantes=PontosJogador.

captura_valida([X|_],Y,Z,Peca):-
	Y==Z,
	!,(
	X==2;X==3),Peca=X.

captura_valida([_|Xs],Y,Z,Peca):-
	Zs is Z+1,
	captura_valida(Xs,Y,Zs,Peca).

/*Primeiro turno. Sendo o jogador 1, � chamada a fun��o do jogador directamente.
se � o jogador 2, o AI joga sempre na posi��o b, de forma a saltar uma
simula��o desnecess�ria: N�o h� capturas na primeira jogada.*/
jogarAIFirstTurn(TabJ1, TabJ2,Pts1,Pts2,0) :-
	jogarAI(TabJ1,TabJ2,Pts1,Pts2,0).

jogarAIFirstTurn(TabJ1, TabJ2,Pts1,Pts2,1):-
	efectuarJogada(TabJ1, TabJ2, 'b',
		       TabJ1New, TabJ2New, Pts1,
		       Pts2, 1, Pt1New, Pt2New, _,1),
	jogarAI(TabJ1New, TabJ2New,Pt1New,Pt2New,0).
/*Similar aos jogarHumano e jogarVariante, verifica gameOvers*/
jogarAI(_,_,Pts1,Pts2,_):-
	gameOver(Pts1, Pts2),!.


jogarAI(TabJ1,TabJ2,Pts1,Pts2,1):-
	/*simula jogadas antes de efectuar. De resto, semelhante a
	jogarHumano e jogarVariante.*/
	Temp is Pts1+Pts2,
	Temp<48,
	printTabuleiro(TabJ1, TabJ2,Pts1,Pts2),
	simularAI(TabJ1, TabJ2, Pts1, Pts2, MelhorJogada),
	verPos(Posicao, MelhorJogada),
	efectuarJogada(TabJ1, TabJ2, Posicao, ResultJ1,
		       ResultJ2, Pts1, Pts2, 1, NewPts1,
		       NewPts2, NextPlayer,1),
	write('AI jogou na casa '),
	write(Posicao),
	write('\n\n'),
	jogarAI(ResultJ1, ResultJ2, NewPts1, NewPts2, NextPlayer).

jogarAI(TabJ1,TabJ2,Pts1,Pts2,0):-
	Temp is Pts1+Pts2,
	/*player normal. Igual a jogarHumano.*/
	Temp<48,
	printTabuleiro(TabJ1, TabJ2,Pts1,Pts2),

	write('Jogar (a,b,c,d,e,f)? \n'),
	read(Jogada),
	validAI(Jogada,TabJ1, TabJ2,Pts1,Pts2,0),
	efectuarJogada(TabJ1, TabJ2, Jogada, ResultJ1,
		       ResultJ2, Pts1, Pts2,0,
		       NewPts1, NewPts2,NextPlayer,0),
	jogarAI(ResultJ1, ResultJ2,NewPts1,NewPts2,
	      NextPlayer).

/*simularAI e simPlay s�o as fun��es que calculam a jogada do computador.
simularAI retorna em MelhorJogada a jogada que o computador vai
efectuar.
*/


/*simPlay aceita a primeira jogada v�lida, qualquer que ela seja,
	na ordem a>b>c>d>e>f. O quarto argumento � a primeira
	jogada em que existe captura, o quinto a primeira jogada v�lida.
        Caso qualquer delas n�o exitsa, � devolvido -1 no argumento
	que lhe corresponde.
*/
simPlay(NewPts2, Pts2, 5, 5,-1):-
	NewPts2>Pts2,
	!. /*	ult jogada � captura, e n�o ha jogadas v�lidas sem captura.*/

simPlay(Pts2, Pts2, 5, 5,-1):-
	!. /*	ult jogada � a �nica v�lida. dado que as capturas t�m prioridade,
	apesar de 5 ser enviado como argumento na captura, � a jogada certa,
	mesmo n�o existindo capturas.*/


simPlay(_, _, 5, _,-1):-
	!. /*h� uma captura dispon�vel que n�o a �ltima casa 'f'.*/


simPlay(-1,_,5,-1,_):-
	!.
          /*n�o h� nenhuma captura dispon�vel, mas h� uma jogada v�lida que n�o a
	  ultima casa 'f'*/



simPlay(-1,_,_,_,_):-
	!. /*valida jogadas inv�lidas, permitindo ao ciclo seguir.
N�o pode aqui chegar com a �ltima jogada devido aos cuts de cima.*/

simPlay(NewPts, Pts2,5,5,-1):-
	NewPts>Pts2,!. /*ultima jogada � captura. N�o h� jogadas v�lidas
	sem captura.*/


simPlay(NewPts, Pts2, Jogada, Jogada,_):-
	NewPts>Pts2, /*a primeira jogada que causa captura � devolvida.*/
	!.


simPlay(NewPts, Pts2,_,_,_):-
	NewPts>Pts2,!. /*valida capturas ap�s j� ter sido escolhida uma captura
	pr�via.*/


simPlay(Pts2, Pts2, 5, -1,_):-
	!. /*�ltima casa � uma jogada v�lida sem captura*/



simPlay(Pts2, Pts2, Jogada, _,Jogada):-
	!. /*valida a primeira jogada sem captura v�lida.*/



simPlay(NewPts, Pts2,_,_,_):-
	NewPts=Pts2,!. /*valida jogadas v�lidas ap�s j� ter sido escolhida uma
	captura	pr�via.*/


/*simula a jogada. se efectuarJogada falha, devolve
NewPts2 = -1 devido ao segundo predicado.*/
simularAI(TabJ1, TabJ2, Pts1, Pts2, MelhorJTemp,
	  _,_,NewPts2):-

	verPos(Jogada, MelhorJTemp),
	efectuarJogada(TabJ1, TabJ2, Jogada, _,
	       _, Pts1, Pts2,1,
	       _, NewPts2,_,1),
	!.


simularAI(_,_,_,_,_,_,_,-1):-
	!.


simularAI(TabJ1, TabJ2, Pts1, Pts2, MelhorJTemp,
	  MelhorJT2,MelhorNaoHaCaptura):-
	/*come�a na posi��o 'a'.*/
	MelhorJTemp>(-1),
	T is MelhorJTemp-1,
	simularAI(TabJ1, TabJ2, Pts1, Pts2, T,
		  MelhorJT2,MelhorNaoHaCaptura),
/* Se NewPts2 for -1, a jogada foi inv�lida.
se NewPts2=Pts2, a jogada n�o causou captura, mas � v�lida.
se NewPts2 � maior que Pts2, h� captura.
Estes casos s�o validados por simPlay. simularAI/8 apenas
serve para obter os NewPts2.
*/
	simularAI(TabJ1, TabJ2, Pts1, Pts2, MelhorJTemp,
		  MelhorJT2,MelhorNaoHaCaptura,NewPts2),

	simPlay(NewPts2, Pts2, MelhorJTemp,
		MelhorJT2,MelhorNaoHaCaptura),
        !.
/*serve para terminar a recursividade que procura a primeira casa
do tabuleiro, posi��o 'a'.*/
simularAI(_, _, _, _, -1,_,_):-!.



simularAI(_,_,_,_,T,_,_):-
	verPos(_,T).


simularAI(TabJ1, TabJ2, Pts1, Pts2, 5, Temp):-
	/*dado existir a possibilidade de existir captura (Temp2) ou n�o
	existir qualquer captura, apenas uma jogada v�lida regular (Temp3),
	chama um novo auxiliar de aridade 7.*/
	simularAI(TabJ1, TabJ2, Pts1, Pts2, 5,
		  Temp2, Temp3),
	(
	(   (Temp2 > Temp3 ; Temp2 < Temp3),/*d� prioridade � captura*/
	    Temp2>(-1),!,Temp is Temp2);
	(   Temp3 > Temp2,!,Temp is Temp3)
	)
	.

/*chama auxiliar de aridade 6*/
simularAI(TabJ1, TabJ2, Pts1, Pts2, MelhorJogada):-
	simularAI(TabJ1, TabJ2, Pts1, Pts2, 5, Temp),
	MelhorJogada is Temp.


go:-

	write('\n'),
	write('Ouri UE \n \n'),
	write('0 - Sair \n'),
	write('1 - Humano vs Humano \n'),
	write('2 - Humano vs Computador \n'),
	write('3 - Ouri especial \n'),
	read(X),(
	(X==0,
	 !,
	 sair);
	(X==1,
	 !,
	 inicializarTabuleiro(Jogador1, Jogador2, 6),
	 jogarHumano(Jogador1, Jogador2,0,0,0));
	(X==2,
	 !,
	inicializarTabuleiro(Jogador1, Jogador2, 6),
	write('\n1 - Primeiro Jogador\n'),
	write('\n2 - Segundo Jogador\n'),
	read(Y),
	(
	 (Y==1,!,jogarAIFirstTurn(Jogador1, Jogador2, 0,0,0));
	 (Y==2,!,jogarAIFirstTurn(Jogador1, Jogador2, 0,0,1))
	)
	);
	(X==3,
	 !,
	 inicializarTabuleiro(Jogador1, Jogador2, 6),
	 write('Ganha quem tem menos pontos!\n'),
	 jogarVariante(Jogador1, Jogador2, 0,0,0));
	(number(X),(X>3;X<0),write('Input inv�lido.\n'),go)).

