import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.junit.Test;

public class UserTest {

	@Test
	public void testNameInObj() {
		// verificar se o nome dado e o nome que esta no objeto Utilizador criado
		Utilizador u = new Utilizador(1,"Nuno");
		assertEquals(u.getName(),"Nuno");
	}
	
	@Test
	public void testIDInObj() {
		// verificar se o ID dado e o ID que esta no objeto Utilizador criado
		Utilizador u = new Utilizador(1,"Nuno");
		assertEquals(u.getID(),1);
	}
	
	@Test
	public void testAddUserName() throws ClassNotFoundException, SQLException{
		// Verificar se o nome de utilizador atribuido ao utilizador foi armazenado na BD

		// inicializar a BD H2 do sistema 
		DBAccess dba = new DBAccess("org.h2.Driver", "jdbc:h2:mem:", "root", "password");
		dba.initialize();
		
		// criar um utilizador e adicionar o mesmo a BD
		Utilizador u = new Utilizador(1,"Nuno");
		u.addUser(dba);

		// Obter uma ligacao a BD
		Connection conn = dba.getConnection();
		Statement stmt = conn.createStatement();
		
		// procurar na BD o nome do utilizador adicionado
		String sqlQuery = "select nome from utilizadores where ID ="+u.getID();
		ResultSet rs = stmt.executeQuery(sqlQuery);
		String x = "";
		while(rs.next()){
			 x = rs.getString("nome");
		}
		
		// verificar se o resultado obtido e o esperado
		assertEquals(x,"Nuno");
	}
	
	@Test
	public void testAddUserID() throws ClassNotFoundException, SQLException{
		// Verificar se o ID atribuido ao utilizador foi armazenado na BD
		
		// Inicializar a BD H2 do sistema
		DBAccess dba = new DBAccess("org.h2.Driver", "jdbc:h2:mem:", "root", "password");
		dba.initialize();
		
		// criar um utilizador e adicionar o mesmo a BD
		Utilizador u = new Utilizador(1,"Nuno");
		u.addUser(dba);
		
		// obter uma ligacao a BD
		Connection conn = dba.getConnection();
		Statement stmt = conn.createStatement();

		// procurar na BD o ID do utilizador adicionado
		String sqlQuery = "select id from utilizadores";
		ResultSet rs = stmt.executeQuery(sqlQuery);
		String x = "";
		while(rs.next()){
			 x = rs.getString("id");
		}

		// verificar se o resultado obtido e o esperado
		assertEquals(x,"1");
	}
	
	@Test
	public void testUpdateName() throws ClassNotFoundException, SQLException{
		// verificar se a atualizacao ao nome do utilizador foi bem sucedida
		
		// Inicializar a BD H2 do sistema
		DBAccess dba = new DBAccess("org.h2.Driver", "jdbc:h2:mem:", "root", "password");
		dba.initialize();
		
		// criar um utilizador e adicionar o mesmo a BD
		Utilizador u = new Utilizador(1,"Nuno");
		u.addUser(dba);
		
		// atualizar o nome de utilizador
		u.updateUser(dba,"Marlene",1);
		
		// obter uma ligacao a BD
		Connection conn = dba.getConnection();
		Statement stmt = conn.createStatement();
		
		// procurar na BD o nome de utilizador correspondente
		String sqlQuery = "select nome from utilizadores where ID ="+u.getID();
		ResultSet rs = stmt.executeQuery(sqlQuery);
		String x = "";
		while(rs.next()){
			 x = rs.getString("nome");
		}
		
		// verificar se o nome obtido e o esperado
		assertEquals(x,"Marlene");
	}

	@Test 
	public void testDeleteUser() throws ClassNotFoundException, SQLException{
		// verificar se um utilizador e corretamente eliminado da BD
		
		// Inicializar a BD H2 do sistema
		DBAccess dba = new DBAccess("org.h2.Driver", "jdbc:h2:mem:", "root", "password");
		dba.initialize();

		// criar e adicionar a BD um utilizador
		Utilizador u = new Utilizador(1,"Nuno");
		u.addUser(dba);
		
		// eliminar o utilizador da BD
		u.deleteUser(dba,1);
		
		// obter uma ligacao a BD
		Connection conn = dba.getConnection();
		Statement stmt = conn.createStatement();
		
		// Contar o numero de utilizadores existentes com o ID igual ao do utilizador eliminado
		String sqlQuery = "select count(nome) from utilizadores where id = 1;";
		ResultSet rs = stmt.executeQuery(sqlQuery);
		int x = 0;
		while(rs.next()){
			 x = rs.getInt(1);
		}
		
		// se o resultado da contagem for zero, o utilizador foi eliminado corretamente (os IDs sao unicos)
		assertTrue(x==0);
	}
	
	@Test (expected = SQLException.class)
	public void testInsertDuplicate() throws ClassNotFoundException, SQLException{
		// verificar se e lancada uma excepcao quando existem utilizadores duplicados
		
		// Inicializar a BD H2 do sistema
		DBAccess dba = new DBAccess("org.h2.Driver", "jdbc:h2:mem:", "root", "password");
		dba.initialize();
		
		// criar e adicionar a BD dois utilizadores com o mesmo ID e nomes diferentes
		Utilizador u = new Utilizador(1,"Nuno");
		u.addUser(dba);
		Utilizador u2 = new Utilizador(1,"Joao");
		u2.addUser(dba);
	}
	
	@Test (expected = NullPointerException.class)
	public void testAddUserNullName() throws SQLException, ClassNotFoundException{
		// verificar se e lancada uma excepcao quando o nome de utilizador e null
		
		// Inicializar a BD H2 do sistema
		DBAccess dba = new DBAccess("org.h2.Driver", "jdbc:h2:mem:", "root", "password");
		dba.initialize();
		
		// criar um novo utilizador com o nome com null e adicionar o mesmo a BD
		Utilizador u = new Utilizador(1,null);
		u.addUser(dba);
	}
	
	@Test (expected = NullPointerException.class)
	public void testUpdateUserNullName() throws ClassNotFoundException, SQLException{
		// verificar se e lancada uma excepcao quando
		// se atualiza o nome de utilizador com null
		
		// Inicializar a BD H2 do sistema
		DBAccess dba = new DBAccess("org.h2.Driver", "jdbc:h2:mem:", "root", "password");
		dba.initialize();
		// criar um novo utilizador e adiciona-lo a BD
		Utilizador u = new Utilizador(1,"Nuno");
		u.addUser(dba);
		// atualizar o nome de utilizador com null
		u.updateUser(dba,null,1);
	}
	
	@Test
	public void testListUser() throws ClassNotFoundException, SQLException{

		// verificar se e possivel listar um utilizador (nome e ID)
		
		// Inicializar a BD H2 do sistema
		DBAccess dba = new DBAccess("org.h2.Driver", "jdbc:h2:mem:", "root", "password");
		dba.initialize();
		
		// criar um utilizador e adiciona-lo a BD
		Utilizador u = new Utilizador(1,"Nuno");
		u.addUser(dba);
		
		// Utilizador dummy para listagem
		Utilizador list = new Utilizador();
		
		// obter o utilizador cujo ID e 1
		String x = list.listOneUser(dba, 1);

		// verificar se a informacao obtida e a pretendida
		assertEquals(u.toString(),x);
	}
	
	@Test
	public void testListSeveralUsers() throws ClassNotFoundException, SQLException{
		// verificar se e possivel listar varios utilizadores
		
		// Inicializar a BD H2 do sistema
		DBAccess dba = new DBAccess("org.h2.Driver", "jdbc:h2:mem:", "root", "password");
		dba.initialize();
		
		// criar varios utilizadores para o teste
		Utilizador u1 = new Utilizador(1,"Nuno");
		Utilizador u2 = new Utilizador(2,"Ana");
		Utilizador u3 = new Utilizador(3,"Maria");
		Utilizador u4 = new Utilizador(4,"Joao");
		
		// Criar uma arraylist com os utilizadores criados para o teste
		ArrayList<Utilizador> a_users = new ArrayList<Utilizador>();
		
		// adicionar os utilizadores criados a arraylist
		a_users.add(u1);
		a_users.add(u2);
		a_users.add(u3);
		a_users.add(u4);
		
		// adicionar os utilizadores a BD
		u1.addUser(dba);
		u2.addUser(dba);
		u3.addUser(dba);
		u4.addUser(dba);
		
		// Utilizador dummy para listagens
		Utilizador list = new Utilizador();
		
		ArrayList<Utilizador> a_users_db = list.listSeveralUsers(dba);
		
		// verificar se a informacao dos utilizadores obtidas e a esperada
		assertEquals(a_users.toString(),a_users_db.toString());
		
	}
}
