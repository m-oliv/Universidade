public class JogadasReversi{
   
  /*Nesta classe encontram-se os métodos que escrevem as peças,
   * as colocam nos locais indicados pelos jogadores, bem como 
   * os métodos que reconhecem os caracteres dados pelos 
   * utilizadores na linha de comandos e que os convertem em 
   * inteiros.*/
  
  
  public static void Peças(int s[][], String w[][]){

//Este método escreve as peças do jogo.
    
    for(int i=0;i<s.length;i++)
      
      for(int j=0;j<s.length;j++){
      
      w[i][j]=" ";
      
//Escreve as peças X no tabuleiro. 
      
      if(s[i][j]==1)
        
        w[i][j]="X";
 
//Escreve as peças 0 no tabuleiro.       
      
      if(s[i][j]==2)
        
        w[i][j]="O"; 
    }
    
  }
  
  public static char Abc(int a){

//Este método reconhece o carácter inserido pelo utilizador na linha de comandos.
    
    char []letras= {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    
    char letra;
    
    letra=letras[a-1];
    
    return letra;
  
  }
  
  public static int AbcInverso(char a){

//Este método converte os caracteres dados pelos jogadores na linha de comandos em inteiros.
    
    char []letras= {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    
    int numero=0;
    
    for(int coluna=0;coluna<letras.length;coluna++){
      
      numero+=1;
      
      if(a==letras[coluna])
        
        break;
    }
    
    return numero;
  }
  
  public static boolean JogadaX(int tab[][],int linha, int coluna){

    /*Este método verifica se a jogada é válida (recorre aos
     * métodos da classe ValidarX.java), coloca a peça X no local
     * assinalado e, caso seja possível, vira peças 0 (recorre aos
     * métodos da classe VirarPeçasX.java para o efeito).*/
    
    boolean validoX=false;
    
    if(tab[linha][coluna]==0){
      
      for(int a=linha-1;a<=linha+1;a++)
        
        for(int b=coluna-1;b<=coluna+1;b++){
        
        if(tab[a][b]==2){
          
          if(a==linha-1&&b==coluna)
            
            if(ValidarX.VerticalCima(a, b, tab)==true){
            
            tab[linha][coluna]=1;
            
            VirarPeçasX.Vertical(a, b, tab);
            
            validoX=true;
          }
          
          if(a==linha+1&&b==coluna)
            
            if((ValidarX.VerticalBaixo(a, b, tab))==true){
            
            tab[linha][coluna]=1;
            
            VirarPeçasX.Vertical(a, b, tab);
            
            validoX=true;
          }
          
          if(a==linha&&b==coluna-1)
            
            if(ValidarX.HorizontalEsq(a, b, tab)==true){
            
            tab[linha][coluna]=1;
            
            VirarPeçasX.Horizontal(a, b, tab);
            
            validoX=true;
          }
          
          if(a==linha&&b==coluna+1)
            
            if(ValidarX.HorizontalDir(a, b, tab)==true){
            
            tab[linha][coluna]=1;
            
            VirarPeçasX.Horizontal(a, b, tab);
            
            validoX=true;
          }
          
          if(a==linha-1&&b==coluna-1)
            
            if(ValidarX.DiagonalEsqCima(a, b, tab)==true){
            
            tab[linha][coluna]=1;
            
            VirarPeçasX.DiagonalCimaBaixo(a, b, tab);
            
            validoX=true;
          }
          
          if(a==linha+1&&b==coluna-1)
            
            if(ValidarX.DiagonalEsqBaixo(a, b, tab)==true){
            
            tab[linha][coluna]=1;
            
            VirarPeçasX.DiagonalBaixoCima(a, b, tab);
             
            validoX=true;
          }
          
          if(a==linha-1&&b==coluna+1)
            
            if(ValidarX.DiagonalDirCima(a, b, tab)==true){
            
            tab[linha][coluna]=1;
            
            VirarPeçasX.DiagonalBaixoCima(a, b, tab);
            
            validoX=true;
          }
          
          if(a==linha+1&&b==coluna+1)
            
            if(ValidarX.DiagonalDirBaixo(a, b, tab)==true){
            
            tab[linha][coluna]=1;
            
            VirarPeçasX.DiagonalCimaBaixo(a, b, tab);
            
            validoX=true;
          }
        }
      }
    }
    
    return validoX;
  }
   
  public static boolean JogadaO(int tab[][],int linha, int coluna){
    
    /*Este método verifica se a jogada é válida (recorre aos
     * métodos da classe ValidarO.java), coloca a peça X no local
     * assinalado e, caso seja possível, vira peças X (recorre aos
     * métodos da classe VirarPeçasO.java para o efeito).*/
    
    boolean validoO=false;
    
    if(tab[linha][coluna]==0){
      
      for(int a=linha-1;a<=linha+1;a++)
        
        for(int b=coluna-1;b<=coluna+1;b++){
        
        if(tab[a][b]==1){
          
          if(a==linha-1&&b==coluna)
            
            if(ValidarO.VerticalCima(a, b, tab)==true){
            
            tab[linha][coluna]=1;
            
            VirarPeçasO.Vertical(a, b, tab);
            
            validoO=true;
          }
          
          if(a==linha+1&&b==coluna)
            
            if((ValidarO.VerticalBaixo(a, b, tab))==true){
            
            tab[linha][coluna]=2;
            
            VirarPeçasO.Vertical(a, b, tab);
            
            validoO=true;
          }
          
          if(a==linha&&b==coluna-1)
            
            if(ValidarO.HorizontalEsq(a, b, tab)==true){
            
            tab[linha][coluna]=2;
            
            VirarPeçasO.Horizontal(a, b, tab);
            
            validoO=true;
          }
          
          if(a==linha&&b==coluna+1)
            
            if(ValidarO.HorizontalDir(a, b, tab)==true){
            
            tab[linha][coluna]=2;
            
            VirarPeçasO.Horizontal(a, b, tab);
            
            validoO=true;
          }
          
          if(a==linha-1&&b==coluna-1)
            
            if(ValidarO.DiagonalEsqCima(a, b, tab)==true){
            
            tab[linha][coluna]=2;
            
            VirarPeçasO.DiagonalCimaBaixo(a, b, tab);
            
            validoO=true;
          }
          
          if(a==linha+1&&b==coluna-1)
            
            if(ValidarO.DiagonalEsqBaixo(a, b, tab)==true){
            
            tab[linha][coluna]=2;
            
            VirarPeçasO.DiagonalBaixoCima(a, b, tab);
             
            validoO=true;
          }
          
          if(a==linha-1&&b==coluna+1)
            
            if(ValidarO.DiagonalDirCima(a, b, tab)==true){
            
            tab[linha][coluna]=2;
            
            VirarPeçasO.DiagonalBaixoCima(a, b, tab);
             
            validoO=true;
          }
          
          if(a==linha+1&&b==coluna+1)
            
            if(ValidarO.DiagonalDirBaixo(a, b, tab)==true){
            
            tab[linha][coluna]=2;
            
            VirarPeçasO.DiagonalCimaBaixo(a, b, tab);
            
            validoO=true;}
        }
      }
    }
    
    return validoO;
  }
}