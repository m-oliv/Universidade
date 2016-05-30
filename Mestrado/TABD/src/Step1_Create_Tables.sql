CREATE TABLE equipa(
	id_equipa INTEGER NOT NULL,
	pais VARCHAR(20) NOT NULL,
	treinador VARCHAR(50),
	PRIMARY KEY(id_equipa)
	);


CREATE TABLE jogadores (
	id_jogador INTEGER NOT NULL,
	nome VARCHAR(50) NOT NULL,
	idade INTEGER,
	nacionalidade VARCHAR(20) NOT NULL,
	equipa INTEGER,
	PRIMARY KEY(id_jogador),
	FOREIGN KEY(equipa) REFERENCES equipa(id_equipa) ON DELETE CASCADE
	);


CREATE TABLE jogos(
	id_jogo INTEGER NOT NULL,
	estadio VARCHAR(20) NOT NULL,
	resultado VARCHAR(3) NOT NULL,
	equipa1 INTEGER NOT NULL,
	equipa2 INTEGER NOT NULL,
	PRIMARY KEY(id_jogo),
	FOREIGN KEY(equipa1) REFERENCES equipa(id_equipa) ON DELETE CASCADE,
	FOREIGN KEY(equipa2) REFERENCES equipa(id_equipa) ON DELETE CASCADE
	);

CREATE TABLE golos(
	id_jogador INTEGER NOT NULL,
	num_golos INTEGER NOT NULL,
	PRIMARY KEY(id_jogador),
	FOREIGN KEY(id_jogador) REFERENCES jogadores(id_jogador) ON DELETE CASCADE
	);