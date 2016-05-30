%---- Predicado que gera o codigo para a instrucao i_value

i_value(X,Y):-
  currentReg(A),                % Obter o num. de registos atual.
  incrementRegC(1,_),           % Incrementar o num. de registos.
  format(atom(T),"t~w",[A]),      % Criar um novo registo.
  assert(tempToReg(X,T)),       % Guardar o registo  numa variavel global auxiliar.
  format("\tori\t$~w, $0, ~w\n",[T,Y]). % Gerar o codigo para a instrucao i_value.


%----- Predicados que geram o codigo para os loads de variaveis
i_aload(X,N):-
  currentF(F),                  % Obter o nome da funcao atual.
  actRec(arg,N,_,FP,F),         % Consultar o registo de ativacao para obter o offset.
  currentReg(A),                % Obter o numero de registos atual.
  incrementRegC(1,_),           % Incrementar o num. de registos .
  format(atom(T),"t~w",[A]),      % Criar um novo registo .
  assert(tempToReg(X,T)),       % Guardar o registo  novo numa variavel global auxiliar.
  format("\tlw\t$~w, ~w($fp)\n",[T,FP]). % Gerar o codigo para a instrucao i_aload.

/* Este predicado funciona de modo semelhante ao anterior.*/
i_lload(X,N):-
  currentF(F),                  % Obter o nome da funcao atual.
  actRec(local,N,_,FP,F),       % Consultar o registo de ativacao para obter o offset.
  currentReg(A),                % Obter o numero de registos atual.
  incrementRegC(1,_),           % Incrementar o num. de registos.
  format(atom(T),"t~w",[A]),      % Criar um novo registo .
  assert(tempToReg(X,T)),       % Guardar o registo  novo numa variavel global auxiliar.
  format("\tlw\t$~w, ~w($fp)\n",[T,FP]). % Gerar o codigo para a instrucao i_lload.

/* Este predicado funciona de modo semelhante aos anteriores.*/
i_load(X,N):-
  currentReg(A),                % Obter o numero de registos atual.
  incrementRegC(1,_),           % Incrementar o num. de registos.
  format(atom(T),"t~w",[A]),      % Criar um novo registo .
  assert(tempToReg(X,T)),       % Guardar o registo  novo numa variavel global auxiliar.
  format("\tla\t$~w, ~w\n",[T,N]), % Gerar o codigo para a instrucao.
  format("\tlw\t$~w, 0($~w)\n",[T,T]). % Gerar o codigo para a instrucao.

%----- Predicados que geram o codigo para os stores de variaveis
i_lstore(N,Y):-
  currentF(F),                  % Obter o nome da funcao atual.
  actRec(local,N,_,FP,F),       % Consultar o registo de ativacao para obter o offset.
  decrementRegC(1,_),           % Decrementar o num. de registos .
  tempToReg(Y,X),               % Obter o registo  correspondente.
  format("\tsw\t$~w, ~w($fp)\n",[X,FP]).  % Gerar o codigo da instrucao i_lstore.

/* Este predicado funciona de modo semelhante ao anterior.*/
i_astore(N,Y):-
  currentF(F),                  % Obter o nome da funcao atual.
  actRec(arg,N,_,FP,F),         % Consultar o registo de ativacao para obter o offset.
  decrementRegC(1,_),           % Incrementar o numero de registos em uso.
  tempToReg(Y,X),               % Obter o registo  correspondente.
  format("\tsw\t$~w, ~w($fp)\n",[X,FP]).  % Gerar o codigo da instrucao i_astore.

/* Este predicado funciona de modo semelhante aos anteriores.*/
i_store(N,X):-
  decrementRegC(1,_),           % Incrementar o numero de registos em uso.
  tempToReg(X,Z),               % Obter o registo  correspondente.
  format("\tla\t$~w, ~w\n",[Z,N]), % Gerar o codigo para a instrucao.
  format("\tsw\t$~w, 0($~w)\n",[Z,Z]). % Gerar o codigo para a instrucao.
