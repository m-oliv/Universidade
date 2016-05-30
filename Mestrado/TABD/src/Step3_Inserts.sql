INSERT INTO equipa VALUES(1,'Alemanha','Joachim Loew');
INSERT INTO equipa(id_equipa,pais,treinador) VALUES(2,'Brasil','Luiz Felipe Scolari');
INSERT INTO equipa(id_equipa,pais,treinador) VALUES(3,'Portugal','Paulo Bento');
INSERT INTO equipa(id_equipa,pais,treinador) VALUES(4,'Inglaterra','Roy Hodgson');


INSERT INTO jogadores(id_jogador,nome,idade,nacionalidade,equipa) VALUES(1,'Thomas Muller',24,'Alemã',1);
INSERT INTO jogadores(id_jogador,nome,idade,nacionalidade,equipa) VALUES(2,'Mesut Ozil',25,'Alemã',1);
INSERT INTO jogadores(id_jogador,nome,idade,nacionalidade,equipa) VALUES(3,'Bastian Schweinsteiger',29,'Alemã',1);
INSERT INTO jogadores(id_jogador,nome,idade,nacionalidade,equipa) VALUES(4,'Mats Hummels',25,'Alemã',1);

INSERT INTO jogadores(id_jogador,nome,idade,nacionalidade,equipa) VALUES(5,'Júlio César',34,'Brasileira',2);
INSERT INTO jogadores(id_jogador,nome,idade,nacionalidade,equipa) VALUES(6,'David Luiz',27,'Brasileira',2);
INSERT INTO jogadores(id_jogador,nome,idade,nacionalidade,equipa) VALUES(7,'Marcelo',26,'Brasileira',2);
INSERT INTO jogadores(id_jogador,nome,idade,nacionalidade,equipa) VALUES(8,'Neymar',22,'Brasileira',2);

INSERT INTO jogadores(id_jogador,nome,idade,nacionalidade,equipa) VALUES(9,'Eduardo Carvalho',31,'Portuguesa',3);
INSERT INTO jogadores(id_jogador,nome,idade,nacionalidade,equipa) VALUES(10,'Fábio Coentrão',26,'Portuguesa',3);
INSERT INTO jogadores(id_jogador,nome,idade,nacionalidade,equipa) VALUES(11,'Raul Meireles',31,'Portuguesa',3);
INSERT INTO jogadores(id_jogador,nome,idade,nacionalidade,equipa) VALUES(12,'Cristiano Ronaldo',29,'Portuguesa',3);

INSERT INTO jogadores(id_jogador,nome,idade,nacionalidade,equipa) VALUES(13,'Joe Hart',27,'Inglesa',4);
INSERT INTO jogadores(id_jogador,nome,idade,nacionalidade,equipa) VALUES(16,'Steven Gerrard',34,'Inglesa',4);
INSERT INTO jogadores(id_jogador,nome,idade,nacionalidade,equipa) VALUES(14,'Frank Lampard',35,'Inglesa',4);
INSERT INTO jogadores(id_jogador,nome,idade,nacionalidade,equipa) VALUES(15,'Wayne Rooney',28,'Inglesa',4);



INSERT INTO jogos(id_jogo,estadio,resultado,equipa1,equipa2) VALUES(1,'Estádio do Maracanã','2-1',1,2);
INSERT INTO jogos(id_jogo,estadio,resultado,equipa1,equipa2) VALUES(2,'Arena de São Paulo','3-5',3,2);
INSERT INTO jogos(id_jogo,estadio,resultado,equipa1,equipa2) VALUES(3,'Estádio Castelão','2-3',4,2);
INSERT INTO jogos(id_jogo,estadio,resultado,equipa1,equipa2) VALUES(4,'Arena Pantanal','1-5',1,3);
INSERT INTO jogos(id_jogo,estadio,resultado,equipa1,equipa2) VALUES(5,'Estádio Castelão','1-0',1,4);
INSERT INTO jogos(id_jogo,estadio,resultado,equipa1,equipa2) VALUES(6,'Estádio do Maracanã','1-0',3,4);

INSERT INTO golos(id_jogador,num_golos) VALUES(1,4);
INSERT INTO golos(id_jogador,num_golos) VALUES(8,5);
INSERT INTO golos(id_jogador,num_golos) VALUES(7,4);
INSERT INTO golos(id_jogador,num_golos) VALUES(10,5);
INSERT INTO golos(id_jogador,num_golos) VALUES(11,4);
INSERT INTO golos(id_jogador,num_golos) VALUES(14,3);
INSERT INTO golos(id_jogador,num_golos) VALUES(16,1);  