import java.io.*;
import java.text.NumberFormat;
import java.util.Locale;
public class Testes {
	
//funcao que retorna o valor maximo do array dado
	public static long maxArray(long[]a){
		long max=a[0];
		for(int i=1;i<a.length;i++){
			if(a[i]>max)
				max=a[i];
		}
		return max;
	}
//funcao que retorna o valor minimo do array dado	
	public static long minArray(long[]a){
		long min=a[0];
		for(int i=1;i<a.length;i++){
			if(a[i]<min)
				min=a[i];
		}
		return min;
	}
//funcao que retorna o valor medio do array dado, tirando os valores minimo e maximo da media
	public static long mediaArray(long[]a){
		long sum=0;
		for(int i=0;i<a.length;i++)
			sum+=a[i];
		sum=sum-maxArray(a)-minArray(a);
		return sum/(a.length-2);
	}
	
	public static void main(String[]args) throws IOException{
		//args[0]- nome do ficheiro; args[1]-tamanho do array; args[2]-numero de testes
		
		

		int nTeste = Integer.parseInt(args[2]);
		int tamanho = Integer.parseInt(args[1]);
//converte os numeros  em String para inteiro
		String filename = args[0];

//inicializa o objecto FileWriter para escrever um novo ficheiro com o nome dado
		FileWriter output= new FileWriter(filename);

//inicializa o objecto PrintWriter para escrever no novo ficheiro 
		PrintWriter file= new PrintWriter(output);

//inicializa o objecto NumberFormat para formatar os valores dos tempos num formato mais facil de ler e comparar
		NumberFormat formatter = NumberFormat.getInstance(new Locale("pt_PT"));

//e feito a primeira escrita no ficheiro criado
		file.println("Numero de testes efectuados -"+nTeste+" Numero de elementos a ordenar-"+tamanho+'\n');
		
		System.out.println("Numero de testes a efectuar- "+nTeste+"  Tamanho dos arrays a gerar- "+tamanho);
		
		System.out.println("A testar o algoritmo Bubblesort:");
		
		long[] bubble = new long[nTeste];
		System.out.println("Progresso:0%");
		
		for(int k = 0;k<nTeste;k++){
			RandomArray r1=new RandomArray(tamanho,0,tamanho*10);
			int[]a=r1.get();
			bubble[k] = Sort.bubblesort(a);
			if((k*100)%(nTeste*10)==0&&k!=0)
//mensagem de progresso dos testes
				System.out.println("Progresso:"+((k*100)/(nTeste*10))*10+"%");
		}
		System.out.println("Ordenacao concluida.");
		
		file.println("-------------------- Bubblesort --------------------------");

//em cada escrita dos valores calculados dos testes e formatado os numeros para que fique com separados dos milhares, para facilitar a leitura dos numeros
		file.println("Tempo Maximo: "+formatter.format(Testes.maxArray(bubble))+"ns");
		file.println("Tempo Minimo: "+formatter.format(Testes.minArray(bubble))+"ns");
		file.println("Tempo Medio: "+formatter.format(Testes.mediaArray(bubble))+"ns");
		
		System.out.println("Teste Bubblesort concluido.");
		System.out.println();
		
		System.out.println("A testar o algoritmo Rank sort:");
		System.out.println("Progresso:0%");

		long[] rank = new long[nTeste];
		for(int k = 0;k<nTeste;k++){
			RandomArray r2=new RandomArray(tamanho,0,tamanho*10);
			int[]a=r2.get();
			rank[k] = Sort.ranksort(a);
			if((k*100)%(nTeste*10)==0&&k!=0)
				System.out.println("Progresso:"+((k*100)/(nTeste*10))*10+"%");
		}
		
		System.out.println("Ordenacao concluida.");
		
		file.println("-------------------- Rank sort --------------------------");
		
		file.println("Tempo Maximo: "+formatter.format(Testes.maxArray(rank))+"ns");
		file.println("Tempo Minimo: "+formatter.format(Testes.minArray(rank))+"ns");
		file.println("Tempo Medio: "+formatter.format(Testes.mediaArray(rank))+"ns");
		
		System.out.println("Teste Ranksort concluido.");
		System.out.println();
		
		System.out.println("A testar o algoritmo Selection sort:");
		System.out.println("Progresso:0%");
		
		long[] selec = new long[nTeste];
		for(int k = 0;k<nTeste;k++){
			RandomArray r3=new RandomArray(tamanho,0,tamanho*10);
			int[]a=r3.get();
			selec[k] = Sort.selectionsort(a);
			if((k*100)%(nTeste*10)==0&&k!=0)
				System.out.println("Progresso:"+((k*100)/(nTeste*10))*10+"%");
		}
		
		System.out.println("Ordenacao concluida.");
		
		file.println("-------------------- Selection sort --------------------------");
		
		file.println("Tempo Maximo: "+formatter.format(Testes.maxArray(selec))+"ns");
		file.println("Tempo Minimo: "+formatter.format(Testes.minArray(selec))+"ns");
		file.println("Tempo Medio: "+formatter.format(Testes.mediaArray(selec))+"ns");
		
		System.out.println("Teste Selectionsort concluido.");
		System.out.println();
		
		System.out.println("A testar o algoritmo Insertion sort:");
		System.out.println("Progresso:0%");
		
		long[] insert = new long[nTeste];
		for(int k = 0;k<nTeste;k++){
			RandomArray r4=new RandomArray(tamanho,0,tamanho*10);
			int[]a=r4.get();
			insert[k] = Sort.insertionsort(a);
			if((k*100)%(nTeste*10)==0&&k!=0)
				System.out.println("Progresso:"+((k*100)/(nTeste*10))*10+"%");
		}
		
		System.out.println("Ordenacao concluida.");
		
file.println("-------------------- Insertion sort --------------------------");
		
		file.println("Tempo Maximo: "+formatter.format(Testes.maxArray(insert))+"ns");
		file.println("Tempo Minimo: "+formatter.format(Testes.minArray(insert))+"ns");
		file.println("Tempo Medio: "+formatter.format(Testes.mediaArray(insert))+"ns");
		
		System.out.println("Teste Insertionsort concluido.");
		System.out.println();
		
		System.out.println("A testar o algoritmo Shellsort:");
		System.out.println("Progresso:0%");
		
		long[] shell = new long[nTeste];
		for(int k = 0;k<nTeste;k++){
			RandomArray r5=new RandomArray(tamanho,0,tamanho*10);
			int[]a=r5.get();
			shell[k] = Sort.shellsort(a);
			if((k*100)%(nTeste*10)==0&&k!=0)
				System.out.println("Progresso:"+((k*100)/(nTeste*10))*10+"%");
		}
		
		System.out.println("Ordenacao concluida.");
		
		file.println("-------------------- Shellsort --------------------------");
		
		file.println("Tempo Maximo: "+formatter.format(Testes.maxArray(shell))+"ns");
		file.println("Tempo Minimo: "+formatter.format(Testes.minArray(shell))+"ns");
		file.println("Tempo Medio: "+formatter.format(Testes.mediaArray(shell))+"ns");
		
		System.out.println("Teste Shellsort concluido.");
		System.out.println();
		
		
		
		System.out.println("A testar o algoritmo Radix sort:");
		System.out.println("Progresso:0%");
		
		long[] radix = new long[nTeste];
		for(int k = 0;k<nTeste;k++){
			RandomArray r6=new RandomArray(tamanho,0,tamanho*10);
			int[]a=r6.get();
			radix[k] = Sort.radixsort(a);
			if((k*100)%(nTeste*10)==0&&k!=0)
				System.out.println("Progresso:"+((k*100)/(nTeste*10))*10+"%");
		}
		
		System.out.println("Ordenacao concluida.");
		
		file.println("-------------------- Radix sort --------------------------");
		
		file.println("Tempo Maximo: "+formatter.format(Testes.maxArray(radix))+"ns");
		file.println("Tempo Minimo: "+formatter.format(Testes.minArray(radix))+"ns");
		file.println("Tempo Medio: "+formatter.format(Testes.mediaArray(radix))+"ns");
		
		System.out.println("Teste Radix sort concluido.");
		System.out.println();
		
		System.out.println("A testar o algoritmo Heapsort:");
		System.out.println("Progresso:0%");
		
		long[] heap = new long[nTeste];
		for(int k = 0;k<nTeste;k++){
			RandomArray r7=new RandomArray(tamanho,0,tamanho*10);
			int[]a=r7.get();
			heap[k] = Sort.heapsort(a);
			if((k*100)%(nTeste*10)==0&&k!=0)
				System.out.println("Progresso:"+((k*100)/(nTeste*10))*10+"%");
		}
		
		System.out.println("Ordenacao concluida.");
		
		file.println("-------------------- Heapsort --------------------------");
		
		file.println("Tempo Maximo: "+formatter.format(Testes.maxArray(heap))+"ns");
		file.println("Tempo Minimo: "+formatter.format(Testes.minArray(heap))+"ns");
		file.println("Tempo Medio: "+formatter.format(Testes.mediaArray(heap))+"ns");
		
		System.out.println("Teste Heapsort concluido.");
		System.out.println();
		
		System.out.println("A testar o algoritmo Mergesort:");
		System.out.println("Progresso:0%");
		
		long[] merge = new long[nTeste];
		for(int k = 0;k<nTeste;k++){
			RandomArray r8=new RandomArray(tamanho,0,tamanho*10);
			int[]a=r8.get();
			merge[k] = Sort.mergesort(a);
			if((k*100)%(nTeste*10)==0&&k!=0)
				System.out.println("Progresso:"+((k*100)/(nTeste*10))*10+"%");
		}
		
		System.out.println("Ordenacao concluida.");
		
		file.println("-------------------- Mergesort --------------------------");
		
		file.println("Tempo Maximo: "+formatter.format(Testes.maxArray(merge))+"ns");
		file.println("Tempo Minimo: "+formatter.format(Testes.minArray(merge))+"ns");
		file.println("Tempo Medio: "+formatter.format(Testes.mediaArray(merge))+"ns");
		
		System.out.println("Teste Mergesort concluido.");
		System.out.println();
		
		System.out.println("A testar o algoritmo Quicksort:");
		System.out.println("Progresso:0%");
		
		long[] quick = new long[nTeste];
		for(int k = 0;k<nTeste;k++){
			RandomArray r9=new RandomArray(tamanho,0,tamanho*10);
			int[]a=r9.get();
			quick[k] = Sort.quicksort(a);
			if((k*100)%(nTeste*10)==0&&k!=0)
				System.out.println("Progresso:"+((k*100)/(nTeste*10))*10+"%");
		}
		
		System.out.println("Ordenacao concluida.");
		
		file.println("-------------------- Quicksort --------------------------");
		
		file.println("Tempo Maximo: "+formatter.format(Testes.maxArray(quick))+"ns");
		file.println("Tempo Minimo: "+formatter.format(Testes.minArray(quick))+"ns");
		file.println("Tempo Medio: "+formatter.format(Testes.mediaArray(quick))+"ns");
		
		System.out.println("Teste Quicksort concluido.");
		
		//apos os testes terem sido feitos e os resultados escritos no ficheiro e fechado o ficheiro
		file.close();
		
		System.out.println("Testes Concluidos. Ficheiro "+filename+" disponivel para consulta.");
		
	}
}
