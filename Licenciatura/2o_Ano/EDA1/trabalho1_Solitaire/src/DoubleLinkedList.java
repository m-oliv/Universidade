public class DoubleLinkedList<T> implements Iterable<T>,List<T>{
  private DoubleNode<T> header; // no header
  private DoubleNode<T> footer; // no footer
  private int size; // inteiro que armazena o tamanho da lista
  
  public DoubleLinkedList(){ // construtor
    this.header=new DoubleNode<T>();
    this.footer=new DoubleNode<T>();
    this.header.setNext(footer);
    this.footer.setPrev(header);
    this.size=0;
  }
  
  public java.util.Iterator<T> iterator(){
    return new DoubleListIterator<T>(header.getNext());
  }

	public boolean empty(){ // retorna true se a lista se encontrar vazia.
		return (header.getNext().equals(footer));
	}
	
  public void add(T x){ // adiciona um elemento a lista.
    DoubleNode<T> n=new DoubleNode<T>(x); // cria-se um novo no com o elemento que pretendemos adicionar.
    n.setNext(footer); // altera-se a referencia seguinte no novo no para que esta indique que o no seguinte ao novo e o footer. 
    n.setPrev(footer.getPrev()); // altera-se a referencia correspondente ao no anterior no novo no, de modo a que este referencie o no que era anterior ao footer. 
    footer.getPrev().setNext(n); // altera-se a referencia seguinte do no que anteriormente era anterior ao footer
    footer.setPrev(n); // altera-se o a referencia do no anterior ao footer (passa a ser o novo no)
    size++; // incrementa-se o size.
  }
  
  public T remove(T x) throws NoInvalidoException{
    // Se nao ha nada na lista:
    if(empty())
        throw new NoInvalidoException("Lista Vazia. Nao e possivel remover."); // lanca uma excepcao

	DoubleNode<T> c = header.getNext(); // copia-se o header para um no temporario
	
	while(!c.getElement().equals(x)){ // enquanto nao se encontrar o no a remover
		
		 if(c.equals(footer)){ // Se chegou ao fim da lista return null
		        return null;
		 }
		c = c.getNext(); // avanca para o no seguinte
	}
	
	// ao encontrar o elemento a ser removido, remove-o
	T temp = c.getElement(); // copia o elemento para um temporario
	// actualiza as referencias dos nos seguinte e anterior
	c.getPrev().setNext(c.getNext()); 
	c.getNext().setPrev(c.getPrev());
	
	size--; // decrementa o size
	
	return temp; // retorna-se o elemento removido.
  }
  
  public String toString(){
	  String res="[";
	  
	    DoubleNode<T> c=header.getNext(); // copia-se o header para um no temporario
	    
	    while(c!=footer){ // enquanto nao chegamos ao fim da lista
	      res+=c.getElement()+ " ; ";
	      c=c.getNext(); // avanca para o no seguinte
	    }
	    return res + "]";  // retorna a string 
  }

  public int size() { // retorna o tamanho da lista
	return size;
  }

  public T get(int i) throws NoInvalidoException{
	  if(empty()){ // se a lista esta vazia
		  throw new NoInvalidoException("Lista Vazia."); //lanca uma excepcao
	  }
	  DoubleNode<T> temp = header.getNext();// copia-se o header para um no temporario
	  
	  for(int j = 0;j<i;j++){ //avancamos na ate estarmos no no pretendido.
		  temp = temp.getNext();
	  }
	  
	return temp.getElement(); // retorna-se o elemento pretendido.
  }

  public T remove(int i) throws NoInvalidoException {
	  if(empty()){ // se a lista esta vazia. 
	        throw new NoInvalidoException("Lista Vazia.");
	    }
	  
	  if(i < 0 || i >= size()){ // se o valor de i e menor que zero ou o valor de i e superior ao numero de elementos na lista.
		  throw new NoInvalidoException("Valor de i invalido.");
	  }
	    
		DoubleNode<T> prev = header;// copia-se o header para um no temporario
		DoubleNode<T> temp = header.getNext(); // copia-se o seguinte ao header para um no temporario
		
		for(int j=0; j<size(); j++){ // avanca-se na lista ate ao elemento pretendido
		    if(i == j){
		        break;
		    }
		    
		    prev = temp;
	        temp = temp.getNext();
		}
		
		T removed = temp.getElement(); // copia-se o elemento para um temporario.

		// actualizam-se as referencias para os nos anterior e seguinte ao no removido.
		temp.getNext().setPrev(prev);
		prev.setNext(temp.getNext());
		size--; //decrementa-se o size
		
		return removed; // retorna-se o elemento removido.
  }

  public void add(T x, int i) throws NoInvalidoException{
	  
	  if(i>=size() || i<0){ // se o valor de i e menor que zero ou o valor de i e superior ao numero de elementos na lista.
			throw new NoInvalidoException("Valor de i invalido.");
		}
		
		DoubleNode<T> temp = header.getNext(); // copia-se o header para um no temporario
		
		for(int j = 0;j<i;j++){ // avanca-se ate ao no pretendido
			temp = temp.getNext();
		}
		
		DoubleNode<T> sub = temp.getNext(); // cria-se um no temporario para armazenar o no seguinte ao indicado
		// alteram-se as referencias.
		temp.setNext(new DoubleNode<T>(x));
		temp.getNext().setNext(sub);
		size++; // incrementa-se o size.

  }
  
  public boolean contains(T x){ // verifica se um elemento se encontra na lista.
	  boolean c = false; // boolean que indica se o elemento foi encontrado
	  DoubleNode<T> temp = header.getNext(); // copia-se o header para um no temporario
	  
	  if(empty()){ // se a lista esta vazia, c continua a ser igual a false
		  return c;
	  }
	  
	  while(temp != footer){ // caso contrario, percorre a lista
		  if(temp.getElement().equals(x)){  // compara o elemento de cada no com o elemento indicado.
			  c = true; // caso o elemento seja encontrado muda o valor de c para true.
		  }
		  temp = temp.getNext(); // avanca para o no seguinte.
	  }
	return c; // retorna o boolean c
  }
 
}
