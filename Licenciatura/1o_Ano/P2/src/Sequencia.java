/* Na classe Sequencia.java � gerada a sequ�cia 
 * aleat�ria de caracteres que o jogador ter� de adivinhar.*/
import java.util.Arrays;

public class Sequencia{
  
  char [] seq = new char [4];                           //Array de caracteres que ir� conter a sequ�ncia aleat�ria.
  
  public Sequencia(){                                   //Construtor.
    
    seq = geraSequencia();
    
  }

  public char [] geraSequencia(){                       //Gera uma sequ�ncia aleat�ria com os caracteres correspondentes �s cores dispon�veis.
    
    char [] seqtemp = new char [4];                     //Tempor�rio.
    
    char [] hip = {'B','G','L','R','W','Y'};            //Hip�teses para o padr�o (Blue, Green, Lilac, Red, White and Yellow).
  
    for (int i = 0; i< 4; i++){
      
      int i1=(int) (6*Math.random());                   //S�o gerados inteiros aleat�rios, cada um correspondendo a uma posi��o do array que cont�m as hip�teses para o padr�o.
    
      seqtemp[i]=hip[i1];                               //S�o copiados os elementos aleat�rios gerados anteriormente para o array tempor�rio 'cores'.
    }
    return seqtemp;                                     //� retornada a sequ�ncia aleat�ria.
  }
  
   
  public String toString(){                             //Retorna a sequ�ncia aleat�ria de caracteres gerada pelo computador.
    
    String a= Arrays.toString(seq);                     //String correspondente ao array que cont�m a sequ�ncia aleat�ria.
    
    return "Sequ�ncia Correcta: "+a;
  }
  
  public char[] getSequencia(){                         //Retorna o array de chars com a sequ�ncia aleat�ria gerada pelo computador.
    return seq;
  }
  
  
}
