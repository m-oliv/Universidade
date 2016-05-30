public class Contador{
  /*Nesta classe são efectuadas as contagens
   * necessárias para o decorrer do jogo. */
  
  public static int ContadorPeçasX(int tabuleiro[][]){
    
//Este método conta o número de peças X que se encontram no tabuleiro.
    
    int peçasX=0;
    
    for(int coluna=1;coluna<tabuleiro.length-1;coluna++)
      
      for(int linha=1;linha<tabuleiro.length-1;linha++){
      
      if(tabuleiro[coluna][linha]==1)
        
        peçasX+=1;
    }
    return peçasX;
  }
   
  public static int ContadorPeçasO(int tabuleiro[][]){
     
//Este método conta o número de peças 0 que se encontram no tabuleiro.
     
    int peçasO=0;
    
    for(int coluna=1;coluna<tabuleiro.length-1;coluna++)
      
      for(int linha=1;linha<tabuleiro.length-1;linha++){
      
      if(tabuleiro[coluna][linha]==2)
        
        peçasO+=1;
    }
    return peçasO; 
  }
  
  public static int ContadorPeçasTotal(int tabuleiro[][]){
    
//Este método conta o número total de jogadas efectuadas por ambos os jogadores.
    
    int peçastotal=0;
    
    for(int coluna=1;coluna<tabuleiro.length-1;coluna++)
      
      for(int linha=1;linha<tabuleiro.length-1;linha++){
      
      if(tabuleiro[coluna][linha]!=0)
        
        peçastotal+=1;
    }
    return peçastotal;
  }
  
  public static int ContarJogadasX(int tabuleiro[][]){
    
// Este método conta o número de jogadas efectuadas pelo jogador X.
    
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
    
//Este método conta o número de jogadas efectuadas pelo jogador 0.
    
    int contador=0;
    
    for(int a=1;a<tabuleiro.length-1;a++)
      
      for(int b=1;b<tabuleiro.length-1;b++){
      
      if(JogadasReversi.JogadaO(tabuleiro, a, b)==true)
        
        contador+=1;
    }
    return contador;
  }
}
  