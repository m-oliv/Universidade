import java.util.Scanner;

public class JogadoresReversi{
  
  /*Nesta classe encontram-se os m�todos que compreendem os
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

//Restri��o (N�o podem ser inseridos inteiros diferentes de 0 e 1.).
    
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
      
//Car�cter dado pelo jogador X na linha de comandos.
        
        char coluna1Temp = (b.next()).charAt(0);
      
//Inteiro resultante da convers�o do car�cter dado pelo jogador X na linha de comandos num inteiro.
        
        coluna1=JogadasReversi.AbcInverso(coluna1Temp);
      
      }
      
//Restri��o (A jogada tem de ser v�lida.).
      
      while(JogadasReversi.JogadaX(x, linha1, coluna1)==false);
      
//Inicializa o m�todo que escreve o tabuleiro e a jogada no output.
      
      TabuleiroReversi.Tabuleirofisico(x);
      
//Se o n�mero de espa�os vazios for igual ao n�mero de pe�as no tabuleiro, o jogo termina.      
      
      if(Contador.ContadorPe�asTotal(x)==(x.length-2)*(x.length-2)||Contador.ContadorPe�asO(x)==0)
      
        MenuGameOver.Vencedor(x);
      
      else
      
// M�todo que inicializa o menu do jogador 0.
        
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
    
//Restri��o (N�o podem ser inseridos inteiros diferentes de 0 e 1.).    
    
    while(escolha0!=1&&escolha0!=0);
    
    if ((escolha0)==0){
      
//Caso o jogador decida passar a jogada, � inicializado o menu do jogador X.
      
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
        
// Car�cter dado pelo jogador 0 na linha de comandos.
        
        char coluna2Temp=(t.next()).charAt(0);
        
//Inteiro resultante da convers�o do car�cter dado pelo jogador 0 na linha de comandos num inteiro.
        
        coluna2=JogadasReversi.AbcInverso(coluna2Temp);
        
      }
      
//Restri��o (A jogada tem de ser v�lida.).      
      
      while(JogadasReversi.JogadaO(z, linha2, coluna2)==false);
      
//Inicializa o m�todo que escreve o tabuleiro e a jogada no output.
      
      TabuleiroReversi.Tabuleirofisico(z);

//Se o n�mero de espa�os vazios for igual ao n�mero de pe�as no tabuleiro, o jogo termina.      
      
      if(Contador.ContadorPe�asTotal(z)==(z.length-2)*(z.length-2)||Contador.ContadorPe�asX(z)==0)
      
        MenuGameOver.Vencedor(z);
      
      else
        
//M�todo que inicializa o menu do jogador X.
        
        menujogadorX(z);
    }
  }
}