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
                   
                   
                </ul>	
            </nav>
        </header>
		
        <body>
			<div id="core" class="clearfix">
				<section id="left">
				<br><br>
				
				
			<form class="pure-form" action="esthist.php" method="post">
					<label for="option-two" class="pure-radio">
						<input id="option-two" type="radio" name="tabelap" value="equipa">
						Equipa<br><br>
						<input id="option-two" type="radio" name="tabelap" value="jogadores">
						Jogador<br><br>
						<input id="option-two" type="radio" name="tabelap" value="jogos">
						Jogos<br><br>
						<input id="option-two" type="radio" name="tabelap" value="golos">
						Golos<br><br>
						<button type="submit" class="pure-button pure-button-primary">Seguinte</button>
					</label>
				</form>
		
			
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


