<!doctype html>
<?php
// class da ligacao a base de dados
require_once __DIR__ . '/db_connect.php';
require_once __DIR__ . '/db_config.php';

//ligar a base de dados
//echo extension_loaded('pgsql') ? 'yes':'no';
$db = new DB_CONNECT();

?>

<head>
  <meta charset="utf-8">
  <title>Campeonato do Mundo da FIFA 2014</title>
  <meta name="description" content="Welcome to my basic template.">
  <link rel="stylesheet" href="css/style.css?v=1">
  <link rel="stylesheet" href="http://yui.yahooapis.com/pure/0.5.0/pure-min.css">
<style type="text/css">
.tg  {border-collapse:collapse;border-spacing:0;border-color:#bbb;margin:0px auto;}
.tg td{font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#bbb;color:#594F4F;background-color:#E0FFEB;opacity: 0.7;}
.tg th{font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#bbb;color:#493F3F;background-color:#9DE0AD;opacity: 0.7;}
</style>

  <style>
    body {
      text-align: center;
      background-color:rgba(1,1,1,0.3);
	  background-image:url('fifa1.jpg');
      background-size:cover;
      background-position:center;
      color: white;
      font-family: helvetica;
    }

     ul{
      padding:10px;
      background-color:rgba(224,224,224,0.3);
    }
    li{
      display:inline;
      padding:0px 10px 0px 10px;
    }
    a{
		color: white;

    }

    p{
      font-size:22px;
      }
      input
      {
        border:0;
        }
         input[type="submit"]
      {
        background:red;
        color:white;
        }
  </style>
</head>

 
<body>
    <div id="wrapper">
    	<header>
            <h1>Campeonato do Mundo da FIFA 2014</h1>
			<br><br>
		<nav>
            	<ul>
                    <li><a rel="external" href="test.php">Inicio</a></li>
					<li><a rel="external" href="estatisticas.php">Estatisticas dos dados</a></li>
                    <li><a rel="external" href="estatisticashist.php">Estatisticas do histórico</a></li>
                  
                </ul>	
            </nav>
        </header>
		
        <body>
			<div id="core" class="clearfix">
				<section id="left">
				<br><br>

				
				  
			<?php
					$tabela = $_POST["tabelap"];
					//echo $tabela;
					
					//EQUIPA
					if ($tabela=="equipa")
					{
						$tabela="equipa_hist";
						$tabelaString="Equipa";
					}
					if ($tabela=="jogadores")
					{
						$tabela="jogadores_hist";
						$tabelaString="Jogadores";
					}
					if ($tabela=="jogos")
					{
						$tabela="jogos_hist";
						$tabelaString="Jogos";
					}
					if ($tabela=="golos")
					{
						$tabela="golos_hist";
						$tabelaString="Golos";
					}
					
					if ($tabela=="equipa_hist")
					{
						echo "<p>Historico da Tabela ".$tabelaString."</p>";
						echo "<table class='tg'>";
						echo "<tr>";
							echo "<th class='tg-031e'>Id Equipa</th>";
							echo "<th class='tg-031e'>Pais</th>";
							echo "<th class='tg-031e'>Treinador</th>";
							echo "<th class='tg-031e'>Operacao</th>";
							echo "<th class='tg-031e'>Utilizador</th>";
							echo "<th class='tg-031e'>Data da operacao</th>";
						echo "</tr>";
						$result = pg_query("SELECT * FROM ".$tabela.";") or die(mysql_error());
						while ($row = pg_fetch_array($result)) {
							$result_reaad1= $row[0];
							$result_reaad2= $row[1];
							$result_reaad3= $row[2];
							$result_reaad4= $row[3];
							$result_reaad5= $row[4];
							$result_reaad6= $row[5];
								echo "<tr>";
									echo "<td class='tg-031e'>$result_reaad1</td>";
									echo "<td class='tg-031e'>$result_reaad2</td>";
									echo "<td class='tg-031e'>$result_reaad3</td>";
									echo "<td class='tg-031e'>$result_reaad4</td>";
									echo "<td class='tg-031e'>$result_reaad5</td>";
									echo "<td class='tg-031e'>$result_reaad6</td>";
							   echo "</tr>";
						}
						echo "</table>";
						
						
						
						//DELETE
						 echo "<br><br>";
						 echo "<p>Dados removidos da tabela ".$tabelaString."</p>";
						echo "<table class='tg'>";
						echo "<tr>";
							echo "<th class='tg-031e'>Id Equipa</th>";
							echo "<th class='tg-031e'>Pais</th>";
							echo "<th class='tg-031e'>Treinador</th>";
							echo "<th class='tg-031e'>Operacao</th>";
							echo "<th class='tg-031e'>Utilizador</th>";
							echo "<th class='tg-031e'>Data da operacao</th>";
						echo "</tr>";
						$result = pg_query("SELECT * FROM ".$tabela." WHERE operacao = 'DELETE';") or die(mysql_error());
						while ($row = pg_fetch_array($result)) {
							$result_reaad1= $row[0];
							$result_reaad2= $row[1];
							$result_reaad3= $row[2];
							$result_reaad4= $row[3];
							$result_reaad5= $row[4];
							$result_reaad6= $row[5];
								echo "<tr>";
									echo "<td class='tg-031e'>$result_reaad1</td>";
									echo "<td class='tg-031e'>$result_reaad2</td>";
									echo "<td class='tg-031e'>$result_reaad3</td>";
									echo "<td class='tg-031e'>$result_reaad4</td>";
									echo "<td class='tg-031e'>$result_reaad5</td>";
									echo "<td class='tg-031e'>$result_reaad6</td>";
							   echo "</tr>";
						}
						echo "</table>";
				}
				
				//JOGADORES
					if ($tabela=="jogadores_hist")
					{
						echo "<p>Historico da Tabela ".$tabelaString."</p>";
						echo "<table class='tg'>";
							echo "<tr>";
						echo "<th class='tg-031e'>Id Jogador</th>";
						echo "<th class='tg-031e'>Nome</th>";
						echo "<th class='tg-031e'>Idade</th>";
						echo "<th class='tg-031e'>Nacionalidade</th>";
						echo "<th class='tg-031e'>Operacao</th>";
						echo "<th class='tg-031e'>Utilizador</th>";
						echo "<th class='tg-031e'>Data da operacao</th>";
					echo "</tr>";
						$result = pg_query("SELECT * FROM ".$tabela.";") or die(mysql_error());
						while ($row = pg_fetch_array($result)) {
						$result_reaad1= $row[0];
						$result_reaad2= $row[1];
						$result_reaad3= $row[2];
						$result_reaad4= $row[3];
						$result_reaad5= $row[5];
						$result_reaad6= $row[6];
						$result_reaad7= $row[7];
							echo "<tr>";
								echo "<td class='tg-031e'>$result_reaad1</td>";
								echo "<td class='tg-031e'>$result_reaad2</td>";
								echo "<td class='tg-031e'>$result_reaad3</td>";
								echo "<td class='tg-031e'>$result_reaad4</td>";
								echo "<td class='tg-031e'>$result_reaad5</td>";
								echo "<td class='tg-031e'>$result_reaad6</td>";
								echo "<td class='tg-031e'>$result_reaad7</td>";
						   echo "</tr>";
					}
						echo "</table>";
						
						
						
						//DELETE
						 echo "<br><br>";
						 echo "<p>Dados removidos da tabela ".$tabelaString."</p>";
						echo "<table class='tg'>";
						echo "<tr>";
							echo "<th class='tg-031e'>Id Jogador</th>";
							echo "<th class='tg-031e'>Nome</th>";
							echo "<th class='tg-031e'>Idade</th>";
							echo "<th class='tg-031e'>Nacionalidade</th>";
							echo "<th class='tg-031e'>Operacao</th>";
							echo "<th class='tg-031e'>Utilizador</th>";
							echo "<th class='tg-031e'>Data da operacao</th>";
						echo "</tr>";
						$result = pg_query("SELECT * FROM ".$tabela." WHERE operacao = 'DELETE';") or die(mysql_error());
						while ($row = pg_fetch_array($result)) {
						$result_reaad1= $row[0];
						$result_reaad2= $row[1];
						$result_reaad3= $row[2];
						$result_reaad4= $row[3];
						$result_reaad5= $row[5];
						$result_reaad6= $row[6];
						$result_reaad7= $row[7];
							echo "<tr>";
								echo "<td class='tg-031e'>$result_reaad1</td>";
								echo "<td class='tg-031e'>$result_reaad2</td>";
								echo "<td class='tg-031e'>$result_reaad3</td>";
								echo "<td class='tg-031e'>$result_reaad4</td>";
								echo "<td class='tg-031e'>$result_reaad5</td>";
								echo "<td class='tg-031e'>$result_reaad6</td>";
								echo "<td class='tg-031e'>$result_reaad7</td>";
						   echo "</tr>";
					}
						echo "</table>";
				}				
				
				//JOGOS
					if ($tabela=="jogos_hist")
					{
						echo "<p>Historico da Tabela ".$tabelaString."</p>";
						echo "<table class='tg'>";
						echo "<tr>";
							echo "<th class='tg-031e'>Id Jogo</th>";
							echo "<th class='tg-031e'>Estadio</th>";
							echo "<th class='tg-031e'>Resultado</th>";
							echo "<th class='tg-031e'>Primeira Equipa</th>";
							echo "<th class='tg-031e'>Segunda Equipa</th>";
							echo "<th class='tg-031e'>Operacao</th>";
							echo "<th class='tg-031e'>Utilizador</th>";
							echo "<th class='tg-031e'>Data da operacao</th>";
						echo "</tr>";
						$result = pg_query("SELECT * FROM ".$tabela.";") or die(mysql_error());
						while ($row = pg_fetch_array($result)) {
						$result_reaad1= $row[0];
						$result_reaad2= $row[1];
						$result_reaad3= $row[2];
						$result_reaad4= $row[3];
						$result_reaad5= $row[4];
						$result_reaad6= $row[5];
						$result_reaad7= $row[6];
						$result_reaad8= $row[7];
							echo "<tr>";
								echo "<td class='tg-031e'>$result_reaad1</td>";
								echo "<td class='tg-031e'>$result_reaad2</td>";
								echo "<td class='tg-031e'>$result_reaad3</td>";
								echo "<td class='tg-031e'>$result_reaad4</td>";
								echo "<td class='tg-031e'>$result_reaad5</td>";
								echo "<td class='tg-031e'>$result_reaad6</td>";
								echo "<td class='tg-031e'>$result_reaad7</td>";
								echo "<td class='tg-031e'>$result_reaad8</td>";
						   echo "</tr>";
					}
						echo "</table>";
						
						
						
						//DELETE
						 echo "<br><br>";
						 echo "<p>Dados removidos da tabela ".$tabelaString."</p>";
						echo "<table class='tg'>";
						echo "<tr>";
							echo "<th class='tg-031e'>Id Jogo</th>";
							echo "<th class='tg-031e'>Estadio</th>";
							echo "<th class='tg-031e'>Resultado</th>";
							echo "<th class='tg-031e'>Primeira Equipa</th>";
							echo "<th class='tg-031e'>Segunda Equipa</th>";
							echo "<th class='tg-031e'>Operacao</th>";
							echo "<th class='tg-031e'>Utilizador</th>";
							echo "<th class='tg-031e'>Data da operacao</th>";
						echo "</tr>";
						$result = pg_query("SELECT * FROM ".$tabela." WHERE operacao = 'DELETE';") or die(mysql_error());
						while ($row = pg_fetch_array($result)) {
						$result_reaad1= $row[0];
						$result_reaad2= $row[1];
						$result_reaad3= $row[2];
						$result_reaad4= $row[3];
						$result_reaad5= $row[4];
						$result_reaad6= $row[5];
						$result_reaad7= $row[6];
						$result_reaad8= $row[7];
							echo "<tr>";
								echo "<td class='tg-031e'>$result_reaad1</td>";
								echo "<td class='tg-031e'>$result_reaad2</td>";
								echo "<td class='tg-031e'>$result_reaad3</td>";
								echo "<td class='tg-031e'>$result_reaad4</td>";
								echo "<td class='tg-031e'>$result_reaad5</td>";
								echo "<td class='tg-031e'>$result_reaad6</td>";
								echo "<td class='tg-031e'>$result_reaad7</td>";
								echo "<td class='tg-031e'>$result_reaad8</td>";
						   echo "</tr>";
					}
						echo "</table>";
				}				
				
				
				//GOLOS
					if ($tabela=="golos_hist")
					{
						echo "<p>Historico da Tabela ".$tabelaString."</p>";
						echo "<table class='tg'>";
						echo "<tr>";
							echo "<th class='tg-031e'>Jogador</th>";
							echo "<th class='tg-031e'>Numero de golos</th>";
							echo "<th class='tg-031e'>Operacao</th>";
							echo "<th class='tg-031e'>Utilizador</th>";
							echo "<th class='tg-031e'>Data da operacao</th>";
						echo "</tr>";
						$result = pg_query("SELECT * FROM ".$tabela.";") or die(mysql_error());
							while ($row = pg_fetch_array($result)) {
												$result_reaad1= $row[0];
												$result_reaad2= $row[1];
												$result_reaad3= $row[2];
												$result_reaad4= $row[3];
												$result_reaad5= $row[4];
													echo "<tr>";
														echo "<td class='tg-031e'>$result_reaad1</td>";
														echo "<td class='tg-031e'>$result_reaad2</td>";
														echo "<td class='tg-031e'>$result_reaad3</td>";
														echo "<td class='tg-031e'>$result_reaad4</td>";
														echo "<td class='tg-031e'>$result_reaad5</td>";
												   echo "</tr>";
											}
						echo "</table>";
						
						
						
						//DELETE
						 echo "<br><br>";
						 echo "<p>Dados removidos da tabela ".$tabelaString."</p>";
						echo "<table class='tg'>";
						echo "<tr>";
							echo "<th class='tg-031e'>Jogador</th>";
							echo "<th class='tg-031e'>Numero de golos</th>";
							echo "<th class='tg-031e'>Operacao</th>";
							echo "<th class='tg-031e'>Utilizador</th>";
							echo "<th class='tg-031e'>Data da operacao</th>";
						echo "</tr>";
						$result = pg_query("SELECT * FROM ".$tabela." WHERE operacao = 'DELETE';") or die(mysql_error());
							while ($row = pg_fetch_array($result)) {
						$result_reaad1= $row[0];
						$result_reaad2= $row[1];
						$result_reaad3= $row[2];
						$result_reaad4= $row[3];
						$result_reaad5= $row[4];
							echo "<tr>";
								echo "<td class='tg-031e'>$result_reaad1</td>";
								echo "<td class='tg-031e'>$result_reaad2</td>";
								echo "<td class='tg-031e'>$result_reaad3</td>";
								echo "<td class='tg-031e'>$result_reaad4</td>";
								echo "<td class='tg-031e'>$result_reaad5</td>";
						   echo "</tr>";
					}	
						echo "</table>";
				}				
				
				
				//número de operações totais sobre uma tabela
					echo "<br><br>";
					echo "<p>Numero de operações totais sobre a tabela ".$tabelaString."</p>";
					echo "<table class='tg'>";
					echo "<tr>";
						echo "<th class='tg-031e'>Numero de operações</th>";
					echo "</tr>";

					$result = pg_query("SELECT count(operacao) FROM ".$tabela.";") or die(mysql_error());
					while ($row = pg_fetch_array($result)) {
						$result_reaad1= $row[0];
						
							echo "<tr>";
								echo "<td class='tg-031e'>$result_reaad1</td>";
						   echo "</tr>";
					}
					echo "</table>";
					
					//contar inserts
					echo "<br><br>";
					echo "<p>Numero de INSERT na tabela ".$tabelaString."</p>";
					echo "<table class='tg'>";
					echo "<tr>";
						echo "<th class='tg-031e'>Numero de INSERT</th>";
					echo "</tr>";

					$result = pg_query("SELECT count(operacao) FROM ".$tabela." WHERE operacao = 'INSERT';") or die(mysql_error());
					while ($row = pg_fetch_array($result)) {
						$result_reaad1= $row[0];
						
							echo "<tr>";
								echo "<td class='tg-031e'>$result_reaad1</td>";
						   echo "</tr>";
					}
					echo "</table>";
					
					//contar UPDATE
					echo "<br><br>";
					echo "<p>Numero de UPDATE na tabela ".$tabelaString."</p>";
					echo "<table class='tg'>";
					echo "<tr>";
						echo "<th class='tg-031e'>Numero de UPDATE</th>";
					echo "</tr>";

					$result = pg_query("SELECT count(operacao) FROM ".$tabela." WHERE operacao = 'UPDATE';") or die(mysql_error());
					while ($row = pg_fetch_array($result)) {
						$result_reaad1= $row[0];
						
							echo "<tr>";
								echo "<td class='tg-031e'>$result_reaad1</td>";
						   echo "</tr>";
					}
					echo "</table>";
					
					//contar DELETE
					echo "<br><br>";
					echo "<p>Numero de DELETE na tabela ".$tabelaString."</p>";
					echo "<table class='tg'>";
					echo "<tr>";
						echo "<th class='tg-031e'>Numero de DELETE</th>";
					echo "</tr>";

					$result = pg_query("SELECT count(operacao) FROM ".$tabela." WHERE operacao = 'DELETE';") or die(mysql_error());
					while ($row = pg_fetch_array($result)) {
						$result_reaad1= $row[0];
						
							echo "<tr>";
								echo "<td class='tg-031e'>$result_reaad1</td>";
						   echo "</tr>";
					}
					echo "</table>";
					
					
					if ($tabela=="equipa_hist")
					{
					//listar as alterações feitas num determinado intervalo de tempo
					 echo "<br><br>";
					 echo "<p>Alterações feitas num determinado intervalo de tempo na tabela ".$tabelaString."</p>";
					echo "<table class='tg'>";
					echo "<tr>";
						echo "<th class='tg-031e'>Id Equipa</th>";
						echo "<th class='tg-031e'>Pais</th>";
						echo "<th class='tg-031e'>Treinador</th>";
						echo "<th class='tg-031e'>Operacao</th>";
						echo "<th class='tg-031e'>Utilizador</th>";
						echo "<th class='tg-031e'>Data da operacao</th>";
					echo "</tr>";
					$result = pg_query("SELECT * FROM ".$tabela." WHERE data_hora_op BETWEEN TO_TIMESTAMP('2014-06-19 00:06:00', 'YYYY-MM-DD hh24:mi:ss') AND TO_TIMESTAMP('2014-06-25 01:50:00', 'YYYY-MM-DD hh24:mi:ss');") or die(mysql_error());
					while ($row = pg_fetch_array($result)) {
						$result_reaad1= $row[0];
						$result_reaad2= $row[1];
						$result_reaad3= $row[2];
						$result_reaad4= $row[3];
						$result_reaad5= $row[4];
						$result_reaad6= $row[5];
							echo "<tr>";
								echo "<td class='tg-031e'>$result_reaad1</td>";
								echo "<td class='tg-031e'>$result_reaad2</td>";
								echo "<td class='tg-031e'>$result_reaad3</td>";
								echo "<td class='tg-031e'>$result_reaad4</td>";
								echo "<td class='tg-031e'>$result_reaad5</td>";
								echo "<td class='tg-031e'>$result_reaad6</td>";
						   echo "</tr>";
					}
					echo "</table>";
				}
				if ($tabela=="jogadores_hist")
					{
					//listar as alterações feitas num determinado intervalo de tempo
					 echo "<br><br>";
					 echo "<p>Alterações feitas num determinado intervalo de tempo na tabela ".$tabelaString."</p>";
					echo "<table class='tg'>";
					echo "<tr>";
						echo "<th class='tg-031e'>Id Jogador</th>";
						echo "<th class='tg-031e'>Nome</th>";
						echo "<th class='tg-031e'>Idade</th>";
						echo "<th class='tg-031e'>Nacionalidade</th>";
						echo "<th class='tg-031e'>Operacao</th>";
						echo "<th class='tg-031e'>Utilizador</th>";
						echo "<th class='tg-031e'>Data da operacao</th>";
					echo "</tr>";
					$result = pg_query("SELECT * FROM ".$tabela." WHERE data_hora_op BETWEEN TO_TIMESTAMP('2014-06-19 00:06:00', 'YYYY-MM-DD hh24:mi:ss') AND TO_TIMESTAMP('2014-06-25 01:50:00', 'YYYY-MM-DD hh24:mi:ss');") or die(mysql_error());
					while ($row = pg_fetch_array($result)) {
						$result_reaad1= $row[0];
						$result_reaad2= $row[1];
						$result_reaad3= $row[2];
						$result_reaad4= $row[3];
						$result_reaad5= $row[5];
						$result_reaad6= $row[6];
						$result_reaad7= $row[7];
							echo "<tr>";
								echo "<td class='tg-031e'>$result_reaad1</td>";
								echo "<td class='tg-031e'>$result_reaad2</td>";
								echo "<td class='tg-031e'>$result_reaad3</td>";
								echo "<td class='tg-031e'>$result_reaad4</td>";
								echo "<td class='tg-031e'>$result_reaad5</td>";
								echo "<td class='tg-031e'>$result_reaad6</td>";
								echo "<td class='tg-031e'>$result_reaad7</td>";
						   echo "</tr>";
					}
					echo "</table>";
				}
				if ($tabela=="jogos_hist")
					{
					//listar as alterações feitas num determinado intervalo de tempo
					 echo "<br><br>";
					 echo "<p>Alterações feitas num determinado intervalo de tempo na tabela ".$tabelaString."</p>";
					echo "<table class='tg'>";
					echo "<tr>";
						echo "<th class='tg-031e'>Id Jogo</th>";
						echo "<th class='tg-031e'>Estadio</th>";
						echo "<th class='tg-031e'>Resultado</th>";
						echo "<th class='tg-031e'>Primeira Equipa</th>";
						echo "<th class='tg-031e'>Segunda Equipa</th>";
						echo "<th class='tg-031e'>Operacao</th>";
						echo "<th class='tg-031e'>Utilizador</th>";
						echo "<th class='tg-031e'>Data da operacao</th>";
					echo "</tr>";
					$result = pg_query("SELECT * FROM ".$tabela." WHERE data_hora_op BETWEEN TO_TIMESTAMP('2014-06-19 00:06:00', 'YYYY-MM-DD hh24:mi:ss') AND TO_TIMESTAMP('2014-06-25 01:50:00', 'YYYY-MM-DD hh24:mi:ss');") or die(mysql_error());
					while ($row = pg_fetch_array($result)) {
						$result_reaad1= $row[0];
						$result_reaad2= $row[1];
						$result_reaad3= $row[2];
						$result_reaad4= $row[3];
						$result_reaad5= $row[4];
						$result_reaad6= $row[5];
						$result_reaad7= $row[6];
						$result_reaad8= $row[7];
							echo "<tr>";
								echo "<td class='tg-031e'>$result_reaad1</td>";
								echo "<td class='tg-031e'>$result_reaad2</td>";
								echo "<td class='tg-031e'>$result_reaad3</td>";
								echo "<td class='tg-031e'>$result_reaad4</td>";
								echo "<td class='tg-031e'>$result_reaad5</td>";
								echo "<td class='tg-031e'>$result_reaad6</td>";
								echo "<td class='tg-031e'>$result_reaad7</td>";
								echo "<td class='tg-031e'>$result_reaad8</td>";
						   echo "</tr>";
					}
					echo "</table>";
				}
				if ($tabela=="golos_hist")
					{
					//listar as alterações feitas num determinado intervalo de tempo
					 echo "<br><br>";
					 echo "<p>Alterações feitas num determinado intervalo de tempo na tabela ".$tabelaString."</p>";
					echo "<table class='tg'>";
					echo "<tr>";
						echo "<th class='tg-031e'>Jogador</th>";
						echo "<th class='tg-031e'>Numero de golos</th>";
						echo "<th class='tg-031e'>Operacao</th>";
						echo "<th class='tg-031e'>Utilizador</th>";
						echo "<th class='tg-031e'>Data da operacao</th>";
					echo "</tr>";
					$result = pg_query("SELECT * FROM ".$tabela." WHERE data_hora_op BETWEEN TO_TIMESTAMP('2014-06-19 00:06:00', 'YYYY-MM-DD hh24:mi:ss') AND TO_TIMESTAMP('2014-06-25 01:50:00', 'YYYY-MM-DD hh24:mi:ss');") or die(mysql_error());
					while ($row = pg_fetch_array($result)) {
						$result_reaad1= $row[0];
						$result_reaad2= $row[1];
						$result_reaad3= $row[2];
						$result_reaad4= $row[3];
						$result_reaad5= $row[4];
							echo "<tr>";
								echo "<td class='tg-031e'>$result_reaad1</td>";
								echo "<td class='tg-031e'>$result_reaad2</td>";
								echo "<td class='tg-031e'>$result_reaad3</td>";
								echo "<td class='tg-031e'>$result_reaad4</td>";
								echo "<td class='tg-031e'>$result_reaad5</td>";
						   echo "</tr>";
					}
					echo "</table>";
				}
					
					
							
			?>

			
		
				</section>
				
				<section id="right">
					
				</section>
			</div>
        </body>
        <footer>
			<p style="color:black">TABD</p>
        </footer>
    </div>

</body>
</html>


