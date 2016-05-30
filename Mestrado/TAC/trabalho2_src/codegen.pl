:-consult('codegenregaux.pl').
:-consult('codegenLS.pl').
:-consult('codegenarit.pl').

:-redefine_system_predicate(call(_,_)).

% Limpar variaveis globais para guardar valores auxiliares.
:-retractall(localAloc(_,_,_)).
:-retractall(actRec(_,_,_,_,_)).
:-retractall(fpOffL(_)).
:-retractall(fpOffA(_)).
:-retractall(currentReg(_)).
:-retractall(tempToReg(_,_)).
:-retractall(regInUse(_)).
:-retractall(fArgs(_)).

% Criar novas variaveis globais com valores auxiliares predefinidos.
:-asserta(fpOffL(-4)).
:-asserta(fpOffA(0)).
:-asserta(currentReg(0)).
:-asserta(regInUse([])).
:-asserta(fArgs([])).

%---- Predicados que permitem ler a IR a partir do std input
%---- main -> inserir input normal (IR) e executar
%---- exit -> sair do main.
main:-
  write('\n'),
  write('\tInsira a Rep. Intermedia (IR):\n'),
  read(X),
  call(X),
  main.

exit:-
  abort.

%----- Predicados que geram o codigo dos Prints
i_print(X):-
  tempToReg(X,C),               % Obter registo temporario correspondente.
  decrementRegC(1,_),           % Decrementar o num. de registos.
  format("\ti_print$\t$~w\n",[C]).% Gerar codigo da instrucao print.

/* Este predicado funciona do mesmo modo que o anterior.*/
b_print(X):-
  tempToReg(X,C),               % Obter registo temporario correspondente.
  decrementRegC(1,_),           % Decrementar o num. de registos.
  format("\tb_print$\t$~w\n",[C]).% Gerar codigo da instrucao print.


%---- Predicados que geram o codigo dos Jumps (cjump e jump)
jump(L):-
  format("\tj\t~w\n",[L]),      % Gerar codigo da instrucao jump.
  resetReg.                     % Reset dos registos (num. registos e variaveis globais correspondentes).

cjump(C,LT,LF):-
  tempToReg(C,A),               % Obter registo temporario correspondente.
  decrementRegC(1,_),           % Decrementar o num. de registos.
  format("\tbeq\t$~w, $0, ~w\n",[A,LF]),% Gerar codigo da instrucao cjump.
  jump(LT),                     % Gerar codigo do jump para a label correta.
  resetReg.                     % Reset dos registos  (num registos e variaveis globais correspondentes).

%----- Predicado que gera o codigo das Labels
label(L):-
  format("~w:",[L]),            % Gerar codigo da label.
  resetReg.                     % Reset dos registos  (num registos e variaveis globais correspondentes).

%----- Predicados que geram o codigo das Function Calls e dos Procedure Calls
i_call(X,Y,Z):-
  currentReg(A),                % Obter o num. de registos .
  getRegArgs(Z),                % Verificar quais os registos  a que correspondem os argumentos da funcao.
  fArgs(W),                     % Obter os registos  a que correspondem os argumentos da funcao.
  getReg(A,W,K),                % Obter registos atuais (sem os argumentos da funcao).
  saveRegInUse(K),              % Gerar codigo para guardar valor dos registos atuais.
  reverse(W,ARG),               % Inverter a ordem da lista dos argumentos (para que o push seja feito na ordem certa).
  pushArgs(ARG),                % Gerar codigo correspondente ao push dos argumentos da funcao.
  format("\tjal\t~w\n",[Y]),      % Gerar codigo para a instrucao jal.
  length(Z,N),                  % Calcular num. de argumentos da funcao.
  decrementRegC(N,C),           % Subtrair num. de argumentos da funcao ao num. de registos .
  format(atom(T),"t~w",[C]),      % Obter novo registo temporario.
  asserta(tempToReg(X,T)),       % Guardar o registo temporario.
  incrementRegC(1,_),           % Incrementar o num. de registos .
  format("\tor\t$~w, $0, $v0\n", [T]), % Gerar codigo para armazenar o valor retornado pela funcao.
  reverse(K,M),                 % Inverter a ordem da lista dos registos atuais.
  restoreRegInUse(M),           % Gerar codigo para restaurar os registos atuais.
  clearInfoCall.                % Reset a informacao auxiliar usada nas function calls.

/* O predicado call funciona de um modo identico ao do predicado i_call, não sendo
necessario gerar codigo para armazenar o valor de retorno
(os procedimentos nao possuem valor de retorno).*/

call(X,Y):-
  currentReg(A),                % Obter o num. de registos .
  getRegArgs(Y),                % Verificar quais os registos  a que correspondem os argumentos da funcao.
  fArgs(W),                     % Obter os registos  a que correspondem os argumentos da funcao.
  getReg(A,W,K),                % Obter registos atuais (sem os argumentos da funcao).
  saveRegInUse(K),              % Gerar codigo para guardar valor dos registos atuais.
  reverse(W,ARG),               % Inverter a ordem da lista dos argumentos (para que o push seja feito na ordem certa).
  pushArgs(ARG),                % Gerar codigo correspondente ao push dos argumentos da funcao.
  length(Y,N),                  % Calcular num. de argumentos da funcao.
  decrementRegC(N,_),           % Subtrair num. de argumentos da funcao ao num. de registos .
  format("\tjal\t~w\n",[X]),      % Gerar codigo para a instrucao jal.
  reverse(K,M),                 % Inverter a ordem da lista dos registos atuais.
  restoreRegInUse(M),           % Gerar codigo para restaurar os registos atuais.
  clearInfoCall.                % Reset a informacao auxiliar usada nas function calls.

%----- Predicados que geram o codigo para os retornos das funcoes
i_return(X):-
  tempToReg(X,C),               % Obter registo temporario correspondente.
  decrementRegC(1,_),           % Decrementar o num. de registos .
  format("\tor\t$v0, $0, $~w\n",[C]),% Gerar o codigo para a instrucao correspondente ao retorno da funcao.
  resetReg.                     % Reset dos registos  (num registos e variaveis globais correspondentes).

/* Este predicado funciona do mesmo modo que o anterior.*/
b_return(X):-
  tempToReg(X,C),               % Obter registo temporario correspondente.
  decrementRegC(1,_),           % Decrementar o num. de registos .
  format("\tor\t$v0, $0, $~w\n",[C]),% Gerar o codigo para a instrucao correspondente ao retorno da funcao.
  resetReg.                     % Reset dos registos  (num registos e variaveis globais correspondentes).

%----- Predicados que geram o codigo para as funcoes
function(X,Y):-
  asserta(currentF(X)),         % Guardar o nome da funcao atual.
  functionHeader(X),            % Gerar o codigo do cabecalho da funcao.
  localAloc(Z,W,X),             % Obter o espaco alocado para variaveis locais e a libertar no prologo da funcao.
  prologue(Z),                  % Gerar codigo do prologo.
  listInstr(Y),                 % Processar as instrucoes da funcao.
  epilogue(W),                  % Gerar o codigo do epilogo.
  resetAll(X),                  % Reset aos auxiliares.
  resetReg.                     % Reset aos registos .


%-- Predicado que gera o codigo do cabecalho da funcao.
functionHeader(X):-
  write('\n\t.data\n'),
  generateMacros,               % Gerar codigo das macros (prints).
  write('\n\t.text\n'),
  (X=='main',
  write('\t.globl main\n'),     % Acrescentar esta linha, se o nome da funcao for 'main'.
  format("~w:\n",[X]);
  format("~w:\n",[X])).           % Escrever a label correspondete ao nome da funcao.


%----- Predicado que gera o codigo do prologo

/* O argumento deste predicado corresponde
ao espaco a reservar para as variaveis locais do programa. */
prologue(X):-
  write('\tsw\t$fp, -4($sp)\n'),
  write('\taddiu\t$fp, $sp, -4\n'),
  write('\tsw\t$ra, -4($fp)\n'),
  format("\taddiu\t$sp, $fp, -~w\n",[X]).


%----- Predicado que gera o codigo do epilogo

/* O argumento deste predicado corresponde
ao espaco a libertar quando o programa termina. */

epilogue(X):-
  write('\tlw\t$ra, -4($fp)\n'),
  format("\taddiu\t$sp, $fp, ~w\n",[X]),
  write('\tlw\t$fp, 0($fp)\n'),
  write('\tjr\t$ra\n').


%------ Predicado que gera o registo de ativacao de funcoes e procedimentos

id(N,_,_,FA,LV):-
  buildRec(FA,arg,N),         % Guardar os argumentos no registo de ativacao
  buildRec(LV,local,N),       % Guardar as variaveis locais no registo de ativacao
  length(LV,X),               % Obter o numero de variaveis locais da funcao
  length(FA,Y),               % Obter o numero de argumentos da funcao
  alocSpace(X,Y,N).           % Registar o espaco a alocar para variaveis locais e a libertar no prologo.


%----- Predicados que geram o codigo para as variaveis globais do programa.

/*Se a variavel global não tiver valor atribuido.*/
id(X,var,int):-
  write('\t.data\n'),         % Gerar diretiva .data
  format("~w:\t.space 4\n",[X]).% Gerar codigo.

id(X,var,bool):-
  write('\t.data\n'),         % Gerar diretiva .data
  format("~w:\t.space 4\n",[X]).% Gerar codigo.

/* Se a variavel global tiver valor atribuido.*/

id(X,var,int,Y):-
  write('\t.data\n'),             % Gerar diretiva .data
  format("~w:\t.word ~w\n",[X,Y]).% Gerar codigo.

/*No caso dos booleanos, para 'true' guarda-se numa word o valor 1 e para
'false' guardar-se numa word o valor 0. */

id(X,var,bool,true):-
  write('\t.data\n'),         % Gerar diretiva .data
  format("~w:\t.word 1\n",[X]). % Gerar codigo.

id(X,var,bool,false):-
  write('\t.data\n'),         % Gerar a diretiva .data
  format("~w:\t.word 0\n",[X]). % Gerar codigo.
