	CREATE OR REPLACE FUNCTION "criarHistorico2"() RETURNS text AS
$BODY1$DECLARE
 
	getAllTablesQuery text := E'SELECT table_name FROM information_schema.tables WHERE table_schema = \'public\' ORDER BY table_name;';
	getAllTablesQueryCount text := E'WITH temp as (SELECT table_name FROM information_schema.tables WHERE table_schema = \'public\' ) SELECT count(table_name) FROM temp;';
	findForeignKeysQuery text := E'SELECT tc.constraint_name, tc.table_name, kcu.column_name, ccu.table_name AS foreign_table_name, ccu.column_name AS foreign_column_name FROM information_schema.table_constraints AS tc JOIN information_schema.key_column_usage AS kcu ON tc.constraint_name = kcu.constraint_name JOIN information_schema.constraint_column_usage AS ccu ON ccu.constraint_name = tc.constraint_name WHERE constraint_type = \'FOREIGN KEY\';';
	getCurrentTableQuery text;
	getCurrentTableQueryCount text;
	tableCreateQuery text;
	tableCreateTrigger text;
	tableCreateTriggerReal text;
	tableCreateTriggerUp text;
	tableCreateTriggerRealUp text;
	tableCreateTriggerIns text;
	tableCreateTriggerRealIns text;
	_Ghosts text [];
	_Tables text [];
	_TablesToGhost text [];
	_nTables int;
	_result text := '';
	
	
--Qual é a ideia aqui?
--Basicamente: 
-- get all tables dá todas as tabelas.
-- verificar se tablename_ghost existe.
-- Não existindo, verificar constraints de foreign keys e primary keys.
-- Nota: sendo a função chamada sobre uma base de dados que existe,
-- NÃO importa verificar outras restrições.
-- Recriar a tabela ghost com as constraints encontradas
-- continuar o loop até ver todas as tabelas.


--Isto não permite parâmetros, portanto o histórico é APENAS E SÓ sobre _toda_ a base de dados.
--Parametrizar a função ia permitir que o ciclo que percorre a BD principal tivesse apenas 1 iteração.


--Problema grave: ORDEM das bases de dados. Como criar foreign keys sem ter todas as tabelas ghost criadas?
--Solução: Primeiro cria tudo, depois aplica constraints.

--Alterações devem ser looped carefully nos triggers de update e delete.
--Inserts, however, n causam raia.


	_tempResult text;
	_arrayResult record;
	positionGhosts int=0;
	positionTables int=0;
	positionNewTables int=0;
	maxColumns int=0;
	--Serve para inserir as colunas. (conta o # de cols)
	tempCounter int=0;
	
BEGIN

	
	BEGIN
		EXECUTE getAllTablesQueryCount INTO _nTables;
		--neste ponto não houve excepção, portanto TEMOS todos os table names.= 0 ;
		FOR _tempResult IN EXECUTE getAllTablesQuery 
		LOOP
			--_ghost indica uma tabela fantasma e é uma das restrições do programa. Tabelas com o nome _ghost são ignoradas.
			IF position('_hist' in _tempResult) > 0 THEN
				_Ghosts[positionGhosts]=_tempResult;
				positionGhosts := positionGhosts + 1;
			ELSE 
				_Tables[positionTables]=_tempResult;
				positionTables := positionTables + 1;
			
			END IF;
			
			_result := _result || _tempResult || ';'; --debug
		END LOOP;
		EXCEPTION WHEN undefined_column THEN
			END;
	--Neste ponto temos uma de duas situações: Excepção (não deve ocorrer...) ou Ghosts e Tables preenchido
	--com os nomes das tabelas ghost ou normais. Vamos, de seguida, procurar
	--quais os ghosts que estão relacionados com as tabelas de Tables para não serem criados.
	
	_result := _result || 'tables: ' ||  positionTables || ';' ||  'Ghosts: ' ||  positionGhosts || ';'; 
	
	IF positionGhosts!=0 THEN
	
		FOR i IN 0..positionTables-1
		LOOP
			FOR j IN 0..positionGhosts-1 
			LOOP
			_tempResult := lower(_Tables[i]) || '_hist';
			IF position(_tempResult in lower(_Ghosts[j])) > 0 THEN
				--Neste ponto temos uma tabela que JÁ tem ghost. Não interessa.
				EXIT;
			END IF;
			IF j = positionGhosts-1 THEN
				--Só verificando todos os ghosts é garantido que não tem ghost, por isso só na última iteração é que se adiciona. às tablestoghost.
				_TablesToGhost[positionNewTables]=_Tables[i];
				positionNewTables = positionNewTables+1;
			END IF;
			END LOOP;
			
		END LOOP;
	ELSE
		_TablesToGhost=_Tables;
		positionNewTables=positionTables;
		
	END IF;
	
	--Neste ponto:
	--_TablesToGhost tem os nomes de todas as tabelas sobre as quais vamos querer CREATE TABLE com o ghost.
	--_Ghosts tem todas as tabelas com _ghost no nome. Note-se que não é verificado que estão
	--bem construídas, potentially defective.
	--Vamos criar todas as tabelas SEM constraints.

	FOR i IN 0..positionNewTables-1
	LOOP
		tableCreateQuery := 'CREATE TABLE ' || _TablesToGhost[i] || '_hist (';
		getCurrentTableQuery := E'select column_name, data_type, character_maximum_length from INFORMATION_SCHEMA.COLUMNS where table_name = \'' || _TablesToGhost[i] || E'\';';

		--Conta quantas colunas há. Necessário para construír a string da query.
		getCurrentTableQueryCount := E'WITH temp AS (select column_name, data_type, character_maximum_length from INFORMATION_SCHEMA.COLUMNS where table_name = \'' || _TablesToGhost[i] || E'\'' || ') select count(column_name) from temp;';
		EXECUTE getCurrentTableQueryCount INTO maxColumns;
		tempCounter=1;

		--Obtém as colunas para _arrayResult.
		FOR _arrayResult IN EXECUTE getCurrentTableQuery
		LOOP
		
			tableCreateQuery := tableCreateQuery || _arrayResult.column_name ;
			tableCreateQuery := tableCreateQuery || ' ' || _arrayResult.data_type ;
			IF _arrayResult.character_maximum_length != 0 THEN
				tableCreateQuery := tableCreateQuery || '(' || _arrayResult.character_maximum_length || ')' ;				
			END IF;
			--separa colunas por vírgulas EXCEPTO a última.
			--IF tempCounter != maxColumns THEN
			--	tableCreateQuery := tableCreateQuery || ',';
			--END IF;
			--tempCounter := tempCounter + 1;
			tableCreateQuery := tableCreateQuery || ',';
		END LOOP;
		tableCreateQuery := tableCreateQuery || 'operacao VARCHAR(20) NOT NULL, utilizador VARCHAR(20) NOT NULL, data_hora_op TIMESTAMP NOT NULL' || ');';
		EXECUTE tableCreateQuery;
	END LOOP;

--Triggers: Delete
	FOR i IN 0..positionTables-1
	LOOP
		tableCreateTrigger := 'CREATE OR REPLACE FUNCTION ' || _Tables[i] || E'_hist_D() RETURNS trigger AS \$BODY\$ ' || 'DECLARE insertIntoHistory varchar; currentDate timestamp; currentUser varchar; currentOp varchar; BEGIN';

		tableCreateTrigger := tableCreateTrigger || ' currentDate := CURRENT_TIMESTAMP; ' || ' currentUser := CURRENT_USER; '|| E' currentOP := \'DELETE\'; ';

		tableCreateTrigger := tableCreateTrigger || ' insertIntoHistory := ' || E'\'INSERT INTO \' || \'' || _Tables[i] || E'_hist\' || \'' || E' VALUES (\'' ;

		getCurrentTableQuery := E'select column_name, data_type, character_maximum_length from INFORMATION_SCHEMA.COLUMNS where table_name = \'' || _Tables[i] || E'\';';

		--Conta quantas colunas há. Necessário para construír a string da query.
		getCurrentTableQueryCount := E'WITH temp AS (select column_name, data_type, character_maximum_length from INFORMATION_SCHEMA.COLUMNS where table_name = \'' || _Tables[i] || E'\'' || ') select count(column_name) from temp;';
		EXECUTE getCurrentTableQueryCount INTO maxColumns;
		tempCounter=1;

		--Obtém as colunas para _arrayResult.
		FOR _arrayResult IN EXECUTE getCurrentTableQuery
		LOOP
		
			tableCreateTrigger := tableCreateTrigger || E'|| E\'\\\'\' ||' || 'OLD.'|| _arrayResult.column_name || E'|| E\'\\\'\' ||' ;
			tableCreateTrigger := tableCreateTrigger || E'\',\'';
		END LOOP;
		tableCreateTrigger := tableCreateTrigger || E'|| E\'\\\'\' ||'  ||  ' currentOp '  ||  E'|| E\'\\\'\' ||'  || E'\',\'' ||  E' || E\'\\\'\'' ||   '|| currentUser ||' ||  E'E\'\\\'\' ||' ||  E'\',\'' || E' || E\'\' ' || '|| ' ||  E'E\'\\\'\'' || '|| currentDate || ' || E'E\'\\\'\'' || ' ||'  ||  E'\');\';';
		tableCreateTrigger := tableCreateTrigger || ' EXECUTE insertIntoHistory;' || ' RETURN NEW;'|| ' END;' ||  ' $BODY$ LANGUAGE plpgsql VOLATILE NOT LEAKPROOF;';
		EXECUTE tableCreateTrigger;
		tableCreateTriggerReal := 'CREATE TRIGGER ' || _Tables[i] || '_H_D' || ' AFTER DELETE ON ' || _Tables[i] || ' FOR EACH ROW EXECUTE PROCEDURE ' || _Tables[i] || '_hist_D()' ;
		EXECUTE tableCreateTriggerReal;
	END LOOP;


--Triggers: Update
	FOR i IN 0..positionTables-1
	LOOP
		tableCreateTriggerUp := 'CREATE OR REPLACE FUNCTION ' || _Tables[i] || E'_hist_U() RETURNS trigger AS \$BODY\$ ' || 'DECLARE insertIntoHistory varchar; currentDate timestamp; currentUser varchar; currentOp varchar; BEGIN';

		tableCreateTriggerUp := tableCreateTriggerUp || ' currentDate := CURRENT_TIMESTAMP; ' || ' currentUser := CURRENT_USER; '|| E' currentOP := \'UPDATE\'; ';

		tableCreateTriggerUp := tableCreateTriggerUp || ' insertIntoHistory := ' || E'\'INSERT INTO \' || \'' || _Tables[i] || E'_hist\' || \'' || E' VALUES (\'' ;

		getCurrentTableQuery := E'select column_name, data_type, character_maximum_length from INFORMATION_SCHEMA.COLUMNS where table_name = \'' || _Tables[i] || E'\';';

		--Conta quantas colunas há. Necessário para construír a string da query.
		getCurrentTableQueryCount := E'WITH temp AS (select column_name, data_type, character_maximum_length from INFORMATION_SCHEMA.COLUMNS where table_name = \'' || _Tables[i] || E'\'' || ') select count(column_name) from temp;';
		EXECUTE getCurrentTableQueryCount INTO maxColumns;
		tempCounter=1;

		--Obtém as colunas para _arrayResult.
		FOR _arrayResult IN EXECUTE getCurrentTableQuery
		LOOP
		
			tableCreateTriggerUp := tableCreateTriggerUp || E'|| E\'\\\'\' ||' || 'OLD.'|| _arrayResult.column_name || E'|| E\'\\\'\' ||' ;
			tableCreateTriggerUp := tableCreateTriggerUp || E'\',\'';
		END LOOP;
		tableCreateTriggerUp := tableCreateTriggerUp || E'|| E\'\\\'\' ||'  ||  ' currentOp '  ||  E'|| E\'\\\'\' ||'  || E'\',\'' ||  E' || E\'\\\'\'' ||   '|| currentUser ||' ||  E'E\'\\\'\' ||' ||  E'\',\'' || E' || E\'\' ' || '|| ' ||  E'E\'\\\'\'' || '|| currentDate || ' || E'E\'\\\'\'' || ' ||'  ||  E'\');\';';
		tableCreateTriggerUp := tableCreateTriggerUp || ' EXECUTE insertIntoHistory;' || ' RETURN NEW;'|| ' END;' ||  ' $BODY$ LANGUAGE plpgsql VOLATILE NOT LEAKPROOF;';
		EXECUTE tableCreateTriggerUp;
		tableCreateTriggerRealUp := 'CREATE TRIGGER ' || _Tables[i] || '_H_U' || ' AFTER UPDATE ON ' || _Tables[i] || ' FOR EACH ROW EXECUTE PROCEDURE ' || _Tables[i] || '_hist_U()' ;
		EXECUTE tableCreateTriggerRealUp;
	END LOOP;

--Triggers: Insert
	FOR i IN 0..positionTables-1
	LOOP
		tableCreateTriggerIns := 'CREATE OR REPLACE FUNCTION ' || _Tables[i] || E'_hist_I() RETURNS trigger AS \$BODY\$ ' || 'DECLARE insertIntoHistory varchar; currentDate timestamp; currentUser varchar; currentOp varchar; BEGIN';

		tableCreateTriggerIns := tableCreateTriggerIns || ' currentDate := CURRENT_TIMESTAMP; ' || ' currentUser := CURRENT_USER; '|| E' currentOP := \'INSERT\'; ';

		tableCreateTriggerIns := tableCreateTriggerIns || ' insertIntoHistory := ' || E'\'INSERT INTO \' || \'' || _Tables[i] || E'_hist\' || \'' || E' VALUES (\'' ;

		getCurrentTableQuery := E'select column_name, data_type, character_maximum_length from INFORMATION_SCHEMA.COLUMNS where table_name = \'' || _Tables[i] || E'\';';

		--Conta quantas colunas há. Necessário para construír a string da query.
		getCurrentTableQueryCount := E'WITH temp AS (select column_name, data_type, character_maximum_length from INFORMATION_SCHEMA.COLUMNS where table_name = \'' || _Tables[i] || E'\'' || ') select count(column_name) from temp;';
		EXECUTE getCurrentTableQueryCount INTO maxColumns;
		tempCounter=1;

		--Obtém as colunas para _arrayResult.
		FOR _arrayResult IN EXECUTE getCurrentTableQuery
		LOOP
		
			tableCreateTriggerIns := tableCreateTriggerIns || E'|| E\'\\\'\' ||' || 'NEW.'|| _arrayResult.column_name || E'|| E\'\\\'\' ||' ;
			tableCreateTriggerIns := tableCreateTriggerIns || E'\',\'';
		END LOOP;
		tableCreateTriggerIns := tableCreateTriggerIns || E'|| E\'\\\'\' ||'  ||  ' currentOp '  ||  E'|| E\'\\\'\' ||'  || E'\',\'' ||  E' || E\'\\\'\'' ||   '|| currentUser ||' ||  E'E\'\\\'\' ||' ||  E'\',\'' || E' || E\'\' ' || '|| ' ||  E'E\'\\\'\'' || '|| currentDate || ' || E'E\'\\\'\'' || ' ||'  ||  E'\');\';';
		tableCreateTriggerIns := tableCreateTriggerIns || ' EXECUTE insertIntoHistory;' || ' RETURN NEW;'|| ' END;' ||  ' $BODY$ LANGUAGE plpgsql VOLATILE NOT LEAKPROOF;';
		EXECUTE tableCreateTriggerIns;
		tableCreateTriggerRealIns := 'CREATE TRIGGER ' || _Tables[i] || '_H_I' || ' AFTER INSERT ON ' || _Tables[i] || ' FOR EACH ROW EXECUTE PROCEDURE ' || _Tables[i] || '_hist_I()' ;
		EXECUTE tableCreateTriggerRealIns;
	END LOOP;
	RETURN _result;
END;
$BODY1$
LANGUAGE plpgsql VOLATILE NOT LEAKPROOF
COST 100;


SELECT "criarHistorico"();