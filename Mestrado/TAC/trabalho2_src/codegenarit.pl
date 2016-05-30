
%----- Predicados que geram o codigo das instrucoes aritmeticas.

i_add(X,Y,Z):-
  decrementRegC(2,B),               % Decrementar o num. de registos atuais.
  tempToReg(Y,C),                   % Obter o registo de um dos argumentos da operacao.
  tempToReg(Z,D),                   % Obter o registo do outro argumento da operacao.
  format(atom(T),"t~w",[B]),          % Criar um novo registo.
  assert(tempToReg(X,T)),           % Guardar o novo registo numa variavel global auxiliar.
  format("\taddu\t$~w, $~w, $~w\n",[T,C,D]),  % Gerar o codigo para a instrucao i_add.
  incrementRegC(1,_).               % Incrementar o num. de registos atuais.

i_sub(X,Y,Z):-
  decrementRegC(2,B),               % Decrementar o num. de registos atuais.
  tempToReg(Y,C),                   % Obter o registo de um dos argumentos da operacao.
  tempToReg(Z,D),                   % Obter o registo do outro argumento da operacao.
  format(atom(T),"t~w",[B]),          % Criar um novo registo.
  assert(tempToReg(X,T)),           % Guardar o novo registo numa variavel global auxiliar.
  format("\tsubu\t$~w, $~w, $~w\n",[T,C,D]),  % Gerar o codigo para a instrucao i_sub.
  incrementRegC(1,_).               % Incrementar o num. de registos atuais.

i_mul(X,Y,Z):-
  decrementRegC(2,B),               % Decrementar o num. de registos atuais.
  tempToReg(Y,C),                   % Obter o registo de um dos argumentos da operacao.
  tempToReg(Z,D),                   % Obter o registo do outro argumento da operacao.
  format(atom(T),"t~w",[B]),          % Criar um novo registo.
  assert(tempToReg(X,T)),           % Guardar o novo registo numa variavel global auxiliar.
  format("\tmult\t$~w, $~w\n",[C,D]),  % Gerar o codigo para a instrucao.
  format("\tmflo\t$~w\n",T),        % Gerar o codigo para a instrucao.
  incrementRegC(1,_).               % Incrementar o num. de registos atuais.

i_div(X,Y,Z):-
  decrementRegC(2,B),               % Decrementar o num. de registos atuais.
  tempToReg(Y,C),                   % Obter o registo de um dos argumentos da operacao.
  tempToReg(Z,D),                   % Obter o registo do outro argumento da operacao.
  format(atom(T),"t~w",[B]),          % Criar um novo registo.
  assert(tempToReg(X,T)),           % Guardar o novo registo numa variavel global auxiliar.
  format("\tdiv\t$~w, $~w\n",[C,D]),  % Gerar o codigo para a instrucao.
  format("\tmflo\t$~w\n",[T]),  % Gerar o codigo para a instrucao.
  incrementRegC(1,_).               % Incrementar o num. de registos atuais.

mod(X,Y,Z):-
  decrementRegC(2,B),               % Decrementar o num. de registos atuais.
  tempToReg(Y,C),                   % Obter o registo de um dos argumentos da operacao.
  tempToReg(Z,D),                   % Obter o registo do outro argumento da operacao.
  format(atom(T),"t~w",[B]),          % Criar um novo registo.
  assert(tempToReg(X,T)),           % Guardar o novo registo numa variavel global auxiliar.
  format("\tdiv\t$~w, $~w\n",[C,D]),  % Gerar o codigo para a instrucao.
  format("\tmfhi\t$~w\n",[T]),  % Gerar o codigo para a instrucao.
  incrementRegC(1,_).               % Incrementar o num. de registos atuais.

/* Predicado que gera o codigo da instrucao i_inv. */
i_inv(X,Y):-
  decrementRegC(1,B),             % Decrementar o num. de registos atuais.
  tempToReg(Y,C),                 % Obter o registo do argumento.
  format(atom(T),"t~w",[B]),        % Criar um novo registo.
  assert(tempToReg(X,T)),         % Guardar o novo registo numa variavel global auxiliar.
  format("\tsubu\t$~w, $0, $~w\n",[T,C]), % Gerar o codigo para a instrucao i_inv.
  incrementRegC(1,_).             % Incrementar o num. de registos atual.

/* Predicado que gera o codigo da instrucao not. */
not(X,Y):-
  decrementRegC(1,B),             % Decrementar o num. de registos atuais.
  tempToReg(Y,C),                 % Obter o registo do argumento.
  format(atom(T),"t~w",[B]),        % Criar um novo registo.
  assert(tempToReg(X,T)),         % Guardar o novo registo numa variavel global auxiliar.
  format("\txori\t$~w, $~w, 1\n",[T,C]), % Gerar o codigo para a instrucao i_inv.
  incrementRegC(1,_).             % Incrementar o num. de registos atual.


%------ Predicados que geram o codigo para as comparacoes

i_lt(X,Y,Z):-
  decrementRegC(2,B),             % Decrementar o num. de registos atuais.
  tempToReg(Y,C),                 % Obter o registo correspondente a um argumento.
  tempToReg(Z,D),                 % Obter o registo correspondente ao outro argumento.
  format(atom(T),"t~w",[B]),        % Criar um novo registo.
  assert(tempToReg(X,T)),         % Guardar o novo registo numa variavel global auxiliar.
  format("\tslt\t$~w, $~w, $~w\n",[T,C,D]), % Gerar o codigo para a instrucao i_lt.
  incrementRegC(1,_).             % Incrementar o num. de registos atual.

i_eq(X,Y,Z):-
  decrementRegC(2,B),             % Decrementar o num. de registos atuais.
  tempToReg(Y,C),                 % Obter o registo correspondente a um argumento.
  tempToReg(Z,D),                 % Obter o registo correspondente ao outro argumento.
  format(atom(T),"t~w",[B]),        % Criar um novo registo.
  assert(tempToReg(X,T)),         % Guardar o novo registo numa variavel global auxiliar.
  format("\txor\t$~w, $~w, $~w\n",[T,C,D]), % Gerar o codigo para a instrucao.
  format("\tsltiu\t$~w, $~w, 1\n",[T,T]), % Gerar o codigo para a instrucao.
  incrementRegC(1,_).             % Incrementar o num. de registos atual.

i_le(X,Y,Z):-
  decrementRegC(2,B),             % Decrementar o num. de registos atuais.
  tempToReg(Y,C),                 % Obter o registo correspondente a um argumento.
  tempToReg(Z,D),                 % Obter o registo correspondente ao outro argumento.
  format(atom(T),"t~w",[B]),        % Criar um novo registo.
  assert(tempToReg(X,T)),         % Guardar o novo registo numa variavel global auxiliar.
  format("\tslt\t$~w, $~w, $~w\n",[T,D,C]), % Gerar o codigo para a instrucao.
  format("\txori\t$~w, $~w, 1\n",[T,T]), % Gerar o codigo para a instrucao.
  incrementRegC(1,_).             % Incrementar o num. de registos atual.

i_ne(X,Y,Z):-
  decrementRegC(2,B),             % Decrementar o num. de registos atuais.
  tempToReg(Y,C),                 % Obter o registo correspondente a um argumento.
  tempToReg(Z,D),                 % Obter o registo correspondente ao outro argumento.
  format(atom(T),"t~w",[B]),        % Criar um novo registo.
  assert(tempToReg(X,T)),         % Guardar o novo registo numa variavel global auxiliar.
  format("\txor\t$~w, $~w, $~w\n",[T,C,D]), % Gerar o codigo para a instrucao.
  format("\tsltiu\t$~w, 0, $~w\n",[T,T]), % Gerar o codigo para a instrucao.
  incrementRegC(1,_).             % Incrementar o num. de registos atual.
