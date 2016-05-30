/*Esta classe permite atribuir uma 
 * pontuação ao jogador.*/


public class Classificacao{
 
  int pontos;                            //Inteiro que irá corresponder ao número do jogador.
  
  
  public Classificacao(){                //Construtor.
    pontos = 0;
    
  }
  
  public Classificacao(int p){           //Construtor que recebe argumentos.
   this.pontos=p;
  }
  
  public int getPontos(){                //Retorna o número correspondente à pontuação do jogador.
    return pontos;
  }
  
  public void setPontos(int a){          //Permite a alteração da pontuação.
    pontos=a;
  }
  
  public String toString(){              //Retornar a pontuação sob a forma de uma String.
    
    return "Pontuação: "+pontos;
  }
 
}
  

  
  