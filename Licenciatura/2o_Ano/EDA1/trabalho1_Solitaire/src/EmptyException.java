
public class EmptyException extends Exception{
	
	// Excepcao que sera lancada quando uma estrutura de dados se encontrar vazia.
	
	public EmptyException(){
		super();
	}
	
	public EmptyException(String s){
		super(s);
	}
}
