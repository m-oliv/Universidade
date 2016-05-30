import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

public class DocumentoTest {

	@Test (expected=DocumentNotFormattedException.class)
	public void testFilepathWrongContent() throws Exception {
		Date dat= new Date(System.currentTimeMillis());
		long datelong= dat.getTime();
		new Documento(1,"docs/documento2.txt", new Timestamp(datelong), 1);
	}
	
	@Test (expected=Exception.class)
	public void testFilepathConstructor() throws Exception {
		Date dat= new Date(System.currentTimeMillis());
		long datelong= dat.getTime();
		new Documento(1,"docs//NotAVirus.exe", new Timestamp(datelong), 1);
	}

	@Test (expected=Exception.class)
	public void testFilepathConstructorNullFilepath() throws Exception {
		Date dat= new Date(System.currentTimeMillis());
		long datelong= dat.getTime();
		new Documento(1,null, new Timestamp(datelong), 1);
	}

	@Test (expected=FileNotFoundException.class)
	public void testFilepathConstructorWrongDocumentName() throws Exception {
		Date dat= new Date(System.currentTimeMillis());
		long datelong= dat.getTime();
		new Documento(1,"docs//NotAVirus.txt", new Timestamp(datelong), 1);
	}
	
	@Test
	public void testTitleInObj() throws Exception {
		// verificar se o titulo atribuido esta no objeto
		
		// obter uma data para criacao
		Date dat= new Date(System.currentTimeMillis());
		long datelong= dat.getTime();
		
		// criar um documento para o teste
		Documento u = new Documento(1,"ola","adeus", new Timestamp(datelong), 1);
		
		// verificar se o titulo e o pretendido
		assertEquals(u.getTitle(),"ola");
	}
	
	@Test
	public void testIDInObj() throws Exception {
		// verificar se o ID atribuido esta no objeto
		
		// obter uma data para criacao
		Date dat= new Date(System.currentTimeMillis());
		long datelong= dat.getTime();
		
		// criar um documento para o teste
		Documento u = new Documento(1,"ola","adeus", new Timestamp(datelong), 1);
		
		// verificar se o ID obtido e o esperado
		assertEquals(u.getID(),1);
	}
	
	@Test
	public void testBodyInObj() throws Exception {
		// verificar se o corpo do documento atribuido esta no objeto
		
		// obter uma data para criacao
		Date dat= new Date(System.currentTimeMillis());
		long datelong= dat.getTime();
		
		// criar um documento para o teste
		Documento u = new Documento(1,"ola","adeus",new Timestamp(datelong), 1);
		
		// verificar se o corpo do documento obtido e o pretendido
		assertEquals(u.getBody(),"adeus");
	}
	
	@Test
	public void testID_userInObj() throws Exception {
		// verificar se o ID_user do documento atribuido esta no objeto

		// obter uma data para criacao
		Date dat= new Date(System.currentTimeMillis());
		long datelong= dat.getTime();
		// criar um documento para o teste
		Documento u = new Documento(1,"ola","adeus", new Timestamp(datelong), 1);
		// verificar se o ID_user do documento obtido e o pretendido
		assertEquals(u.getUser(),1);
	}
	
	@Test
	public void testD_criacaoInObj() throws Exception {
		// verificar se a data de criacao do documento atribuido esta no objeto

		// obter uma data para criacao
		Date dat= new Date(System.currentTimeMillis());
		long datelong= dat.getTime();
		// criar um documento para o teste
		Documento u = new Documento(1,"ola","adeus", new Timestamp(datelong), 1);
		// verificar se a data de criacao do documento obtida e a pretendido
		assertEquals(u.getD_criacao(),new Timestamp(datelong));
	}
	
	@Test
	public void testAddDocTitle() throws Exception,ClassNotFoundException, SQLException{
		// Verificar se o titulo atribuido ao documento foi armazenado na BD

		// inicializar a BD H2 do sistema 
		DBAccess dba = new DBAccess("org.h2.Driver", "jdbc:h2:mem:", "root", "password");
		dba.initialize();

		// criar um utilizador e adicionar o mesmo a BD para pasar o id para o documento
		Utilizador u = new Utilizador(1,"Nuno");
		u.addUser(dba);

		// obter uma data para criacao
		Date dat= new Date(System.currentTimeMillis());
		long datelong= dat.getTime();
		// criar um documento e adiciona o mesmo a BD
		Documento d = new Documento(1,"ola","adeus", new Timestamp(datelong), 1);
		d.addDoc(dba);

		// Obter uma ligacao a BD
		Connection conn = dba.getConnection();
		Statement stmt = conn.createStatement();

		// procurar na BD o titulo do documento adicionado
		String sqlQuery = "select title from documentos;";
		ResultSet rs = stmt.executeQuery(sqlQuery);
		String x = "";
		while(rs.next()){
			 x = rs.getString("title");
		}
		// verificar se o resultado obtido e o esperado
		assertEquals(x,"ola");
	}
	
	@Test
	public void testAddDocBody() throws Exception,ClassNotFoundException, SQLException{
		// Verificar se o titulo atribuido ao documento foi armazenado na BD

		// inicializar a BD H2 do sistema 
		DBAccess dba = new DBAccess("org.h2.Driver", "jdbc:h2:mem:", "root", "password");
		dba.initialize();
		// criar um utilizador e adicionar o mesmo a BD para pasar o id para o documento
		Utilizador u = new Utilizador(1,"Nuno");
		u.addUser(dba);
		// obter uma data para criacao
		Date dat= new Date(System.currentTimeMillis());
		long datelong= dat.getTime();
		// criar um documento e adiciona o mesmo a BD
		Documento d = new Documento(1,"ola","adeus", new Timestamp(datelong), 1);
		d.addDoc(dba);
		// Obter uma ligacao a BD
		Connection conn = dba.getConnection();
		Statement stmt = conn.createStatement();
		
		// procurar na BD o corpo do documento adicionado
		String sqlQuery = "select body from documentos;";
		ResultSet rs = stmt.executeQuery(sqlQuery);
		String x = "";
		while(rs.next()){
			 x = rs.getString("body");
		}
		// verificar se o resultado obtido e o esperado
		assertEquals(x,"adeus");
	}
	@Test
	public void testAddDocID() throws Exception,ClassNotFoundException, SQLException{
		// Verificar se o id atribuido ao documento foi armazenado na BD

		// inicializar a BD H2 do sistema 
		DBAccess dba = new DBAccess("org.h2.Driver", "jdbc:h2:mem:", "root", "password");
		dba.initialize();

		// criar um utilizador e adicionar o mesmo a BD para pasar o id para o documento
		Utilizador u = new Utilizador(1,"Nuno");
		u.addUser(dba);
		
		// obter uma data para criacao
		Date dat= new Date(System.currentTimeMillis());
		long datelong= dat.getTime();
		// criar um documento e adiciona o mesmo a BD
		Documento d = new Documento(1,"ola","adeus", new Timestamp(datelong), 1);
		d.addDoc(dba);
		// Obter uma ligacao a BD
		Connection conn = dba.getConnection();
		Statement stmt = conn.createStatement();
		// procurar na BD o id do documento adicionado
		String sqlQuery = "select id from documentos;";
		ResultSet rs = stmt.executeQuery(sqlQuery);
		int x = 0;
		while(rs.next()){
			 x = rs.getInt("id");
		}
		// verificar se o resultado obtido e o esperado
		assertEquals(x,1);
	}
	
	@Test
	public void testAddDocTimestamp() throws Exception,ClassNotFoundException, SQLException{
		// Verificar se o Timestamp atribuido ao documento foi armazenado na BD

		// inicializar a BD H2 do sistema 
		DBAccess dba = new DBAccess("org.h2.Driver", "jdbc:h2:mem:", "root", "password");
		dba.initialize();
		// criar um utilizador e adicionar o mesmo a BD para pasar o id para o documento
		Utilizador u = new Utilizador(1,"Nuno");
		u.addUser(dba);

		// obter uma data para criacao
		Date dat= new Date(System.currentTimeMillis());
		long datelong= dat.getTime();
		// criar um documento e adiciona o mesmo a BD
		Documento d = new Documento(1,"ola","adeus", new Timestamp(datelong), 1);
		d.addDoc(dba);
		// Obter uma ligacao a BD
		Connection conn = dba.getConnection();
		Statement stmt = conn.createStatement();
		// procurar na BD a Timestamp do documento adicionado
		String sqlQuery = "select d_criacao from documentos;";
		ResultSet rs = stmt.executeQuery(sqlQuery);
		Timestamp x= new Timestamp(0);
		while(rs.next()){
			 x = rs.getTimestamp("d_criacao");
		}
		// verificar se o resultado obtido e o esperado
		assertEquals(x, new Timestamp(datelong));
	}
	
	@Test
	public void testAddDocID_user() throws Exception,ClassNotFoundException, SQLException{

		// Verificar se o Id do utilizador associado atribuido ao documento foi armazenado na BD

		// inicializar a BD H2 do sistema 
		DBAccess dba = new DBAccess("org.h2.Driver", "jdbc:h2:mem:", "root", "password");
		dba.initialize();
		
		// criar um utilizador e adicionar o mesmo a BD para pasar o id para o documento
		Utilizador u = new Utilizador(1,"Nuno");
		u.addUser(dba);
		
		// obter uma data para criacao
		Date dat= new Date(System.currentTimeMillis());
		long datelong= dat.getTime();
		// criar um documento e adiciona o mesmo a BD
		Documento d = new Documento(1,"ola","adeus", new Timestamp(datelong), 1);
		d.addDoc(dba);
		// Obter uma ligacao a BD
		Connection conn = dba.getConnection();
		Statement stmt = conn.createStatement();

		// procurar na BD o Id do utilizador associado do documento adicionado
		String sqlQuery = "select id_user from documentos;";
		ResultSet rs = stmt.executeQuery(sqlQuery);
		int x = 0;
		while(rs.next()){
			 x = rs.getInt("id_user");
		}
		// verificar se o resultado obtido e o esperado
		assertEquals(x,1);
	}
	
	
	@Test
	public void testUpdateTitle() throws Exception,ClassNotFoundException, SQLException{

		// verificar se a atualizacao do titulo do documento foi bem sucedida

		// inicializar a BD H2 do sistema 
		DBAccess dba = new DBAccess("org.h2.Driver", "jdbc:h2:mem:", "root", "password");
		dba.initialize();
		// criar um utilizador e adicionar o mesmo a BD para pasar o id para o documento
		Utilizador u = new Utilizador(1,"Nuno");
		u.addUser(dba);
		
		// obter uma data para criacao
		Date dat= new Date(System.currentTimeMillis());
		long datelong= dat.getTime();
		// criar um documento e adiciona o mesmo a BD
		Documento d = new Documento(1,"ola","adeus", new Timestamp(datelong), 1);
		d.addDoc(dba);
		// atualizar o titulo de utilizador
		d.updateDocTitle(dba,"OLA",1,new Timestamp(datelong));
		// Obter uma ligacao a BD
		Connection conn = dba.getConnection();
		Statement stmt = conn.createStatement();
		// procurar na BD o titulo do documento correspondente
		String sqlQuery = "select title from documentos;";
		ResultSet rs = stmt.executeQuery(sqlQuery);
		String x = "";
		while(rs.next()){
			 x = rs.getString("title");
		}
		// verificar se o resultado obtido e o esperado
		assertEquals(x,"OLA");
	}


	@Test
	public void testUpdateBody() throws Exception,ClassNotFoundException, SQLException{

		// verificar se a atualizacao do corpo do documento foi bem sucedida

		// inicializar a BD H2 do sistema 
		DBAccess dba = new DBAccess("org.h2.Driver", "jdbc:h2:mem:", "root", "password");
		dba.initialize();
		// criar um utilizador e adicionar o mesmo a BD para pasar o id para o documento
		Utilizador u = new Utilizador(1,"Nuno");
		u.addUser(dba);

		// obter uma data para criacao
		Date dat= new Date(System.currentTimeMillis());
		long datelong= dat.getTime();
		// criar um documento e adiciona o mesmo a BD
		Documento d = new Documento(1,"ola","adeus", new Timestamp(datelong), 1);
		d.addDoc(dba);
		// atualizar o corpo de utilizador
		d.updateDocBody(dba,"ADEUS",1,new Timestamp(datelong));
		
		// Obter uma ligacao a BD
		Connection conn = dba.getConnection();
		Statement stmt = conn.createStatement();

		// procurar na BD o corpo do documento correspondente
		String sqlQuery = "select body from documentos;";
		ResultSet rs = stmt.executeQuery(sqlQuery);
		String x = "";
		while(rs.next()){
			 x = rs.getString("body");
		}
		// verificar se o resultado obtido e o esperado
		assertEquals(x,"ADEUS");
	}
	
	@Test
	public void testUpdateID_user() throws Exception,ClassNotFoundException, SQLException{

		// verificar se a atualizacao do utilizador associado ao documento foi bem sucedida

		// inicializar a BD H2 do sistema 
		DBAccess dba = new DBAccess("org.h2.Driver", "jdbc:h2:mem:", "root", "password");
		dba.initialize();

		// criar dois utilizadores e adiciona os mesmos a BD para pasar os ids para os documentos
		Utilizador u = new Utilizador(1,"Nuno");
		u.addUser(dba);
		Utilizador u1 = new Utilizador(2,"Nuna");
		u1.addUser(dba);

		// obter uma data para criacao
		Date dat= new Date(System.currentTimeMillis());
		long datelong= dat.getTime();
		// criar um documento e adiciona o mesmo a BD
		Documento d = new Documento(1,"ola","adeus", new Timestamp(datelong), 1);
		d.addDoc(dba);
		
		// atualizar o utilizador associado ao documento
		d.updateDocId_user(dba,2,1,new Timestamp(datelong));
		// Obter uma ligacao a BD
		Connection conn = dba.getConnection();
		Statement stmt = conn.createStatement();

		// procurar na BD  utilizador associado ao documento correspondente
		String sqlQuery = "select id_user from documentos;";
		ResultSet rs = stmt.executeQuery(sqlQuery);
		int x = -1;
		while(rs.next()){
			 x = rs.getInt("id_user");
		}
		// verificar se o resultado obtido e o esperado
		assertEquals(x,2);
	}
	
	@Test
	public void testUpdateD_alteracao() throws Exception,ClassNotFoundException, SQLException{

		// verificar se a atualizacao data de alteracao ao documento foi bem sucedida

		// inicializar a BD H2 do sistema 
		DBAccess dba = new DBAccess("org.h2.Driver", "jdbc:h2:mem:", "root", "password");
		dba.initialize();

		// criar um utilizador e adicionar o mesmo a BD para pasar o id para o documento
		Utilizador u = new Utilizador(1,"Nuno");
		u.addUser(dba);
	
		// obter uma data para criacao
		Date dat= new Date(System.currentTimeMillis());
		long datelong= dat.getTime();
		// criar um documento e adiciona o mesmo a BD
		Documento d = new Documento(1,"ola","adeus", new Timestamp(datelong), 1);
		d.addDoc(dba);
		
		//cria um timestamp temporario com a data intruduzida
		Timestamp test=new Timestamp(datelong);
		//atualizar o Timestamp da data de alteracao do documento
		d.updateDocTitle(dba,"OLA",1,new Timestamp(datelong));
		
		
		// Obter uma ligacao a BD
		Connection conn = dba.getConnection();
		Statement stmt = conn.createStatement();

		// procurar na BD o Timestamp de alteracao do documento correspondente
		String sqlQuery = "select d_alteracao from documentos;";
		ResultSet rs = stmt.executeQuery(sqlQuery);
		Timestamp temp=new Timestamp(0);
		while(rs.next()){
			 temp = rs.getTimestamp("d_alteracao");
		}
		// verificar se o resultado obtido e o esperado
		assertEquals(temp,test);
	}
	
	@Test 
	public void testDeleteDoc() throws Exception, ClassNotFoundException, SQLException{

		// verificar se um utilizador e corretamente eliminado da BD
		
		// Inicializar a BD H2 do sistema
		DBAccess dba = new DBAccess("org.h2.Driver", "jdbc:h2:mem:", "root", "password");
		dba.initialize();

		// criar um utilizador e adicionar o mesmo a BD para pasar o id para o documento
		Utilizador u = new Utilizador(1,"Nuno");
		u.addUser(dba);

		// obter uma data para criacao
		Date dat= new Date(System.currentTimeMillis());
		long datelong= dat.getTime();

		// criar um documento e adiciona o mesmo a BD
		Documento d = new Documento(1,"ola","adeus", new Timestamp(datelong), 1);
		d.addDoc(dba);
		// eliminar o documento da BD
		d.deleteDoc(dba, 1);
		// Obter uma ligacao a BD
		Connection conn = dba.getConnection();
		Statement stmt = conn.createStatement();
		// Contar o numero de documentos existentes com o ID igual ao do documento eliminado
		String sqlQuery = "select count(title) from documentos where id = 1;";
		ResultSet rs = stmt.executeQuery(sqlQuery);
		int x = 0;
		while(rs.next()){
			 x = rs.getInt(1);
		}
		// se o resultado da contagem for zero, o documento foi eliminado corretamente (os IDs sao unicos)
		assertTrue(x==0);
	}
	
	@Test (expected = NullPointerException.class)
	public void testTitleNullObj()throws Exception {
		// obter uma data para criacao
		Date dat= new Date(System.currentTimeMillis());
		long datelong= dat.getTime();
		// criar um documento para o teste com o titulo a null
		Documento u = new Documento(1,null,"adeus", new Timestamp(datelong), 1);
	}
	@Test (expected = NullPointerException.class)
	public void testBodyNullObj()throws Exception {
		// obter uma data para criacao
		Date dat= new Date(System.currentTimeMillis());
		long datelong= dat.getTime();
		// criar um documento para o teste com o corpo a null
		Documento u = new Documento(1,"ola",null, new Timestamp(datelong), 1);
	}
	
	@Test (expected = NullPointerException.class)
	public void testD_criacaoNullObj()throws Exception {
		// criar um documento para o teste com data de criacao a null
		Documento u = new Documento(1,"ola","adeus", null, 1);
	
	}

	@Test (expected = SQLException.class)
	public void testInsertDuplicate() throws Exception,ClassNotFoundException, SQLException{
		// verificar se e lancada uma excepcao quando existem documentos com id duplicados
		
		// Inicializar a BD H2 do sistema
		DBAccess dba = new DBAccess("org.h2.Driver", "jdbc:h2:mem:", "root", "password");
		dba.initialize();
		// obter uma data para criacao
		Date dat= new Date(System.currentTimeMillis());
		long datelong= dat.getTime();
		// criar um utilizador e adicionar o mesmo a BD para pasar o id para o documento
		Utilizador u = new Utilizador(1,"Nuno");
		u.addUser(dba);
		// criar um documento 
		Documento d1 = new Documento(1,"ola","adeus", new Timestamp(datelong), 1);
		d1.addDoc(dba);
		// criar um documento com o mesmo id
		Documento d2 = new Documento(1,"ola","adeus", new Timestamp(datelong), 1);
		d2.addDoc(dba);
	}
	
	
	
	@Test (expected = NullPointerException.class)
	public void testUpdateDocNullTitle() throws Exception, ClassNotFoundException, SQLException{
		// verificar se e lancada uma excepcao quando o titulo do documento e null
		
		// Inicializar a BD H2 do sistema

		DBAccess dba = new DBAccess("org.h2.Driver", "jdbc:h2:mem:", "root", "password");
		dba.initialize();
		// criar um utilizador e adicionar o mesmo a BD para pasar o id para o documento
		Utilizador u = new Utilizador(1,"Nuno");
		u.addUser(dba);
		// obter uma data para criacao
		Date dat= new Date(System.currentTimeMillis());
		long datelong= dat.getTime();
		// criar um documento e adiciona o mesmo a BD
		Documento d = new Documento(1,"ola","adeus", new Timestamp(datelong), 1);
		d.addDoc(dba);
		// atualizar o titulo do documento com null
		d.updateDocTitle(dba,null,1,new Timestamp(datelong));
	}
	
	@Test (expected = NullPointerException.class)
	public void testUpdateDocNullBody() throws Exception, ClassNotFoundException, SQLException{
		DBAccess dba = new DBAccess("org.h2.Driver", "jdbc:h2:mem:", "root", "password");
		dba.initialize();
		Utilizador u = new Utilizador(1,"Nuno");
		u.addUser(dba);

		// obter uma data para criacao
		Date dat= new Date(System.currentTimeMillis());
		long datelong= dat.getTime();
		// criar um documento e adiciona o mesmo a BD
		Documento d = new Documento(1,"ola","adeus", new Timestamp(datelong), 1);
		d.addDoc(dba);
		// atualizar o corpo do documento com null
		d.updateDocBody(dba,null,1,new Timestamp(datelong));
	}
	@Test (expected = Exception.class)
	public void testUpdateDocNullId_user() throws Exception, ClassNotFoundException, SQLException{
		DBAccess dba = new DBAccess("org.h2.Driver", "jdbc:h2:mem:", "root", "password");
		dba.initialize();
		Utilizador u = new Utilizador(1,"Nuno");
		u.addUser(dba);
		// obter uma data para criacao
		Date dat= new Date(System.currentTimeMillis());
		long datelong= dat.getTime();
		// criar um documento e adiciona o mesmo a BD
		Documento d = new Documento(1,"ola","adeus", new Timestamp(datelong), 1);
		d.addDoc(dba);
		// atualizar um utilizador associado ao documento invalido
		d.updateDocId_user(dba,-1,1,new Timestamp(datelong));
	}
}

