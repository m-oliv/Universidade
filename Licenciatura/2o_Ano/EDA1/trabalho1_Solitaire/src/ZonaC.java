public class ZonaC{
  // as variaveis de instancia que caracterizam as zona C,
  // correspodente as 7 pihas de cartas, com cartas virdas para baixo e cartas viradas para cima
  
	
	private StackArray<Carta> [][] p; 
  
 
@SuppressWarnings("unchecked")
public ZonaC(List<Carta> l) throws OverflowException, NoInvalidoException{
    // O construtor , que recebe a lista de l(c/  28 cartas) e distribui as cartas da lista, 
    //convenientemente  pelas 7 pilhas.
	  
	  p = new StackArray[2][7];
	  
	  for(int i = 0;i<7;i++){ // inicializar as pilhas de cartas viradas para baixo e para cima.
		  p[0][i] = new StackArray<Carta>(i);
		  p[1][i] = new StackArray<Carta>(13);
	  }
	  
	  for(int o = 0; o<7;o++){ // colocar cartas na pilha das cartas viradas para baixo
		  for(int x = 0;x<o;x++){
			  p[0][o].push(l.remove(l.get(x)));
		  }
	  }
	  
	 for(int j = 0;j<7;j++){ // inserir cartas na pilha de cartas viradas para cima.
		  p[1][j].push(l.remove(l.get(0))); // colocar uma carta virada para cima em cada pilha
		 
	  }
  }
  
  
  public void add(int i, Carta x) throws InvalidPlayException, EmptyException, OverflowException{
	  
	  if(i<1 || i>7){ // se o valor de i corresponder a uma pilha que nao exista.
			throw new InvalidPlayException("Jogada Invalida. A pilha indicada nao existe. i tem de estar no intervalo [1,7].");
		}
	  
	  if(p[1][i-1].empty() && !x.valor().equals(Carta.Valor.REI)){// se a pilha esta vazia e a carta a ser removida da lista nao e um rei
		  throw new InvalidPlayException("Jogada Invalida. Pilha vazia e carta a ser la colocada nao e um rei.");
	  }
	  
	  if(!p[1][i-1].empty() && x.valor().ordinal()>p[1][i-1].top().valor().ordinal()){ // se a carta a colocar na pilha for superior a que esta no topo.
		  throw new InvalidPlayException("Jogada Invalida. Carta a colocar na pilha com valor superior a do topo da pilha.");
	  }
	  
	  if(!p[1][i-1].empty() && (x.valor().ordinal() != (p[1][i-1].top().valor().ordinal()-1))){ // se a carta a colocar na pilha nao for a que tem valor anterior a que esta no topo.
		  throw new InvalidPlayException("Jogada Invalida. Carta a colocar na pilha nao tem o valor anterior a do topo da pilha.");
	  }
	  
	  
	  if(!p[1][i-1].empty() && p[1][i-1].top().naipe().equals(x.naipe())){ // se as cartas sao do mesmo naipe
		  throw new InvalidPlayException("Jogada Invalida. Cartas sao do mesmo naipe.");
	  }
	  
	  // se as cartas sao de naipes diferentes, mas os naipes sao da mesma cor
	  if(!p[1][i-1].empty() && ((p[1][i-1].top().naipe().equals(Carta.Naipe.COPAS) && x.naipe().equals(Carta.Naipe.OUROS)) || (p[1][i-1].top().naipe().equals(Carta.Naipe.OUROS) && x.naipe().equals(Carta.Naipe.COPAS)))){
		  throw new InvalidPlayException("Jogada Invalida. Cartas sao de naipes da mesma cor.");
	  }
	  
	  if(!p[1][i-1].empty() && ((p[1][i-1].top().naipe().equals(Carta.Naipe.ESPADAS) && x.naipe().equals(Carta.Naipe.PAUS)) || (p[1][i-1].top().naipe().equals(Carta.Naipe.PAUS) && x.naipe().equals(Carta.Naipe.ESPADAS)))){
		  throw new InvalidPlayException("Jogada Invalida. Cartas sao de naipes da mesma cor.");
	  }
	  
	  p[1][i-1].push(x); // colocar cartas na pilha
	  
  }
  
  public void add(List<Carta> s,int i) throws NoInvalidoException, InvalidPlayException, EmptyException, OverflowException{
    //adiciona as cartas de s a pilha i.
    //Caso nao seja possivel adicionar todas as cartas de s a pilha i deve ser lancada uma excepcao
	  
	  if(i<1 || i>7){// se o valor de i corresponder a uma pilha que nao exista.
			throw new InvalidPlayException("Jogada Invalida. A pilha indicada nao existe. i tem de estar no intervalo [1,7].");
		} 
	 
	 while(!s.empty()){
		 if(p[1][i-1].empty() && !s.get(0).valor().equals(Carta.Valor.REI)){// se a pilha esta vazia e a carta a ser removida da lista nao e um rei
			  throw new InvalidPlayException("Jogada Invalida. Pilha vazia e carta a ser la colocada nao e um rei.");
		  }
		  
		  if(!p[1][i-1].empty() && s.get(0).valor().ordinal()>p[1][i-1].top().valor().ordinal()){ // se a carta a colocar na pilha for superior a que esta no topo.
			  throw new InvalidPlayException("Jogada Invalida. Carta a colocar na pilha com valor superior a do topo da pilha.");
		  }
		  
		  if(!p[1][i-1].empty() && (s.get(0).valor().ordinal() != (p[1][i-1].top().valor().ordinal()-1))){ // se a carta a colocar na pilha nao for a que tem valor anterior a que esta no topo.
			  throw new InvalidPlayException("Jogada Invalida. Carta a colocar na pilha nao tem o valor anterior a do topo da pilha.");
		  }
		  
		  
		  if(!p[1][i-1].empty() && p[1][i-1].top().naipe().equals(s.get(0).naipe())){ // se as cartas sao do mesmo naipe
			  throw new InvalidPlayException("Jogada Invalida. Cartas sao do mesmo naipe.");
		  }
		  
		  
		  // se as cartas sao de naipes diferentes, mas os naipes sao da mesma cor
		  if(!p[1][i-1].empty() && ((p[1][i-1].top().naipe().equals(Carta.Naipe.COPAS) && s.get(0).naipe().equals(Carta.Naipe.OUROS)) || (p[1][i-1].top().naipe().equals(Carta.Naipe.OUROS) && s.get(0).naipe().equals(Carta.Naipe.COPAS)))){
			  throw new InvalidPlayException("Jogada Invalida. Cartas sao de naipes da mesma cor.");
		  }
		  
		  if(!p[1][i-1].empty() && ((p[1][i-1].top().naipe().equals(Carta.Naipe.ESPADAS) && s.get(0).naipe().equals(Carta.Naipe.PAUS)) || (p[1][i-1].top().naipe().equals(Carta.Naipe.PAUS) && s.get(0).naipe().equals(Carta.Naipe.ESPADAS)))){
			  throw new InvalidPlayException("Jogada Invalida. Cartas sao de naipes da mesma cor.");
		  }
		  
		  p[1][i-1].push(s.remove(s.get(0))); // colocar cartas na pilha  
	 } 
	 
	 	
	 }
  
  public List<Carta> retira (int n, int i) throws InvalidPlayException, EmptyException, OverflowException{
    //retira n cartas da pilha  i, e devolve-as numa lista. As n cartas retiradas devem ser 
    // um subconjunto das cartas viradas para cima da pilha i. Caso sejam retiradas todas as n cartas
    //viradas para cima deve ser virada uma nova carta.
	  
	  DoubleLinkedList<Carta> retiradas = new DoubleLinkedList<Carta>(); // lista temporaria que armazena as cartas removidas das pilhas.
	  
	  if(i<1 || i>7){ // se o valor de i corresponder a uma pilha que nao exista.
			throw new InvalidPlayException("Jogada Invalida. A pilha indicada nao existe. i tem de estar no intervalo [1,7].");
		}
	  
	  if(n > (p[0][i-1].size() + p[1][i-1].size())){ // se o numero de cartas a remover ultrapassar o numero de cartas nas pilhas de cartas viradas para cima e para baixo
		  throw new InvalidPlayException("Jogada Invalida. Nao existem cartas suficientes para executar a remocao.");
	  }
	  
	  for(int j = 0;j<n;j++){ 
		  
		  retiradas.add(p[1][i-1].pop()); // vao-se removendo cartas
		  
		  if(p[1][i-1].empty() && !p[0][i-1].empty()){ // se a pilha de onde estamos a remover ficar vazia, vira-se uma carta da pilha das cartas viradas para baixo
			  p[1][i-1].push(p[0][i-1].pop());
		  }
		  
	  }
	  
    return retiradas; // retorna-se a lista que contem as cartas removidas.
  }
  
  public Carta retira(int i) throws EmptyException, OverflowException,InvalidPlayException{
    //retira uma carta da pilha i. Igual ao metodo anterior mas para uma unica carta
    
	if(i<1 || i>7){// se o valor de i corresponder a uma pilha que nao exista.
		throw new InvalidPlayException("Jogada Invalida. A pilha indicada nao existe. i tem de estar no intervalo [1,7].");
	}
    
    if(p[1][i-1].empty()){ //se a pilha ficar vazia, vira-se uma carta da pilha das cartas viradas para baixo
    	p[1][i-1].push(p[0][i-1].pop());
    }
    
    return p[1][i-1].pop();
  } 
 
}
  
  