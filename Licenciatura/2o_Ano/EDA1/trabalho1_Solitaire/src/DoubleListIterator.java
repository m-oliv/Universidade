public class DoubleListIterator<T> implements java.util.Iterator<T>{
  private DoubleNode<T> current;
  
  public DoubleListIterator(DoubleNode<T> c){
    this.current=c;
  }
  
  public T next(){ // retorna o elemento seguinte
    T e=current.getElement();
    current=current.getNext();
    return e;
  }
  
  public boolean hasNext(){ // verifica se existe um elemento seguinte
    return current.getNext()!=null;
  }
  
  public void remove(){
    throw new  UnsupportedOperationException();
  }
  
}