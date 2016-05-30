/* Esta classe permite ao jogador introduzir quatro Strings,
 * que ser�o tratados de forma a ser obtida uma combina��o de
 * caracteres, correspondetes � jogada efectuada. Nesta classe
 * � tamb�m efectuada a avalia��o da jogada dada pelo utilizador,
 * atrav�s da compara��o com a sequ�ncia gerada aleat�riamente
 * pelo computador.*/

import java.util.Scanner;
import java.util.Arrays;


public class Jogada{
  
  char [] jogada = new char[4];                               //Array que ir� conter a jogada efectuada.
  int [] antirepete= {0,0,0,0};                               //Array que impedir� a repeti��o da avalia��o, sempre existem pe�as repetidas.
  char []avalia= new char[4];                                 //Array que ir� conter a avalia��o da jogada efectuada.
  
  Scanner s = new Scanner (System.in);
  
  public Jogada(){                                            //Construtor.
    
  }
  
  public char[] geraJogada(){                                 //Recebe os caracteres da jogada definidos pelo utilizador e gera a mesma.
    for (int i=0;i<4;i++){
    avalia[i]=' ';}
    
    char [] cores = new char[4];                              //Tempor�rio.
    
    String escolha;                                           //String que conter� uma pe�a dada pelo utilizador.
    
    char dif;                                                 //Tempor�rio que converter� a String introduzida num caractere.
  
   for(int i=0; i<4; i++){
      
       do{ 
        
        System.out.println("Por favor introduza o caractere correspondente � "+(i+1)+"� cor da sua jogada:");
      
        escolha = s.next();                                  //"Recebe" a String introduzida pelo utilizador.
        dif = escolha.charAt(0);                             //Converte a String introduzida pelo utilizador num char.
        cores[i] = dif;                                      //Coloca o char convertido no array que conter� as pe�as dadas pelo jogador.
     
       }while (dif!='B' && dif!='G'&&dif!='L'&&dif!='R'&&dif!='W'&&dif!='Y'); //Restri��o:  as pe�as dadas n�o podem ser diferentes das cores dispon�veis.
   }
    
    for (int i=0; i<4; i++){                                 //Copia o array com a jogada para um segundo array.
      jogada[i] = cores[i];
    }
    
    return jogada;                                           //Retorna a jogada gerada.
  
  }
   
  
 public void avaliaJogada(char [] sequencia){                //Avalia a jogada dada pelo utilizador.
   
   String a = String.valueOf(jogada[0]);                     //Cont�m a primeira pe�a da jogada efectuada pelo jogador.
   String b = String.valueOf(jogada[1]);                     //Cont�m a segunda pe�a da jogada efectuada pelo jogador.
   String c = String.valueOf(jogada[2]);                     //Cont�m a terceira pe�a da jogada efectuada pelo jogador.
   String d = String.valueOf(jogada[3]);                     //Cont�m a quarta pe�a da jogada efectuada pelo jogador.
   
   String e = String.valueOf(sequencia[0]);                  //Cont�m a primeira pe�a da sequ�ncia aleat�ria.
   String f = String.valueOf(sequencia[1]);                  //Cont�m a segunda pe�a da sequ�ncia aleat�ria.
   String g = String.valueOf(sequencia[2]);                  //Cont�m a terceira pe�a da sequ�ncia aleat�ria.
   String h = String.valueOf(sequencia[3]);                  //Cont�m a quarta pe�a da sequ�ncia aleat�ria.
   
   if(a.equals(e)==true&&antirepete[0]==0){                                    //Compara a primeira pe�a da jogada com a primeira pe�a da sequ�ncia a adivinhar.
     avalia[0] ='b';                                         //Caso se verifique a igualdade, o programa retorna a pe�a pequena b na posi��o correspondente do array avalia.
     antirepete[0]=1;                                        //� colocado o valor 1 no array antirepete, o que evita que se repitam avalia��es e que permite ao programa ignorar esta posi��o do array antirepete quando se realiza uma nova avalia��o.
   }
   if(b.equals(f)==true&&antirepete[1]==0){                  //Compara a segunda pe�a da jogada com a segunda pe�a da sequ�ncia a adivinhar.
     avalia[1]='b';                                          //Caso se verifique a igualdade, o programa retorna a pe�a pequena b na posi��o correspondente do array avalia.
     antirepete[1]=1;                                        //� colocado o valor 1 no array antirepete, o que evita que se repitam avalia��es e que permite ao programa ignorar esta posi��o do array antirepete quando se realiza uma nova avalia��o.
   }
   if(c.equals(g)==true&&antirepete[2]==0){                  //Compara a terceira pe�a da jogada com a terceira pe�a da sequ�ncia a adivinhar.
     avalia[2]='b';                                          //Caso se verifique a igualdade, o programa retorna a pe�a pequena b na posi��o correspondente do array avalia.
     antirepete[2]=1;                                        //� colocado o valor 1 no array antirepete, o que evita que se repitam avalia��es e que permite ao programa ignorar esta posi��o do array antirepete quando se realiza uma nova avalia��o.
   }
   if(d.equals(h)==true&&antirepete[3]==0){                  //Compara a quarta pe�a da jogada com a quarta pe�a da sequ�ncia a adivinhar.
     avalia[3]='b';                                          //Caso se verifique a igualdade, o programa retorna a pe�a pequena b na posi��o correspondente do array avalia.
     antirepete[3]=1;                                        //� colocado o valor 1 no array antirepete, o que evita que se repitam avalia��es e que permite ao programa ignorar esta posi��o do array antirepete quando se realiza uma nova avalia��o.
   }
   if(a.equals(f)==true&&avalia[0]==' '&&antirepete[1]==0){                  //Compara a primeira pe�a da jogada com a segunda pe�a da sequ�ncia a adivinhar.
     avalia[0]='w';                                        //Caso se verifique a igualdade, o programa retorna a pe�a pequena w na posi��o correspondente do array avalia.
     antirepete[1]=1;                                      //� colocado o valor 1 no array antirepete, o que evita que se repitam avalia��es e que permite ao programa ignorar esta posi��o do array antirepete quando se realiza uma nova avalia��o.
   }
   if(a.equals(g)==true&&avalia[0]==' '&&antirepete[2]==0){                  //Compara a primeira pe�a da jogada com a terceira pe�a da sequ�ncia a adivinhar.
     avalia[0]='w';                                        //Caso se verifique a igualdade, o programa retorna a pe�a pequena w na posi��o correspondente do array avalia.
     antirepete[2]=1;                                      //� colocado o valor 1 no array antirepete, o que evita que se repitam avalia��es e que permite ao programa ignorar esta posi��o do array antirepete quando se realiza uma nova avalia��o.
   }
   if(a.equals(h)==true&&avalia[0]==' '&&antirepete[3]==0){                  //Compara a primeira pe�a da jogada com a quarta pe�a da sequ�ncia a adivinhar.
     avalia[0]='w';                                        //Caso se verifique a igualdade, o programa retorna a pe�a pequena w na posi��o correspondente do array avalia.
     antirepete[3]=1;                                      //� colocado o valor 1 no array antirepete, o que evita que se repitam avalia��es e que permite ao programa ignorar esta posi��o do array antirepete quando se realiza uma nova avalia��o.
   }
   if(b.equals(e)==true&&antirepete[0]==0&&avalia[1]==' '){//Compara a segunda pe�a da jogada com a primeira pe�a da sequ�ncia a adivinhar.
     avalia[1]='w';                                        //Caso se verifique a igualdade, o programa retorna a pe�a pequena w na posi��o correspondente do array avalia.
     antirepete[0]=1;                                      //� colocado o valor 1 no array antirepete, o que evita que se repitam avalia��es e que permite ao programa ignorar esta posi��o do array antirepete quando se realiza uma nova avalia��o.
   }
   if(b.equals(g)==true&&antirepete[2]==0&&avalia[1]==' '){//Compara a segunda pe�a da jogada com a terceira pe�a da sequ�ncia a adivinhar.
     avalia[1]='w';                                        //Caso se verifique a igualdade, o programa retorna a pe�a pequena w na posi��o correspondente do array avalia.
     antirepete[2]=1;                                      //� colocado o valor 1 no array antirepete, o que evita que se repitam avalia��es e que permite ao programa ignorar esta posi��o do array antirepete quando se realiza uma nova avalia��o.
   }
   if(b.equals(h)==true&&antirepete[3]==0&&avalia[1]==' '){//Compara a segunda pe�a da jogada com a quarta pe�a da sequ�ncia a adivinhar.
     avalia[1]='w';                                        //Caso se verifique a igualdade, o programa retorna a pe�a pequena w na posi��o correspondente do array avalia.
     antirepete[3]=1;                                      //� colocado o valor 1 no array antirepete, o que evita que se repitam avalia��es e que permite ao programa ignorar esta posi��o do array antirepete quando se realiza uma nova avalia��o.
   }
   if(c.equals(e)==true&&antirepete[0]==0&&avalia[2]==' '){//Compara a terceira pe�a da jogada com a primeira pe�a da sequ�ncia a adivinhar.
     avalia[2]='w';                                        //Caso se verifique a igualdade, o programa retorna a pe�a pequena w na posi��o correspondente do array avalia.
     antirepete[0]=1;                                      //� colocado o valor 1 no array antirepete, o que evita que se repitam avalia��es e que permite ao programa ignorar esta posi��o do array antirepete quando se realiza uma nova avalia��o. 
   }
   if(c.equals(f)==true&&antirepete[1]==0&&avalia[2]==' '){//Compara a terceira pe�a da jogada com a segunda pe�a da sequ�ncia a adivinhar.
     avalia[2]='w';                                        //Caso se verifique a igualdade, o programa retorna a pe�a pequena w na posi��o correspondente do array avalia.
     antirepete[1]=1;                                      //� colocado o valor 1 no array antirepete, o que evita que se repitam avalia��es e que permite ao programa ignorar esta posi��o do array antirepete quando se realiza uma nova avalia��o.
   }
   if(c.equals(h)==true&&antirepete[3]==0&&avalia[2]==' '){//Compara a terceira pe�a da jogada com a quarta pe�a da sequ�ncia a adivinhar.
     avalia[2]='w';                                        //Caso se verifique a igualdade, o programa retorna a pe�a pequena w na posi��o correspondente do array avalia.
     antirepete[3]=1;                                      //� colocado o valor 1 no array antirepete, o que evita que se repitam avalia��es e que permite ao programa ignorar esta posi��o do array antirepete quando se realiza uma nova avalia��o.
   }
   if(d.equals(e)==true&&antirepete[0]==0&&avalia[3]==' '){//Compara a quarta pe�a da jogada com a primeira pe�a da sequ�ncia a adivinhar.
     avalia[3]='w';                                        //Caso se verifique a igualdade, o programa retorna a pe�a pequena w na posi��o correspondente do array avalia.
     antirepete[0]=1;                                      //� colocado o valor 1 no array antirepete, o que evita que se repitam avalia��es e que permite ao programa ignorar esta posi��o do array antirepete quando se realiza uma nova avalia��o.
   }
   if(d.equals(f)==true&&antirepete[1]==0&&avalia[3]==' '){//Compara a quarta pe�a da jogada com a segunda pe�a da sequ�ncia a adivinhar.
     avalia[3]='w';                                        //Caso se verifique a igualdade, o programa retorna a pe�a pequena w na posi��o correspondente do array avalia.
     antirepete[1]=1;                                      //� colocado o valor 1 no array antirepete, o que evita que se repitam avalia��es e que permite ao programa ignorar esta posi��o do array antirepete quando se realiza uma nova avalia��o.
   }
   if(d.equals(g)==true&&antirepete[2]==0&&avalia[3]==' '){//Compara a quarta pe�a da jogada com a terceira pe�a da sequ�ncia a adivinhar.
     avalia[3]='w';                                        //Caso se verifique a igualdade, o programa retorna a pe�a pequena w na posi��o correspondente do array avalia.
     antirepete[2]=1;                                      //� colocado o valor 1 no array antirepete, o que evita que se repitam avalia��es e que permite ao programa ignorar esta posi��o do array antirepete quando se realiza uma nova avalia��o.
   }
 }
 
 
 public void resetAvalia(){                                 // Efectua o reset aos arrays avalia e jogada.
   char [] b = new char [4];
   int [] c = {0,0,0,0};
   avalia=b;
   antirepete=c;
 }
 public String restantes(int a){                             //Retorna o n�mero de jogadas restantes sob a forma de uma String.
    return "Jogadas Restantes: "+a;
  }
 
 
 public String toString(){                                   //Retorna a Jogada efectuada e a respectiva avalia��o sob a forma de uma String.
   String av = Arrays.toString(avalia);
   String jog = Arrays.toString(jogada);                     //String tempor�ria que conter� o array k sob a forma de uma String.
   
    return " Jogada Efectuada: "+jog+" ------> "+av;        //Retorna a jogada efectuada e a respectiva avalia��o sob a forma de uma String.
  }
 
}