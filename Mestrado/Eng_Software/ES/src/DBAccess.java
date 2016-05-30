import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBAccess {
	static String JDBC_DRIVER;  
	static String DB_URL;
	
	Connection conn = null;
	   
	public DBAccess(String jdbc_driver, String dbURL, String user, String password) throws ClassNotFoundException, SQLException{
		JDBC_DRIVER = jdbc_driver;
		DB_URL = dbURL;
		
	    Class.forName( JDBC_DRIVER );
	    // criar uma ligacao a base de dados
	    conn = DriverManager.getConnection(dbURL, user, password);
	}; 
	
	public Connection getConnection(){
		// obter a ligacao a base de dados
		return conn;
	}
	
	public void initialize() throws SQLException{		
		// inicializar a base de dados do sistema
		
		// criar a tabela na qual se vao armazenar as informacoes do utilizador
		String sqlQuery = "CREATE TABLE utilizadores (id int not null, nome varchar(255) not null, primary key(id))";
		runQuery(sqlQuery);
		
		// criar a tabela na qual se vao armazenar as informacoes dos documentos
		String sqlQuery2 = "CREATE TABLE documentos (id int not null, title varchar(255), "
				+ "body varchar(1000), d_criacao timestamp not null, d_alteracao timestamp not null, id_user int not null, "
				+ "primary key(id, id_user), foreign key(id_user) references utilizadores(id))";
		runQuery(sqlQuery2);
	}
	
	public void runQuery(String query) throws SQLException{
		// executar uma query
		Statement stmt = getConnection().createStatement();
		stmt.execute(query);
		
	}
	
}
