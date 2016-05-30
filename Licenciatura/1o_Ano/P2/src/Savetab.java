import java.util.ArrayList;
/*Esta classe tem como principal fun��o gerar a arraylist
 * onde ser�o guardados os tabuleiros dos jogos anteriormente efectuados.*/

public class Savetab <T> {
  
  int maxtab;
  ArrayList <T> tabs;
  
  public Savetab(){                             //Construtor.
    this.maxtab=100;                            //Tamanho m�ximo de elementos na ArrayList.
    tabs = new ArrayList<T>(maxtab);
  }
  
  public T getTab(int a){                       //Retorna o tabuleiro de jogo que se encontra na posi��o correspondente ao inteiro a.
    return tabs.get(a);
  }
  
  public void insereTab(T b){                   //Insere elementos na lista de tabuleiros.
    tabs.add(b);
  }
  
  public String toString(){                     //Retorna todos os elementos da arraylist sob a forma de uma String.
    return tabs.toString();
  }
  
}
    