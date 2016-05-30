public class Baralho{
   // defina aqui as variaveis de instancia da classe
	private DoubleLinkedList<Carta> b; // lista ligada que ira armazenar as 52 cartas que constituem o baralho
  
  public Baralho(){
    // aqui o construtor, deve providenciar as 52 cartas de jogar
	  
	  this.b = new DoubleLinkedList<Carta>();
	  
	// colocar as 52 cartas na estrutura.
	  
		for(int i = 0;i<4;i++){ 
			  for(int j = 0;j<13;j++){ // Cria as 13 cartas de cada naipe e coloca-as na lista que representa o baralho.
				  b.add(new Carta(Carta.Valor.values()[j],Carta.Naipe.values()[i]));
			  }
		}
  }
  
  public Carta get() throws NoInvalidoException{
   // retorna uma carta do baralho. A ordem e aleatoria. O baralho fica com menos uma carta.
	
	  int random = (int )(Math.random() * b.size()); // calcula o inteiro correspodente a posicao da carta no baralho.
	
    return b.remove(b.get(random)); // remove a carta removida de ordem aleatoria.
  }
  
  public String toString(){
    // uma representacao das cartas do baralho
    return b.toString();
  }
  
  public boolean verifica(Carta x){
	  // verifica se uma carta esta no baralho
    return b.contains(x);
  }
  
}
      
      