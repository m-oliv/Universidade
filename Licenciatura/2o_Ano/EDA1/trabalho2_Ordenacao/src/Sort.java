import java.util.ArrayList;


public class Sort {
	
	public static long insertionsort(int a[]){ // algoritmo Insertion sort 
		
		long t1 = System.nanoTime();//regista o tempo quando o algoritmo comeca a ser executado (em nano-segundos)
	
		for (int i=1; i<a.length;i++){ 
			int aux=a[i]; // coloca o elemento num temporario
			int j=i; // guarda a posicao do elemento num temporario
			
			while (j>0 && aux < a[j-1]){ 
				// se o elemento actual for menor que o que se encontra na posicao anterior, 
				// movem-se todos os elementos de modo a que o elemento actual possa ser colocado na posicao correcta
					trocaArray(a,j,j-1); // trocam-se os elementos
					j--; // decrementa a posicao
				}
				a[j]=aux; // coloca o elemento na posicao correcta
		}
		
		long t2 = System.nanoTime();//regista o tempo quando o algoritmo termina a sua execucao
		
		return (t2-t1);//retorna a diferenca entre os dois tempos
	}
	
	public static long bubblesort(int a[]){ // algoritmo Bubblesort
		
		long t1 = System.nanoTime();//regista o tempo quando o algoritmo comeca a ser executado (em nano-segundos)
		
		for (int i=1;i<a.length;i++){// percorre-se o array, fazendo comparacoes
			for (int j=0;j<a.length-i;j++){
				if (a[j]>a[j+1]){ // se o valor actual e maior que o seguinte, trocam-se estes valores
					trocaArray(a,j,j+1); // trocam-se os elementos
				}
			}
		}
		
		long t2 = System.nanoTime();//regista o tempo quando o algoritmo termina a sua execucao
		
		return (t2-t1);//retorna a diferenca entre os dois tempos
	}
	
	public static long selectionsort(int a[]){ // algoritmo Selection sort
		
		long t1 = System.nanoTime();//regista o tempo quando o algoritmo comeca a ser executado (em nano-segundos)
		
		for(int i = 0; i<a.length;i++){ // percorre-se o array em busca do elemento que tem o valor menor
			int min = i; // guarda-se a posicao desse elemento
			for(int j = i+1;j<a.length;j++){
				if(a[j] < a[min]){ // compara-se o elemento que tem o menor valor com os outros elementos
					min = j; // se houver um elemento com um valor menor, a posicao deste elemento substitui o valor na variavel min.
				}
			}
			trocaArray(a,i,min);// Troca-se o elemento actual com o que tem o valor minimo.
		}
		
		long t2 = System.nanoTime();//regista o tempo quando o algoritmo termina a sua execucao
		
		return (t2-t1);//retorna a diferenca entre os dois tempos
	}
	

	public static long shellsort(int a[]){ // algoritmo Shellsort
		
		long t1 = System.nanoTime();//regista o tempo quando o algoritmo comeca a ser executado (em nano-segundos)
	
	    int tam = a.length;
	    int dif = tam / 2; // calcula-se o tamanho das subsequencias
	    int c;
	    while (dif > 0) {
	        for (int i = dif; i < tam; i++) {
	            c = a[i]; // guarda-se o valor actual num temporario
	            for(int j = i; j>= dif && a[j-dif] > c; j-=dif){ 
	            	trocaArray(a, j, j-dif);//troca os dois valores nas posicoes j e j-dif do array a
	            }
	        }
	        dif = dif / 2; // reduz-se o tamanho das subsequencias
	    }
	    
	    long t2 = System.nanoTime();//regista o tempo quando o algoritmo termina a sua execucao
		
		return (t2-t1);//retorna a diferenca entre os dois tempos
	}
	
	public static long ranksort(int a[]){ // algoritmo Rank sort
		
		long t1 = System.nanoTime();//regista o tempo quando o algoritmo comeca a ser executado (em nano-segundos)
		
		int []fim=new int[a.length];// array que armazena os elementos ordenados
		
		for(int i=0;i<a.length;i++){
			int rank=0; // inicializa o rank
			for(int g=0;g<a.length;g++){ // percorre o array e calcula o rank de cada elemento
				if(a[g]<a[i])  // de cada vez que existe um elemento com um valor menor que o do elemento actual
					rank++; // incrementa o rank
			}
			fim[rank]=a[i]; // guarda o elemento na posicao correcta do array ordenado (segundo o rank calculado)
		}
		
		long t2 = System.nanoTime();//regista o tempo quando o algoritmo termina a sua execucao
		
		return (t2-t1);//retorna a diferenca entre os dois tempos
	
	}
	
	public static long radixsort(int a[]){ // algoritmo Radix sort
		
		long t1 = System.nanoTime();//regista o tempo quando o algoritmo comeca a ser executado (em nano-segundos)
		
		int n=maxn(a);//calcula o valor do numero com mais digitos do array
		for(int i=0;i<n;i++){
			int t=(int)Math.pow(10, i);//10^i;
			a=subradix(a,t);//repete o processo de agrupar e de retornar para o array ate que atinja o valor maior de digitos dos numeros do array
		}
		
		long t2 = System.nanoTime();//regista o tempo quando o algoritmo termina a sua execucao
		
		return (t2-t1);//retorna a diferenca entre os dois tempos
	}
	
	public static int[] subradix(int a[],int digit){
		@SuppressWarnings("unchecked")
		ArrayList <Integer>sub[]=new ArrayList[10];//cria um array com 10 arraylists para poder agrupar os numeros consoante o digito a comparar
		
		for(int j=0;j<10;j++)
			sub[j]=new ArrayList<Integer>();//inicializa-se cada arraylist do array
		
		for(int i=0;i<a.length;i++){
			int v=(a[i] % (digit*10)) / digit;//retira o digito do numero para saber em que arraylist vai introduzir o numero
			sub[v].add(a[i]);//introduz o numero no arraylist correspondente ao digito
		}
		int temp=0;
//apos ter posto todos os numeros nos 10 grupos coloca novamente no array na ordem da esquerda para a direita, do grupo 0 ate ao grupo 9
		for(int i=0;i<10;i++){
			if(sub[i].isEmpty()!=true)
			do{
				a[temp]=sub[i].remove(0);
				temp++;
			}
			while(sub[i].isEmpty()!=true);
		}
		return a;//retorna o array
	}
	
	public static int maxn(int a[]){//calcula o valor do numero com mais digitos do array
		int n=0;
		int temp = 0;
		
		for(int i=0;i<a.length;i++){
			temp=String.valueOf(a[i]).length();
			if(temp>n)
				n=temp;
		}
		return n;
	}

	public static long quicksort(int a[]){ // algoritmo Quicksort
		
		long t1 = System.nanoTime();//regista o tempo quando o algoritmo comeca a ser executado (em nano-segundos)
		
		quicksort(a,0,a.length-1); // aplica o Quicksort ao array dado
		
		long t2 = System.nanoTime();//regista o tempo quando o algoritmo termina a sua execucao
		
		return (t2-t1);//retorna a diferenca entre os dois tempos
	}
	
	private static void quicksort(int[]a,int esq,int dir){ // aplica o quicksort recursivamente, de modo a ordenar os elementos nas particoes
		if(esq<dir){
			int k=quick_part(a,esq,dir); // cria as particoes no array
			quicksort(a,esq,k); // aplica o quicksort a uma particao
			quicksort(a,k+1,dir); // aplica o quicksort a outra particao
		}
	}
	
	private static int quick_part(int []a,int esq,int dir){ // cria as particoes no array
		int i=esq; // guarda a posicao inicial da particao
		int j=dir; // guarda a posicao final da particao
		int pivot=a[(esq+dir)/2]; // obtem o pivot, que neste caso e o elemento que se encontra no centro da particao
		
		boolean achou=false;
		
		while (!achou){
			
			while (a[i]<pivot) i++; // se o elemento actual tem um valor menor que o do pivot, incrementa-se a posicao inicial
			
			while (a[j]>pivot) j--; // se o elemento actual tem um valor maior que o do pivot, decrementa-se a posicao final
			
			if (i<j){ // se a posicao inicial for menor que a final
				trocaArray(a,i,j); // trocam-se os elementos
				i++; // incrementa-se a posicao inicial
				j--; // decrementa-se a posicao final
			}
			else
				achou=true; 
		}
		return j;
	}
	
	private static void trocaArray(int []a,int pos1, int pos2){//troca os valores das posicoes pos1 e pos2 do array dado
		int temp=a[pos1];
		a[pos1]=a[pos2];
		a[pos2]=temp;
		
	}
	
	
	public static long heapsort(int [] a){ // algoritmo Heapsort
		
		long t1 = System.nanoTime();//regista o tempo quando o algoritmo comeca a ser executado (em nano-segundos)
		
		constroiHeap(a); // constroi a heap
		
		for(int i = a.length - 1; i>0; i--){ // percorre o array e aplica o algoritmo
			trocaArray(a,0,i); // troca o elemento na posicao inicial com o elemento da posicao actual
			heapify(a,0,i-1); // heapifica
		}
		
		long t2 = System.nanoTime();//regista o tempo quando o algoritmo termina a sua execucao
		
		return (t2-t1);//retorna a diferenca entre os dois tempos
	}
	
	private static void constroiHeap(int a[]){ // constroi a heap
		
		for(int i = (a.length/2); i>0;i--){ // percorre o array
			heapify(a,i,a.length-1); // heapifica de baixo para cima (partindo da raiz ate chegar as folhas)
		}
	}
	
	private static void heapify( int [] a, int i, int size){ // processo de heapificacao
		
		int j = 2*i + 1; // obtem a raiz da sub-heap
		
		if(j<size && a[j] < a[j+1]){ // se o valor do pai e superior ao do filho, avanca
			j++;
		}
		
		if(j<= size && a[i] < a[j]){ // caso contrario
			trocaArray(a,i,j); // trocam-se os elementos
			heapify(a,j,size); // heapifica recursivamente de cima para baixo (partindo das folhas ate chegar a raiz)
		}
	}
	
	public static long mergesort(int [] a){ // algoritmo Mergesort
		
		int [] b = new int[a.length];// auxiliar
		
		long t1 = System.nanoTime();//regista o tempo quando o algoritmo comeca a ser executado (em nano-segundos)
		
		ordMerge(a,b,0,a.length-1); // aplica o algoritmo
		
		long t2 = System.nanoTime();//regista o tempo quando o algoritmo termina a sua execucao
		
		return (t2-t1);//retorna a diferenca entre os dois tempos
	}
	
	private static void ordMerge(int [] a, int [] temp, int esq, int dir){
		if(esq < dir){
			int meio = (esq+dir)/2; // calcula a posicao do elemento que se encontra no centro do array
			ordMerge(a,temp,esq,meio); // ordena a primeira lista
			ordMerge(a,temp,meio+1,dir); // ordena a segunda lista
			merge(a,temp,esq,meio+1,dir); // junta as duas listas ordenadas, gerando o array ordenado
		}
	}
	
	private static void merge(int [] a, int []temp, int esq, int meio, int dir){ // funde as duas listas, mantendo os seus elementos ordenados
		
		int fim1 = meio-1; // calcula a posicao do ultimo elemento da primeira lista
		
		int p = esq; // guarda o valor da posicao inicial
		
		int n = dir - esq + 1; // calcula a posicao do primeiro elemento da segunda lista

		while(esq <= fim1 && meio <= dir){ // ordenam-se os elementos das sublistas
			
			if(a[esq] < a[meio]){ 
				temp[p++] = a[esq++];
			}
			else{
				temp[p++] = a[meio++];
			}
		}
		
		for(int i = esq; i<=fim1;i++){
			temp[p++] = a[i];
		}
		
		for(int i = meio; i<=dir;i++){
			temp[p++] = a[i];
		}
		
		for(int i = 0; i<n;i++, dir--){
			a[dir] = temp[dir];
		}
	}		
	
}
