/* Na classe Sequencia.java é gerada a sequêcia 
 * aleatória de caracteres que o jogador terá de adivinhar.*/
import java.util.Arrays;

public class Sequencia{
  
  char [] seq = new char [4];                           //Array de caracteres que irá conter a sequência aleatória.
  
  public Sequencia(){                                   //Construtor.
    
    seq = geraSequencia();
    
  }

  public char [] geraSequencia(){                       //Gera uma sequência aleatória com os caracteres correspondentes às cores disponíveis.
    
    char [] seqtemp = new char [4];                     //Temporário.
    
    char [] hip = {'B','G','L','R','W','Y'};            //Hipóteses para o padrão (Blue, Green, Lilac, Red, White and Yellow).
  
    for (int i = 0; i< 4; i++){
      
      int i1=(int) (6*Math.random());                   //São gerados inteiros aleatórios, cada um correspondendo a uma posição do array que contém as hipóteses para o padrão.
    
      seqtemp[i]=hip[i1];                               //São copiados os elementos aleatórios gerados anteriormente para o array temporário 'cores'.
    }
    return seqtemp;                                     //É retornada a sequência aleatória.
  }
  
   
  public String toString(){                             //Retorna a sequência aleatória de caracteres gerada pelo computador.
    
    String a= Arrays.toString(seq);                     //String correspondente ao array que contém a sequência aleatória.
    
    return "Sequência Correcta: "+a;
  }
  
  public char[] getSequencia(){                         //Retorna o array de chars com a sequência aleatória gerada pelo computador.
    return seq;
  }
  
  
}
