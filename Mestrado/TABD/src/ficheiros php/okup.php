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
				if(isset($_POST['pais']))
				{
					$pais = $_POST["pais"];
					$treinador = $_POST["treinador"];	
					$result = pg_query("UPDATE equipa SET treinador='".$treinador."' WHERE id_equipa='".$pais."';");
					
					if ($result!=false)
						echo "UPDATE OK!";
					else
						echo "NOT UPDATE";
				}
				
				
				if(isset($_POST['nome'])){
					$nome = $_POST["nome"];
					$idade = $_POST["idade"];
					$nacionalidade = $_POST["nacionalidade"];
					if ($nacionalidade ==="" && $idade ===""){  
						$result=false;}
					else if ($nacionalidade ==="")
						$result = pg_query("UPDATE jogadores SET idade='".$idade."' WHERE id_jogador='".$nome."';");	
					else if ($idade ==="")
						$result = pg_query("UPDATE jogadores SET nacionalidade='".$nacionalidade."' WHERE id_jogador='".$nome."';");	
					else{
						$result = pg_query("UPDATE jogadores SET idade='".$idade."',nacionalidade='".$nacionalidade."' WHERE id_jogador='".$nome."';");			
					}
					if ($result!=false)
						echo "UPDATE OK!";
					else
						echo "NOT UPDATE";
				}
				
				if(isset($_POST['id_jogo'])){
					$estadio = $_POST["estadio"];
					$resultado = $_POST["resultado"];
					$id_jogo = $_POST['id_jogo'];
					if ($estadio ==="" && $resultado ===""){  
						$result=false;}
					else if ($resultado ==="")
						$result = pg_query("UPDATE jogos SET estadio='".$estadio."' WHERE id_jogo='".$id_jogo."';");	
					else if ($estadio ==="")
						$result = pg_query("UPDATE jogos SET resultado='".$resultado."' WHERE id_jogo='".$id_jogo."';");	
					else{
						$result = pg_query("UPDATE jogos SET estadio='".$estadio."',resultado='".$resultado."' WHERE id_jogo='".$id_jogo."';");			
					}
					if ($result!=false)
						echo "UPDATE OK!";
					else
						echo "NOT UPDATE";
				}
				
				if(isset($_POST['id_jogador'])){
					$id_jogador = $_POST["id_jogador"];
					$num_golos = $_POST["num_golos"];		
					$result = pg_query("UPDATE golos SET num_golos='".$num_golos."' WHERE id_jogador='".$id_jogador."';");			

				if ($result!=false)
						echo "UPDATE OK!";
					else
						echo "NOT UPDATE";
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


