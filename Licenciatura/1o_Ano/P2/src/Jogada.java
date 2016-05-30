/* Esta classe permite ao jogador introduzir quatro Strings,
 * que serão tratados de forma a ser obtida uma combinação de
 * caracteres, correspondetes à jogada efectuada. Nesta classe
 * é também efectuada a avaliação da jogada dada pelo utilizador,
 * através da comparação com a sequência gerada aleatóriamente
 * pelo computador.*/

import java.util.Scanner;
import java.util.Arrays;


public class Jogada{
  
  char [] jogada = new char[4];                               //Array que irá conter a jogada efectuada.
  int [] antirepete= {0,0,0,0};                               //Array que impedirá a repetição da avaliação, sempre existem peças repetidas.
  char []avalia= new char[4];                                 //Array que irá conter a avaliação da jogada efectuada.
  
  Scanner s = new Scanner (System.in);
  
  public Jogada(){                                            //Construtor.
    
  }
  
  public char[] geraJogada(){                                 //Recebe os caracteres da jogada definidos pelo utilizador e gera a mesma.
    for (int i=0;i<4;i++){
    avalia[i]=' ';}
    
    char [] cores = new char[4];                              //Temporário.
    
    String escolha;                                           //String que conterá uma peça dada pelo utilizador.
    
    char dif;                                                 //Temporário que converterá a String introduzida num caractere.
  
   for(int i=0; i<4; i++){
      
       do{ 
        
        System.out.println("Por favor introduza o caractere correspondente à "+(i+1)+"ª cor da sua jogada:");
      
        escolha = s.next();                                  //"Recebe" a String introduzida pelo utilizador.
        dif = escolha.charAt(0);                             //Converte a String introduzida pelo utilizador num char.
        cores[i] = dif;                                      //Coloca o char convertido no array que conterá as peças dadas pelo jogador.
     
       }while (dif!='B' && dif!='G'&&dif!='L'&&dif!='R'&&dif!='W'&&dif!='Y'); //Restrição:  as peças dadas não podem ser diferentes das cores disponíveis.
   }
    
    for (int i=0; i<4; i++){                                 //Copia o array com a jogada para um segundo array.
      jogada[i] = cores[i];
    }
    
    return jogada;                                           //Retorna a jogada gerada.
  
  }
   
  
 public void avaliaJogada(char [] sequencia){                //Avalia a jogada dada pelo utilizador.
   
   String a = String.valueOf(jogada[0]);                     //Contém a primeira peça da jogada efectuada pelo jogador.
   String b = String.valueOf(jogada[1]);                     //Contém a segunda peça da jogada efectuada pelo jogador.
   String c = String.valueOf(jogada[2]);                     //Contém a terceira peça da jogada efectuada pelo jogador.
   String d = String.valueOf(jogada[3]);                     //Contém a quarta peça da jogada efectuada pelo jogador.
   
   String e = String.valueOf(sequencia[0]);                  //Contém a primeira peça da sequência aleatória.
   String f = String.valueOf(sequencia[1]);                  //Contém a segunda peça da sequência aleatória.
   String g = String.valueOf(sequencia[2]);                  //Contém a terceira peça da sequência aleatória.
   String h = String.valueOf(sequencia[3]);                  //Contém a quarta peça da sequência aleatória.
   
   if(a.equals(e)==true&&antirepete[0]==0){                                    //Compara a primeira peça da jogada com a primeira peça da sequência a adivinhar.
     avalia[0] ='b';                                         //Caso se verifique a igualdade, o programa retorna a peça pequena b na posição correspondente do array avalia.
     antirepete[0]=1;                                        //É colocado o valor 1 no array antirepete, o que evita que se repitam avaliações e que permite ao programa ignorar esta posição do array antirepete quando se realiza uma nova avaliação.
   }
   if(b.equals(f)==true&&antirepete[1]==0){                  //Compara a segunda peça da jogada com a segunda peça da sequência a adivinhar.
     avalia[1]='b';                                          //Caso se verifique a igualdade, o programa retorna a peça pequena b na posição correspondente do array avalia.
     antirepete[1]=1;                                        //É colocado o valor 1 no array antirepete, o que evita que se repitam avaliações e que permite ao programa ignorar esta posição do array antirepete quando se realiza uma nova avaliação.
   }
   if(c.equals(g)==true&&antirepete[2]==0){                  //Compara a terceira peça da jogada com a terceira peça da sequência a adivinhar.
     avalia[2]='b';                                          //Caso se verifique a igualdade, o programa retorna a peça pequena b na posição correspondente do array avalia.
     antirepete[2]=1;                                        //É colocado o valor 1 no array antirepete, o que evita que se repitam avaliações e que permite ao programa ignorar esta posição do array antirepete quando se realiza uma nova avaliação.
   }
   if(d.equals(h)==true&&antirepete[3]==0){                  //Compara a quarta peça da jogada com a quarta peça da sequência a adivinhar.
     avalia[3]='b';                                          //Caso se verifique a igualdade, o programa retorna a peça pequena b na posição correspondente do array avalia.
     antirepete[3]=1;                                        //É colocado o valor 1 no array antirepete, o que evita que se repitam avaliações e que permite ao programa ignorar esta posição do array antirepete quando se realiza uma nova avaliação.
   }
   if(a.equals(f)==true&&avalia[0]==' '&&antirepete[1]==0){                  //Compara a primeira peça da jogada com a segunda peça da sequência a adivinhar.
     avalia[0]='w';                                        //Caso se verifique a igualdade, o programa retorna a peça pequena w na posição correspondente do array avalia.
     antirepete[1]=1;                                      //É colocado o valor 1 no array antirepete, o que evita que se repitam avaliações e que permite ao programa ignorar esta posição do array antirepete quando se realiza uma nova avaliação.
   }
   if(a.equals(g)==true&&avalia[0]==' '&&antirepete[2]==0){                  //Compara a primeira peça da jogada com a terceira peça da sequência a adivinhar.
     avalia[0]='w';                                        //Caso se verifique a igualdade, o programa retorna a peça pequena w na posição correspondente do array avalia.
     antirepete[2]=1;                                      //É colocado o valor 1 no array antirepete, o que evita que se repitam avaliações e que permite ao programa ignorar esta posição do array antirepete quando se realiza uma nova avaliação.
   }
   if(a.equals(h)==true&&avalia[0]==' '&&antirepete[3]==0){                  //Compara a primeira peça da jogada com a quarta peça da sequência a adivinhar.
     avalia[0]='w';                                        //Caso se verifique a igualdade, o programa retorna a peça pequena w na posição correspondente do array avalia.
     antirepete[3]=1;                                      //É colocado o valor 1 no array antirepete, o que evita que se repitam avaliações e que permite ao programa ignorar esta posição do array antirepete quando se realiza uma nova avaliação.
   }
   if(b.equals(e)==true&&antirepete[0]==0&&avalia[1]==' '){//Compara a segunda peça da jogada com a primeira peça da sequência a adivinhar.
     avalia[1]='w';                                        //Caso se verifique a igualdade, o programa retorna a peça pequena w na posição correspondente do array avalia.
     antirepete[0]=1;                                      //É colocado o valor 1 no array antirepete, o que evita que se repitam avaliações e que permite ao programa ignorar esta posição do array antirepete quando se realiza uma nova avaliação.
   }
   if(b.equals(g)==true&&antirepete[2]==0&&avalia[1]==' '){//Compara a segunda peça da jogada com a terceira peça da sequência a adivinhar.
     avalia[1]='w';                                        //Caso se verifique a igualdade, o programa retorna a peça pequena w na posição correspondente do array avalia.
     antirepete[2]=1;                                      //É colocado o valor 1 no array antirepete, o que evita que se repitam avaliações e que permite ao programa ignorar esta posição do array antirepete quando se realiza uma nova avaliação.
   }
   if(b.equals(h)==true&&antirepete[3]==0&&avalia[1]==' '){//Compara a segunda peça da jogada com a quarta peça da sequência a adivinhar.
     avalia[1]='w';                                        //Caso se verifique a igualdade, o programa retorna a peça pequena w na posição correspondente do array avalia.
     antirepete[3]=1;                                      //É colocado o valor 1 no array antirepete, o que evita que se repitam avaliações e que permite ao programa ignorar esta posição do array antirepete quando se realiza uma nova avaliação.
   }
   if(c.equals(e)==true&&antirepete[0]==0&&avalia[2]==' '){//Compara a terceira peça da jogada com a primeira peça da sequência a adivinhar.
     avalia[2]='w';                                        //Caso se verifique a igualdade, o programa retorna a peça pequena w na posição correspondente do array avalia.
     antirepete[0]=1;                                      //É colocado o valor 1 no array antirepete, o que evita que se repitam avaliações e que permite ao programa ignorar esta posição do array antirepete quando se realiza uma nova avaliação. 
   }
   if(c.equals(f)==true&&antirepete[1]==0&&avalia[2]==' '){//Compara a terceira peça da jogada com a segunda peça da sequência a adivinhar.
     avalia[2]='w';                                        //Caso se verifique a igualdade, o programa retorna a peça pequena w na posição correspondente do array avalia.
     antirepete[1]=1;                                      //É colocado o valor 1 no array antirepete, o que evita que se repitam avaliações e que permite ao programa ignorar esta posição do array antirepete quando se realiza uma nova avaliação.
   }
   if(c.equals(h)==true&&antirepete[3]==0&&avalia[2]==' '){//Compara a terceira peça da jogada com a quarta peça da sequência a adivinhar.
     avalia[2]='w';                                        //Caso se verifique a igualdade, o programa retorna a peça pequena w na posição correspondente do array avalia.
     antirepete[3]=1;                                      //É colocado o valor 1 no array antirepete, o que evita que se repitam avaliações e que permite ao programa ignorar esta posição do array antirepete quando se realiza uma nova avaliação.
   }
   if(d.equals(e)==true&&antirepete[0]==0&&avalia[3]==' '){//Compara a quarta peça da jogada com a primeira peça da sequência a adivinhar.
     avalia[3]='w';                                        //Caso se verifique a igualdade, o programa retorna a peça pequena w na posição correspondente do array avalia.
     antirepete[0]=1;                                      //É colocado o valor 1 no array antirepete, o que evita que se repitam avaliações e que permite ao programa ignorar esta posição do array antirepete quando se realiza uma nova avaliação.
   }
   if(d.equals(f)==true&&antirepete[1]==0&&avalia[3]==' '){//Compara a quarta peça da jogada com a segunda peça da sequência a adivinhar.
     avalia[3]='w';                                        //Caso se verifique a igualdade, o programa retorna a peça pequena w na posição correspondente do array avalia.
     antirepete[1]=1;                                      //É colocado o valor 1 no array antirepete, o que evita que se repitam avaliações e que permite ao programa ignorar esta posição do array antirepete quando se realiza uma nova avaliação.
   }
   if(d.equals(g)==true&&antirepete[2]==0&&avalia[3]==' '){//Compara a quarta peça da jogada com a terceira peça da sequência a adivinhar.
     avalia[3]='w';                                        //Caso se verifique a igualdade, o programa retorna a peça pequena w na posição correspondente do array avalia.
     antirepete[2]=1;                                      //É colocado o valor 1 no array antirepete, o que evita que se repitam avaliações e que permite ao programa ignorar esta posição do array antirepete quando se realiza uma nova avaliação.
   }
 }
 
 
 public void resetAvalia(){                                 // Efectua o reset aos arrays avalia e jogada.
   char [] b = new char [4];
   int [] c = {0,0,0,0};
   avalia=b;
   antirepete=c;
 }
 public String restantes(int a){                             //Retorna o número de jogadas restantes sob a forma de uma String.
    return "Jogadas Restantes: "+a;
  }
 
 
 public String toString(){                                   //Retorna a Jogada efectuada e a respectiva avaliação sob a forma de uma String.
   String av = Arrays.toString(avalia);
   String jog = Arrays.toString(jogada);                     //String temporária que conterá o array k sob a forma de uma String.
   
    return " Jogada Efectuada: "+jog+" ------> "+av;        //Retorna a jogada efectuada e a respectiva avaliação sob a forma de uma String.
  }
 
}