
public class StackArray<E> implements Stack<E>{
	
	private E [] s;
	private int top = -1;
	private int maxsize = 100; // tamanho maximo predefinido.

	@SuppressWarnings("unchecked")
	public StackArray(int n){ // construtor em que e fornecido um inteiro correspondente ao tamanho maximo da stack
		s = (E[]) new Object[n];
		this.maxsize = n;
	}
	
	@SuppressWarnings("unchecked")
	public StackArray(){ // construtor vazio
		s = (E[]) new Object[maxsize];
	}
	
	
	public void push(E o) throws OverflowException {
		
		// coloca um elemento na stack
	
		if(top == maxsize){ // se a stack esta cheia, lanca uma excepcao
			throw new OverflowException("Stack cheia.");
			
		}
		
		s[top+1] = o; // insere o elemento no topo da stack
		top++; //incrementa o inteiro que indica o topo da stack
	
	}

	
	public E top() throws EmptyException {
		// retorna o elemento que se encontra no topo da stack
		
		if(top == -1){ // se a stack se encontra vazia, lanca uma excepcao
			throw new EmptyException("Stack vazia.");
		}
		
		return s[top];
	}


	public E pop() throws EmptyException {
		// remove o elemento no topo da stack
		E temp; // temporario
		
		if(empty()){ // se a stack esta vazia, lanca uma excepcao
			throw new EmptyException("Stack vazia.");
		}
		
		temp = top(); // coloca o elemento a ser removido num temporario
		top--; // decrementa o size
		
		return temp; // retorna o elemento removido
	}

	
	public int size() { // retorna o tamanho da stack
		return top+1;
	}

	
	public boolean empty() { // retorna true se a stack estiver vazia
		return (top == -1);
	}
	
	
	public String toString(){ // uma representacao da pilha
		String s1 = "[";
		for(int i = 0;i<s.length;i++){
			s1 += s[i] + " ";
		}
		s1 +="]";
		
		return s1;
	}
	
}
