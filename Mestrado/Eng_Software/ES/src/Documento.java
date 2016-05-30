import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Timestamp;
import java.sql.SQLException;

public class Documento {
	private int id;
	private String title;
	private String body;
	private Timestamp d_criacao;
	private Timestamp d_alteracao;
	private int id_user = -1;

	private String readValue=null;
	private File documentFile;


	//construtor que recebe o titulo e corpo como string
	public Documento(int id, String title, String body, Timestamp timestamp, int id_user) throws Exception {
		// excecao quando nao e instanciado o titulo
		if (title.equals(null)) {
			throw new NullPointerException();
		}
		// excecao quando nao e instanciado o corpo
		if (body.equals(null)) {
			throw new NullPointerException();
		}
		// excecao quando nao e instanciado um user
		if (id_user == -1) { 
			throw new Exception("id_user wrong");
		}
		// excecao quando nao e instanciado o Timestamp
		if (timestamp.equals(null)) {
			throw new NullPointerException();
		}
		this.id = id;
		this.title = title;
		this.body = body;
		this.d_criacao = timestamp;
		this.id_user = id_user;
	}

	/*OVERLOAD: Versao que carrega com filepath em vez de hard coded title e body*/
	public Documento(int id, String filepath, Timestamp timestamp, int id_user) throws Exception {
		// excecao quando nao e instanciado um user
		if (id_user == -1) {
			throw new Exception("id_user wrong");
		}
		// excecao quando nao e instanciado o Timestamp
		if (timestamp.equals(null)) {
			throw new NullPointerException();
		}
		this.id = id;
		this.d_criacao = timestamp;
		this.id_user = id_user;
		// excecao quando nao e instanciado um caminho para o ficheiro
		if(filepath.equals(null)){
			throw new Exception("File path is null.");
		}
		// excecao quando o formato do ficheiro nao e txt
		if(!filepath.endsWith(".txt")){
			throw new Exception("File format is not TXT.");
		}

		documentFile = new File(filepath);
		// excecao quando o ficheiro instaciado nao existe
		if(!documentFile.exists())
			throw new FileNotFoundException();
		BufferedReader reader = null;
		try 
		{
			reader = new BufferedReader (new FileReader(documentFile) );
			// Linha 1: "title:"
			readValue=reader.readLine();
			
			if(readValue.compareTo("title:")!=0)
				throw new DocumentNotFormattedException("Document exists, but format is incorrect: No \"title:\" header.");
			// Linha 2: "sometitle"
			readValue=reader.readLine();
			title=readValue;
			readValue=reader.readLine();
			if(readValue.compareTo("")!=0)
				throw new DocumentNotFormattedException("Document exists, but format is incorrect: No new line after the title.");
			// Linha 4: "body:"
			readValue=reader.readLine();
			if(readValue.compareTo("body:")!=0)
				throw new DocumentNotFormattedException("Document exists, but format is incorrect: No \"body:\" header.");
			// Linhas 5+: "anything goes"
			readValue=reader.readLine();
			body="";
			while(readValue!=null)
			{
				body+=readValue+"\n";
				readValue=reader.readLine();
			}
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		finally 
		{
		    try 
		    {
		        if (reader != null) 
		        {
		            reader.close();
		        }
		    } 
		    catch (Exception e) 
		    {
				e.printStackTrace();
		    }
		}

	}

	public String getTitle() {
		// obter o titulo do documento
		return title;
	}

	public String getBody() {
		// obter o corpo do documento
		return body;
	}

	public int getID() {
		// obter o ID do documento
		return id;
	}

	public int getUser() {
		// obter o utilizador responsavel
		return id_user;
	}

	public Timestamp getD_criacao() {
		// obter a data de criacao
		return d_criacao;
	}

	public Timestamp getD_alteracao() {
		// obter a data de alteracao
		return d_alteracao;
	}

	public void addDoc(DBAccess dbaccess) throws Exception {
		// adicionar um documento a BD
			// caso contrario, adicionar o documento a BD
			String sqlQuery = "INSERT INTO documentos VALUES( " + id + ", '"
					+ title + "','" + body + "', '" + d_criacao + "','"
					+ d_criacao + "', " + id_user + ")";
			dbaccess.runQuery(sqlQuery);
		
	}


	public void updateDocTitle(DBAccess dba, String n, int id,Timestamp d_alteracao) throws SQLException {
		// atualizar o titulo do documento

		if (n.equals(null)) {
			// se o titulo for null, lancar uma excepcao
			throw new NullPointerException();
		}

		else {
			// caso contrario atualizar na BD o titulo do documento
			String sqlQuery = "UPDATE documentos SET title = '" + n
					+ "' WHERE id = " + id;
			dba.runQuery(sqlQuery);
			
			// atualizar na BD a data de alteracao
			String sqlQuerytime_update = "UPDATE documentos SET d_alteracao = '"
					+ d_alteracao + "' WHERE id = " + id;
			dba.runQuery(sqlQuerytime_update);
		}

	}

	public void updateDocBody(DBAccess dba, String n, int id, Timestamp d_alteracao) throws SQLException {
		// atualizar o corpo do documento
		
		if (n.equals(null)) {
			// caso o corpo seja null, lancar uma excepcao
			throw new NullPointerException();
		} else {
			// caso contrario, atualizar na BD o corpo do documento
			String sqlQuery = "UPDATE documentos SET body = '" + n
					+ "' WHERE id = " + id;
			dba.runQuery(sqlQuery);
			
			//atualizar na BD a data de alteracao
			String sqlQuerytime_update = "UPDATE documentos SET d_alteracao = '"
					+ d_alteracao + "' WHERE id = " + id;
			dba.runQuery(sqlQuerytime_update);
		}

	}

	public void updateDocId_user(DBAccess dba, int id_user,	int id, Timestamp d_alteracao) throws Exception {
		// atualizar o ID do utilizador responsavel
		
		
		if (id_user == -1) {
			// se o ID for invalido, lancar uma excepcao
			throw new Exception("id_user wrong");
		} else {
			// caso contrario, atualizar na BD o ID do utilizador responsavel
			String sqlQuery = "UPDATE documentos SET id_user = '" + id_user
					+ "' WHERE id = " + id;
			dba.runQuery(sqlQuery);
			
			// atualizar a data de alteracao
			String sqlQuerytime_update = "UPDATE documentos SET d_alteracao = '"
					+ d_alteracao + "' WHERE id = " + id;
			dba.runQuery(sqlQuerytime_update);
		}

	}


	public void deleteDoc(DBAccess dba, int i) throws SQLException {
		// remover o documento da BD
		String sqlQuery = "DELETE FROM documentos WHERE id = " + i;
		dba.runQuery(sqlQuery);
	}

	public String toString(){
		//obter dados do documento numa string
		return "Titulo: "+this.title+"; Id do doc: "+id+"; Id do user: "+id_user+id+"; Timestamp create: "+d_criacao;
	}

}
