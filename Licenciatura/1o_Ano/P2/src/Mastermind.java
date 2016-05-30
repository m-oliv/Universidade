/*Esta classe irá controlar o jogo. Aqui será possível criar
 * perfis para os jogadores, gerar jogos e gerir os referidos jogos.*/

import java.util.Scanner;
import java.util.ArrayList;

public class Mastermind{
  
  Scanner s = new Scanner(System.in);
  Menu m = new Menu();                                 //Cria um objecto Menu que permite gerar os menus do jogo.
  Vitoria v = new Vitoria();                           //Cria um objecto Vitoria que permite gerar um vencedor através da comparação das pontuações.
  Sequencia seq1 = new Sequencia();                    //Cria um objecto Sequencia que permite gerar a sequência aleatória que o jogador 1 tem de adivinhar.
  Sequencia seq2 = new Sequencia();                    //Cria um objecto Sequencia que permite gerar a sequência aleatória que o jogador 2 tem de adivinhar.
  Jogada jog1 = new Jogada();                          //Cria um objecto Jogada que permite gerar e avaliar as jogadas do jogador 1.
  Jogada jog2 = new Jogada();                          //Cria um objecto Jogada que permite gerar e avaliar as jogadas do jogador 2.
  Tabuleiro <String> t1 = new Tabuleiro <String>(m.n); //Cria um objecto Tabuleiro que permite gerar o tabuleiro do jogador 1.
  Tabuleiro <String> t2 = new Tabuleiro <String>(m.n); //Cria um objecto Tabuleiro que permite gerar o tabuleiro do jogador 2.
  String nome1 ="";                                    //Permite inicializar a String nome1 com o valor "".
  String nome2 ="";                                    //Permite inicializar a String noe2 com o valor "".
  boolean check1,check2;                               //Boolean que permite verificar se o jogador adivinhou a sequência aleatória.
  
  public Mastermind(){                                 //Construtor.
  }
  
  public void perfilJogo1(){                           //Gera um perfil de jogo para o jogador 1.
    m.menuEntrada();                                   //Mostra o menu da entrada.
    System.out.println("Por favor introduza o nome do jogador 1: ");
    nome1 = s.next();                                  //"Recebe" o input do jogador. Corresponderá ao nome com que o jogador será identificado ao longo do jogo.
    Jogador jogador1 = new Jogador(nome1);             //Cria um objecto Jogador que permite gerir os dados do jogador.
    System.out.println(jogador1.toString());           //Retorna a informação do jogador sob a forma de uma String.
    
  }
  
  public void geraJogo1(){                             //Gera um jogo para o jogador1.
    System.out.println();
    jog1.resetAvalia();                                //Efectua o reset aos arrays jogada e avalia da classe Jogada.
    check1 = false;                                    //Inicializa o boolean check1 com o valor false.
    System.out.println("As cores disponíveis são: Blue (B) , Green (G) , Lilac (L) , Red (R) , White (W) e Yellow (Y).");
    jog1.geraJogada();                                 //Gera uma jogada.
    jog1.avaliaJogada(seq1.seq);                       //Avalia a jogada efectuada.
    String jogada1 = new String(jog1.toString());      //Cria uma String que contém a jogada efectuada.
    t1.setMaxsize(m.n);                                //Redefine o tamanho máximo do tabuleiro de jogo, que corresponderá agora ao valor inserido pelo jogador no método menuSizeTabuleiro da classe Menu.
    t1.insere(jogada1);                                //Insere a jogada no tabuleiro de jogo.
    
    System.out.println(nome1+"'s Tabuleiro");
    
    for(int b=0;b<t1.getSize();b++)
      System.out.println((b+1)+"º"+t1.getPlay(b));     //Escreve a jogada que foi colocada no tabuleiro. Exemplo: 1ª Jogada: [R,G,B,Y]------->[w,b,v,b].
    System.out.println(jog1.restantes(t1.espaçoVazio()));//Retorna o número de jogadas restantes.
    check1=v.checkVitoria(jog1.avalia);                //Verifica se o jogador adivinhou na sequência aleatória gerada pelo computador.
  }
  
  public void gestorJogo1(){                            //Gere o jogo do jogador 1.
    System.out.println(nome1+ " é a sua vez.");
    Classificacao cf1 = new Classificacao(t1.getSize());//Calcula a pontuação do jogador 1.  
    t1.setMaxsize(m.n);                                 //Redefine o tamanho máximo do tabuleiro de jogo, que corresponderá agora ao valor inserido pelo jogador no método menuSizeTabuleiro da classe Menu.
    
    for(int i = 0;i<m.n;i++){
      if(check1==true||t1.isFull()==true){              //Caso o jogador tenha acertado na combinação ou o tabuleiro de jogo esteja cheio, o programa sai.
        break;                    
      }
      else{
        geraJogo1();                                    //Caso contrário, o programa gera um novo jogo para o jogador 1.
      } 
    }
    System.out.println(seq1.toString());
  }
  
  public void gestorJogo2(){                            //Gere o jogo do jogador 2.
    System.out.println(nome2+ " é a sua vez.");
    Classificacao cf2 = new Classificacao(t2.getSize());//Calcula a pontuação do jogador 2.
    t2.setMaxsize(m.n);                                 //Redefine o tamanho máximo do tabuleiro de jogo, que corresponderá agora ao valor inserido pelo jogador no método menuSizeTabuleiro da classe Menu.
    for(int i = 0;i<m.n;i++){
      if(check2==true||t2.isFull()==true){              //Caso o jogador tenha acertado na combinação ou o tabuleiro de jogo esteja cheio, o programa sai.
        v.vitoria(nome1,nome2,t1.getSize(),t2.getSize(),check1,check2);//Quando o jogador 2 termina o jogo, o programa compara a pontuação deste jogador com a obtida pelo jogador 1 e determina qual dos jogadores é o vencedor.
        break;                                          //De seguida o programa sai.
      }
      else{
        geraJogo2();                                    //Caso contrário, o programa gera um novo jogo para o jogador 2.
      }
    }
    System.out.println(seq2.toString());
    
  }
  public void perfilJogo2(){                            //Gera um perfil de jogo para o jogador 2.
    System.out.println("Por favor introduza o nome do jogador 2: ");
    nome2 = s.next();                                  //"Recebe" o input do jogador. Corresponderá ao nome com que o jogador será identificado ao longo do jogo.
    Jogador j2 = new Jogador(nome2);                   //Cria um objecto Jogador que permite gerir os dados do jogador.
    System.out.println(j2.toString());                 //Retorna a informação do jogador sob a forma de uma String.
  }
  
  public void geraJogo2(){                             //Gera um jogo para o jogador 2.
    System.out.println();
    jog2.resetAvalia();                                //Efectua o reset aos arrays jogada e avalia da classe Jogada.
    check2 = false;                                    //Inicializa o boolean check2 com o valor false.
    System.out.println("As cores disponíveis são: Blue (B) , Green (G) , Lilac (L) , Red (R) , White (W) e Yellow (Y).");
    jog2.geraJogada();                                 //Gera uma jogada.
    jog2.avaliaJogada(seq2.seq);                       //Avalia a jogada efectuada.
    String jogada2 = new String(jog2.toString());      //Cria uma String que contém a jogada efectuada.
    t2.setMaxsize(m.n);                                //Redefine o tamanho máximo do tabuleiro de jogo, que corresponderá agora ao valor inserido pelo jogador no método menuSizeTabuleiro da classe Menu.
    t2.insere(jogada2);                                //Insere a jogada no tabuleiro de jogo.
    
    
    System.out.println(nome2+"'s Tabuleiro");
    
    for(int b=0;b<t2.getSize();b++)
      System.out.println((b+1)+"º"+t2.getPlay(b));     //Escreve a jogada que foi colocada no tabuleiro. Exemplo: 1ª Jogada: [R,G,B,Y]------->[w,b,v,b].
    System.out.println(jog2.restantes(t2.espaçoVazio()));//Retorna o número de jogadas restantes.
    check2=v.checkVitoria(jog2.avalia);                //Verifica se o jogador adivinhou na sequência aleatória gerada pelo computador.
  }
  
  public static void main(String [] args){
    boolean sair=false;                                //Inicializa o boolean sair com o valor false.
    boolean sair2;
    int consulta;
    Savetab <String> salvar= new Savetab <String> ();  //Cria um objecto Savetab, onde serão guardados os tabuleiros dos jogos efectuados.
    do{
      Mastermind master = new Mastermind();            //Cria um objecto Mastermind que permite criar e gerir jogos do popular jogo Mastermind.
      master.perfilJogo1();                            //Cria o perfil do primeiro jogador.
      master.perfilJogo2();                            //Cria o perfil do segundo jogador.
      master.gestorJogo1();                            //Cria o gestor de jogo do jogador 1.
      salvar.insereTab(master.t1.toString());          //Guarda o tabuleiro do primeiro jogo no objecto Savetab.
      master.gestorJogo2();                            //Cria o gestor de jogo do jogaor 2.
      salvar.insereTab(master.t2.toString());          //Guarda o tabuleiro do segundo jogo no objecto Savetab.
      sair=master.m.menuGameover();                    //Recebe o resultado do boolean resultante do método menuGameover da classe Menu.
      if(sair==true){                                  //Caso o boolean sair possua o valor true, o programa exibe um pequeno menu.
        do{
        sair2=master.m.menuSair();                     //Recebe o resultado do boolean do método menuSair da classe Menu.
        if(sair2==false){                              //Caso o boolean sair2 possua o valor false, o programa exibe um pequeno menu.
          consulta=master.m.menuConsultar();           //Recebe o resultado do boolean do méotodo menuConsultar da classe Menu.
          System.out.println(salvar.getTab(consulta)); //Retorna o tabuleiro final do jogador 1 escolhido pelo jogador.
          System.out.println(salvar.getTab(consulta+1));//Retorna o tabuleiro final do jogador 2 escolhido pelo jogador.
        }
        }while(sair2==false);                          //Repete o ciclo enquanto o boolean sair2 não contiver o valor true.
      }
    }while(sair==false);                               //Repete o ciclo enquanto o boolean sair não contiver o valor true.
  }
}
  
