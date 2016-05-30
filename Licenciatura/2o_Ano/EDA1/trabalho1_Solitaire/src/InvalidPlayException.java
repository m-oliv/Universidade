
public class InvalidPlayException extends Exception{
	
	// Excepcao que sera lancada sempre que for efectuada uma jogada invalida.
	
	public InvalidPlayException(){
		super();
	}
	
	public InvalidPlayException(String s){
		super(s);
	}
}
