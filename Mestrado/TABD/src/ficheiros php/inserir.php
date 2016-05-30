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
				echo '<form class="pure-form"  action="ok.php" method="post">';
						echo  '<fieldset>';
						echo 'Pais: <input type="text" id="pais"  maxlength="20" name="pais" required><br>';
						echo 'Treinador: <input type="text" maxlength="50" name="treinador"><br>';
						echo '<button type="submit" class="pure-button pure-button-primary">Inserir</button>';
						
						echo '</fieldset>';
				echo '</form>';
				}
				if ($tabela=="jogadores")
				{
				echo '<form id="formbox" class="pure-form"   action="ok.php" method="post">';
						echo  '<fieldset>';
						echo 'Nome:	<input type="text" maxlength="50" name="nome" required><br>';
						echo 'Idade: <input type="number" name="idade"><br>';
						echo 'Nacionalidade: <input type="text" maxlength="20" name="nacionalidade" required><br>';
						echo "Equipa  ";
						echo "<select name='id_equipa' form='formbox'>";


						$result = pg_query("SELECT *FROM equipa") or die(mysql_error());
							while ($row = pg_fetch_array($result)) {
								$result_reaad1= $row[0];
								$result_reaad2= $row[1];
								echo " <option  value='".$result_reaad1."'>".$result_reaad2."</option>";
							}
						echo "</select>";
						echo "<br><br>";
						echo '<button type="submit" class="pure-button pure-button-primary">Inserir</button>';
						echo '</fieldset>';
				echo '</form>';
				}
				if ($tabela=="jogos")
				{
				echo '<form class="pure-form" id="formboxjogos"  action="ok.php" method="post">';
						echo  '<fieldset>';
						echo 'Estadio: <input type="text" name="estadio" maxlength="20" required><br>';
						echo 'Resultado: <input maxlength="3" type="text" name="resultado"  placeholder="ex: 2-1" required><br>';
						//echo 'Id Equipa1: <input type="number" name="id_equipa1" required><br><br>';
						echo "Equipa 1  ";
						echo "<select name='id_equipa1' form='formboxjogos'>";


						$result = pg_query("SELECT *FROM equipa") or die(mysql_error());
							while ($row = pg_fetch_array($result)) {
								$result_reaad1= $row[0];
								$result_reaad2= $row[1];
								echo " <option  value='".$result_reaad1."'>".$result_reaad2."</option>";
							}

						echo "</select>";
						echo "<br><br>";
						//echo 'Id Equipa2: <input type="number" name="id_equipa2" required><br><br>';
						echo "Equipa 2  ";
						echo "<select name='id_equipa2' form='formboxjogos'>";


						$result = pg_query("SELECT *FROM equipa") or die(mysql_error());
							while ($row = pg_fetch_array($result)) {
								$result_reaad1= $row[0];
								$result_reaad2= $row[1];
								echo " <option  value='".$result_reaad1."'>".$result_reaad2."</option>";
							}

						echo "</select>";
						echo "<br><br>";
						echo '<button type="submit" class="pure-button pure-button-primary">Inserir</button>';
						echo '</fieldset>';
				echo '</form>';
				}
				if ($tabela=="golos")
				{
				echo '<form class="pure-form"  id="formboxgolos" action="ok.php" method="post">';
						echo  '<fieldset>';
						//echo 'Id Jogador:	<input type="number" name="id_jogador" required><br>';
						echo "Jogadores ";
						echo "<select name='id_jogador' form='formboxgolos'>";


						$result = pg_query("(
											SELECT jogadores.id_jogador, nome
											FROM jogadores, golos
											ORDER BY jogadores.id_jogador
											)
											EXCEPT (
											SELECT jogadores.id_jogador, nome
											FROM jogadores, golos
											WHERE jogadores.id_jogador=golos.id_jogador
											)") or die(mysql_error());
							while ($row = pg_fetch_array($result)) {
								$result_reaad1= $row[0];
								$result_reaad2= $row[1];
								echo " <option  value='".$result_reaad1."'>".$result_reaad2."</option>";
							}

						echo "</select>";
						echo "<br><br>";
						echo 'Numero de golos: <input type="number" name="num_golos" required><br><br>';
						echo '<button type="submit" class="pure-button pure-button-primary">Inserir</button>';
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


