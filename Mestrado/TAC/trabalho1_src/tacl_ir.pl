%----- Consult the other files of the specification
:-consult('tacl_ir_exp.pl').
:-consult('tacl_ir_LSV.pl').
:-consult('tacl_ir_stat.pl').
:-consult('tacl_ir_aux.pl').
%------ Resets of all saved values of indexes of temporary registers and Labels
:-retractall(tTemp(_)).
:-retractall(fTemp(_)).
:-retractall(label(_)).

%------- Default values of all saved values of indexes of temporary registers and Labels
:-assert(tTemp(0)).
:-assert(fTemp(0)).
:-asserta(label(0)).
:-asserta(arg_listT([])).
:-asserta(arg_listF([])).

%----------------------------------- IR TACL

%---------- IR Store IDs

rep(EXP,T):-
  EXP = id(X,KIND,TYPE),      % Check if EXP is an ID without a return type (usually used in assigns)
  id_st(X,KIND,TYPE,T).       % Call the predicate that generates the intermediate representation

%---------- IR Load IDs
rep(EXP,T):-
  EXP = id(X,KIND,TYPE):TYPE, % Check if EXP is an identifier
  id(X,KIND,TYPE,T).          % Call the predicate that generates the intermediate representation

%---------- IR Literals
rep(EXP,T):-
  EXP = int_literal(X):int,   % Check if EXP is an int_literal
  int_literal(X,T).           % Call the predicate that generates the intermediate representation

rep(EXP,T):-
  EXP = real_literal(X):real, % Check if EXP is a real_literal
  real_literal(X,T).          % Call the predicate that generates the intermediate representation

%--------- True and False
rep(EXP,T):-
  EXP = true,                 % Check if EXP is the expression 'true'
  bool(t,T).                  % Call the predicate that generates the intermediate representation

rep(EXP,T):-
  EXP = false,                % Check if EXP is the expression 'true'
  bool(f,T).                  % Call the predicate that generates the intermediate representation

%---------- IR Expressions

%---------- IR Arithmetic Expressions

%----- IR plus
rep(EXP,T):-
  EXP = plus(X,Y):int,        % Check if EXP is a plus
  OP = 'i_add',               % Use the instruction for the type int
  bin_op(X,Y,int,OP,T).       % Call the predicate that generates the intermediate representation

rep(EXP,T):-
  EXP = plus(X,Y):real,       % Check if EXP is a plus
  OP = 'r_add',               % Use the instruction for the type real
  bin_op(X,Y,real,OP,T).      % Call the predicate that generates the intermediate representation

%----- IR minus
rep(EXP,T):-
  EXP = minus(X,Y):int,       % Check if EXP is a minus
  OP = 'i_sub',               % Use the instruction for the type int
  bin_op(X,Y,int,OP,T).       % Call the predicate that generates the intermediate representation

rep(EXP,T):-
  EXP = minus(X,Y):real,      % Check if EXP is a minus
  OP = 'r_sub',               % Use the instruction for the type real
  bin_op(X,Y,real,OP,T).      % Call the predicate that generates the intermediate representation

%----- IR times
rep(EXP,T):-
  EXP = times(X,Y):int,       % Check if EXP is a times
  OP = 'i_mul',               % Use the instruction for the type int
  bin_op(X,Y,int,OP,T).       % Call the predicate that generates the intermediate representation

rep(EXP,T):-
  EXP = times(X,Y):real,      % Check if EXP is a times
  OP = 'r_mul',               % Use the instruction for the type real
  bin_op(X,Y,real,OP,T).      % Call the predicate that generates the intermediate representation

%----- IR div
rep(EXP,T):-
  EXP = div(X,Y):int,         % Check if EXP is a div
  OP = 'i_div',               % Use the instruction for the type int
  bin_op(X,Y,int,OP,T).       % Call the predicate that generates the intermediate representation

rep(EXP,T):-
  EXP = div(X,Y):real,        % Check if EXP is a div
  OP = 'r_div',               % Use the instruction for the type real
  bin_op(X,Y,real,OP,T).      % Call the predicate that generates the intermediate representation

%----- IR Mod
rep(EXP,T):-
  EXP = mod(X,Y):int,        % Check if EXP is a mod
  OP = 'mod',                % Use the instruction
  bin_op(X,Y,int,OP,T).      % Call the predicate that generates the intermediate representation

%----- IR Inv
rep(EXP,T):-
  EXP = inv(X):int,        % Check if EXP is an inv
  OP = 'i_inv',            % Use the instruction for the type int
  inv(X,int,OP,T).         % Call the predicate that generates the intermediate representation

rep(EXP,T):-
  EXP = inv(X):real,       % Check if EXP is an inv
  OP = 'r_inv',            % Use the instruction for the type real
  inv(X,real,OP,T).        % Call the predicate that generates the intermediate representation

%----- IR Int Coercion
rep(EXP,T):-
  EXP = toreal(X):real,    % Check if EXP is a coercion
  toreal(X,T).             % Call the predicate that generates the intermediate representation


%---------- IR Logical Expressions

%----- IR OR
rep(EXP,T):-
  EXP = or(X,Y):bool,    % Check if EXP is an OR
  label(L),              % Get the index of the current label
  atom_concat('l',L,LT), % Generate the label in the form 'l<index>' (case true)
  new_label(LF),         % Generate a new label (case false)
  or(X,Y,LT,LF,T).       % Call the predicate that generates the intermediate representation

%----- IR AND
rep(EXP,T):-
  EXP = and(X,Y):bool,    % Check if EXP is an AND
  label(L),               % Get the index of the current label
  atom_concat('l',L,LF),  % Generate the label in the form 'l<index>' (case false)
  new_label(LT),          % Generate a new label (case true)
  and(X,Y,LT,LF,T).       % Call the predicate that generates the intermediate representation

%----- IR NOT
rep(EXP,T):-
  EXP = not(X):bool,     % Check if EXP is a NOT
  not(X,T).              % Call the predicate that generates the intermediate representation


%---------- IR Comparison Expressions

%----- IR EQ
rep(EXP,T):-
  EXP = eq(X,Y):bool,    % Check if EXP is an EQ
  get_type(X,int),       % Get the type of the expression
  OP = 'i_eq',           % Use the instruction for the correct type
  bin_op(X,Y,int,OP,T).  % Call the predicate that generates the intermediate representation

rep(EXP,T):-
  EXP = eq(X,Y):bool,    % Check if EXP is an EQ
  get_type(X,real),      % Get the type of the expression
  OP = 'r_eq',           % Use the instruction for the correct type
  bin_op(X,Y,real,OP,T). % Call the predicate that generates the intermediate representation

%----- IR NE
rep(EXP,T):-
  EXP = ne(X,Y):bool,   % Check if EXP is an NE
  get_type(X,int),      % Get the type of the expression
  OP = 'i_ne',          % Use the instruction for the correct type
  bin_op(X,Y,int,OP,T). % Call the predicate that generates the intermediate representation

rep(EXP,T):-
  EXP = ne(X,Y):bool,   % Check if EXP is an NE
  get_type(X,real),     % Get the type of the expression
  OP = 'r_ne',          % Use the instruction for the correct type
  bin_op(X,Y,real,OP,T).% Call the predicate that generates the intermediate representation

%----- IR LT
rep(EXP,T):-
  EXP = lt(X,Y):bool,  % Check if EXP is an LT
  get_type(X,int),     % Get the type of the expression
  OP = 'i_lt',         % Use the instruction for the correct type
  bin_op(X,Y,int,OP,T).% Call the predicate that generates the intermediate representation

rep(EXP,T):-
  EXP = lt(X,Y):bool,   % Check if EXP is an LT
  get_type(X,real),     % Get the type of the expression
  OP = 'r_lt',          % Use the instruction for the correct type
  bin_op(X,Y,real,OP,T).% Call the predicate that generates the intermediate representation

%----- IR GT
rep(EXP,T):-
  EXP = gt(X,Y):bool,  % Check if EXP is an GT
  get_type(X,int),     % Get the type of the expression
  OP = 'i_lt',         % Use the instruction for the correct type
  bin_op(Y,X,int,OP,T).% Call the predicate that generates the intermediate representation

rep(EXP,T):-
  EXP = lt(X,Y):bool,   % Check if EXP is an GT
  get_type(X,real),     % Get the type of the expression
  OP = 'r_lt',          % Use the instruction for the correct type
  bin_op(Y,X,real,OP,T).% Call the predicate that generates the intermediate representation

%----- IR GE
rep(EXP,T):-
  EXP = ge(X,Y):bool,  % Check if EXP is an GE
  get_type(X,int),     % Get the type of the expression
  OP = 'i_le',         % Use the instruction for the correct type
  bin_op(Y,X,int,OP,T).% Call the predicate that generates the intermediate representation

rep(EXP,T):-
  EXP = ge(X,Y):bool,   % Check if EXP is an GE
  get_type(X,real),     % Get the type of the expression
  OP = 'r_le',          % Use the instruction for the correct type
  bin_op(Y,X,real,OP,T).% Call the predicate that generates the intermediate representation

%----- IR LE
rep(EXP,T):-
  EXP = le(X,Y):bool,  % Check if EXP is an LE
  get_type(X,int),     % Get the type of the expression
  OP = 'i_le',         % Use the instruction for the correct type
  bin_op(X,Y,int,OP,T).% Call the predicate that generates the intermediate representation

rep(EXP,T):-
  EXP = le(X,Y):bool,   % Check if EXP is an LE
  get_type(X,real),     % Get the type of the expression
  OP = 'r_le',          % Use the instruction for the correct type
  bin_op(X,Y,real,OP,T).% Call the predicate that generates the intermediate representation



%-------------------- IR Statements

%--------- Assign Statement
rep(EXP,T):-
  EXP = assign(A,B),   % Check if EXP is an assign statement
  assign(A,B,T).       % Call the predicate that generates the intermediate representation

%--------- Compound Statement

rep(EXP,T):-
  EXP = [_|_],       % Check if EXP is a list
  comp(EXP,T).       % Call the predicate that generates the intermediate representation

%------- Declarations
rep(EXP,T):-
  EXP = var(A,B),   % Check if EXP is a declaration
  var(A,B,T).       % Call the predicate that generates the intermediate representation

%-------- Prints

rep(EXP,T):-
  EXP = print(A),        % Check if EXP is a print statement
  get_type(A,TYPE),      % Get the type of the expression to print
  print(A,TYPE,T).       % Call the predicate that generates the intermediate representation

%-------- IF

%--- IF With Else
rep(EXP,T):-
  EXP = if(A,B,C),         % Check if EXP is an IF with an ELSE
  new_label(LT),           % Generate a new label (case true)
  new_label(LF),           % Generate a new label (case false)
  if(A,B,C,LT,LF,T).       % Call the predicate that generates the intermediate representation

%--- IF Without Else
rep(EXP,T):-
  EXP = if(A,B,nil),         % Check if EXP is an IF without an ELSE
  new_label(LT),                  % Get the index of the current label
  %atom_concat('l',L,LT),     % Generate the label in the form 'l<index>'
  new_label(LF),             % Generate a new label (case false)
  if(A,B,nil,LT,LF,T).       % Call the predicate that generates the intermediate representation

%-------- WHILE

rep(EXP,T):-
  EXP = while(A,B),            % Check if EXP is a WHILE statement
  label(L),                    % Get the index of the current label
  atom_concat('l',L,LS),       % Generate the label of the beginning of the while in the form 'l<index>'
  new_label(LT),               % Generate a new label (case true)
  new_label(LF),               % Generate a new label (case false)
  while(A,B,LS,LT,LF,T).       % Call the predicate that generates the intermediate representation

%-------- Function Body

rep(EXP,W):-
  EXP = body(X,Y,Z),       % Check if EXP is a body (of a procedure or a function)
  body(X,Y,Z,W).           % Call the predicate that generates the intermediate representation

%-------- Function Call
rep(EXP,T):-
  EXP = call(X,Y):TYPE,    % Check if EXP is a function call
  fcall(X,Y,TYPE,T).       % Call the predicate that generates the intermediate representation

%-------- Procedure Call
rep(EXP,T):-
  EXP = call(X,Y),         % Check if EXP is a procedure call
  arg(1,Y,TY),             % Separate the info relative to the type from the rest of the expression
  get_type(TY,TYPE),       % Get the type of the procedure
  pcall(X,Y,TYPE,T).       % Call the predicate that generates the intermediate representation

%------- IR Auxiliary Predicates
rep(nil,_).                 % If EXP is nil, do nothing

rep([],_).                  % If EXP is an empty list, do nothing

rep([nil],_).               % If EXP is a list with one element (nil), do nothing

%------- Functions

fun(FI,_,FB):-
  format('function @~w\n',FI),  % Generate the representation of the header of the function
  rep(FB,_),                % Generate the intermediate representation of the function body
  resetAll(_).              % Reset all counters (labels and temporary registers)
