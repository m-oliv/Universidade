%Condicoes
% valor(registo,valor do registo)
valor(a,va).
valor(b,vb).
valor(c,vc).
valor(d,vd).
valor(e,ve).

/*Acções
afectar_r(R1,R2).
somar_r(R1,R2,R3).
*/
accao(afectar_r(valor(R1,VR1),valor(R2,VR2)), [] ,[valor(R2,VR1)],[valor(R2,VR2)]).
accao(somar_r(valor(R1,VR1),valor(R2,VR2),valor(R3,VR3)),[],[valor(R3,VR1+VR2)],[valor(R3,VR3)]).

/*Estados*/

estado_inicial([valor(a,va),valor(b,vb),valor(c,vc),valor(d,vd),valor(e,ve)]).
estado_final([valor(a,vb),valor(b,va),valor(c,vb),valor(d,vd),valor(e,ve)]).
 /* estado_final([valor(a,vb),valor(b,va),valor(c,va+vb),valor(d,vc),valor(e,va)]). */