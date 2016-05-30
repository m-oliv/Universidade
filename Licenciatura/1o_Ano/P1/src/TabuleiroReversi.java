public class TabuleiroReversi{
  
  /*Nesta classe encontram-se os m�todos que escrevem o tabuleiro,
   * os seus limites, e as coordenadas das linhas e das colunas.*/
  
  public static void Tabuleiro(int p[][]){

    /*Este m�todo escreve o tabuleiro inicial com as quatro pe�as no centro.*/ 
    
    
//Invoca o m�todo que coloca as pe�as iniciais no centro do tabuleiro.   
    
    Tabuleiroinicial(p);
    
//Invoca o m�todo que escreve o tabuleiro no output.    
    
    Tabuleirofisico(p);
    
//Invoca o menu do primeiro jogador (que se encontra na classe JogadoresReversi.java).    
    
    JogadoresReversi.menujogadorX(p);
  }
     
  public static void Tabuleirofisico(int b [][]){

//Este m�todo escreve o tabuleiro com a jogada inicial.
    
    String [][]tabuleiro= new String [b.length][b.length];
    
//Invoca o m�todo Pe�as, que escreve a jogada inicial.    
    
    JogadasReversi.Pe�as(b,tabuleiro);
    
    Letras(b);
    
    Tra�osvert(b);
    
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
      
      Tra�osvert(b);
    }
  
    Letras(b);
    
    System.out.println();
    
//Conta o n�mero de pe�as de cada jogador e escreve-o no output.    
    
    System.out.println("N� de pe�as X - "+Contador.ContadorPe�asX(b)+"     N� de pe�as O - "+Contador.ContadorPe�asO(b));
  
    System.out.println();
  }
    
  public static void Letras(int d[][]){

//Este m�todo escreve as coordenadas das colunas (caracteres).
    
    char []letras= {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    
    System.out.print("  ");
    
    for(int linha=0;linha<d.length-2;linha++)
      
      System.out.print(letras[linha]+ " ");
    
    System.out.println();
  
  }
  
  public static void Tra�osvert(int e[][]){

//Este m�todo escreve as linhas.
    
    System.out.print("  ");
    
    for(int linha=0;linha<e.length-2;linha++)
    
      System.out.print("- ");
    
    System.out.println();
  }
  
  public static void Tabuleiroinicial(int c[][]){

//Este m�todo coloca a primeira jogada no centro do tabuleiro.
  
//Escreve as pe�as X nos locais assinalados.    
    
    c[c.length/2][(c.length/2)-1]=1;
    
    c[(c.length/2)-1][c.length/2]=1;
    
//Escreve as pe�as 0 nos locais assinalados.    
    
    c[c.length/2][c.length/2]=2;
    
    c[(c.length/2)-1][(c.length/2)-1]=2;
  }
}