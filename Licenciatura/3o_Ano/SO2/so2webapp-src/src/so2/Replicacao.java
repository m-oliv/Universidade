package so2;

import java.io.*;
import java.util.*;

public class Replicacao{

	private Vector<String> addr = new Vector<String>(); // vector que armazena os enderecos dos servidores do backend
	private Vector<String> port = new Vector<String>(); // vector que armazena os portos dos servidores do backend
	private int numRM = 0; // int que indica o numero de RM existentes
	private Vector<ViagensClient> operacionais = new Vector<ViagensClient>(); // vector que armazena os RM disponiveis
	
	public Replicacao(){
		
	}
	
	public void checkOperacionais(){ // metodo que obtem a informacao sobre os RMs disponiveis
		
		// comeca por ler a informacao do ficheiro de propriedades.
		try{
			byte b[] = new byte[2048];
			
			try{
				FileInputStream fi = new FileInputStream("rmiprop");
				int lidos = fi.read(b);
			}catch(Exception e){
				e.printStackTrace();
			}
			
			String info = new String(b);
			
			String details [] = info.split(" ");
			
			numRM = Integer.parseInt(details[0]); // primeiro elemento da informacao lida e sempre o numero de RMs

					for(int i = 1;i<details.length;i++){
							if(i%2 == 0){ // guardar portos dos RMs
								port.add(details[i]);
							}
							else{// guardar os enderecos dos RMs
								addr.add(details[i]);
							}
					}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		for(int i = 0; i<numRM; i++){ // armazenar os RMs disponiveis no vector criado para o efeito
			ViagensClient ped = new ViagensClient(addr.get(i),port.get(i));
			operacionais.add(ped);
		}
		
	}
	
	public Vector<String> fazPedidoPesquisa(String p1){ // metodo que faz um pedido de pesquisa e retorna a resposta mais comum
		
		Vector<String> resultados = new Vector<String>();
		Vector<String> respostas = new Vector<String>();
		
		int [] num_resp = new int[numRM]; // armazena o numero de respostas iguais 
		int max = 0; // numero de respostas certas (maximo do array)
		int index = 0; // indice do array que tem a resposta mais comum
		
		for(int x = 0; x<num_resp.length;x++){ // inicializar o array que armazena o numero de respostas
			num_resp[x] = 0;
		}
		

			for(int k = 0;k<numRM;k++){ //recebe as respostas e armazena-as num vector
				operacionais.get(k).assocObjRem(); 
				respostas.add(operacionais.get(k).pesquisar(p1).toString()); // obtem a resposta
			}
			
			for(int l = 0;l<respostas.size();l++){
				for(int m = 0; m<respostas.size();m++){
					if(respostas.get(l).equals(respostas.get(m))){ // verifica quantas respostas iguais existem
						num_resp[l]++;
						
					}
				}
			}
			
			 for (int n = 0; n < num_resp.length; n++) { // verifica qual e a resposta mais comum
	                if(max < num_resp[n]){
	                   max = num_resp[n];
	                }
	            }
			
			for(int o = 0; o<num_resp.length; o++){ // iguala o vector que contem a resposta final ao correspondente a resposta mais comum
				if( max == num_resp[o]){
					index = o;	
					resultados = operacionais.get(index).pesquisar(p1);
				}
		}
		return resultados; // retorna a resposta
	}

	public Vector<String> fazPedidoConsultaLP(int p1){ // metodo que faz um pedido de consulta e retorna a resposta mais comum
		
		
		Vector<String> resultados = new Vector<String>();
		Vector<String> respostas = new Vector<String>();
		
		int [] num_resp = new int[numRM]; // armazena o numero de respostas iguais 
		int max = 0; // numero de respostas certas (maximo do array)
		int index = 0;// indice do array que tem a resposta mais comum
		
		for(int x = 0; x<num_resp.length;x++){ // inicializar o array do num de respostas
			num_resp[x] = 0;
		}
		

			for(int k = 0;k<numRM;k++){ //recebe as respostas e armazena-as num vector
				operacionais.get(k).assocObjRem();
				respostas.add(operacionais.get(k).consultaListaP(p1).toString()); // obtem a resposta
			}
			
			for(int l = 0;l<respostas.size();l++){
				for(int m = 0; m<respostas.size();m++){ // verifica quantas respostar iguais existem
					if(respostas.get(l).equals(respostas.get(m))){
						num_resp[l]++;
						
					}
				}
			}
			
			 for (int n = 0; n < num_resp.length; n++) { // verifica qual e a resposta mais comum
	                if(max < num_resp[n]){
	                   max = num_resp[n];
	                }
	            }
			
			 
			for(int o = 0; o<num_resp.length; o++){ // selecciona a resposta mais comum
				if( max == num_resp[o]){
					index = o;
					
					resultados = operacionais.get(index).consultaListaP(p1);
				}
			
		}
		return resultados;
	}
	
	
	public static void main(String args[]){
		Replicacao r = new Replicacao();
		r.checkOperacionais();
		Vector<String> res = r.fazPedidoPesquisa("Nova Iorque");
		System.out.println("Tesde da Pesquisa:");
		for(int i = 0;i<res.size();i++){
			System.out.println(res.get(i));
		}
		
		Vector<String> res2 = r.fazPedidoConsultaLP(2);
		System.out.println("Teste da Consulta da Lista de Passageiros:");
		for(int j = 0;j<res2.size();j++){
			System.out.println(res2.get(j));
		}
	}
	
}