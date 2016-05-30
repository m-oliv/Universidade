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
				if ($tabela=="equipa")
				{
					echo "<table class='tg'>";
					echo "<tr>";
						echo "<th class='tg-031e'>Equipa</th>";
						echo "<th class='tg-031e'>Treinador</th>";
						echo "<th class='tg-031e'>Numero Jogadores</th>";
					echo "</tr>";
					$result = pg_query("SELECT *FROM ".$tabela."") or die(mysql_error());
					while ($row = pg_fetch_array($result)) {
						$result_reaad1= $row[1];
						$result_reaad2= $row[2];
						
							echo "<tr>";
								echo "<td class='tg-031e'>$result_reaad1</td>";
								echo "<td class='tg-031e'>$result_reaad2</td>";
								$resultnj = pg_query("SELECT COUNT(equipa) FROM jogadores WHERE equipa = ".$row[0]." ;") or die(mysql_error());
								$rownj = pg_fetch_array($resultnj);
								$result_reaad3= $rownj[0];
								echo "<td class='tg-031e'>$result_reaad3</td>";
								
						   echo "</tr>";
					}
					echo "</table>";
				}
				if ($tabela=="jogadores")
				{
					echo "<table class='tg'>";
					echo "<tr>";
						echo "<th class='tg-031e'>Nome</th>";
						echo "<th class='tg-031e'>Nacionalidade</th>";
						echo "<th class='tg-031e'>Idade</th>";
						echo "<th class='tg-031e'>Golos</th>";
					echo "</tr>";
					$result = pg_query("SELECT *FROM ".$tabela."") or die(mysql_error());
					while ($row = pg_fetch_array($result)) {
					$result_reaad1= $row[1];
					$result_reaad2= $row[3];
					$result_reaad3= $row[2];
						echo "<tr>";
							echo "<td class='tg-031e'>$result_reaad1</td>";
							echo "<td class='tg-031e'>$result_reaad2</td>";
							echo "<td class='tg-031e'>$result_reaad3</td>";
							$resultnj = pg_query("SELECT num_golos FROM golos WHERE id_jogador = ".$row[0]." ;") or die(mysql_error());
								$rownj = pg_fetch_array($resultnj);
								$result_reaad4= $rownj[0];
								if ($result_reaad4==null)
								{
									$result_reaad4=0;
								}
								
								echo "<td class='tg-031e'>$result_reaad4</td>";
					   echo "</tr>";
					}
					echo "</table>";
				}
				if ($tabela=="jogos")
				{
					echo "<table class='tg'>";
					echo "<tr>";
						echo "<th class='tg-031e'>Estadio</th>";
						echo "<th class='tg-031e'>Resultado</th>";
						echo "<th class='tg-031e'>Primeira Equipa</th>";
						echo "<th class='tg-031e'>Segunda Equipa</th>";
					echo "</tr>";
					$result = pg_query("SELECT *FROM ".$tabela."") or die(mysql_error());
					while ($row = pg_fetch_array($result)) {
					$result_reaad1= $row[1];
					$result_reaad2= $row[2];
						echo "<tr>";
							echo "<td class='tg-031e'>$result_reaad1</td>";
							echo "<td class='tg-031e'>$result_reaad2</td>";
							$resultnj = pg_query("SELECT pais FROM equipa WHERE id_equipa = ".$row[3]." ;") or die(mysql_error());
							$rownj = pg_fetch_array($resultnj);
							$result_reaad3= $rownj[0];
							echo "<td class='tg-031e'>$result_reaad3</td>";
							$resultnj = pg_query("SELECT pais FROM equipa WHERE id_equipa = ".$row[4]." ;") or die(mysql_error());
							$rownj = pg_fetch_array($resultnj);
							$result_reaad4= $rownj[0];
							echo "<td class='tg-031e'>$result_reaad4</td>";
					   echo "</tr>";
					}
					echo "</table>";
				}
				if ($tabela=="golos")
				{
					echo "<table class='tg'>";
					echo "<tr>";
						echo "<th class='tg-031e'>Nome Jogador</th>";
						echo "<th class='tg-031e'>Golos</th>";
					echo "</tr>";
					$result = pg_query("SELECT *FROM ".$tabela."") or die(mysql_error());
					while ($row = pg_fetch_array($result)) {
					
						$result_reaad2= $row[1];
						
							echo "<tr>";
								$resultnj = pg_query("SELECT nome FROM jogadores WHERE id_jogador = ".$row[0]." ;") or die(mysql_error());
								$rownj = pg_fetch_array($resultnj);
								$result_reaad1= $rownj[0];
								echo "<td class='tg-031e'>$result_reaad1</td>";
								echo "<td class='tg-031e'>$result_reaad2</td>";
								
								
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


