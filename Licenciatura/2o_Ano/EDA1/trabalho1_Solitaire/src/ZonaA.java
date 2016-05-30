public class ZonaA{
	
	// variaveis de instancia da classe
  private StackArray<Carta> esc;  // stack correspondente a pilha das cartas escondidas
  private StackArray<Carta> vis; // stack correspondente a pilha das cartas visiveis (viradas para cima)
  
  public ZonaA(List<Carta> l) throws OverflowException, NoInvalidoException{
	// recebe uma lista com 24 cartas e coloca-as na estrutura correspondente as cartas escondidas
	  
	  // inicializa as pilhas de cartas viradas para cima e de cartas viradas para baixo
	  this.esc = new StackArray<Carta>(24);
	  this.vis = new StackArray<Carta>(24);

	  // coloca as 24 cartas na pilha das cartas viradas para baixo (inicialmente todas as cartas estao viradas para baixo)
	  for(int i =0;i<24;i++){
		  if(!l.empty()){
			  esc.push(l.remove(l.get(0)));
		  }
	  }  
    
  }
  
  public Carta virar() throws OverflowException, EmptyException, InvalidPlayException{
    // retira uma carta das escondidas e coloca-a nas cartas viradas para cima (no topo)
	
	  if(esc.empty() && vis.empty()){ // se ja nao existem nem cartas na pilha das escondidas nem na pilha das visiveis
		  throw new InvalidPlayException("Jogada Invalida. Nao existem mais cartas para virar.");
	  }
	  
	  if(esc.empty() && !vis.empty()){ // se ja nao ha cartas visiveis, mas ha cartas escondidas
		 while(!vis.empty()){ // voltam a esconder-se todas as cartas da pilha das visiveis.
			  esc.push(vis.pop());
		  }
	  }

	  vis.push(esc.pop()); // vira-se uma carta da pilha das escondidas.
	 
    return vis.top(); // retorna-se a carta virada.
  }
  
  public Carta retirar() throws EmptyException, OverflowException, InvalidPlayException{
    
	  if(esc.empty() && vis.empty()){ // se ja nao existem cartas.
		  throw new InvalidPlayException("Jogada Invalida. Nao existem mais cartas para virar.");
	  }
	  
	  return vis.pop(); // substituir pela carta apropriada
  }
  
}