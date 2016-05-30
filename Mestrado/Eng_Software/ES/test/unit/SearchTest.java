import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.junit.Test;


public class SearchTest {

	@Test
	public void testTitleSearchSingle() throws ClassNotFoundException, SQLException {
		/*Testa se ha 1 documento com titulo lorem*/
		DBAccess dbaccess=populate();
		DocSearch d = new DocSearch(dbaccess);
		ArrayList<Integer> results = d.searchTitles("Lorem");
		assertEquals(results.size(), 1);
	}
	
	@Test
	public void testTitleSearchMulti() throws ClassNotFoundException, SQLException {
		/*Testa se ha 2 documentos com titulo tiitulo*/
		DBAccess dbaccess=populate();
		DocSearch d = new DocSearch(dbaccess);
		ArrayList<Integer> results = d.searchTitles("tiitulo");
		assertEquals(results.size(), 2);
	}
	
	@Test (expected=NullPointerException.class)
	public void testTitleSearchNulls() throws ClassNotFoundException, SQLException {
		//Testa pesquisa da string vazia
		DBAccess dbaccess=populate();
		DocSearch d = new DocSearch(dbaccess);
		d.searchTitles(null);
	}

	@Test
	public void testBodySearchMissing() throws ClassNotFoundException, SQLException {
		//body nao existe no documento
		DBAccess dbaccess=populate();
		DocSearch d = new DocSearch(dbaccess);
		ArrayList<Integer> results = d.searchBodies("foleiruo");
		assertEquals(results.size(), 0);
	}
	

	@Test
	public void testBodySearchFound() throws ClassNotFoundException, SQLException {
		//Body existe no documento
		DBAccess dbaccess=populate();
		DocSearch d = new DocSearch(dbaccess);
		ArrayList<Integer> results = d.searchBodies("Lorem");
		assertEquals(results.size(), 1);
	}

	@Test (expected=NullPointerException.class)
	public void testBodySearchNull() throws ClassNotFoundException, SQLException {
		//Body existe no documento
		DBAccess dbaccess=populate();
		DocSearch d = new DocSearch(dbaccess);
		d.searchBodies(null);
	}
	

	@Test
	public void testGenericSearchMulti() throws ClassNotFoundException, SQLException {
		//Encontrou varios docs com o resultado
		DBAccess dbaccess=populate();
		DocSearch d = new DocSearch(dbaccess);
		ArrayList<Integer> results = d.searchGeneric("foleiro");
		assertEquals(results.size(), 2);
	}

	@Test
	public void testGenericSearchSingle() throws ClassNotFoundException, SQLException {
		//Encontrou um doc com o resultado
		DBAccess dbaccess=populate();
		DocSearch d = new DocSearch(dbaccess);
		ArrayList<Integer> results = d.searchGeneric("Lorem");
		assertEquals(results.size(), 1);
	}
	@Test (expected=NullPointerException.class)
	public void testGenericSearchNull() throws ClassNotFoundException, SQLException {
		//null test
		DBAccess dbaccess=populate();
		DocSearch d = new DocSearch(dbaccess);
		d.searchGeneric(null);
	}
	
	@Test
	public void testSearchByIdSingle() throws Exception {
		//Encontrou um doc com o resultado
		DBAccess dbaccess=populate();
		DocSearch d = new DocSearch(dbaccess);
		ArrayList<String> results = d.searchGeneric(1);
		assertEquals(results.size(), 1);
	}

	
	@Test (expected=Exception.class)
	public void testSearchByIdNoIdFound() throws Exception {
		//null test
		DBAccess dbaccess=populate();
		DocSearch d = new DocSearch(dbaccess);
		d.searchGeneric(56);
	}

	@Test (expected=NullPointerException.class)
	public void testSearchByIdNulls() throws Exception {
		//Null test
		DBAccess dbaccess=populate();
		DocSearch d = new DocSearch(dbaccess);
		d.searchGeneric(-1);
	}

	@Test 
	public void testSearchByDate() throws ClassNotFoundException, SQLException {
		//Procura por data. 
		DBAccess dbaccess=populate();
		DocSearch d = new DocSearch(dbaccess);
		long testTime=System.currentTimeMillis();
		
		/*Dado que os documentos ja foram testados, neste ponto nao sao novamente. nao ha, 
		 * portanto, testes relevantes para este bloco try catch.*/
		
		try {
			Documento d1 = new Documento(4, "titulo bonito", "body reles", new Timestamp(testTime+50), 1);
			d1.addDoc(dbaccess);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		d.updateDB();
		ArrayList<String> results = d.searchByDate(new Timestamp(testTime));
		assertEquals(results.size(), 1);
	}
	
	
	@Test  (expected=NullPointerException.class)
	public void testSearchByDateNulls() throws ClassNotFoundException, SQLException {
		
		DBAccess dbaccess=populate();
		DocSearch d = new DocSearch(dbaccess);
		d.searchByDate(null);
	}
	
	
	private DBAccess populate() throws ClassNotFoundException, SQLException{
		
		/*Cria uma base de dados com dados.
		 * User: 1, Nuno
		 * Documentos: 1, 2, 3; o 3 e lido de docs/documento.txt.
		 * Retorna a base de dados DBAccess criada.
		 * Os testes de utilizadores e documentos ja estao efectuados,
		 * pelo que nao voltam a ser nesta fase.*/
		
		DBAccess sourceDatabase=new DBAccess("org.h2.Driver", "jdbc:h2:mem:", "root", "password");
		sourceDatabase.initialize();
		
		Utilizador u1=new Utilizador(1,"Nuno");
		u1.addUser(sourceDatabase);
		
		try {
			Documento d1 = new Documento(1, "tiitulo bonito", "body foleiro", new Timestamp(System.currentTimeMillis()), 1);
			d1.addDoc(sourceDatabase);
			Documento d2 = new Documento(2, "tiitulo bonito", "body foleiro", new Timestamp(System.currentTimeMillis()), 1);
			d2.addDoc(sourceDatabase);
			Documento d3 = new Documento(3, "docs/documento.txt", new Timestamp(System.currentTimeMillis()), 1);
			d3.addDoc(sourceDatabase);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sourceDatabase;
		
	}
	
}
