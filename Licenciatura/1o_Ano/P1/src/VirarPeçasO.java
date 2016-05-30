public class VirarPe�asO{

  /*Esta classe cont�m os m�todos est�ticos que
   * t�m como principal fun��o virar pe�as X e
   * substitu�las por pe�as 0.*/
  
  public static void Vertical(int linha, int coluna, int tab[][]){

//Este m�todo vira pe�as X na vertical. 
    
    boolean virar=false;
    
    int repetirO=0;
    
    int repetirX=0;
    
    for(int a=0;a<tab.length;a++){
    
      for(int b=coluna;b==coluna;b++){
      
        if(tab[a][b]==2){
        
          if(repetirO==0||(repetirO==1&&repetirX>0))
          
            repetirO+=1;
         
          virar=true;
        }
       
        if(virar=true&&repetirO<2){
        
          if(tab[a][b]==1){
          
            tab[a][b]=2;
           
            repetirX+=1;
         }
        }
       
        if(repetirO==2)
          
          virar=false;
      }
    }
  }
  
  public static void Horizontal(int linha, int coluna, int tab[][]){
  
//Este m�todo vira pe�as X na horizontal.    
    
    boolean virar=false;
    
    int repetirO=0;
    
    int repetirX=0;
    
    for(int a=linha;a==linha;a++){
    
      for(int b=0;b<tab.length;b++){
      
        if(tab[a][b]==2){
        
          if(repetirO==0||(repetirO==1&&repetirX>0))
          
            repetirO+=1;
          
          virar=true;
        }
        
        if(virar=true&&repetirO<2){
        
          if(tab[a][b]==1){
          
            tab[a][b]=2;
            
            repetirX+=1;
          }
        }
        
        if(repetirO==2)
        
          virar=false;
      }
    }
  }
  
  public static void DiagonalCimaBaixo(int linha, int coluna, int tab[][]){
  
//Este m�todo vira pe�as X na diagonal (percorre-a de cima para baixo).    
    
    boolean virar=false;
    
    int repetirO=0;
    
    int repetirX=0;
    
    int diagonal=linha-coluna;
    
    for(int a=0;a<tab.length;a++)
    
      for(int b=0;b<tab.length;b++){
      
      if(a-b==diagonal){
      
        if(tab[a][b]==2){
        
          if(repetirO==0||(repetirO==1&&repetirX>0))
          
            repetirO+=1;
          
          virar=true;
        }
        
        if(virar=true&&repetirO<2){
        
          if(tab[a][b]==1){
          
            tab[a][b]=2;
            
            repetirX+=1;
          }
        }
        
        if(repetirO==2)
        
          virar=false;
      }
    }
  }
  
  public static void DiagonalBaixoCima(int linha, int coluna, int tab[][]){
  
//Este m�todo vira pe�as X na diagonal (percorre-a de baixo para cima).    
    
    boolean virar=false;
    
    int repetirO=0;
    
    int repetirX=0;
    
    int diagonal=linha+coluna;
    
    for(int a=0;a<tab.length;a++)
    
      for(int b=0;b<tab.length;b++){
      
      if(a+b==diagonal){
      
        if(tab[a][b]==2){
        
          if(repetirO==0||(repetirO==1&&repetirX>0))
          
            repetirO+=1;
          
          virar=true;
        }
        
        if(virar=true&&repetirO<2){
        
          if(tab[a][b]==1){
          
            tab[a][b]=2;
            
            repetirX+=1;
          }
        }
        
        if(repetirO==2)
        
          virar=false;
      }
    }
  }
}