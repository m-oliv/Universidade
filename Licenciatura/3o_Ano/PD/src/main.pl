
/*Verifica se o jogador usou uma letra válida (a,b,c,d,e,f).*/
valid(X,_,_,_,_,_):-
	(X=='a'; X=='b';X=='c';X=='d';X=='e';X=='f').

/*Qualquer situação em que uma peça seja mal jogada, esta função é chamada.
*/

valid(_,TabJ1,TabJ2,Pontos1,Pontos2,Jogador):-
	Temp is Pontos1+Pontos2,
	Temp<48,
	write('\nJogada inválida!\n\n'),
	jogarHumano(TabJ1, TabJ2,Pontos1,Pontos2,Jogador).
validVariante(X,_,_,_,_,_):-
	(X=='a'; X=='b';X=='c';X=='d';X=='e';X=='f').

validVariante(_,TabJ1,TabJ2,Pontos1,Pontos2,Jogador):-
	Temp is Pontos1+Pontos2,
	Temp<48,
	write('\nJogada inválida!\n\n'),
	jogarVariante(TabJ1, TabJ2,Pontos1,Pontos2,Jogador).

validAI(X,_,_,_,_,_):-
	(X=='a'; X=='b';X=='c';X=='d';X=='e';X=='f').

validAI(_,TabJ1,TabJ2,Pontos1,Pontos2,Jogador):-
	Temp is Pontos1+Pontos2,
	Temp<48,
	write('\nJogada inválida!\n\n'),
	jogarAI(TabJ1, TabJ2,Pontos1,Pontos2,Jogador).
/*
As regras do ouri são aplicadas nestas funções.
validInfCycles trata do caso especial do tabuleiro de ciclos infinito,
em que nenhum dos jogadores poderia terminar. Dado que o jogo termina
aos 25 ou mais pontos, esta situação causa um empate automático.

	*/

validInfCycles([1,0,0,0,0,0],[1,0,0,0,0,0]):-
	!,
	gameOver(24,24).
/*Qualquer outro tabuleiro valida a regra.*/
validInfCycles(_,_).


/* A função validRules trata da regra do ouri da "casa com 1 semente".
Esta regra dita que é proíbido jogar em casas com uma semente
caso existam casas com mais.
É, no entanto, imperativo o uso de duas flags.
Flag activa ou desactiva a regra, sendo esta regra automaticamente
validada com a flag a 1. Tal torna-se necessário devido à situação de
bloqueio:
000000
400101
O jogador de baixo é o jogador que efectua a jogada seguinte.
Por esta regra, o jogador era forçado a jogar 'a',
no entanto, tento o oponente um tabuleiro de zeros, esta regra terá de
ser ignorada. Flag colmata este problema.

FlagWrites determina se é imprimido no output a mensagem com o erro,
sendo esta uma necessidade devido à simulação do AI também usar esta
função.


*/

/*Se tenta jogar numa casa com 1 semente
	quando ainda existem casas com mais sementes*/

validRules([],1,_,Y,FlagWrites):-
	Y>1,
	!,
	(
	(   FlagWrites==0,!,
	write('Há casas com mais de 1 semente!')
	)
	),
	fail.
/*chegados ao fim da lista e com Y = 1 (zero implicava não existirem
jogadas, nessa situação nem é possível chegar a esta função. Outro valor
seria tratado na função acima, que tem um cut vermelho para impedir
que ambas sejam lidas), a regra valida a jogada.*/

validRules([],_,_,_,_).

/*Y guarda a casa do tabuleiro com maior número de sementes.
Se esta for 1 ou 0, é garantido não existirem casas com mais
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
/*aceita automáticamente a regra com flag a 1*/

validRules(_,_, Flag,_):-
	Flag==1.

/*Regra que proíbe jogar em casas sem peças.
FlagWrites a 1 implica que o write é ignorado,
necessário devido à possibilidade de esta ser uma simulação do AI.*/
validRules0([X|_],0,FlagWrites):-
	X=0,
	!,
	(
	(   FlagWrites==0,!,write('Jogou num zero!')
	)
	),
	fail.
/*aceita jogadas em casas com mais de 0 peças*/
validRules0([X|_],0,_):-
	X>0,
	!.


/*procura a posição onde se jogou*/
validRules0([_|Xs], Posicao,FlagWrites):-
	Temp is Posicao - 1,
	validRules0(Xs, Temp,FlagWrites).


/*ValidRulesSupp/7 e as suas auxiliares, SumPontos, ValidRulesSupp/3 e validate,
	são chamados para tratar das regras suplementares do Ouri.
	Verifica-se primeiro se existe jogada possível.
Não existindo, soma à pontuação do jogador os pontos de todas as peças
no seu campo. Existindo, verifica se a dada é possível. Sabemos que o
oponente tem um tabuleiro de zeros neste ponto.
*/


/*Soma todos os valores do tabuleiro*/
sumPontos([A,B,C,D,E,F],Result):-
	Result is A+B+C+D+E+F.


validate(NPecas,Pos,FlagWrites):-
	NPecas=<5-Pos,
	(
	(   FlagWrites==0,!,write('Há uma jogada disponível
	para o jogo prosseguir, mas não a seleccionou.\n'))),
	fail.


validate(NPecas, Pos,_):-
	NPecas>5-Pos.

/*validRulesSupp/3
Verifica a existência de jogada válida.
6 é o número de peças necessário ter na casa 'a' para
atingir o tabuleiro oponente, sendo dado como argumento.*/

/*1 há jogada disponível. 0 não há.*/
validRulesSupp(-1,_,1):-!.

validRulesSupp([],_,0):-!.

validRulesSupp([X|_], Pos,  IsAv):-
	X>=Pos, /*jogada disponível!*/
	!, /*força o predicado validRulesSupp(-1,_,1). dando 1 ao valor IsAv.*/
	validRulesSupp(-1,_,IsAv).

/*Decrementa o segundo argumento, já que em 'b' são
5 as peças necessárias para chegar ao tabuleiro oponente,
4 em 'c', e assim sucessivamente.*/
validRulesSupp([X|Xs], Pos, IsAv):-
	X=<Pos,
	Temp is Pos-1,
	validRulesSupp(Xs, Temp, IsAv).



/*com o primeiro argumento a 0, o tabuleiro oponente não
está vazio, e a regra é desnecessária, aceitando automáticamente.*/
validRulesSupp(0, _,_, _, 0,_,_):-!.
/*Sem jogada disponível e o tabuleiro oponente vazio,
os pontos ficam do jogador actual.*/
validRulesSupp(1, Tab,_, _, ResultPts,_,_):-
	validRulesSupp(Tab,6, IsAvailable),
	IsAvailable==0,
	!,
	sumPontos(Tab, ResultPts).
/*É indicado que existe uma jogada possível para o jogo não terminar de imediato,
antes de esta ser feita, por esta função*/
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
	 write('Há uma jogada disponível para o jogo	prosseguir\n')
	)
	)
	.

/*O jogador escolheu a jogada errada nesta função. É dado erro através de
validade/3. */
validRulesSupp(1, Tab,NPecas, Posicao, ResultPts,1,FlagWrites):-

	validRulesSupp(Tab,6, IsAvailable),
	IsAvailable>0,
	!,
	validate(NPecas, Posicao,FlagWrites),
	ResultPts=0.




/*verifica se o oponente tem um tabuleiro
só com zeros. caso tenha, activa as regras suplement.
IsEmpty é 1 em tal situação.*/


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

/*Mesmo que gameOver, mas ganha quem tem menor pontuação.*/

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

/*Verifica a posição da lista que corresponde à jogada feita*/
verPos(a, 0).
verPos(b, 1).
verPos(c, 2).
verPos(d, 3).
verPos(e, 4).
verPos(f, 5).

/*jogCommit efectua a jogada. Quando o último argumento
é 1, a jogada é a do jogador que está a jogar
actualmente; quando o último argumento é 2, é a do
oponente. Qual é qual vai depender dos argumentos
(se é dado tab1,tab2 ou tab2,tab1.
									    */

/*jogCommit para o player 1
 o primeiro argumento é o número de peças à esquerda
 que vai ser incrementado. Esta esquerda significa
 "antes da posição onde o jogador jogou", ou seja,
 só é diferente de 0 se o número de peças for grande o
 suficiente para partir do sítio onde se joga,
 atravessar o tabuleiro oponente e voltar a entrar
 no tabuleiro do jogador.

 Exemplo:

 000000
 000080

 Jogando em 8, NPecasEsq será igual a 1, já que
 111111
 1000X1

 No tabuleiro acima, X foi o local de onde se começou
 a jogada (após efectuada, X=0). Há 1 casa no tabuleiro
 de baixo à esquerda de X que recebeu uma peça.

 O segundo argumento é o número de peças à direita. É
 a mesma lógica das da esquerda mas conta o nº à
 direita de X.

 Estes 2 argumentos são calculados antes de chamar
 esta função!

 O terceiro argumento é a posição onde o jogador
 jogou na lista.

 O quarto (Multiplier) é para o caso de uma jogada dar
 uma volta completa ao campo, em que todas as casas
 são incrementadas 1 peça. Quanto mais voltas completas
 maior o Multiplier.

 o 5º argumento é o tabuleiro recebido do jogador.
 o 6º argumento é o reultado a devolver.
 o último é para distinguir o jogador que está a
 jogar (1) do adversário (2).

 */
 /*Aqui toda a lista foi corrida e não há mais peças
 a inserir.*/
jogCommit(0,0, _, _, [], [],1).

/*Aqui a lista ainda não foi toda corrida, mas como
temos de devolver Y|Ys como o resultado perfeito de
inserir as peças, continuamos a correr a lista, mesmo
sem mais peças a inserir. Só altera os valores
se houve uma volta completa (Multiplier!=0).*/
jogCommit(0,0, -1, Multiplier, [X|Xs], [Y|Ys],1):-
	Y is X+Multiplier,
	jogCommit(0,0,-1,Multiplier,Xs,Ys,1).

/*A função chega aqui quando já leu as peças à
esquerda e está na posição onde o jogador jogou.
interessa que nessa posição fiquem 0 peças
e que se continue a inserir as peças à direita desta.
o 3º argumento a -1 força esta função a ir para as de
cima depois de ficar o Y a 0.*/
jogCommit(0,NPecasDir, 0,
	  Multiplier, [_|Xs], [Y|Ys],1):-
	Y is 0,
	Temp is NPecasDir,
	jogCommit(0,Temp, -1, Multiplier, Xs, Ys,1).

/*Insere as peças à direita da posição onde se jogou.*/
jogCommit(0,NPecasDir, -1,Multiplier,
	  [X|Xs],[Y|Ys],1):-
	Temp is NPecasDir-1,
	Y is X+Multiplier+1,
	jogCommit(0,Temp, -1,Multiplier,Xs,Ys,1)

	.
/*Procura a posição onde se jogou. Só muda o valor
da casa se Multiplier!=0*/
jogCommit(0, NPecasDir, Posicao, Multiplier,
	  [X|Xs], [Y|Ys],1):-
	T1 is Posicao-1,
	Y is X+Multiplier,
	jogCommit(0, NPecasDir,T1,
		  Multiplier,Xs,Ys, 1).


/*Vai adicionando peças nas posições iniciais da lista
se se verificou que depois da inserção de peças à
direita e no campo adversário ainda há sobras.
Este valor é calculado antes da chamada da função.*/
jogCommit(NPecasEsq, NPecasDir, Posicao,
	  Multiplier, [X|Xs], [Y|Ys],1):-
	T1 is Posicao-1,
	Temp is NPecasEsq-1,
	Y is X+Multiplier+1,
	jogCommit(Temp, NPecasDir,T1,
		  Multiplier,Xs,Ys, 1).

/*Para o oponente, apenas interessam as peças que
sobram depois de inserir à direita da posição onde
se jogou.*/

/*Não há mais peças para incrementar.
Como o tabuleiro oponente é no sentido oposto, aqui
chamo recursivamente antes de incrementar as casas.*/
jogCommit(0, Pos, Multiplier, [X|Xs], [Y|Ys], 2):-
	!,
	T is Pos-1,
	jogCommit(0, T, Multiplier, Xs, Ys,2),
	Y is X+Multiplier.

jogCommit(_, _, _, [], [],_).

/*Havendo peças a jogar, coloca-as.*/
jogCommit(NPecas, Posicao, Multiplier,
	  [X|Xs], [Y|Ys],2):-

	T1 is Posicao-1,
	T2 is NPecas -1,
	Y is X+Multiplier+1,
	jogCommit(T2,T1,Multiplier,Xs,Ys, 2).


/*verSobras é um auxiliar para o tabuleiro do oponente.
Verifica se há peças suficientes para passar do
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

/*verEsq e verDir calculam as peças a incrementar
à esquerda e à direita da posição onde o jogador
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



/*efectua uma jogada calculando os valores das peças
a incrementar (REsq, RDir, Resultado) nos tabuleiros
do jogador e do oponente.
Multiplier é o número de voltas completas ao tabuleiro.*/

efectuarJogadaAux(TabJ1, TabJ2, NPecas,
		  TabJ1Result, TabJ2Result,
		  Posicao, Pontos, PontosNovos):-
	/*Posicao é a pos a saltar*/

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


/*encontra o número total de peças a jogar na posição
dada pelo jogador.*/
efectuarJogadaAux([X|_], 0, X):-!.

efectuarJogadaAux([_|Xs], Posicao, NPecas):-
	T is Posicao-1,
	efectuarJogadaAux(Xs, T, NPecas).


/*Valida a jogada (validRules) e efectua-a se sucede.
0 corresponde ao jogador1, 1 ao jogador2; os
argumentos TabJ1 e TabJ2 são invertidos nas funções
seguintes para espelhar qual o tabuleiro de onde
a jogada partiu.
verifica ciclos infinitos primeiro.
Em seguida, obtém o número de peças na casa jogada.
Verifica se o oponente tem um tabuleiro de zeros, activando as regras
suplementares se tal se aplica.
Verifica se o jogador jogou num 0,
valida a regra da 1 semente (proíbido jogar em casas com 1 semente tendo
casas com mais), e apenas validadas todas as condições anteriores,
efectua	a jogada.
Após tal, valida novamente os ciclos infinitos, se o oponente tem um
tabuleiro de zeros, calcula as novas pontuações, e verifica o jogador
seguinte através do obtido em IsEmpty2 - pode ser possível ocorrer uma
captura que deixe o oponente com um tabuleiro de zeros, situação na qual
o seu turno é saltado.
o oitavo argumento dita quem está a jogar: 0 o jogador 1, 1 o jogador 2.
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
	write('Pontuação \nJogador 2: '),
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

/*Lê jogadas. Este é um loop até que uma letra válida seja inserida,
	e caso mesmo assim a jogada seja inválida, valid volta a chamar
	esta função com o mesmo jogador. Termina com as pontuações
	pre definidas (mais que 25 pontos). jogarVariante é
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


/*Cria dois tabuleiros com 4 peças em cada
casa de tamanho Size. É aqui dado 6 pelo menu.*/
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

captura(Tabuleiro, PosicaoUltimaPeça,
	PontosJogador, TabuleiroResultante,
	PontosResultantes):-
	Z=0,
	PosicaoUltimaPeça>=0,
	PosicaoUltimaPeça<6,
	captura_valida(Tabuleiro,
		       PosicaoUltimaPeça,Z,Peca),
	!,
	Pontos is PontosJogador+Peca,
	changelist(Tabuleiro,PosicaoUltimaPeça,0,T),
	PUP is PosicaoUltimaPeça-1,
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

/*Primeiro turno. Sendo o jogador 1, é chamada a função do jogador directamente.
se é o jogador 2, o AI joga sempre na posição b, de forma a saltar uma
simulação desnecessária: Não há capturas na primeira jogada.*/
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

/*simularAI e simPlay são as funções que calculam a jogada do computador.
simularAI retorna em MelhorJogada a jogada que o computador vai
efectuar.
*/


/*simPlay aceita a primeira jogada válida, qualquer que ela seja,
	na ordem a>b>c>d>e>f. O quarto argumento é a primeira
	jogada em que existe captura, o quinto a primeira jogada válida.
        Caso qualquer delas não exitsa, é devolvido -1 no argumento
	que lhe corresponde.
*/
simPlay(NewPts2, Pts2, 5, 5,-1):-
	NewPts2>Pts2,
	!. /*	ult jogada é captura, e não ha jogadas válidas sem captura.*/

simPlay(Pts2, Pts2, 5, 5,-1):-
	!. /*	ult jogada é a única válida. dado que as capturas têm prioridade,
	apesar de 5 ser enviado como argumento na captura, é a jogada certa,
	mesmo não existindo capturas.*/


simPlay(_, _, 5, _,-1):-
	!. /*há uma captura disponível que não a última casa 'f'.*/


simPlay(-1,_,5,-1,_):-
	!.
          /*não há nenhuma captura disponível, mas há uma jogada válida que não a
	  ultima casa 'f'*/



simPlay(-1,_,_,_,_):-
	!. /*valida jogadas inválidas, permitindo ao ciclo seguir.
Não pode aqui chegar com a última jogada devido aos cuts de cima.*/

simPlay(NewPts, Pts2,5,5,-1):-
	NewPts>Pts2,!. /*ultima jogada é captura. Não há jogadas válidas
	sem captura.*/


simPlay(NewPts, Pts2, Jogada, Jogada,_):-
	NewPts>Pts2, /*a primeira jogada que causa captura é devolvida.*/
	!.


simPlay(NewPts, Pts2,_,_,_):-
	NewPts>Pts2,!. /*valida capturas após já ter sido escolhida uma captura
	prévia.*/


simPlay(Pts2, Pts2, 5, -1,_):-
	!. /*última casa é uma jogada válida sem captura*/



simPlay(Pts2, Pts2, Jogada, _,Jogada):-
	!. /*valida a primeira jogada sem captura válida.*/



simPlay(NewPts, Pts2,_,_,_):-
	NewPts=Pts2,!. /*valida jogadas válidas após já ter sido escolhida uma
	captura	prévia.*/


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
	/*começa na posição 'a'.*/
	MelhorJTemp>(-1),
	T is MelhorJTemp-1,
	simularAI(TabJ1, TabJ2, Pts1, Pts2, T,
		  MelhorJT2,MelhorNaoHaCaptura),
/* Se NewPts2 for -1, a jogada foi inválida.
se NewPts2=Pts2, a jogada não causou captura, mas é válida.
se NewPts2 é maior que Pts2, há captura.
Estes casos são validados por simPlay. simularAI/8 apenas
serve para obter os NewPts2.
*/
	simularAI(TabJ1, TabJ2, Pts1, Pts2, MelhorJTemp,
		  MelhorJT2,MelhorNaoHaCaptura,NewPts2),

	simPlay(NewPts2, Pts2, MelhorJTemp,
		MelhorJT2,MelhorNaoHaCaptura),
        !.
/*serve para terminar a recursividade que procura a primeira casa
do tabuleiro, posição 'a'.*/
simularAI(_, _, _, _, -1,_,_):-!.



simularAI(_,_,_,_,T,_,_):-
	verPos(_,T).


simularAI(TabJ1, TabJ2, Pts1, Pts2, 5, Temp):-
	/*dado existir a possibilidade de existir captura (Temp2) ou não
	existir qualquer captura, apenas uma jogada válida regular (Temp3),
	chama um novo auxiliar de aridade 7.*/
	simularAI(TabJ1, TabJ2, Pts1, Pts2, 5,
		  Temp2, Temp3),
	(
	(   (Temp2 > Temp3 ; Temp2 < Temp3),/*dá prioridade à captura*/
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
	(number(X),(X>3;X<0),write('Input inválido.\n'),go)).

