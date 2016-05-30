/*Esta classe tem como única função comparar as pontuações
 * dos jogadores e indicar um vencedor ou, a ocorrer, um
 * possivel empate.*/

public class Vitoria{
  
  public Vitoria(){
  }
  
  public void vitoria(String c, String d, int a, int b, boolean e, boolean f){           //Compara as pontuações dos jogadores e indica um vencedor.
    
    if((e==true&&f==false)||a<b==true)                                                   //Verifica se o jogador 1 é o vencedor.
      System.out.println("O jogador "+c+" é o vencedor!");
    
    if((f==true&&e==false)||b<a==true)                                                   //Verifica se o jogador 2 é o vencedor.
      System.out.println("O jogador "+d+" é o vencedor!");
    
    if((e==true&&f==true&&(a==b))||(e==false&&f==false&&(a==b)))                         //Verifica se ocorre um empate.
      System.out.println("É um empate!");
  }
  public boolean checkVitoria(char [] a){                                                //Verifica se o array que o método recebe tem todas as posições preenchidas com o carácter 'b'.
    boolean check=true;
    for(int w=0;w<4;w++){
      if(a[w]!='b'){
        return check=false;
      }
    }
    return check;
  }
}
  
  