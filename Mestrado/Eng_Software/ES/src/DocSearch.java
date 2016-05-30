import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


/*A class DocSearch recebe como argumento na criacao um documento.*/
public class DocSearch {
	
	//allDocs vai ter todos os documentos.
	private ArrayList<Documento> allDocs;
	private DBAccess sourceDatabase;
	
	
	public DocSearch(DBAccess dba){
		/*Nao sao precisos argumentos para instanciar porque os documentos sao retirados da base de dados.*/
		sourceDatabase=dba;
		updateDB();
		
	}
	public void updateDB()
	{
		allDocs = new ArrayList<Documento>();
		try {
			/*Inicializacao da base de dados.*/
			Connection conn = sourceDatabase.getConnection();
			Statement stmt = conn.createStatement();
			
			/*Query. Querem-se todos os dados de todos os documentos neste ponto, por isso o select e a tudo (*) */
			String sqlQuery = "SELECT * FROM documentos;";
			ResultSet rs = stmt.executeQuery(sqlQuery);
			while(rs.next()){
				
				/*para cada resultado e acrescentado um documento, instanciado a partir da informacao obtida
				 * da base de dados.*/
				
				int id=Integer.parseInt(rs.getString("id"));
				String title=rs.getString("title");
				String body=rs.getString("body");
				SimpleDateFormat create=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
				Timestamp createTS=new Timestamp(create.parse(rs.getString("d_criacao")).getTime());
				//String modify=rs.getString("d_alteracao");
				int user=Integer.parseInt(rs.getString("id_user"));
				
				//Instancia um documento que serve para pesquisa.
				//Isto funciona para documentos _pequenos_ dado que os objectos sao instanciados para a memoria.
				//Uma biblioteca extensa obrigava a verficiar doc a doc sem carregar em memoria e ir fazendo free/garbage collect.
				Documento current = new Documento(id,title,body, createTS, user);
				/*Acrescenta ao allDocs o documento que encontrou e continua o ciclo.*/
				allDocs.add(current);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*Procura no campo de titulo.*/
	public ArrayList<Integer> searchTitles(String toSearch){
		if(toSearch.equals(null)){
			throw new NullPointerException();
		}
		
		/*Para saber quanto anda no ciclo.*/
		int nDocs=allDocs.size();
		
		/*ArrayList vai conter os ids dos documentos que contem o titulo.*/
		ArrayList<Integer> docsContain = new ArrayList<Integer>();
		
		/*Itera sobre os documentos da base de dados, ja carregados no construtor desta classe.*/
		for(int i=0; i<nDocs; i++)
		{
			Documento current=allDocs.get(i);
			
			//o indexOf da a posicao da string onde encontra match (complexidade o(n)). -1 e o resultado se _nao_
			//encontra na string o que esta no argumento. (toSearch)
			if(current.getTitle().indexOf(toSearch)!=-1)
			{
				//Se o documento TEM o titulo toSearch, acrescenta-o ao docsContain (a arraylist que guarda os resultados)
				docsContain.add(current.getID());
			}		
		}
	
		/*Retorna arraylist dos documentos que contem o que foi pesquisado. De momento, listados por ID.*/
		return docsContain;
	}

	
	public ArrayList<Integer> searchBodies(String toSearch){
		if(toSearch.equals(null)){
			throw new NullPointerException();
		}
		
		/*Para saber quanto anda no ciclo.*/
		int nDocs=allDocs.size();
		
		/*ArrayList vai conter os ids dos documentos que contem o body.*/
		ArrayList<Integer> docsContain = new ArrayList<Integer>();
		
		/*Itera sobre os documentos da base de dados, ja carregados no construtor desta classe.*/
		for(int i=0; i<nDocs; i++)
		{
			Documento current=allDocs.get(i);
			
			//o indexOf da a posicao da string onde encontra match (complexidade o(n)). -1 e o resultado se _nao_
			//encontra na string o que esta no argumento. (toSearch)
			if(current.getBody().indexOf(toSearch)!=-1)
			{
				//Se o documento TEM o body toSearch, acrescenta-o ao docsContain (a arraylist que guarda os resultados)
				docsContain.add(current.getID());
			}		
		}
		
		/*Retorna arraylist dos documentos que contem o que foi pesquisado. De momento, listados por ID.*/
		return docsContain;
	}
	


	
	public ArrayList<Integer> searchGeneric(String toSearch){
		if(toSearch.equals(null)){
			throw new NullPointerException();
		}

		/*Para saber quanto anda no ciclo.*/
		int nDocs=allDocs.size();

		/*ArrayList vai conter os ids dos documentos que contem o toSearch.*/
		ArrayList<Integer> docsContain = new ArrayList<Integer>();

		/*Itera sobre os documentos da base de dados, ja carregados no construtor desta classe.*/
		for(int i=0; i<nDocs; i++)
		{
			Documento current=allDocs.get(i);
			
			//o indexOf da a posicao da string onde encontra match (complexidade o(n)). -1 e o resultado se _nao_
			//encontra na string o que esta no argumento. (toSearch)
			if(current.getBody().indexOf(toSearch)!=-1)
			{
				//Se o documento TEM o body toSearch, acrescenta-o ao docsContain (a arraylist que guarda os resultados).
				//De notar que, se ja foi encontrado no titulo, o documento ja esta no docsContain, portanto
				//e terminada esta iteracao com continue para evitar que o mesmo documento se repita.
				docsContain.add(current.getID());
				continue;
			}

			if(current.getTitle().indexOf(toSearch)!=-1)
			{
				//Se o documento TEM o title toSearch, acrescenta-o ao docsContain (a arraylist que guarda os resultados).
				//De notar que, neste ponto, _nao_ tem o toSearch no BODY, so no title.
				docsContain.add(current.getID());
			}		
		}
		
		/*Retorna arraylist dos documentos que contem o que foi pesquisado. De momento, listados por ID.*/
		return docsContain;
	}
	

	public ArrayList<String> searchGeneric(int toSearch) throws Exception{
		if(toSearch<0){
			throw new NullPointerException();
		}
		
		/*Para saber quanto anda no ciclo.*/
		int nDocs=allDocs.size();

		/*ArrayList vai conter os titulos do documento que contem o toSearch. Dado que os ids sao unicos, o resultado DEVERA ser sempre
		 * de size 0 ou 1.*/
		ArrayList<String> docsContain = new ArrayList<String>();

		/*Itera sobre os documentos da base de dados, ja carregados no construtor desta classe.*/
		for(int i=0; i<nDocs; i++)
		{
			Documento current=allDocs.get(i);
			//Neste caso o id e um integer, portanto pode ser comparado direcatmente com ==.
			if(current.getID()==toSearch)
			{
				//Acrescenta o documento se o id e encontrado.
				docsContain.add(current.getTitle());
			}
		}
		if(docsContain.size()==0)
			throw new Exception("Document ID not listed on the database.");
		/*Retorna arraylist dos documentos que contem o que foi pesquisado. De momento, listados por titulo.*/
		return docsContain;
	}
	
	
	public ArrayList<String> searchByDate(Timestamp toSearch){
		if(toSearch.equals(null)){
			throw new NullPointerException();
		}
		
		/*Para saber quanto anda no ciclo.*/
		int nDocs=allDocs.size();
		
		/*ArrayList vai conter os titulos dos documentos cuja data de criacao excede toSearch.*/
		ArrayList<String> docsContain = new ArrayList<String>();

		/*Itera sobre os documentos da base de dados, ja carregados no construtor desta classe.*/
		for(int i=0; i<nDocs; i++)
		{
			Documento current=allDocs.get(i);
			//Timestamp.after(toSearch) da true se toSearch for MENOR que Timestamp.
			if(current.getD_criacao().after(toSearch)){
				docsContain.add(current.getTitle());
			}
		}
		/*Retorna arraylist dos documentos que contem o que foi pesquisado. De momento, listados por titulo.*/
		return docsContain;
	}

	public String toString(){
		
		/*Para saber quanto anda no ciclo.*/
		int nDocs=allDocs.size();
		
		String result="";
		
		/*Itera sobre os documentos da base de dados, ja carregados no construtor desta classe.*/
		for(int i=0; i<nDocs; i++)
		{
			Documento current=allDocs.get(i);
			result+=current.toString()+";\n";
		}
		return result;
	}


}
