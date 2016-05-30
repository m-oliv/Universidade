<?php
//classe para fazer a ligação a base de dados
class DB_CONNECT {

    // constructor
    function __construct() {
        // conecta a base de dados
        $this->connect();
    }

    // destructor
    function __destruct() {
        // fecha a coneccao a base de dados
        $this->close();
    }

    /**
     * Funcao para conectar a base de dados
     */
    function connect() {
        // importar variaveis de coneccao
        require_once __DIR__ . '/db_config.php';

        // Concetar a base de dados 
        //$con = mysql_connect(DB_SERVER, DB_USER, DB_PASSWORD) or die(mysql_error());
       $con="host=".DB_SERVER." port=".DB_PORT." dbname=".DB_DATABASE." user=".DB_USER." password=".DB_PASSWORD."";
       $db=pg_connect($con) or die('connection failed');

        // selecionar base de dados
        //$db = mysql_select_db(DB_DATABASE) or die(mysql_error()) or die(mysql_error());

        // retorna o cursor da coneccao
        return $db;
    }

    /**
     * Funcao para fechar a coneccao com a base de dados
     */
    function close() {
        // fecha a coneccao a base de dados
        pg_close();
    }

}

?>