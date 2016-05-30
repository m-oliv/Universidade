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
				echo '<form class="pure-form" id="foreq" action="okup.php" method="post">';
						echo  '<fieldset>';
						echo "Pais  ";
						echo "<select name='pais' form='foreq'>";

						$result = pg_query("SELECT *FROM equipa") or die(mysql_error());
							while ($row = pg_fetch_array($result)) {
								$result_reaad1= $row[0];
								$result_reaad2= $row[1];
								echo " <option  value='".$result_reaad1."'>".$result_reaad2."</option>";
							}
						echo "</select>";
						echo "<br><br>";
						echo 'Treinador: <input type="text" maxlength="50" name="treinador"><br><br>';
						echo '<button type="submit" class="pure-button pure-button-primary">Atualizar</button>';
						
						echo '</fieldset>';
				echo '</form>';
				}
				if ($tabela=="jogadores")
				{
				echo '<form id="formbox" class="pure-form"   action="okup.php" method="post">';
						echo  '<fieldset>';
						echo "Nome ";
						echo "<select name='nome' form='formbox'>";
						$result = pg_query("SELECT *FROM jogadores") or die(mysql_error());
							while ($row = pg_fetch_array($result)) {
								$result_reaad1= $row[0];
								$result_reaad2= $row[1];
								echo " <option  value='".$result_reaad1."'>".$result_reaad2."</option>";
							}
						echo "</select>";
						echo "<br><br>";
						echo 'Idade: <input type="number" name="idade"><br>';
						echo 'Nacionalidade: <input type="text" maxlength="20" name="nacionalidade" ><br>';
						echo '<button type="submit" class="pure-button pure-button-primary">Atualizar</button>';
						echo '</fieldset>';
				echo '</form>';
				}
				if ($tabela=="jogos")
				{
				echo '<form class="pure-form" id="formboxjogos"  action="okup.php" method="post">';
						echo  '<fieldset>';
						//echo 'Id Equipa1: <input type="number" name="id_equipa1" required><br><br>';
						echo "Jogo	  ";
						echo "<select name='id_jogo' form='formboxjogos'>";
						$result = pg_query("WITH tteam as (SELECT pais as nac, id_jogo as idt1 FROM jogos natural inner join equipa where jogos.equipa1=equipa.id_equipa)
SELECT id_jogo, nac, pais FROM tteam, jogos natural inner join equipa where jogos.equipa2=equipa.id_equipa AND id_jogo=idt1
;") or die(mysql_error());
							while ($row = pg_fetch_array($result)) {
								$result_reaad1= $row[0];
								$result_reaad3= $row[1];
								$result_reaad4= $row[2];
								echo " <option  value='".$result_reaad1."'>".$result_reaad3." vs " .$result_reaad4."</option>";
							}

						echo "</select>";
						echo "<br><br>";
						echo 'Estadio: <input type="text" name="estadio" maxlength="20" required><br>';
						echo 'Resultado: <input maxlength="3" type="text" name="resultado"  placeholder="ex: 2-1" required><br>';
						echo '<button type="submit" class="pure-button pure-button-primary">Atualizar</button>';
						echo '</fieldset>';
				echo '</form>';
				}
				if ($tabela=="golos")
				{
				echo '<form class="pure-form"  id="formboxgolos" action="okup.php" method="post">';
						echo  '<fieldset>';
						//echo 'Id Jogador:	<input type="number" name="id_jogador" required><br>';
						echo "Jogadores ";
						echo "<select name='id_jogador' form='formboxgolos'>";


							$result = pg_query(" (
													SELECT jogadores.id_jogador, nome
													FROM jogadores NATURAL INNER JOIN golos
													)
											") or die(mysql_error());
							while ($row = pg_fetch_array($result)) {
								$result_reaad1= $row[0];
								$result_reaad2= $row[1];
								echo " <option  value='".$result_reaad1."'>".$result_reaad2."</option>";
							}

						echo "</select>";
						echo "<br><br>";
						echo 'Numero de golos: <input type="number" name="num_golos" required><br><br>';
						echo '<button type="submit" class="pure-button pure-button-primary">Atualizar</button>';
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


