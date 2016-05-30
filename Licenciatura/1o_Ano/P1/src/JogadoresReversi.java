import java.util.Scanner;

public class JogadoresReversi{
  
  /*Nesta classe encontram-se os métodos que compreendem os
   * menus dos jogadores.*/
  
  public static void menujogadorX(int x [][]){

//Escreve o menu do jogador X.
  
    Scanner b= new Scanner(System.in);
    
    int escolhaX;
    
    do{
      
//Menu para o primeiro jogador.
      
      System.out.println("Jogador X pretende: 0: Passar;   1: Jogar;");
      
//Inteiro dado pelo jogador X na linha de comandos.
      
      escolhaX = b.nextInt();
    }

//Restrição (Não podem ser inseridos inteiros diferentes de 0 e 1.).
    
    while(escolhaX!=1 && escolhaX!=0); 

//Caso o jogador decida passar a vez, o programa inicializa o menu do jogador 0.
    
    if (escolhaX==0){ 
    
      menujogador0(x);

    }  
    
//Caso o jogador X decida jogar o programa continua.
    
    else{
      
      int linha1,coluna1;
      
      do{
      
        System.out.println("Insira a linha entre 1 e "+(x.length-2)+ " em que prentende jogar:");   
      
//Inteiro dado pelo jogador X na linha de comandos.
        
        linha1 = b.nextInt();
      
        System.out.println("Insira uma coluna entre a e "+ JogadasReversi.Abc(x.length-2)+ " em que pretende jogar:");
      
//Carácter dado pelo jogador X na linha de comandos.
        
        char coluna1Temp = (b.next()).charAt(0);
      
//Inteiro resultante da conversão do carácter dado pelo jogador X na linha de comandos num inteiro.
        
        coluna1=JogadasReversi.AbcInverso(coluna1Temp);
      
      }
      
//Restrição (A jogada tem de ser vàlida.).
      
      while(JogadasReversi.JogadaX(x, linha1, coluna1)==false);
      
//Inicializa o método que escreve o tabuleiro e a jogada no output.
      
      TabuleiroReversi.Tabuleirofisico(x);
      
//Se o número de espaços vazios for igual ao número de peças no tabuleiro, o jogo termina.      
      
      if(Contador.ContadorPeçasTotal(x)==(x.length-2)*(x.length-2)||Contador.ContadorPeçasO(x)==0)
      
        MenuGameOver.Vencedor(x);
      
      else
      
// Método que inicializa o menu do jogador 0.
        
        menujogador0(x);
    }
  }
  
  public static void menujogador0(int z[][]){
    
//Escreve o menu do jogador 0.   
  
    Scanner t= new Scanner(System.in);
    
    int escolha0;
    
    do{
    
//Menu para o segundo jogador.
      
      System.out.println("Jogador 0 pretende: 0: Passar;   1: Jogar;");
      
//Inteiro dado pelo jogador 0 na linha de comandos.
      
      escolha0 = t.nextInt();
    }
    
//Restrição (Não podem ser inseridos inteiros diferentes de 0 e 1.).    
    
    while(escolha0!=1&&escolha0!=0);
    
    if ((escolha0)==0){
      
//Caso o jogador decida passar a jogada, é inicializado o menu do jogador X.
      
      menujogadorX(z);
      
    }  
    
//Caso o jogador decida jogar o programa continua.
    
    else{
      
      int linha2,coluna2;
      
      do{
      
        System.out.println("Insira a linha entre 1 e "+(z.length-2)+" em que pretende jogar:");
        
//Inteiro dado pelo jogador 0 na linha de comandos.
        
        linha2 = t.nextInt();
        
        System.out.println("Insira uma coluna entre a e "+ JogadasReversi.Abc(z.length-2)+" em que pretende jogar:");
        
// Carácter dado pelo jogador 0 na linha de comandos.
        
        char coluna2Temp=(t.next()).charAt(0);
        
//Inteiro resultante da conversão do carácter dado pelo jogador 0 na linha de comandos num inteiro.
        
        coluna2=JogadasReversi.AbcInverso(coluna2Temp);
        
      }
      
//Restrição (A jogada tem de ser válida.).      
      
      while(JogadasReversi.JogadaO(z, linha2, coluna2)==false);
      
//Inicializa o método que escreve o tabuleiro e a jogada no output.
      
      TabuleiroReversi.Tabuleirofisico(z);

//Se o número de espaços vazios for igual ao número de peças no tabuleiro, o jogo termina.      
      
      if(Contador.ContadorPeçasTotal(z)==(z.length-2)*(z.length-2)||Contador.ContadorPeçasX(z)==0)
      
        MenuGameOver.Vencedor(z);
      
      else
        
//Método que inicializa o menu do jogador X.
        
        menujogadorX(z);
    }
  }
}