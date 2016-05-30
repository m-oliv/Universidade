public class ZonaB{
	
	 private StackArray<Carta> p []; // array que ira armazenar as quatro stacks que constituem a zona B.
  
	@SuppressWarnings("unchecked")
	public ZonaB(){
		// inicializa-se o array e as quatro stacks.
	  this.p = new StackArray[4];
	  
	  for(int i = 0; i<p.length;i++){
		  p[i] = new StackArray<Carta>(13); // note-se que as stacks tem o tamanho maximo 13 (numero de cartas que constituem um naipe).
	  }
  	}
  
  	public void put(Carta x) throws OverflowException, EmptyException,InvalidPlayException {
	
  	//coloca a carta x numa qualquer pilha valida desta zona
  		
  		for(int i = 0;i<p.length;i++){
  		
  		if(!p[i].empty()){ // se a stack nao esta vazia.
			  if(p[i].top().naipe().equals(x.naipe())){ // se o naipe da carta a inserir e igual ao das cartas que la estao.
				  if(p[i].top().valor().ordinal() == x.valor().ordinal()-1){ // se o valor da carta no topo da pilha e o anterior ao da carta a inserir.
					  p[i].push(x); // coloca a carta na pilha.
					  break; // para o ciclo
				  }
				  else{
					  if(!(p[i].top().valor().ordinal() == x.valor().ordinal()-1)){ // caso a carta no topo da pilha nao seja a carta imediatamente anterior a que se pretende inserir.
						  throw new InvalidPlayException("Jogada Invalida. Nao e carta seguinte a do topo.");
					  }
					  
				  }
			  }
		}
  		
  		if(p[i].empty() && x.valor().ordinal() == 0){ // se a stack esta vazia e a carta e um AS
			p[i].push(x);
			break;
  		}
  		
  		if(p[i].empty() && !(x.valor().ordinal() == 0)){ // caso a pilha esteja vazia e a carta a inserir nao seja um as
  			throw new InvalidPlayException("Jogada Invalida. Carta nao e um AS.");
  		}
  			
  	}
  		
  }
  	
  	public void put(Carta x, int i) throws OverflowException, EmptyException, InvalidPlayException{
  		
  	  // coloca a carta x especificamente na i-esima pilha
  		
  		if(i<1 || i>4){ // caso o valor i inserido seja invalido
  			throw new InvalidPlayException("Jogada Invalida. A pilha indicada nao existe. i tem de estar no intervalo [1,4].");
  		}
	 
  		if(!p[i-1].empty()){ // se a stack nao esta vazia.
			  if(p[i-1].top().naipe().equals(x.naipe())){ // se o naipe da carta a inserir e igual ao das cartas que la estao.
				  if(p[i-1].top().valor().ordinal() == x.valor().ordinal()-1){ // se o valor da carta no topo da pilha e o anterior ao da carta a inserir.
					  p[i-1].push(x); // coloca a carta na pilha.
					  
				  }
				  else{
					  if(!(p[i-1].top().valor().ordinal() == x.valor().ordinal()-1)){// caso a carta no topo da pilha nao seja a carta imediatamente anterior a que se pretende inserir.
						  throw new InvalidPlayException("Jogada Invalida. Nao e carta seguinte a do topo.");
					  }
					  
				  }
			  }
			  else{
				  if(!(p[i-1].top().naipe().equals(x.naipe()))){ // caso as cartas sejam de naipes diferentes.
					  throw new InvalidPlayException("Jogada Invalida. Naipes diferentes.");
				  }
			  }
		}
		
		if(p[i-1].empty() && x.valor().ordinal() == 0){ // se a stack esta vazia e a carta e um AS
			p[i-1].push(x);
			
		}
		
		if(p[i-1].empty() && !(x.valor().ordinal() == 0)){ // caso a pilha se encontre vazia e a carta a inserir nao seja um AS
			
			throw new InvalidPlayException("Jogada Invalida. Carta nao e um AS.");
		}
			
  	}

}