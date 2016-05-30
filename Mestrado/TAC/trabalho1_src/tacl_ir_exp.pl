%---------------- Expressions

%------ Binary operators (Arithmetic and Comparison Expressions)

bin_op(A,B,int,OP,TN):-
  rep(A,X),                 % Generate the intermediate representation of the first argument
  write('\n'),
  newTemp(_),               % Increment the temporary register counter (type int)
  rep(B,XX),                % Generate the intermediate representation of the second argument
  newTemp(_),               % Increment the temporary register counter (type int)
  tTemp(TN),                % Get the index of the current temporary
  format('\nt~w <- ~w t~w, t~w',[TN,OP,X,XX]).  % Generate the intermediate representation to the given operation

bin_op(A,B,real,OP,FN):-
  rep(A,Y),                 % Generate the intermediate representation of the first argument
  write('\n'),
  newFTemp(_),              % Increment the temporary register counter (type real)
  rep(B,YY),                % Generate the intermediate representation of the second argument
  newFTemp(_),              % Increment the temporary register counter (type real)
  fTemp(FN),                % Get the index of the current temporary
  format('\nfp~w <- ~w fp~w, fp~w',[FN,OP,Y,YY]).  % Generate the intermediate representation to the given operation

%------ Inv

inv(A,int,OP,T):-
  rep(A,X),                 % Generate the intermediate representation of the argument
  newTemp(TN),              % Increment the temporary register counter (type int)
  format('\n~w <- ~w t~w',[TN,OP,X]),  % Generate the intermediate representation to the inv expression
  tTemp(T).                 % Get the index of the current temporary

inv(A,real,OP,T):-
  rep(A,Y),                 % Generate the intermediate representation of the argument
  newFTemp(FN),             % Increment the temporary register counter (type int)
  format('\n~w <- ~w fp~w',[FN,OP,Y]),  % Generate the intermediate representation to the inv expression
  fTemp(T).                 % Get the index of the current temporary

%------ Int Coercion

toreal(A,T):-
  rep(A,X),                 % Generate the intermediate representation of the argument
  newFTemp(FN),             % Generate a new temporary to hold the result of the coersion
  newTemp(_),               % Increment the temporary register counter (type int)
  format('\n~w <- itor t~w',[FN,X]),  % Generate the intermediate representation to the toreal expression
  fTemp(T).                 % Get the index of the current temporary


%--------------- Logical Expressions

%------ OR

or(A,B,LT,LF,X):-
  rep(A,X),                 % Generate the intermediate representation of the first argument
  format('\ncjump t~w, ~w, ~w\n~w:\n',[X,LT,LF,LF]),
  newTemp(_),               % Increment the temporary register counter (type int)
  newFTemp(_),              % Increment the temporary register counter (type real)
  rep(B,TN),                % Generate the intermediate representation of the second argument
  format('\nt~w <- i_copy t~w\n~w:',[X,TN,LT]), % Generate the representation of the copy instruction
  resetT(X).                % Reset the counter of the temporary registers used in the cjump instruction (type int)

%------ OR (Condition)

or_cond(A,B,LT,LF,TN):-
  rep(A,X),                 % Generate the intermediate representation of the first argument
  format('\ncjump t~w, ~w, ~w\n~w:\n',[X,LT,LF,LF]),
  newTemp(_),               % Increment the temporary register counter (type int)
  newFTemp(_),              % Increment the temporary register counter (type real)
  rep(B,TN).                % Generate the intermediate representation of the second argument

%------ AND

and(A,B,LT,LF,X):-
  rep(A,X),                 % Generate the intermediate representation of the first argument
  format('\ncjump t~w,~w,~w\n~w:\n',[X,LT,LF,LT]),
  newTemp(_),               % Increment the temporary register counter (type int)
  newFTemp(_),              % Increment the temporary register counter (type real)
  rep(B,TN),                % Generate the intermediate representation of the second argument
  format('\nt~w <- i_copy t~w\n~w:\n',[X,TN,LF]), % Generate the representation of the copy instruction
  resetT(X).                % Reset the counter of the temporary registers used in the cjump instruction (type int)

%------ AND (Condition)

and_cond(A,B,LT,LF,TN):-
  rep(A,X),                 % Generate the intermediate representation of the first argument
  format('\ncjump t~w,~w,~w\n~w:\n',[X,LT,LF,LT]),
  newTemp(_),               % Increment the temporary register counter (type int)
  newFTemp(_),              % Increment the temporary register counter (type real)
  rep(B,TN).                % Reset the counter of the temporary registers used in the cjump instruction (type int)

%------ NOT

not(A,T):-
  rep(A,X),                 % Generate the intermediate representation of the argument
  newTemp(TN),              % Generate a new register to hold the result of the operation
  format('\n~w <- not t~w',[TN,X]), % Generate the intermediate representation of the not operation
  tTemp(T).                 % Get the current temporary register (type int)
