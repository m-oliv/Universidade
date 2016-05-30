/* Nesta classe s�o criados os menus que orientar�o o jogador
 * durante a fase inicial do jogo. Ser�o disponibilizados dois
 * menus criados nesta classe: o menu da entrada e o menu que
 * permite a escolha do tamanho do tabuleiro de jogo.*/

import java.util.Scanner;

public class Menu{
  
  int opcao;                                     //Inteiro correspondente op��o a escolher no primeiro menu.
  int n;                                         //Inteiro correspondente ao tamanho m�ximo do tabuleiro.
  boolean sair=false;

  Scanner s = new Scanner(System.in);
  
  public Menu(){                                 //Construtor.
    this.opcao=opcao;
    this.n=n;
  }
  
  public void menuEntrada(){                     //Este m�todo escreve o menu da entrada.  
    
    System.out.println("Mastermind UE.");
    
    do{
      System.out.println("Por favor seleccione uma op��o:");
      System.out.println("0-Sair  1-Jogar");

      opcao=s.nextInt();                         //Inteiro dado na linha de comandos.      
      
    }while(opcao!=1&&opcao!=0);                  //Restri��o (N�o podem ser inseridos inteiros diferentes de 0 e 1).
    
    switch(opcao){
      case 0:return;                             //Caso o jogador escolha a op��o "sair", o programa n�o retorna nada e sai.
      case 1:menuSizeTabuleiro();                //Caso o jogador escolha "jogar", o programa avan�a para o menu que permite escolher o tamanho do tabuleiro de jogo.
    }
  }
  
  public boolean menuGameover(){                 //Este m�todo escreve o menu que ser� apresentado em caso de Game Over.
    
    System.out.println(" Game Over ");
    do{
      System.out.println("Deseja jogar novamente?");
      System.out.println("0-Ver outros Jogos/Sair  1-Jogar Novamente");

      opcao=s.nextInt();                         //Inteiro dado na linha de comandos.      
      
    }while(opcao!=1&&opcao!=0);                  //Restri��o (N�o podem ser inseridos inteiros diferentes de 0 e 1).
    
    switch(opcao){
      case 0: return sair=true;                  //Caso o jogador escolha a op��o "sair", o programa retorna o boolean sair como true.
      case 1: return sair=false;                 //Caso o jogador escolha "jogar", o programa retorna o boolean sair como false.
    }
    return sair;
  }
  public boolean menuSair(){                     //Este m�todo gera um pequeno menu que permite ao jogador decidir se prefere abandonar o jogo ou consultar os tabuleiros de jogos anteriores.
    do{
      System.out.println("Deseja consultar tabuleiros?");
      System.out.println("0-Sair  1-Consultar tabuleiros");
      
      opcao=s.nextInt();                         //Inteiro dado na linha de comandos.      
      
    }while(opcao!=1&&opcao!=0);                  //Restri��o (N�o podem ser inseridos inteiros diferentes de 0 e 1).
    
    switch(opcao){
      case 0: return sair=true;                  //Caso o jogador escolha a op��o "sair", o programa retorna o boolean sair como true.
      case 1: return sair=false;                 //Caso o jogador escolha a op��o "Consultar Tabuleiros", o programa retorna o boolean sair como false.
    }
    return sair;
  }
  
    public int menuConsultar(){                  //Este m�todo retorna um pequeno menu que permite ao jogador escolher o tabuleiro que permite consultar.
    do{
      System.out.println("Introduza o numero do tabuleiro do 1� jogador de  1 a 99 (tem de ser um n�mero impar):");
      System.out.println();
      
      opcao=s.nextInt();                         //Inteiro dado na linha de comandos.      
      
    }while(opcao%2==0&&(opcao<1||opcao>100));    //Restri��o (N�o podem ser inseridos inteiros pares, menores que 0 ou maiores que 100).
    return opcao-1;
  }
    
  public void menuSizeTabuleiro(){               //Permite definir um tamanho para o tabuleiro de jogo.
    do{
      
      System.out.println("Insira um n�mero que corresponda ao tamanho do tabuleiro:");
      
      n=s.nextInt();                             //Argumento dado na linha de comandos.
      
    }while(n<2||n>16);                           //Restri��o (O inteiro dado n�o pode ser menor do que 1 nem maior do que 16).
  }
  
}