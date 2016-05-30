/*Esta classe permite atribuir uma 
 * pontua��o ao jogador.*/


public class Classificacao{
 
  int pontos;                            //Inteiro que ir� corresponder ao n�mero do jogador.
  
  
  public Classificacao(){                //Construtor.
    pontos = 0;
    
  }
  
  public Classificacao(int p){           //Construtor que recebe argumentos.
   this.pontos=p;
  }
  
  public int getPontos(){                //Retorna o n�mero correspondente � pontua��o do jogador.
    return pontos;
  }
  
  public void setPontos(int a){          //Permite a altera��o da pontua��o.
    pontos=a;
  }
  
  public String toString(){              //Retornar a pontua��o sob a forma de uma String.
    
    return "Pontua��o: "+pontos;
  }
 
}
  

  
  