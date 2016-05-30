import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Utilizador {
	private int id;
	private String nome;
	
	public Utilizador(){
		// para o caso em que so se pretende fazer listagens
	}
	
	public Utilizador(int i, String n){
		// cada utilizador tem um ID e um nome (o nome nao pode ser null)
		if(n.equals(null)){
			throw new NullPointerException();
		}
		this.id = i;
		this.nome = n;
	}
	
	public String getName(){
		// retornar o nome do utilizador
		return nome;
	}
	
	public int getID(){
		// retornar o ID do utilizador
		return id;
	}
	
	public void addUser(DBAccess dbaccess) throws SQLException{
		// adicionar um novo utilizador a base de dados
		
		if(nome.equals(null)){
			// se o nome for null, lanca uma excepcao
			throw new NullPointerException(); 
		}
		else{
			// caso contrario, adicionar o utilizador a BD
			String sqlQuery = "INSERT INTO utilizadores VALUES( " + id + ", '" + nome + "')";
			dbaccess.runQuery(sqlQuery);
		}
	}
	
	public void updateUser(DBAccess dba, String n, int i) throws SQLException{
		
		// actualizar um utilizador na base de dados (apenas o nome)
		
			if(n.equals(null)){
				throw new NullPointerException(); // se o nome for null, lanca excepcao
			}
			else{
				// caso contrario, faz update do nome (para o utilizador cujo ID e fornecido)
				String sqlQuery = "UPDATE utilizadores SET nome = '" + n + "' WHERE id = "+ i;
				dba.runQuery(sqlQuery);
			}

	}

	public void deleteUser(DBAccess dba, int i) throws SQLException {

		//remove da base de dados o utilizador que tem o ID fornecido
		String sqlQuery = "DELETE FROM utilizadores WHERE id = "+ i;
		dba.runQuery(sqlQuery);
	}

	public String toString(){
		// retorna uma string com a informacao do utilizador
		return "ID: "+id+"; Nome: "+nome+";";
	}
	
	public String listOneUser(DBAccess dba, int id) throws SQLException{
		// listar um utilizador
		
		// obter uma ligacao a BD
		Connection conn = dba.getConnection();
		Statement stmt = conn.createStatement();
			
		// obter da BD a informacao do utilizador pretendido
		String sqlQuery = "select id,nome from utilizadores where id ="+id;
		ResultSet rs = stmt.executeQuery(sqlQuery);

		int x = 0;
		String y = "";
		while(rs.next()){
			 x = rs.getInt("id");
			 y = rs.getString("nome");
		}
	
		String u_list = "ID: "+x+"; Nome: "+y+";";
		
		return u_list;
	}
	
	public ArrayList<Utilizador> listSeveralUsers(DBAccess dba) throws SQLException{
		// listar varios utilizadores
		
		// variaveis temporarias
		int id_u = 0;
		String nome_u = "";
		
		// criar uma arraylist para guardar os utilizadores obtidos da BD
		ArrayList<Utilizador> a_users_db = new ArrayList<Utilizador>();
				
		// obter uma ligacao a BD
		Connection conn = dba.getConnection();
		Statement stmt = conn.createStatement();
		
		// obter a informacao dos utilizadores a partir da BD
		String sqlQuery = "select id,nome from utilizadores";
		ResultSet rs = stmt.executeQuery(sqlQuery);
		
		while(rs.next()){
			// para cada utilizador, obter o ID e o nome
			 id_u = rs.getInt("id");
			 nome_u = rs.getString("nome");
			 // criar um novo utilizador com a informacao obtida
			 Utilizador u = new Utilizador(id_u,nome_u);
			 // adicionar o utilizador a arraylist pretendida
			 a_users_db.add(u);
			 // reset as variaveis temporarias
			 id_u = 0;
			 nome_u = "";
		}
		return a_users_db;

	}
	
}
