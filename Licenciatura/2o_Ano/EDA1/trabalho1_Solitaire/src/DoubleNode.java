public class DoubleNode<T>{
	// No da lista duplamente ligada.
	
  private T element; // elemento armazenado neste no
  private DoubleNode<T> next; // referencia para o seguinte
  private DoubleNode<T> prev; // referencia para o no anterior
  
  public DoubleNode(T e){ // construtor cujo parametro e um elemento
    this.element=e;
    this.next=null;
    this.prev=null;
  }
  
  public DoubleNode(){ // construtor vazio
    this(null);
  }
  
  public T getElement(){ // retorna o elemento armazenado neste no
	  return element;
  }
  
  public void setElement(T e){ // altera o elemento armazenado neste no
	  this.element = e;
  }
  
  public DoubleNode<T> getNext(){ // retorna a referencia para o no seguinte
	  return next;
  }
  
  public void setNext(DoubleNode<T> n){ // altera a referencia para o no seguinte
	  this.next = n;
  }
  
  public DoubleNode<T> getPrev(){ // retorna a referencia para o no anterior
	  return prev;
  }
  
  public void setPrev(DoubleNode<T> p){ // altera a referencia para o no anterior
	  this.prev = p;
  }
  
}
