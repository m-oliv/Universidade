public class TabuleiroReversi{
  
  /*Nesta classe encontram-se os métodos que escrevem o tabuleiro,
   * os seus limites, e as coordenadas das linhas e das colunas.*/
  
  public static void Tabuleiro(int p[][]){

    /*Este método escreve o tabuleiro inicial com as quatro peças no centro.*/ 
    
    
//Invoca o método que coloca as peças iniciais no centro do tabuleiro.   
    
    Tabuleiroinicial(p);
    
//Invoca o método que escreve o tabuleiro no output.    
    
    Tabuleirofisico(p);
    
//Invoca o menu do primeiro jogador (que se encontra na classe JogadoresReversi.java).    
    
    JogadoresReversi.menujogadorX(p);
  }
     
  public static void Tabuleirofisico(int b [][]){

//Este método escreve o tabuleiro com a jogada inicial.
    
    String [][]tabuleiro= new String [b.length][b.length];
    
//Invoca o método Peças, que escreve a jogada inicial.    
    
    JogadasReversi.Peças(b,tabuleiro);
    
    Letras(b);
    
    Traçosvert(b);
    
//Escreve as colunas.   
    
    for(int linha =1; linha<b.length-1;linha++){
      
      for(int coluna =1; coluna<b.length-1;coluna++){
        
        if(coluna==1){

          System.out.print(linha+ "|");
        
        }
        
        System.out.print(tabuleiro[linha] [coluna]+ "|");
        
        if(coluna==b.length-2)
          
          System.out.print(linha);
      }
      
      System.out.println();
      
      Traçosvert(b);
    }
  
    Letras(b);
    
    System.out.println();
    
//Conta o número de peças de cada jogador e escreve-o no output.    
    
    System.out.println("Nº de peças X - "+Contador.ContadorPeçasX(b)+"     Nº de peças O - "+Contador.ContadorPeçasO(b));
  
    System.out.println();
  }
    
  public static void Letras(int d[][]){

//Este método escreve as coordenadas das colunas (caracteres).
    
    char []letras= {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    
    System.out.print("  ");
    
    for(int linha=0;linha<d.length-2;linha++)
      
      System.out.print(letras[linha]+ " ");
    
    System.out.println();
  
  }
  
  public static void Traçosvert(int e[][]){

//Este método escreve as linhas.
    
    System.out.print("  ");
    
    for(int linha=0;linha<e.length-2;linha++)
    
      System.out.print("- ");
    
    System.out.println();
  }
  
  public static void Tabuleiroinicial(int c[][]){

//Este método coloca a primeira jogada no centro do tabuleiro.
  
//Escreve as peças X nos locais assinalados.    
    
    c[c.length/2][(c.length/2)-1]=1;
    
    c[(c.length/2)-1][c.length/2]=1;
    
//Escreve as peças 0 nos locais assinalados.    
    
    c[c.length/2][c.length/2]=2;
    
    c[(c.length/2)-1][(c.length/2)-1]=2;
  }
}