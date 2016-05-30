import java.util.ListIterator;
import java.util.Random;
import java.util.ArrayList;
public class RandomArray {
	
	private final int [] r;
	private final int min;
	private final int max;
	private final int size;
	
	public RandomArray(int n, int k1, int k2){
		this.size=n;
		this.r = new int[this.size];//inicializa-se o array com o tamanho dado
		this.min = k1;
		this.max = k2;
		generateArray();//gera o array com os numero aleatorios consoante os valores dados
	}
	
	private void generateArray(){
		if(this.r.length>(max-min+1)){
		System.out.println("Tamanho de array maior que o numero de inteiros possiveis a gerar.");
			return;
		}
		ArrayList <Integer> a=new ArrayList<Integer>();
//cria uma arraylist para introduzir os numero aleatorios
		Random gen=new Random();//inicializa-se o objecto que vai gerar os numeros aleatorios
		int rand;
		for(int c=0;c<this.size;){
			rand=min+gen.nextInt(max-min+1);//o numero aleatorio vai ser gerado entre o intervalo [max,min]
			if(a.contains(rand)==false){//se o numero gerado nao tiver contido na arraylist adiciona-o e incrementa o c
				a.add(rand);
				c++;
			}
			//caso contrario repete o ciclo com o mesmo valor de c
		}
		ListIterator<Integer> i=a.listIterator();//inicializa-se o iterador para percorrer a arraylist
		int b=0;
//retira os valores do arraylist para o array ate chegar ao fim do arraylist
		do{
			
			this.r[b]=i.next();
			b++;
		}while(i.hasNext()==true);
		
}
		
	
	public int[] get(){ // retorna o array de valores aleatorios criado
		return r;
	}
	
	public void printArray() { // escreve o array criado no output
		for(int i = 0; i<this.get().length;i++){
			System.out.print(this.get()[i]+";");
		}
		System.out.println();
		
	}
	
}
