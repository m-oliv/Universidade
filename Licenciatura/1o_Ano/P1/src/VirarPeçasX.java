public class VirarPe�asX{
 
  /*Esta classe cont�m os m�todos est�ticos que
   * t�m como principal fun��o virar pe�as 0 e
   * substitu�las por pe�as X.*/
  
  public static void Vertical(int linha, int coluna, int tab[][]){
  
    //Este m�todo vira pe�as 0 na vertical.    
    
    boolean virar=false;
    
    int repetirX=0;
    
    int repetirO=0;
    
    for(int a=0;a<tab.length;a++){
    
      for(int b=coluna;b==coluna;b++){
      
        if(tab[a][b]==1){
        
          if(repetirX==0||(repetirX==1&&repetirO>0))
          
            repetirX+=1;
          
          virar=true;
        }
       
        if(virar=true&&repetirX<2){
        
          if(tab[a][b]==2){
          
            tab[a][b]=1;
            
            repetirO+=1;
          }
        }
        
        if(repetirX==2)
        
          virar=false;
      }
    }
  }
  
  public static void Horizontal(int linha, int coluna, int tab[][]){
  
//Este m�todo vira pe�as 0 na horizontal.    
    
    boolean virar=false;
    
    int repetirX=0;
    
    int repetirO=0;
    
    for(int a=linha;a==linha;a++){
    
      for(int b=0;b<tab.length;b++){
      
        if(tab[a][b]==1){
        
          if(repetirX==0||(repetirX==1&&repetirO>0))
          
            repetirX+=1;
          
          virar=true;
        }
        
        if(virar=true&&repetirX<2){
        
          if(tab[a][b]==2){
          
            tab[a][b]=1;
            
            repetirO+=1;
          }
        }
        
        if(repetirX==2)
        
          virar=false;
      }
    }
  }
  
  public static void DiagonalCimaBaixo(int linha, int coluna, int tab[][]){
  
//Este m�todo vira pe�as 0 na diagonal (percorre-a de cima para baixo).    
    
    boolean virar=false;
    
    int repetirX=0;
    
    int repetirO=0;
    
    int diagonal=linha-coluna;
    
    for(int a=0;a<tab.length;a++)
    
      for(int b=0;b<tab.length;b++){
      
      if(a-b==diagonal){
      
        if(tab[a][b]==1){
        
          if(repetirX==0||(repetirX==1&&repetirO>0))
          
            repetirX+=1;
          
          virar=true;
        }
        
        if(virar=true&&repetirX<2){
          
          if(tab[a][b]==2){
          
            tab[a][b]=1;
            
            repetirO+=1;
          }
        }
        
        if(repetirX==2)
        
          virar=false;
      }
    }
  }
  
  public static void DiagonalBaixoCima(int linha, int coluna, int tab[][]){
  
//Este m�todo vira pe�as 0 na diagonal (percorre-a de baixo para cima).     
    
    boolean virar=false;
    
    int repetirX=0;
    
    int repetirO=0;
    
    int diagonal=linha+coluna;
    
    for(int a=0;a<tab.length;a++)
    
      for(int b=0;b<tab.length;b++){
      
      if(a+b==diagonal){
      
        if(tab[a][b]==1){
        
          if(repetirX==0||(repetirX==1&&repetirO>0))
          
            repetirX+=1;
          
          virar=true;
        }
        
        if(virar=true&&repetirX<2){
        
          if(tab[a][b]==2){
          
            tab[a][b]=1;
            
            repetirO+=1;
          }
        }
        
        if(repetirX==2)
        
          virar=false;
      }
    }
  }
}