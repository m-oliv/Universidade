%------- Auxiliary Predicate to Process Arguments From Function Call

callArg(A,T):-
  A\=[],                    % Checks if the list of expressions is not empty.
  gethead(A,C),             % Retrieve the head of the list
  rep(C,_),                 % Generate the intermediate representation for the head
  argsList(_),              % Add the result to the list of function arguments
  newTemp(_),               % Increment the counter for temporary register (type int)
  newFTemp(_),              % Increment the counter for temporary register (type real)
  write('\n'),
  removehead(A,B),          % Get the the initial list (minus the head)
  rep(B,T).                 % Generate the intermediate representation for the rest of the list

callArg([],_).              % If the list is empty, do nothing.


%--------------- Helper Predicates

%------- Aux. to get the type of an expression
get_type(X,C):-
  arg(2,X,C).               % Retrieve the type of the given expression (argument X)

%---- Auxiliary Predicates to Generate Labels

%------ Generate Label True
new_label(N):-
  label(P),                 % Get the index of the current label
  X is P + 1,               % Increment this index by 1
  retract(label(P)),        % Remove the predicate that holds the old index
  asserta(label(X)),        % Add the predicate that holds the new index
  atom_concat('l',X,N).     % Generate the label in the form 'l<index>'.

%----- Reset All Counters and Lists
resetAll(_):-
  % Remove the predicate that holds the index for the current temporary registers and label
  % Also remove the predicate that holds the lists of arguments (used in function or procedure calls)
  retractall(tTemp(_)),
  retractall(fTemp(_)),
  retractall(label(_)),
  retractall(arg_listT(_)),
  retractall(arg_listF(_)),
  % Add the predicate that holds the index for the current temporary registers and label with the default value (zero)
  % Also add the predicate that holds the lists of arguments (used in function or procedure calls)
  assert(tTemp(0)),
  assert(fTemp(0)),
  asserta(label(0)),
  asserta(arg_listT([])),
  asserta(arg_listF([])).

%------------ Counters

%----- Counter for Integer Temporaries
newTemp(K):-
  tTemp(X),                 % Get the index of the current temporary
  TN is X + 1,              % Increment this value by 1
  retract(tTemp(X)),        % Remove the old predicate that holds the old index value
  asserta(tTemp(TN)),       % Add the new predicate that holds the new index value
  atom_concat('t',TN,K).    % Generate the new register in the form 't<index>'

%----- Counter for Floating-point Temporaries
newFTemp(K):-
  fTemp(X),                 % Get the index of the current temporary
  FN is X + 1,              % Increment this value by 1
  retract(fTemp(X)),        % Remove the old predicate that holds the old index value
  asserta(fTemp(FN)),       % Add the new predicate that holds the new index value
  atom_concat('fp',FN,K).   % Generate the new register in the form 'fp<index>'

%----- Reset Counters

%------- Reset Counters for Integer Temporaries

%----- Reset completelly (start in zero)
resetallT(_):-
  retractall(tTemp(_)),     % Remove the old predicate that holds the old index value
  assert(tTemp(0)).         % Add the new predicate that holds the default index value (zero)

%----- Reset to a given temporary
resetT(X):-
  retractall(tTemp(_)),     % Remove the old predicate that holds the old index value
  assert(tTemp(X)).         % Add the new predicate that holds the new index value (argument X)

%------- Reset Counters for Floating-point Temporaries

%----- Reset completelly (start in zero)
resetallFP(_):-
  retractall(fTemp(_)),     % Remove the old predicate that holds the old index value
  assert(fTemp(0)).         % Add the new predicate that holds the default index value (zero)

%----- Reset to a given temporary
resetFP(X):-
  retractall(fTemp(_)),     % Remove the old predicate that holds the old index value
  assert(fTemp(X)).         % Add the new predicate that holds the new index value (argument X)

%------ Auxiliary Predicates to Manipulate Lists
removehead([_|Tail], Tail). % Remove the head from a list and return the new list

gethead([X|_],X).           % Get the first element of the list (head)

%---------- Auxiliary Predicate to Manage Function Call Arguments
argsList(_):-
  tTemp(X),                 % Get the index of the current temporary (type int)
  fTemp(Y),                 % Get the index of the current temporary (type real)
  atom_concat('t',X,TA),    % Generate the temporary register in the form 't<index>'
  atom_concat('fp',Y,FA),   % Generate the temporary register in the form 'fp<index>'
  arg_listT(LTe),           % Get the old list of arguments (type int)
  arg_listF(FTe),           % Get the old list of arguments (type real)
  retract(arg_listT(LTe)),  % Remove the old list of arguments (type int)
  retract(arg_listF(FTe)),  % Remove the old list of arguments (type real)
  append(LTe,[TA],KA),      % Add the new argument to the old list (type real)
  append(FTe,[FA],KB),      % Add the new argument to the old list (type int)
  asserta(arg_listT(KA)),   % Add the predicate that holds the new list of arguments
  asserta(arg_listF(KB)).   % Add the predicate that holds the new list of arguments

%--------- Auxiliary Predicates to Reset Function Call Arguments
%-------- Reset Arguments that are Integer Temporaries
resetALT(_):-
    retractall(arg_listT(_)),% Remove the current list of arguments (type int)
    asserta(arg_listT([])).  % Add the list of arguments with default values (empty list)

%-------- Reset Arguments that are Floating-point Temporaries
resetALF(_):-
    retractall(arg_listF(_)),% Remove the current list of arguments (type real)
    asserta(arg_listF([])).  % Add the list of arguments with default values (empty list)
