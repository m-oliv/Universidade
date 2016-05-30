%----------------- Statements

%---------- Assign Statement

assign(A,B,X):-
  rep(B,_),                 % Generate the intermediate representation of the first argument
  write('\n'),
  rep(A,X).                 % Generate the intermediate representation of the second argument

%---------- Prints

print(A,int,T):-
  rep(A,TN),                % Generate the intermediate representation of the expression to print
  format('\ni_print t~w',TN), % Generate the intermediate representation of the print statement
  newTemp(T).               % Generate a new temporary register (type int)

print(A,real,F):-
  rep(A,FN),                % Generate the intermediate representation of the expression to print
  format('\nr_print fp~w',FN), % Generate the intermediate representation of the print statement
  newFTemp(F).               % Generate a new temporary register (type int)

print(A,bool,T):-
  rep(A,TN),                % Generate the intermediate representation of the expression to print
  format('\nb_print t~w',TN), % Generate the intermediate representation of the print statement
  newTemp(T).               % Generate a new temporary register (type real)

%------ Conditions (From IF, WHILE, etc)

%------- If the condition is an OR
cond(C,LT,_,T):-
  C = or(X,Y):bool,         % Check if the condition is an OR
  new_label(LF),            % Generate a new label (case false)
  or_cond(X,Y,LT,LF,T).     % Call the predicate that generates the intermediate representation

%------- If the condition is an AND
cond(C,_,LF,T):-
  C = and(X,Y):bool,         % Check if the condition is an AND
  new_label(LT),             % Generate a new label (case true)
  and_cond(X,Y,LT,LF,T).     % Call the predicate that generates the intermediate representation

%------- If the condition is anything else
cond(C,_,_,T):-
  rep(C,T).     % Call the predicate that generates the intermediate representation

%-------- IF
%-------- IF Without Else
if(C,A,nil,LT,LF,T):-
  cond(C,LT,LF,X),          % Generate the intermediate representation of the condition
  format('\ncjump t~w, ~w, ~w',[X,LT,LF]),
  format('\n~w:\n',LT),     % Generate the representation of the label (case true of the condition)
  newTemp(_),               % Increment the counter of the temporary registers (type int)
  newFTemp(_),              % Increment the counter of the temporary registers (type real)
  rep(A,T),                 % Generate the representation of the body of the IF
  format('\n~w:\n',LF).     % Generate the representation of the label (case false of the condition)

%-------- IF With Else
if(C,A,B,LT,LF,T):-
  cond(C,LT,LF,X),          % Generate the intermediate representation of the condition
  format('\ncjump t~w, ~w, ~w',[X,LT,LF]),
  format('\n~w:\n',LT),     % Generate the representation of the label (case true of the condition)
  newTemp(_),               % Increment the counter of the temporary registers (type int)
  newFTemp(_),              % Increment the counter of the temporary registers (type real)
  rep(A,_),                 % Generate the representation of the body of the IF
  new_label(LFF),           % Generate the label of the end of the IF
  format('\njump ~w',LFF),  % Generate the representation to the Jump to the end of the IF
  format('\n~w:\n',LF),     % Generate the representation of the label to the ELSE (case false of the condition)
  rep(B,T),                 % Generate the representation of the body of the ELSE
  format('\n~w:\n',LFF).    % Generate the representation of the label (end of the IF)

%-------- WHILE

while(A,B,LS,LT,LF,T):-
  format('~w:\n',LS),       % Generate the label at the beginning of the WHILE
  cond(A,LT,LF,X),          % Generate the intermediate representation of the condition
  format('\ncjump t~w, ~w, ~w',[X,LT,LF]),
  newTemp(_),               % Increment the counter of the temporary registers (type int)
  newFTemp(_),              % Increment the counter of the temporary registers (type real)
  format('\n~w:\n',LT),     % Generate the representation of the label (case true of the condition)
  rep(B,T),                 % Generate the representation of the body of the WHILE
  % Generate the representation to the Jump to the beginning of the WHILE
  % And Generate the representation of the label (end of the WHILE - case false of the condition)
  format('jump ~w\n~w:\n',[LS,LF]).

%-------- Procedure Call

pcall(A,B,int,T):-
  resetALT(_),              % Reset the list of arguments
  callArg(B,_),             % Generate the representation of the arguments
  arg_listT(K),             % Get the current list of arguments
  format('call @~w, ~w',[A,K]), % Generate the representation of the call
  newTemp(_),               % Increment the counter of the temporary registers (type int)
  tTemp(T),                 % Get the current register
  resetALT(_).              % Reset the list of arguments

pcall(A,B,real,X):-
  resetALF(_),              % Reset the list of arguments
  callArg(B,_),             % Generate the representation of the arguments
  arg_listF(K),             % Get the current list of arguments
  format('call @~w, ~w',[A,K]), % Generate the representation of the call
  newFTemp(_),              % Increment the counter of the temporary registers (type real)
  fTemp(X),                 % Get the current register
  resetALF(_).              % Reset the list of arguments

%-------- Function Call

fcall(A,B,int,T):-
  resetALT(_),              % Reset the list of arguments
  callArg(B,_),             % Generate the representation of the arguments
  arg_listT(K),             % Get the current list of arguments
  sort(K,KK),               % Sort the arguments
  newTemp(XN),              % Generate a new temporary register to hold the result of the call
  format('~w <- call @~w, ~w',[XN,A,KK]), % Generate the representation of the call
  resetALT(_),              % Reset the list of arguments
  tTemp(T).                 % Get the current temporary register

fcall(A,B,real,T):-
  resetALF(_),              % Reset the list of arguments
  callArg(B,_),             % Generate the representation of the arguments
  arg_listF(K),             % Get the current list of arguments
  sort(K,KK),               % Sort the arguments
  newTemp(FN),              % Generate a new temporary register to hold the result of the call
  format('fp~w <- call @~w, ~w',[FN,A,KK]), % Generate the representation of the call
  resetALF(_),              % Reset the list of arguments
  fTemp(T).                 % Get the current temporary register

%-------- Compound Statement
comp(A,T):-
  A\=[],
  gethead(A,C),        % Get the first statement
  rep(C,_),            % Generate the intermediate representation
  write('\n'),
  removehead(A,B),     % Get the other statements
  rep(B,T).            % Generate the intermediate representation

comp([],_).             % If the list is empty, do nothing.

%------- Declarations

var(ID,A,T):-
  A\=nil,
  rep(A,_),               % Generate the intermediate representation of the expression
  write('\n'),
  rep(ID,T).              % Generate the intermediate representation of the id

var(_,nil,_).              % If the declaration is nil, do nothing.

%------ Function Body

body(D,S,E,T):-
  E\=nil,
  rep(D,_),                 % Generate the intermediate representation of the declarations
  rep(S,_),                 % Generate the intermediate representation of the statement
  rep(E,T),                 % Generate the intermediate representation of the expression
  get_type(E,TYPE),         % Get the type of the return expression
  return(TYPE).             % Generate the intermediate representation of the return expression

%------ Procedure Body

body(D,S,nil,T):-
  rep(D,_),                 % Generate the intermediate representation of the declarations
  write('\n'),
  rep(S,T).                 % Generate the intermediate representation of the statement

%---------- Return Statements

return(int):-
  tTemp(TN),                   % Get the index of the current temporary register (type int)
  format('\ni_return t~w',TN). % Generate the intermediate representation

return(real):-
  fTemp(FN),                 % Get the index of the current temporary register (type real)
  format('\nr_return fp~w',FN). % Generate the intermediate representation
