:- initialization(main).

main :- 
    lerArvore(ArvAbs).

lerArvore(ArvAbs) :- arvAbs( [] , [ArvAbs] ).

arvAbs(Pa,Pd) :- 
		write(Pa),nl, 
		read(Pa-D1), !,
		arvAbs(D1,Pd).
arvAbs(Pa,Pa).
