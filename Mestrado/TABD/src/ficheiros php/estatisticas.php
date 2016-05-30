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
					echo "<p>Equipa com mais jogos</p>";
					echo "<table class='tg'>";
					echo "<tr>";
						echo "<th class='tg-031e'>Jogador</th>";
						echo "<th class='tg-031e'>Numero de golos</th>";
					echo "</tr>";
					$result = pg_query("WITH max_golos AS (
SELECT max(num_golos) AS mg FROM jogadores NATURAL INNER JOIN golos)
SELECT nome, num_golos FROM jogadores NATURAL INNER JOIN golos, max_golos WHERE num_golos = mg;") or die(mysql_error());
					while ($row = pg_fetch_array($result)) {
						$result_reaad1= $row[0];
						$result_reaad2= $row[1];
						
							echo "<tr>";
								echo "<td class='tg-031e'>$result_reaad1</td>";
								echo "<td class='tg-031e'>$result_reaad2</td>";
						   echo "</tr>";
					}
					echo "</table>";
					
					echo "<br><br>";
					echo "<p>Equipa com mais jogos</p>";
					
					echo "<table class='tg'>";
					echo "<tr>";
						echo "<th class='tg-031e'>Pais</th>";
						echo "<th class='tg-031e'>Golos</th>";
					echo "</tr>";
					$temp='"Total Goals"';
					$result = pg_query("WITH temptable AS(
	SELECT (WITH max_golos_equipas AS(
			SELECT SUM(num_golos) AS ".$temp.", pais FROM equipa NATURAL INNER JOIN jogadores NATURAL INNER JOIN golos WHERE equipa.id_equipa=jogadores.equipa GROUP BY pais
			)
		SELECT MAX(".$temp.") as maxGoals FROM max_golos_equipas) 
		as MaxGoals, SUM(num_golos) as totalGoals, pais 
		FROM  equipa NATURAL INNER JOIN jogadores NATURAL INNER JOIN golos
		WHERE equipa.id_equipa=jogadores.equipa 
		GROUP BY pais
)
SELECT pais, totalgoals AS golos FROM temptable WHERE maxgoals=totalgoals;") or die(mysql_error());
					while ($row = pg_fetch_array($result)) {
					$result_reaad1= $row[0];
					$result_reaad2= $row[1];
						echo "<tr>";
							echo "<td class='tg-031e'>$result_reaad1</td>";
							echo "<td class='tg-031e'>$result_reaad2</td>";
					   echo "</tr>";
					}
					echo "</table>";
					
					 echo "<br><br>";
					 echo "<p>Equipa com mais jogos</p>";
					echo "<table class='tg'>";
					echo "<tr>";
						echo "<th class='tg-031e'>Estadio</th>";
						echo "<th class='tg-031e'>Numero de jogos</th>";
					echo "</tr>";
					$result = pg_query("WITH tableWithMaximums AS(
    WITH maxEstadios AS(
        WITH count_estadios AS (
            SELECT count(estadio), estadio
            FROM jogos
            GROUP BY (estadio)
                    )
        SELECT max(count) FROM count_estadios
    )
    SELECT count(estadio), estadio, max FROM maxEstadios, jogos GROUP BY estadio, max
)
SELECT estadio, count FROM tableWithMaximums WHERE count=max;
") or die(mysql_error());
					while ($row = pg_fetch_array($result)) {
					$result_reaad1= $row[0];
					$result_reaad2= $row[1];
						echo "<tr>";
							echo "<td class='tg-031e'>$result_reaad1</td>";
							echo "<td class='tg-031e'>$result_reaad2</td>";
					   echo "</tr>";
					}
					echo "</table>";
			 
					echo "<br><br>";
					echo "<p>Equipa com mais jogos</p>";
					echo "<table class='tg'>";
					echo "<tr>";
						echo "<th class='tg-031e'>Equipas</th>";
						echo "<th class='tg-031e'>Numero de Jogos</th>";
					echo "</tr>";
					$eq='"Equipa"';
					$nj='"Numero de Jogos"';
					$result = pg_query("WITH preparation AS(
	WITH maximum AS(
		WITH contagem AS(
			SELECT count(pais)
			FROM equipa natural inner join jogos
			WHERE equipa.id_equipa = jogos.equipa1 OR equipa.id_equipa = jogos.equipa2
			GROUP BY pais
		)
		SELECT max(count)
		FROM contagem
	)
	SELECT max, pais, count(pais)
	FROM equipa natural inner join jogos, maximum
	WHERE equipa.id_equipa = jogos.equipa1 OR equipa.id_equipa = jogos.equipa2
	GROUP BY pais, maximum.max
	)
SELECT pais AS ".$eq.", count as ".$nj." FROM preparation WHERE max=count;") or die(mysql_error());
					while ($row = pg_fetch_array($result)) {
						$result_reaad1= $row[0];
						$result_reaad2= $row[1];
						
							echo "<tr>";
								echo "<td class='tg-031e'>$result_reaad1</td>";
								echo "<td class='tg-031e'>$result_reaad2</td>";
						   echo "</tr>";
					}
					echo "</table>";
					echo "<br><br>";
					echo "<p>Equipa que mudou mais vezes de treinador</p>";
					
					echo "<table class='tg'>";
					echo "<tr>";
						echo "<th class='tg-031e'>Pais</th>";
					echo "</tr>";
					
					$result = pg_query("WITH manager_change AS(
	SELECT pais,count(pais) as num_t FROM (select pais, treinador from equipa_hist WHERE operacao = 'UPDATE') as m_change GROUP BY pais
	) 

	SELECT pais FROM manager_change WHERE manager_change.num_t = (SELECT max(num_t) as m FROM manager_change);
") or die(mysql_error());
					while ($row = pg_fetch_array($result)) {
					$result_reaad1= $row[0];
					
						echo "<tr>";
							echo "<td class='tg-031e'>$result_reaad1</td>";
					   echo "</tr>";
					}
					echo "</table>";
					
					
						echo "<br><br>";
					echo "<p>Jogador cujos dados foram mais vezes alterados</p>";
					
					echo "<table class='tg'>";
					echo "<tr>";
						echo "<th class='tg-031e'>Jogador</th>";
					echo "</tr>";
					
					$result = pg_query("WITH n_alt AS (
	SELECT nome, count(nome) as num_alt FROM jogadores_hist WHERE operacao = 'UPDATE' group by nome
	) 
	SELECT nome FROM n_alt WHERE num_alt = (SELECT max(num_alt) FROM n_alt);
") or die(mysql_error());
					while ($row = pg_fetch_array($result)) {
					$result_reaad1= $row[0];
					
						echo "<tr>";
							echo "<td class='tg-031e'>$result_reaad1</td>";
					   echo "</tr>";
					}
					echo "</table>";
					
					echo "<br><br>";
					echo "<p>Equipa com mais jogadores que sairam</p>";
					
					echo "<table class='tg'>";
					echo "<tr>";
						echo "<th class='tg-031e'>Pais</th>";
					echo "</tr>";
					
					$result = pg_query("WITH num_saidas AS(

	SELECT pais,count(pais) as n_saiu FROM jogadores_hist,equipa WHERE operacao = 'DELETE' AND jogadores_hist.equipa = id_equipa GROUP BY pais
	)

	SELECT pais FROM num_saidas WHERE n_saiu = (SELECT MAX(n_saiu) FROM num_saidas); 
") or die(mysql_error());
					while ($row = pg_fetch_array($result)) {
					$result_reaad1= $row[0];
					
						echo "<tr>";
							echo "<td class='tg-031e'>$result_reaad1</td>";
					   echo "</tr>";
					}
					echo "</table>";
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


