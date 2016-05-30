package so2;

import java.util.*;
import java.io.*;
import java.rmi.*;
import java.sql.*;
import javax.crypto.*;
import java.security.*;

public class LojaImpl implements Loja{
	
	private java.sql.Connection c= null;
    private java.sql.Statement stat= null;
    private int cod_passageiro = 0;
    private int cod_venda = 0;
    
    public LojaImpl(){
    	
    }
    
	public String connectBD(){ // metodo que permite efectuar a ligacao a base de dados
		
		// le a informacao do ficheiro de propriedades que contem o host da BD, o nome da BD, o nome de utilizador e a password
		byte b[] = new byte[1024];
		
		try{
			FileInputStream fi = new FileInputStream("propriedades");
			int lidos = fi.read(b);
		}catch(Exception e){
			e.printStackTrace();
			return "Erro a ler o ficheiro de propriedades.";
		}
		
		String info = new String(b);
		String details [] = info.split(" ");
			// utiliza a informacao obtida para efectuar a ligacao a BD
			 try {
		            Class.forName ("org.postgresql.Driver");        
		            // url = "jdbc:postgresql://host:port/database",
		            c = DriverManager.getConnection("jdbc:postgresql://"+details[0]+":5432/"+details[1],
		                                              details[2],
		                                              details[3]);
		            
		            stat = c.createStatement();
		            
		        }
		        catch (Exception e) {
		            e.printStackTrace();
		            return "Problemas na ligacao a BD.";
		        }
		return "Ligacao a BD bem sucedida.";
	}
	
	public String logoutBD(){ // metodo que permite efectuar o logout da BD
		// fecha a ligacao a BD
		 try {
	            stat.close();
	            c.close();
	            return "Logout bem sucedido.";
	        }
	        catch (Exception e) {
	            e.printStackTrace();
	            return "Erro no Logout.";
	        }
	}

	public Vector<String> pesquisa(String destino){ // metodo que efectua a pesquisa de voos por destino
		
		int id = 0; // temporario correspondente ao ID do voo.
		java.sql.Timestamp dataH = new java.sql.Timestamp((new java.util.Date()).getTime()); // temporario correspondente ao timestamp que contem a data e a hora do voo
		int vagos = 0; // temporario correspondente ao numero de lugares vagos.
		Vector<String> results = new Vector<String>(); // temporario correspondente aos resultados da pesquisa
		Vector<String> erro = new Vector<String>(); // temporario que armazena os eventuais erros que existirem durante a pesquisa
		
		connectBD(); // efectua a ligacao a BD
 		
		try { // pesquisa na BD voos com o destino indicado
            ResultSet rs = stat.executeQuery("SELECT id_voo,data_hora,lugares_vagos from Voo where destino = '"+destino+"';");
            
            while (rs.next()) {
                id= rs.getInt("id_voo"); // obtem o ID do voo
                dataH= rs.getTimestamp("data_hora"); // obtem a data e a hora do voo
                vagos = rs.getInt("lugares_vagos"); // obtem o numero de lugares vagos
                String resultado = "Codigo do Voo: "+id+";"+"Data e Hora: "+dataH+";"+"Lugares Vagos: "+vagos+";"; // cria uma string com o resultado da pesquisa
                results.add(resultado); // adiciona o resultado ao vector criado para o efeito
            }
            rs.close(); // liberta os recursos utilizados pelo ResultSet
        }
        catch (Exception e) {
            e.printStackTrace();
            erro.add("Ocorreram erros durante a pesquisa na BD."); // em caso de erro, adiciona a mensagem de erro ao vector correspondente
            return erro;
        }
		
 		logoutBD(); // termina a ligacao a BD
 		
		return results;
	}
	

	public Vector<String> consultaLP(int id_v){ // metodo que efectua a consulta da lista de passageiros de um voo
		
		int id = 0; // temporario que corresponde ao ID do voo
		int lugar = 0; // temporario que corresponde ao lugar do passageiro
		String nome = ""; // temporario correspondente ao nome do passageiro
		Vector<String> results = new Vector<String>(); // temporario correspondente aos resultados da pesquisa
		Vector<String> erro = new Vector<String>(); // temporario que armazena os eventuais erros que existirem durante a pesquisa
		
		connectBD(); // efectua a ligacao a BD
		
		try {// pesquisa na BD a lista de passageiros do voo indicado
            ResultSet rs = stat.executeQuery("select id_voo,nome,lugar " +
            		"from listapass natural inner join cliente " +
            		"where cliente.id_passageiro = listapass.id_passageiro and id_voo = '"+id_v+"';");
            
            while (rs.next()) {
                id= rs.getInt("id_voo"); // obtem o ID do voo
                nome= rs.getString("nome"); // obtem o nome do passageiro
                lugar = rs.getInt("lugar"); // obtem o lugar do passageiro
                results.add("Nome do Passageiro: "+nome+";"+"Lugar: "+lugar+";"); // adiciona a entrada da lista de passageiros ao vector dos resultados
            }
            rs.close(); // liberta os recursos utilizados pelo ResultSet
        }
        catch (Exception e) {
            e.printStackTrace();
            erro.add("Ocorreram erros durante a pesquisa na BD."); // em caso de erro, adiciona a mensagem de erro ao vector correspondente
            
            return erro;
        }
		
		logoutBD(); // fecha a ligacao a BD
		
		return results;
		
	}
	

	public String comprar(int id_v, String [] nomes, int nLug){ // metodo que permite efectuar a compra de lugares num voo
		
		int lug = 0; // temporario correspondente ao lugar
		String info_voo = ""; // temporario correspondente a informacao do voo
		String dest = ""; // temporario correspondente ao destino
		java.sql.Timestamp dataH = new java.sql.Timestamp((new java.util.Date()).getTime()); // temporario correspondente a data e a hora do voo
		
		connectBD(); // efectua a ligacao a BD
		
		try {// pesquisa na BD pelo voo indicado
            ResultSet rs = stat.executeQuery("SELECT destino, data_hora, lugares_vagos from Voo where id_voo = "+id_v+";");
            while (rs.next()) {
            	lug = rs.getInt("lugares_vagos"); // obtem o numero de lugares vagos no voo
            	dest = rs.getString("destino"); // obtem o destino do voo
            	dataH = rs.getTimestamp("data_hora"); // obtem a data e a hora do voo
            }
            rs.close();// liberta os recursos utilizados pelo ResultSet
        }
        catch (Exception e) {
            e.printStackTrace();
            return ("Ocorreram erros durante a pesquisa na BD."); // retorna uma mensagem de erro caso algo corra mal
        }
		
		int vagos_new = lug - nLug; // calcula o numero de lugares vagos no voo
		
		if(vagos_new >= 0){ // verifica se existem lugares suficientes para que possa ser efectuada a compra
			
			Vector<String> inserts = new Vector<String>(); // vector temporario para armazenar a informacao a ser inserida na BD apos a compra
			
			int actual = lug; // calcula o lugar actual
			
			for(int i = 0; i<nomes.length;i++){ // cria os inserts para a BD
					cod_passageiro++; // atribui um codigo ao passageiro
					String insertC = "insert into cliente values("+cod_passageiro+",'"+nomes[i]+"');"; // gera o insert da info do passageiro para a relacao cliente 
					String insertLP = "insert into listapass values("+id_v+","+cod_passageiro+","+actual+");"; // gera o insert da info do passageiro para a relacao cliente
					inserts.add(insertC); // adiciona o insert no vector criado para o efeito
					inserts.add(insertLP);
					actual--; // actualiza o lugar
			}
			
			for(int k = 0;k<inserts.size();k++){ // insere a informacao na BD
				
				try{
					 stat.executeUpdate(inserts.get(k)); // executa a query
					 
			        }
				catch(Exception e){
					e.printStackTrace();
		            return "Problemas ao inserir dados na BD."; // caso ocorra um erro, retorna uma mensagem
					}
				}
			
				try{ // actualiza o numero de lugares na BD
					stat.execute("UPDATE Voo SET lugares_vagos = "+vagos_new+"WHERE id_voo = "+id_v+";");
				} catch(Exception e){
					e.printStackTrace();
					return "Erro ao actualizar numero de lugares vagos.";
				}
			
			logoutBD(); // fecha a ligacao a BD
			
			inserts.removeAllElements(); // faz reset ao vector dos inserts
			
			cod_venda++; // actualiza o codigo da venda
			Vector<String> n_temp = new Vector<String>(); // vector temporario para armazenar os nomes dos passageiros
			
			for(int l = 0; l<nomes.length;l++){ // adiciona os nomes dos passageiros ao vector temporario
				n_temp.add(nomes[l]);
			}
			// gera a string com a informacao da venda
			String venda = "Codigo da Venda: "+cod_venda+"; "+"Info Voo - "+"ID: "+id_v+"; "+"Destino: "+dest+"; "+"Data e Hora: "+dataH.toString()+"; "+"Lugares Vagos: "+vagos_new+"; "+"Passageiros - "+n_temp.toString()+";";
			String nome_file = "Venda"+cod_venda; // gera o nome do ficheiro que ira armazenar a informacao da venda
			
			try{
				saveVenda(nome_file,venda); // escreve os dados da venda num ficheiro encriptado
			}catch(Exception e){
				e.printStackTrace();
			}
		
		}
		else{ // Caso nao existam lugares suficientes para efectuar a compra, retorna uma mensagem de erro.
			return "Nao existem lugares suficientes disponiveis no voo indicado. Operacao cancelada.";
		}
		
		return "Compra concluida com sucesso."; // se termina com sucesso, retorna a confirmacao
		
	}
	
	/****** EXTRA ******/
	
	public SecretKey obterSecretKey(){ // METODO EXTRA: permite obter a chave secreta que se encontra no KeyStore
		byte b[] = new byte[1024];
		FileInputStream fis = null;
		char [] pass;
		SecretKey sk=null;
		try{
			KeyStore ks = KeyStore.getInstance("JCEKS"); // cria uma instancia de um KeyStore do tipo JCEKS (Java Cryptography Extension KeyStore)
			
			FileInputStream fi = new FileInputStream("KSpwd"); // le a password do keystore de um ficheiro
			int lidos = fi.read(b);
			fi.close();
		
			String pwd = new String(b);
			String details [] = pwd.split(" ");
			pass = details[0].toCharArray();
		
			// cria um keystore vazio com o nome KStore que e protegido pela password lida do ficheiro
		    fis= new FileInputStream("KStore");
		    ks.load(fis, pass);
		    fis.close();
		
		    // obtem a entrada correspondente a uma chave secreta no KeyStore
			KeyStore.SecretKeyEntry ske=(KeyStore.SecretKeyEntry)ks.getEntry("chaveSecreta",new KeyStore.PasswordProtection(pass));
			sk=ske.getSecretKey(); // obtem a chave secreta
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return sk; // retorna a chave secreta
	}
	
	public void saveVenda(String nome,String v) throws RemoteException{ // permite armazenar a informacao da venda num ficheiro encriptado
	
        File temp = new File("temp");     // ficheiro temporario para fornecer o caminho  
        String cam2  = temp.getAbsolutePath();  // string com o caminho ate a pasta src
        int aux = cam2.length();            // Int com o tamanho da string do caminho
        String cam = cam2.substring(0, aux-4); // String com caminho ate a pasta do so2webapp-src
        String caminho = cam+"/Repositorio/"+nome; // gera o nome do ficheiro
        
        try{ // escreve o objecto para um ficheiro (aqui ainda desencriptado)
			FileOutputStream fout = new FileOutputStream(caminho);
            ObjectOutputStream ops = new ObjectOutputStream(fout);    
            ops.writeObject(v);     // escreve o ficheiro
            ops.flush();
            ops.close();            // fecha a stream
            
            SecretKey sk = obterSecretKey(); // obtem a chave secreta
			Crypto c = new Crypto(); // cria uma instancia da classe que implementa os metodos necessarios para encriptacao
			
			c.cifrar(caminho,sk); // encripta o ficheiro utilizando o metodo cifrar
        }
        catch (Exception ex){
            ex.printStackTrace();
        } 
	}
	
	public String consultaRegistos(int id_venda){ // metodo que permite consultar a informacao de vendas armazenadas em ficheiros encriptados
		
		String nome = "Venda"+id_venda+"_enc"; // gera o nome do ficheiro
		String content = ""; // temporario para armazenar a informacao desencriptada
		File temp = new File("temp");     // ficheiro temporario para fornecer o caminho  
        String cam2  = temp.getAbsolutePath();  // string com o caminho ate a pasta src
        int aux = cam2.length();            // Int com o tamanho da string do caminho
        String cam = cam2.substring(0, aux-4); // String com caminho ate a pasta so2webapp-src
        String caminho = cam+"/Repositorio/"+nome; // gera o nome do ficheiro a consultar
        
        try{
        	
        	SecretKey sk = obterSecretKey(); // obtem a chave secreta
        	Crypto c = new Crypto();  // cria uma instancia da classe que implementa os metodos necessarios para encriptacao
        	content = c.decifrar(caminho,sk); // obtem a informacao desencriptada
        	
        }
        catch(Exception e){
        	e.printStackTrace();
        }
        return content; // retorna a informacao solicitada
	}
}
