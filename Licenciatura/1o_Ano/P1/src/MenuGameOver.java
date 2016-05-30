public class MenuGameOver {
  /*Esta classe cont�m o m�todo est�tico
   * "Vencedor", que conta as pe�as X e as 
   * pe�as 0 (recorre aos m�todos da classe
   * Contador.java para o efeito), compara o
   * n�mero de cada tipo de pe�as e, finalmente,
   * escreve quem � o vencedor do jogo Reversi UE.*/
  
  public static void Vencedor (int tab[][]){
  
    if(Contador.ContadorPe�asX(tab)>Contador.ContadorPe�asO(tab))

//Se o n�mero de pe�as X for superior ao n�mero de pe�as 0, o jogador X ganha.      
      
      System.out.println("O Jogador X � o vencedor!!");
    
    if(Contador.ContadorPe�asX(tab)<Contador.ContadorPe�asO(tab))

//Se o n�mero de pe�as 0 for superior ao n�mero de pe�as X, o jogador 0 ganha.      
      
      System.out.println("O Jogador O � o vencedor!!");
    
    if(Contador.ContadorPe�asX(tab)==Contador.ContadorPe�asO(tab))
    
//Se o n�mero de pe�as X for igual ao n�mero de pe�as 0, os jogadores empatam.      
      
      System.out.println("� um empate!!!Ninguem ganhou...");
  }
}