
%------ Predicado auxiliar para criar o registo de ativacao.

buildRec([id(N,TYPE)|AS],KIND,FN):-
  offset(FP,KIND),             % Obter os offsets iniciais para argumentos e variaveis locais.
  asserta(actRec(KIND,N,TYPE,FP,FN)), % Guardar o argumento ou variavel local no registo de ativacao da funcao.
  buildRec(AS,KIND,FN),        % Proceder do mesmo modo para as restantes variaveis locais / argumentos.
  resetOff.                    % Reset aos offsets da variavel global auxiliar.

buildRec([],_,_).

%--- Predicados auxiliares que calculam os offsets dos argumentos e das variaveis locais.

/* A cada novo argumento, adiciona-se 4 bytes ao fp.
Inicialmente o valor guardado em fpOffA (variavel global auxiliar) é zero.*/
offset(F,arg):-
  fpOffA(X),                % Obter o valor atual do offset.
  F is X + 4,               % Incrementar o offset.
  retractall(fpOffA(_)),    % Remover o valor antigo.
  asserta(fpOffA(F)).       % Guardar o novo offset.

/* A cada novo argumento, subtrai-se 4 bytes ao fp.
Inicialmente o valor guardado em fpOffA (variavel global auxiliar) é -4
(inclui-se o espaco reservado para o endereco de retorno).*/
offset(F,local):-
  fpOffL(X),                % Obter o valor atual do offset.
  F is X - 4,               % Decrementar o offset.
  retractall(fpOffL(_)),    % Remover o valor antigo.
  asserta(fpOffL(F)).       % Guardar o novo offset.

%---- Predicado auxiliar que calcula o espaco a alocar.
alocSpace(L,A,F):-
  % Calcular espaco a alocar para variaveis locais.
  Y is L * 4,
  SL is Y + 4,              % Incluir o espaco do endereco de retorno
  % Calcular espaco a alocar para argumentos.
  Z is A * 4,
  SA is Z + 4,              % Incluir o espaco do frame pointer.
  asserta(localAloc(SL,SA,F)). % Guardar informacao numa variavel global auxiliar.

%---- Predicado auxiliar para processar os elementos de uma lista de instrucoes.
listInstr([A|AS]):-
  A,                  % Processar a instrucao (gerar codigo).
  listInstr(AS).      % Processar as restantes instrucoes.

listInstr([]).

%---- Predicado auxiliar para reset aos registos .
resetReg:-
  % Remover informacao antiga.
  retractall(currentReg(_)),
  retractall(tempToReg(_,_)),
  % Iniciar currentReg com o valor default.
  asserta(currentReg(0)).

/* Predicados auxiliares que permitem incrementar e decrementar o
numero de registos .*/
incrementRegC(B,C):-
  currentReg(A),       % Obter numero de registos em uso atual.
  C is A + B,          % Incrementar o numero de registos em uso.
  updateR(A,C).        % Atualizar o numero de registos em uso.

decrementRegC(B,C):-
  currentReg(A),       % Obter numero de registos em uso atual.
  C is A - B,          % Decrementar o numero de registos em uso.
  updateR(A,C).        % Atualizar o numero de registos em uso.

%---- Predicado auxiliar para atualizar o num. de registos .
updateR(A,B):-
  retractall(currentReg(A)),  % Remover o valor antigo.
  asserta(currentReg(B)).     % Atualizar com o valor novo.

/* Predicado auxiliar para reset a informacao auxiliar
usada por funcoes e procedimentos (funcao atual, espaco alocado e registo de ativacao).*/
resetAll(X):-
  % Remover informacao antiga relativa a funcoes ou procedimentos.
  retractall(currentF(X)),
  retractall(localAloc(_,_,X)),
  retractall(actRec(_,X,_,_,_)).

%---- Predicado auxiliar para reset aos offsets.
resetOff:-
  % Remover informacao antiga relativa aos offsets.
  retractall(fpOffL(_)),
  retractall(fpOffA(_)),
  % Restaurar os valores default.
  asserta(fpOffL(-4)),
  asserta(fpOffA(0)).

%---- Predicado auxiliar que permite obter o offset de uma variavel ou argumento.
getOffset(V,KIND,OFF):-
  currentF(F),            % Obter o nome da funcao atual.
  actRec(KIND,V,_,OFF,F). % Consultar o registo de ativacao para obter o offset pretendido.

/* Predicados auxiliares que permitem obter os registos
correspondentes aos argumentos da funcao ou procedimento.*/

getRegArgs([X|XS]):-
  fArgs(Z),               % Obter a lista atual de registos que correspondem a argumentos da funcao.
  tempToReg(X,C),         % Obter registo temporario correspondente.
  append(Z,[C],W),        % Adicionar o registo a lista de registos de argumentos.
  retractall(fArgs(_)),   % Remover informacao antiga.
  asserta(fArgs(W)),      % Armazenar a informacao nova.
  getRegArgs(XS).         % Proceder do mesmo modo para os restantes argumentos.

getRegArgs([]).

%---- Predicado auxiliar que permite fazer o push dos argumentos.
pushArgs([X|XS]):-
% Gerar o codigo correspondente ao push dos argumentos da funcao ou procedimento.
  write('\taddiu\t$sp, $sp, -4\n'),
  format("\tsw\t~w, 0($sp)\n",[X]),
  pushArgs(XS).

pushArgs([]).

/* Predicado auxiliar que permite gerar o codigo para guardar os valores
que se encontram nos registos atuais.*/
saveRegInUse([X|XS]):-
  write('\taddiu\t$sp, $sp, -4\n'),
  format("\tsw\t~w, 0($sp)\n",[X]),
  saveRegInUse(XS).

saveRegInUse([]).

/* Predicado auxiliar que permite obter os registos atuais.*/
used(N):-
% Criar a lista de registos em uso atualmente.
  N > 0,
  B is N-1,
  format(atom(T),"t~w",[B]),
  regInUse(X),
  append(X,[T],L),
  retractall(regInUse(_)),
  asserta(regInUse(L)),
  used(B).

used(0).

/* Predicado auxiliar que permite obter os registos atuais,
excluindo os argumentos da funcao ou procedimento.*/
removeArgs([Y|YS]):-
% Remover os argumentos de uma funcao ou procedimento da lista de registos em uso.
  regInUse(X),
  delete(X,Y,Z),
  sort(Z,W),
  retractall(regInUse(X)),
  asserta(regInUse(W)),
  removeArgs(YS).

removeArgs([]).

/* Predicado auxiliar que permite obter os registos atuais,
excluindo argumentos de funcoes ou procedimentos.*/
getReg(N,X,W):-
  used(N),
  removeArgs(X),
  regInUse(W).

/* Predicado auxiliar que permite limpar a informacao auxiliar usada
pelas instrucoes call, depois do codigo destas ser gerado.*/
clearInfoCall:-
  retractall(regInUse(_)),
  retractall(fArgs(_)),
  asserta(regInUse([])),
  asserta(fArgs([])).

/* Predicados auxiliares que permitem gerar codigo para restaurar
os valores nos correspondentes registos atuais.*/
restoreRegInUse([A|AS]):-
  format("\tlw\t~w, 0($sp)\n",[A]),
  write('\taddiu\t$sp, $sp, 4\n'),
  restoreRegInUse(AS).

restoreRegInUse([]).


%---- Predicados auxiliares para gerar o codigo das macros dos prints.
generateMacros:-
  generatePIMacro,
  generatePBMacro.

generatePIMacro:-
  write('\n\t# print an integer\n'),
  write('\t.macro i_print$ (%int)\n'),
  write('\tor\t$a0, $0, %int\n'),
  write('\tori\t$v0, $0, 1\n'),
  write('\tsyscall\n'),
  write('\tori\t$a0, $0,''\\n''\n'),
  write('\tori\t$v0, $0, 11\n'),
  write('\tsyscall\n'),
  write('\t.end_macro\n').

generatePBMacro:-
  write('\n\t# print a boolean value\n'),
  write('\t.macro b_print$ (%bool)\n'),
  write('\tbeq\t%bool, $0, _$bp1\n'),
  write('\tnop\n'),
  write('\tla\t$a0, true\n'),
  write('\tj\t_$bp2\n'),
  write('\tnop\n'),
  write('\t_$bp1:\tla\t$a0, false\n'),
  write('\t_$bp2:\tori\t$v0, $0, 4\n'),
  write('\tsyscall\n'),
  write('\t.end_macro\n').
