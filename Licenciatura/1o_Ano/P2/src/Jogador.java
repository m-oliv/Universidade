/*A classe Jogador.java permite ao utilizador criar um
 * perfil que inclui um nome. Este perfil será
 * a sua identificação durante o jogo.*/

public class Jogador{
 String nome;
 int restantes;
 
 public Jogador(String nome){                        //Construtor que recebe argumentos.
  this.nome=nome;
  
 }
 
 public Jogador(){                                   //Construtor.
   nome="";
 }
 
  public String getNome(){                           //Retorna o nome do jogador
      return nome;
    }
    
  public void setNome(String a){                     //Altera o nome do jogador
      nome=a;
    }
 
  public String toString(){                          //Retorna uma String semelhante à seguinte: "Nome do Jogador: Pedro".
    return "Nome do Jogador: "+nome+";";
  }
  
  public Object clone(){                             //Permite a clonagem de jogadores.
    return new Jogador(nome);
  }
 
}
