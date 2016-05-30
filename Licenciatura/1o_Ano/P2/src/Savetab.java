import java.util.ArrayList;
/*Esta classe tem como principal função gerar a arraylist
 * onde serão guardados os tabuleiros dos jogos anteriormente efectuados.*/

public class Savetab <T> {
  
  int maxtab;
  ArrayList <T> tabs;
  
  public Savetab(){                             //Construtor.
    this.maxtab=100;                            //Tamanho máximo de elementos na ArrayList.
    tabs = new ArrayList<T>(maxtab);
  }
  
  public T getTab(int a){                       //Retorna o tabuleiro de jogo que se encontra na posição correspondente ao inteiro a.
    return tabs.get(a);
  }
  
  public void insereTab(T b){                   //Insere elementos na lista de tabuleiros.
    tabs.add(b);
  }
  
  public String toString(){                     //Retorna todos os elementos da arraylist sob a forma de uma String.
    return tabs.toString();
  }
  
}
    