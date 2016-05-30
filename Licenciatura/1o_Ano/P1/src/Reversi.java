import java.util.Scanner;

public class Reversi{
  
  /*Para jogar Reversi � necess�rio invocar o comando java Reversi.
   *Nesta classe encontram-se apenas os m�todos "main" e 
   *"Menu". O restante c�digo necess�rio para o funcionamento do
   *jogo Reversi UE est� repartido por outras classes.*/
  
  public static void main(String args[]){

//Inicializa o m�todo "Menu".    
    
    Menu();
   
  }

  public static void Menu(){
    
//Este m�todo escreve o menu da entrada.    
    
    Scanner s = new Scanner(System.in);
    
    System.out.println("Reversi UE.");
    
    int escolha,N;
    
    do{
      
      System.out.println("0-Sair  1-Jogar");

//Inteiro dado na linha de comandos.      
      
      escolha=s.nextInt();
   }

//Restri��o (N�o podem ser inseridos inteiros diferentes de 0 e 1).
    
    while(escolha!=1&&escolha!=0);
    
    if(escolha==0)
      return;
    
    do{
      
      System.out.println("Insira um n�mero par que corresponda ao tamanho do tabuleiro:");
      
//Argumento dado na linha de comandos.      
      
      N=s.nextInt();
    }
  
//Restri��o (O inteiro dado n�o pode ser menor do que 4 nem maior do que 26 e ter� de ser par).    
    
    while(N<4||N>26||N%2!=0);
    
//Cria um array bidimensional de inteiros de tamanho N+2.    
    
    int [][]a = new int [N+2][N+2]; 
    
//Escreve o tabuleiro com as quatro pe�as iniciais no centro do mesmo.    
    
    TabuleiroReversi.Tabuleiro(a);
   
  }
}