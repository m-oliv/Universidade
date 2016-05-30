%------------------------- IDs ----------------------

%----------------- Loads

%------------- Local Variables
id(ID,local,int,TC):-
  tTemp(TC),                            % Get the index of the current temporary register (type int)
  format('t~w <- i_lload @~w',[TC,ID]). % Generate the intermediate representation

id(ID,local,bool,TC):-
  tTemp(TC),                            % Get the index of the current temporary register (type int)
  format('t~w <- i_lload @~w',[TC,ID]). % Generate the intermediate representation

id(ID,local,real,FC):-
  fTemp(FC),                             % Get the index of the current temporary register (type real)
  format('fp~w <- r_lload @~w',[FC,ID]). % Generate the intermediate representation

%------------- Arguments
id(ID,arg,int,TC):-
  tTemp(TC),                            % Get the index of the current temporary register (type int)
  format('t~w <- i_aload @~w',[TC,ID]). % Generate the intermediate representation

id(ID,arg,bool,TC):-
  tTemp(TC),                            % Get the index of the current temporary register (type int)
  format('t~w <- i_aload @~w',[TC,ID]). % Generate the intermediate representation

id(ID,arg,real,FC):-
  fTemp(FC),                             % Get the index of the current temporary register (type real)
  format('fp~w <- r_aload @~w',[FC,ID]). % Generate the intermediate representation

%------------- Global Variables

id(ID,var,int,TC):-
  tTemp(TC),                         % Get the index of the current temporary register (type int)
  format('t~w <- load @~w',[TC,ID]). % Generate the intermediate representation

id(ID,var,bool,TC):-
  tTemp(TC),                         % Get the index of the current temporary register (type int)
  format('t~w <- load @~w',[TC,ID]). % Generate the intermediate representation

id(ID,var,real,FC):-
  fTemp(FC),                          % Get the index of the current temporary register (type real)
  format('fp~w <- load @~w',[FC,ID]). % Generate the intermediate representation


%-------------------- Stores

%----------- Local
id_st(ID,local,int,X):-
  tTemp(TC),                             % Get the index of the current temporary register (type int)
  format('@~w <- i_lstore t~w',[ID,TC]), % Generate the intermediate representation
  newTemp(X).                            % Generate a new temporary register (type int)

id_st(ID,local,bool,X):-
  tTemp(TC),                             % Get the index of the current temporary register (type int)
  format('@~w <- i_lstore t~w',[ID,TC]), % Generate the intermediate representation
  newTemp(X).                            % Generate a new temporary register (type int)

id_st(ID,local,real,X):-
  fTemp(FC),                              % Get the index of the current temporary register (type real)
  format('@~w <- r_lstore fp~w',[ID,FC]), % Generate the intermediate representation
  newFTemp(X).                            % Generate a new temporary register (type real)

%----------- Arguments
id_st(ID,arg,int,X):-
  tTemp(TC),                             % Get the index of the current temporary register (type int)
  format('@~w <- i_astore t~w',[ID,TC]), % Generate the intermediate representation
  newTemp(X).                            % Generate a new temporary register (type int)

id_st(ID,arg,bool,X):-
  tTemp(TC),                             % Get the index of the current temporary register (type int)
  format('@~w <- i_astore t~w',[ID,TC]), % Generate the intermediate representation
  newTemp(X).                            % Generate a new temporary register (type int)

id_st(ID,arg,real,X):-
  fTemp(FC),                              % Get the index of the current temporary register (type real)
  format('@~w <- r_astore fp~w',[ID,FC]), % Generate the intermediate representation
  newFTemp(X).                            % Generate a new temporary register (type real)

%----------- Global Variables
id_st(ID,var,int,X):-
  tTemp(TC),                            % Get the index of the current temporary register (type int)
  format('@~w <- i_store t~w',[ID,TC]), % Generate the intermediate representation
  newTemp(X).                            % Generate a new temporary register (type int)

id_st(ID,var,bool,X):-
  tTemp(TC),                            % Get the index of the current temporary register (type int)
  format('@~w <- i_store t~w',[ID,TC]), % Generate the intermediate representation
  newTemp(X).                            % Generate a new temporary register (type int)

id_st(ID,var,real,X):-
  fTemp(FC),                             % Get the index of the current temporary register (type real)
  format('@~w <- r_store fp~w',[ID,FC]), % Generate the intermediate representation
  newFTemp(X).                            % Generate a new temporary register (type real)


%---------------- Load Values

int_literal(ID,TC):-
  tTemp(TC),                           % Get the index of the current temporary register (type int)
  format('t~w <- i_value ~w',[TC,ID]). % Generate the intermediate representation

real_literal(ID,FC):-
  fTemp(FC),                            % Get the index of the current temporary register (type real)
  format('fp~w <- r_value ~w',[FC,ID]). % Generate the intermediate representation

%---------------- Booleans (True and False)

bool(t,T):-
  tTemp(T),                            % Get the index of the current temporary register (type int)
  format('t~w <- i_value ~w',[T,'1']). % Generate the intermediate representation

bool(f,T):-
  tTemp(T),                            % Get the index of the current temporary register (type int)
  format('t~w <- i_value ~w',[T,'0']). % Generate the intermediate representation
