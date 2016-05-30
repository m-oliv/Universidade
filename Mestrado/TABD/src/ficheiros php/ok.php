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
  <title>Our First HTML5 Page</title>
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
				$tabela = null;
				$idadd=1;
				if(isset($_POST['pais']))
				{
					$pais = $_POST["pais"];
					$treinador = $_POST["treinador"];
					$result = pg_query("SELECT *FROM equipa") or die(mysql_error());
					while ($row = pg_fetch_array($result)) {
						$result_reaad= $row[0];
						if($result_reaad>$idadd)
						$idadd=$result_reaad;
					}
					$idadd++;				
					$result = pg_query("INSERT INTO equipa(id_equipa,pais,treinador) VALUES('".$idadd."','".$pais."','".$treinador."');");
					if ($result!=false)
						echo "INSERT OK!";
					else
						echo "NOT INSERT";
				}
				
				
				if(isset($_POST['nome'])){
					$nome = $_POST["nome"];
					$idade = $_POST["idade"];
					$nacionalidade = $_POST["nacionalidade"];
					$id_equipa = $_POST["id_equipa"];
					$result = pg_query("SELECT *FROM jogadores") or die(mysql_error());
					while ($row = pg_fetch_array($result)) {
						$result_reaad= $row[0];
						if($result_reaad>$idadd)
						$idadd=$result_reaad;
					}
					$idadd++;	
					$result = pg_query("INSERT INTO jogadores(id_jogador,nome,idade,nacionalidade,equipa) VALUES('".$idadd."','".$nome."','".$idade."','".$nacionalidade."','".$id_equipa."');");			
					if ($result!=false)
						echo "INSERT OK!";
					else
						echo "NOT INSERT";
				}
				
				if(isset($_POST['estadio'])){
					$estadio = $_POST["estadio"];
					$resultado = $_POST["resultado"];
					$id_equipa1 = $_POST["id_equipa1"];
					$id_equipa2 = $_POST["id_equipa2"];
					$result = pg_query("SELECT *FROM jogos") or die(mysql_error());
					while ($row = pg_fetch_array($result)) {
						$result_reaad= $row[0];
						if($result_reaad>$idadd)
						$idadd=$result_reaad;
					}
					$idadd++;	
					$result = pg_query("INSERT INTO jogos(id_jogo,estadio,resultado,equipa1,equipa2) VALUES('".$idadd."','".$estadio."','".$resultado."','".$id_equipa1."','".$id_equipa2."');");			
					if ($result!=false)
						echo "INSERT OK!";
					else
						echo "NOT INSERT";
				}
				
				if(isset($_POST['id_jogador'])){
					$id_jogador = $_POST["id_jogador"];
					$num_golos = $_POST["num_golos"];	
					
				$result = pg_query("SELECT *FROM golos") or die(mysql_error());
				while ($row = pg_fetch_array($result)) {
				$idadd++;
				}	
				$result = pg_query("INSERT INTO golos(id_jogador,num_golos) VALUES('".$id_jogador."','".$num_golos."');");
									

				if ($result!=false)
						echo "INSERT OK!";
					else
						echo "NOT INSERT";
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


