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
				if ($tabela=="equipa")
				{
				
				echo "Eliminar uma equipa, elimina automaticamente todos os jogadores da mesma equipa<br>";
				echo '<form class="pure-form" id="formbox" action="okdel.php" method="post">';
						echo  '<fieldset>';
						echo "Equipa  ";
						echo "<select name='pais' form='formbox'>";

						$result = pg_query("SELECT *FROM equipa") or die(mysql_error());
							while ($row = pg_fetch_array($result)) {
								$result_reaad1= $row[0];
								$result_reaad2= $row[1];
								echo " <option  value='".$result_reaad1."'>".$result_reaad2."</option>";
							}
						echo "</select>";
						echo "<br><br>";
						echo '<button type="submit" class="pure-button pure-button-primary">Eliminar</button>';
						
						echo '</fieldset>';
				echo '</form>';
				}
				if ($tabela=="jogadores")
				{
				echo '<form id="formbox" class="pure-form"   action="okdel.php" method="post">';
						echo  '<fieldset>';
						echo "Jogador  ";
						echo "<select name='nome' form='formbox'>";

						$result = pg_query("SELECT *FROM jogadores") or die(mysql_error());
							while ($row = pg_fetch_array($result)) {
								$result_reaad1= $row[0];
								$result_reaad2= $row[1];
								$result_reaad3= $row[3];
								echo " <option  value='".$result_reaad1."'>".$result_reaad2." - ".$result_reaad3. "</option>";
							}
						echo "</select>";
						echo "<br><br>";
						echo '<button type="submit" class="pure-button pure-button-primary">Eliminar</button>';
						echo '</fieldset>';
				echo '</form>';
				}
				if ($tabela=="jogos")
				{
				echo '<form class="pure-form" id="formboxjogos"  action="okdel.php" method="post">';
						echo  '<fieldset>';
						//echo 'Id Equipa1: <input type="number" name="id_equipa1" required><br><br>';
						echo "Jogo	  ";
						echo "<select name='id_jogo' form='formboxjogos'>";
						$result = pg_query("WITH tteam as (SELECT pais as nac, id_jogo as idt1 FROM jogos natural inner join equipa where jogos.equipa1=equipa.id_equipa)
SELECT * FROM tteam, jogos natural inner join equipa where jogos.equipa2=equipa.id_equipa AND id_jogo=idt1
;") or die(mysql_error());
							while ($row = pg_fetch_array($result)) {
								$result_reaad1= $row[2];
								$result_reaad2= $row[0];
								$result_reaad3= $row[8];
								$result_reaad4= $row[3];
								$result_reaad5= $row[4];
								echo " <option  value='".$result_reaad1."'>".$result_reaad2." vs " .$result_reaad3." - ".$result_reaad4." - ".$result_reaad5."</option>";
							}

						echo "</select>";
						echo "<br><br>";
						echo '<button type="submit" class="pure-button pure-button-primary">Eliminar</button>';
						echo '</fieldset>';
				echo '</form>';
				}
				if ($tabela=="golos")
				{
				echo '<form class="pure-form"  id="formboxgolos" action="okdel.php" method="post">';
						echo  '<fieldset>';

						echo "Jogadores ";
						echo "<select name='id_jogador' form='formboxgolos'>";


							$result = pg_query(" (
													SELECT *
													FROM jogadores NATURAL INNER JOIN golos
													)
											") or die(mysql_error());
							while ($row = pg_fetch_array($result)) {
								$result_reaad1= $row[0];
								$result_reaad2= $row[1];
								$result_reaad3= $row[5];
								echo " <option  value='".$result_reaad1."'>".$result_reaad2."-".$result_reaad3."</option>";
							}

						echo "</select>";
						echo "<br><br>";
						echo '<button type="submit" class="pure-button pure-button-primary">Eliminar</button>';
						echo '</fieldset>';
				echo '</form>';
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


