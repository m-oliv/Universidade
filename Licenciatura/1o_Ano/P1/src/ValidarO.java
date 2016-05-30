public class ValidarO{
  
  /* Nesta classe o programa valida as jogadas
   * efectuadas pelo jogador 0 em todas as direcções.*/
  
  public static boolean VerticalCima(int linha, int coluna, int tab[][]){
    
//Valida jogadas efectuadas na vertical, no sentido ascendente.    
    
    boolean Valido=false;
    
    for(int a=linha;a>=0;a--)
    
      for(int b=coluna;b==coluna;b++){
      
      if(tab[a][b]==0)
      
        break;
      
      if(tab[a][b]==2)
      
        return(Valido=true);
    }
    
    return Valido;
  }
  
  public static boolean VerticalBaixo(int linha, int coluna, int tab[][]){
  
//Valida jogadas efectuadas na vertical, sentido descendente.    
    
    boolean Valido=false;
    
    for(int a=linha;a<tab.length;a++)
    
      for(int b=coluna;b<=coluna;b++){
      
      if(tab[a][b]==0)
      
        break;
      
      if(tab[a][b]==2)
      
        return(Valido=true);
    }
    return Valido;
  }
  
  public static boolean HorizontalEsq(int linha, int coluna, int tab[][]){
    
//Valida jogadas efectuadas na horizontal e para a esquerda.    
    
    boolean Valido=false;
    
    for(int a=linha;a<=linha;a++)
    
      for(int b=coluna;b>=0;b--){
      
      if(tab[a][b]==0)
      
        break;
      
      if(tab[a][b]==2)
      
        return(Valido=true);
    }
    return Valido;
  }
  
  public static boolean HorizontalDir(int linha, int coluna, int tab[][]){
  
//Valida jogadas efectuadas na horizontal e para a direita.    
    
    boolean Valido=false;
    
    for(int a=linha;a<=linha;a++)
    
      for(int b=coluna;b<tab.length;b++){
      
      if(tab[a][b]==0)
      
        break;
      
      if(tab[a][b]==2)
      
        return(Valido=true);
    }
    return Valido;
  }
  
  public static boolean DiagonalEsqCima(int linha, int coluna, int tab[][]){
  
//Valida jogadas efectuadas na diagonal esquerda, no sentido ascendente.    
    
    boolean Valido=false;
    
    int diagonal=linha-coluna;
    
    for(int a=linha;a>=0;a--)
    
      for(int b=coluna;b>=0;b--){
      
      if(a-b==diagonal){
      
        if(tab[a][b]==0)
        
          break;
        
        if(tab[a][b]==2)
        
          return(Valido=true);
      }
    }
    return Valido;
  }
  
  public static boolean DiagonalEsqBaixo(int linha, int coluna, int tab[][]){
  
//Valida jogadas efectuadas na diagonal esquerda, no sentido descendente.    
    
    boolean Valido=false;
    
    int diagonal=linha+coluna;
    
    for(int a=linha;a<tab.length;a++)
    
      for(int b=coluna;b>=0;b--){
      
      if(a+b==diagonal){
      
        if(tab[a][b]==0)
        
          break;
      
        if(tab[a][b]==2)
        
          return(Valido=true);
      }
    }
    return Valido;
  }
  
  public static boolean DiagonalDirCima(int linha, int coluna, int tab[][]){
    
//Valida jogadas efectuadas na diagonal direita, no sentido ascendente.    
    
    boolean Valido=false;
    
    int diagonal=linha+coluna;
    
    for(int a=linha;a>=0;a--)
    
      for(int b=coluna;b<tab.length;b++){
      
      if(a+b==diagonal){
      
        if(tab[a][b]==0)
        
          break;
        
        if(tab[a][b]==2)
        
          return(Valido=true);
      }
    }
    return Valido;
  }
  
  public static boolean DiagonalDirBaixo(int linha, int coluna, int tab[][]){
  
//Valida jogadas efectuadas na diagonal direita, no sentido descendente.    
    
    boolean Valido=false;
    
    int diagonal=linha-coluna;
    
    for(int a=linha;a<tab.length;a++)
    
      for(int b=coluna;b<tab.length;b++){
      
      if(a-b==diagonal){
      
        if(tab[a][b]==0)
        
          break;
        
        if(tab[a][b]==2)
        
          return(Valido=true);
      }
    }
    return Valido;
  }
}