public class MenuGameOver {
  /*Esta classe contém o método estático
   * "Vencedor", que conta as peças X e as 
   * peças 0 (recorre aos métodos da classe
   * Contador.java para o efeito), compara o
   * número de cada tipo de peças e, finalmente,
   * escreve quem é o vencedor do jogo Reversi UE.*/
  
  public static void Vencedor (int tab[][]){
  
    if(Contador.ContadorPeçasX(tab)>Contador.ContadorPeçasO(tab))

//Se o número de peças X for superior ao número de peças 0, o jogador X ganha.      
      
      System.out.println("O Jogador X é o vencedor!!");
    
    if(Contador.ContadorPeçasX(tab)<Contador.ContadorPeçasO(tab))

//Se o número de peças 0 for superior ao número de peças X, o jogador 0 ganha.      
      
      System.out.println("O Jogador O é o vencedor!!");
    
    if(Contador.ContadorPeçasX(tab)==Contador.ContadorPeçasO(tab))
    
//Se o número de peças X for igual ao número de peças 0, os jogadores empatam.      
      
      System.out.println("É um empate!!!Ninguem ganhou...");
  }
}