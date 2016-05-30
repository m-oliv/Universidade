/* Nesta classe será criado o tabuleiro de jogo. Esta classe torna
 * também possível adicionar elementos ao tabuleiro, retornar jogadas ou
 * o tabuleiro sob a forma de uma String, verificar se o tabuleiro está cheio,
 * verificar o número de espaços vazios no tabuleiro e alterar e retornar
 * tamanho do referido tabuleiro de jogo.*/

import java.util.ArrayList;

public class Tabuleiro <T>{
 
  int maxsize;                                      //Inteiro correspondente ao tamanho do tabuleiro.
  ArrayList <T> tabuleiro;                          //ArrayList correspondente ao tabuleiro.
  
  public Tabuleiro(int n){                          //Construtor que recebe argumentos.
    this.maxsize = n;
    tabuleiro = new ArrayList <T>(n);
  }
  
  public int getSize(){                             //Retorna o tamanho do tabuleiro.
   int size = tabuleiro.size();
    return size;
      
  }
  
  public void setMaxsize(int a){                    //Permite alterar o valor que corresponde ao tamanho máximo do tabuleiro.
    maxsize=a;
  }
  
  public String toString(){                         //Retorna o toString do tabuleiro.
    return tabuleiro.toString();
  }
  
  public int espaçoVazio(){                         //Obtém o número de espaços vazios no tabuleiro.
    int vazio = maxsize-(this.getSize());
    return vazio;
  }
  
  public T getPlay(int n){                          //Retorna o elemento n do tabuleiro.
    return tabuleiro.get(n);
  }
  
  public boolean isFull(){                          //Verifica se os lugares do tabuleiro estão todos preenchidos.
    boolean cheio = maxsize==this.getSize();
    return cheio;
  }
  
  public void insere(T a){                          //Insere as jogadas do jogador no tabuleiro.
    tabuleiro.add(a);
  }
}
  
  