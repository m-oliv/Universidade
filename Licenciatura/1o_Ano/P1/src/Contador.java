public class Contador{
  /*Nesta classe s�o efectuadas as contagens
   * necess�rias para o decorrer do jogo. */
  
  public static int ContadorPe�asX(int tabuleiro[][]){
    
//Este m�todo conta o n�mero de pe�as X que se encontram no tabuleiro.
    
    int pe�asX=0;
    
    for(int coluna=1;coluna<tabuleiro.length-1;coluna++)
      
      for(int linha=1;linha<tabuleiro.length-1;linha++){
      
      if(tabuleiro[coluna][linha]==1)
        
        pe�asX+=1;
    }
    return pe�asX;
  }
   
  public static int ContadorPe�asO(int tabuleiro[][]){
     
//Este m�todo conta o n�mero de pe�as 0 que se encontram no tabuleiro.
     
    int pe�asO=0;
    
    for(int coluna=1;coluna<tabuleiro.length-1;coluna++)
      
      for(int linha=1;linha<tabuleiro.length-1;linha++){
      
      if(tabuleiro[coluna][linha]==2)
        
        pe�asO+=1;
    }
    return pe�asO; 
  }
  
  public static int ContadorPe�asTotal(int tabuleiro[][]){
    
//Este m�todo conta o n�mero total de jogadas efectuadas por ambos os jogadores.
    
    int pe�astotal=0;
    
    for(int coluna=1;coluna<tabuleiro.length-1;coluna++)
      
      for(int linha=1;linha<tabuleiro.length-1;linha++){
      
      if(tabuleiro[coluna][linha]!=0)
        
        pe�astotal+=1;
    }
    return pe�astotal;
  }
  
  public static int ContarJogadasX(int tabuleiro[][]){
    
// Este m�todo conta o n�mero de jogadas efectuadas pelo jogador X.
    
    int contador=0;
    
    for(int a=1;a<tabuleiro.length-1;a++){
      
      for(int b=1;b<tabuleiro.length-1;b++){
        
        if(JogadasReversi.JogadaX(tabuleiro, a, b)==true)
          
          contador+=1;
      }
    }
    return contador;
  }
  
  public static int ContarJogadasO(int tabuleiro[][]){
    
//Este m�todo conta o n�mero de jogadas efectuadas pelo jogador 0.
    
    int contador=0;
    
    for(int a=1;a<tabuleiro.length-1;a++)
      
      for(int b=1;b<tabuleiro.length-1;b++){
      
      if(JogadasReversi.JogadaO(tabuleiro, a, b)==true)
        
        contador+=1;
    }
    return contador;
  }
}
  