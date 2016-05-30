import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {


	public static void main(String[]args) throws Exception{

		//Valor que vai incrementando que corresponde ao id do ultimo user
		int userCount=1;
		//Valor que vai incrementando que corresponde ao id do ultimo documento
		int documentCount=1;
		//Criacao da Base de dados
		DBAccess sourceDatabase;
		sourceDatabase=new DBAccess("org.h2.Driver", "jdbc:h2:mem:", "root", "password");
		sourceDatabase.initialize();

		//criacao do interface com o utilizador por texti
		Scanner user_input = new Scanner( System.in );
		//flow principal de execucao.
		mainMenuExecutionFlow(user_input, sourceDatabase, userCount, documentCount);

		//fecha o input.
		user_input.close();
	}

	/*Os metodos printXMenu sao apenas print dos menus adequados a situacao actual do utilizador.*/
	private static void printMainMenu(){

		System.out.println("------------Document management system------------");
		System.out.println("---Select an action.");
		System.out.println("---1 - Manage users");
		System.out.println("---2 - Manage documents");
		System.out.println("---3 - Search database");
		System.out.println("---4 - Exit");
		System.out.println("--------------------------------------------------");
	}
	private static void printUserMenu(){

		System.out.println("------------Document management system------------");
		System.out.println("-------------------Manage Users-------------------");
		System.out.println("---Select an action.");
		System.out.println("---1 - Back to main menu");
		System.out.println("---2 - Add user");
		System.out.println("---3 - Remove user");
		System.out.println("---4 - Update user");
		System.out.println("---5 - List users");
		System.out.println("--------------------------------------------------");
	}
	private static void printDocumentMenu(){

		System.out.println("------------Document management system------------");
		System.out.println("-----------------Manage Documents-----------------");
		System.out.println("---Select an action.");
		System.out.println("---1 - Back to main menu");
		System.out.println("---2 - Add document");
		System.out.println("---3 - Remove document");
		System.out.println("---4 - Update document");
		System.out.println("--------------------------------------------------");
	}
	private static void printSearchMenu(){

		System.out.println("------------Document management system------------");
		System.out.println("-----------------Search Documents-----------------");
		System.out.println("---Select an action.");
		System.out.println("---1 - Back to main menu");
		System.out.println("---2 - Search by title");
		System.out.println("---3 - Search by body");
		System.out.println("---4 - Search by title or body");
		System.out.println("---5 - Search by creation date");
		System.out.println("---6 - Search by document ID");
		System.out.println("--------------------------------------------------");
	}
	private static void printUpdateDocMenu(){

		System.out.println("------------Document management system------------");
		System.out.println("-----------------Update Documents-----------------");
		System.out.println("---Select an action.");
		System.out.println("---1 - Back to Documents menu");
		System.out.println("---2 - Update title");
		System.out.println("---3 - Update body");
		System.out.println("---4 - Update user ID");
		System.out.println("--------------------------------------------------");
	}

	//adiciona um utilizador a base de dados target.
	private static void addUsersToDatabase(DBAccess target, int id, Scanner input) throws SQLException{
		System.out.println("-----------------Manage Users:Add-----------------");
		System.out.println("Input a name.");
		//le o input do utilizador
		String reply=input.next();
		//cria o utilizador a inserir
		Utilizador newUser = new Utilizador(id, reply);
		//acrescenta a base de dados
		newUser.addUser(target);

		System.out.println("--------------User added sucessfully.-------------");
		System.out.println("--------------------------------------------------");
	}
	//remove um utilizador da base de dados target.
	private static void removeUsersFromDatabase(DBAccess target, Scanner input) throws SQLException{
		System.out.println("---------------Manage Users:Remove----------------");
		System.out.println("Which user ID do you want to remove?");

		int convertedOption=-1;

		while(convertedOption==-1)
			try{
				//o input e to tipo string, mas id e inteiro, tem de ser convertido.
				String option = input.next();
				convertedOption=Integer.parseInt(option);
				//cria um objecto dummy para remover da base de dados o utilizador pretendido
				Utilizador dummy=new Utilizador(0,"nome");
				dummy.deleteUser(target, convertedOption);
			}
			catch(NumberFormatException nfe){
				//o user nao colocou numeros como input.
				System.out.println("Please, input a number.");
			}
			catch(SQLException nfe){
				//houve falha com a base de dados.
				System.out.println("Conflict with the database. Hint: ID may not exist");
				convertedOption=-1;
			}

		System.out.println("-------------User removed sucessfully.------------");
		System.out.println("--------------------------------------------------");
	}


	//actualiza um utilizador na base de dados target.
	private static void updateUsersOnDatabase(DBAccess target, Scanner input) throws SQLException{
		System.out.println("---------------Manage Users:Remove----------------");
		System.out.println("Which user ID do you want to update?");

		int convertedOption=-1;

		while(convertedOption==-1)
			try{
				//o input e to tipo string, mas id e inteiro, tem de ser convertido.
				String option = input.next();
				convertedOption=Integer.parseInt(option);
				//cria um objecto dummy para remover da base de dados o utilizador pretendido
				Utilizador dummy=new Utilizador(0,"nome");
				System.out.println("What is the new name for user "+convertedOption+"?");
				String newName = input.next();
				dummy.updateUser(target, newName, convertedOption);
			}
		catch(NumberFormatException nfe){
			//o user nao colocou numeros como input.
			System.out.println("Please, input a number.");
		}
		catch(SQLException nfe){
			//houve falha com a base de dados.
			System.out.println("Conflict with the database.");
			convertedOption=-1;
		}

		System.out.println("-------------User updated sucessfully.------------");
		System.out.println("--------------------------------------------------");
	}

	//lista os utilizadores da base de dados target.
	private static void listUsersOnDatabase(DBAccess target) throws SQLException{

		Utilizador dummy=new Utilizador(0,"nome");
		ArrayList<Utilizador> result=dummy.listSeveralUsers(target);
		for(int i=0; i<result.size(); i++){
			System.out.println(result.get(i).toString());
		}
	}
	//adiciona um documento a base de dados target.
	private static void addDocToDatabase(DBAccess target, int docCount,
			Scanner input) throws Exception {
		System.out.println("---------------Manage Documents:Add---------------");
		int convertedOption=-1;
		String fpath;
		while(convertedOption==-1)
			try{
				System.out.println("Input a file path.");
				//le o input do utilizador
				fpath=input.next();

				//o input e to tipo string, mas id e inteiro, tem de ser convertido.
				System.out.println("Input a user to associate with this document. (-1 to cancel)");

				//le o input do utilizador
				String userIDreply=input.next();
				int userID=Integer.parseInt(userIDreply);
				if(userID==-1){
					System.out.println("---------------Operation cancelled.---------------");
					System.out.println("--------------------------------------------------");
					return;
				}
				//tenta criar o doc a inserir
				Documento newDoc = new Documento(docCount, fpath, new Timestamp(System.currentTimeMillis()),userID);
				//acrescenta a base de dados
				newDoc.addDoc(target);
				convertedOption=userID;
			}
			catch(NumberFormatException nfe){
				//o user nao colocou numeros como input.
				System.out.println("Please, input a number.");
			}
			catch(SQLException nfe){
				//houve falha com a base de dados.
				System.out.println("Database error. Hint: User may not exist.");
				convertedOption=-1;
			}
			catch(FileNotFoundException fnfe){
				//houve falha com a base de dados.
				System.out.println("File does not exist.");
				convertedOption=-1;
			}
			catch(DocumentNotFormattedException dnfe){
				//houve falha com a base de dados.
				System.out.println(dnfe.getMessage());
				convertedOption=-1;
			}
			catch(Exception e){
				//houve falha com a base de dados.
				System.out.println(e.getMessage());
				convertedOption=-1;
			}
		

		System.out.println("------------Document added sucessfully.-----------");
		System.out.println("--------------------------------------------------");
		
	}

	//remove um documento da base de dados target.
	private static void removeDocFromDatabase(DBAccess target, Scanner input) throws Exception {
		
		System.out.println("-------------Manage Documents: Remove-------------");
		int convertedOption=-1;
		while(convertedOption==-1)
			try{
				//o input e to tipo string, mas id e inteiro, tem de ser convertido.
				System.out.println("Input the document's ID to remove. (-1 to cancel)");
				//le o input do utilizador
				String userIDreply=input.next();
				int userID=Integer.parseInt(userIDreply);
				if(userID==-1){
					System.out.println("---------------Operation cancelled.---------------");
					System.out.println("--------------------------------------------------");
					return;
				}
				//tenta criar o doc a inserir
				Documento dummy = new Documento(1, "d", "d2" ,new Timestamp(System.currentTimeMillis()),userID);
				//acrescenta a base de dados
				dummy.deleteDoc(target, userID);
				convertedOption=userID;
			}
			catch(NumberFormatException nfe){
				//o user nao colocou numeros como input.
				System.out.println("Please, input a number.");
			}
			catch(SQLException nfe){
				//houve falha com a base de dados.
				System.out.println("Database error. Hint: User may not exist.");
				convertedOption=-1;
			}
			catch(Exception e){
				//houve falha com a base de dados.
				System.out.println(e.getMessage());
				convertedOption=-1;
			}
		

		System.out.println("-----------Document removed sucessfully.----------");
		System.out.println("--------------------------------------------------");
		
	}

	//actualiza um documento na base de dados target. targetField indica o que actualizar
	//0: titulo 
	//2: body
	//3: user owner
	private static void updateDocOnDatabase(DBAccess target, int targetField, Scanner input) throws Exception {
		
		System.out.println("-------------Manage Documents: Update-------------");
		int convertedOption=-1;
		while(convertedOption==-1)
			try{
				//o input e to tipo string, mas id e inteiro, tem de ser convertido.
				System.out.println("Input the document's ID to update. (-1 to cancel)");
				//le o input do utilizador
				String docIDReply=input.next();
				
				int docID=Integer.parseInt(docIDReply);
				if(docID==-1){
					System.out.println("---------------Operation cancelled.---------------");
					System.out.println("--------------------------------------------------");
					return;
				}
				//tenta criar o doc a inserir
				Documento dummy = new Documento(1, "d", "d2" ,new Timestamp(System.currentTimeMillis()),docID);

				System.out.println("Input the intended new field. (-1 to cancel)");
				String argument=input.next();

				int usrID;
				if(targetField==3){
					usrID=Integer.parseInt(argument);
				}
				else
					usrID=0; //irrelevante este valor. targetField indica qual o update a fazer.	

				if(usrID==-1){
					System.out.println("---------------Operation cancelled.---------------");
					System.out.println("--------------------------------------------------");
					return;
				}
				switch(targetField){
					case 0: 
						dummy.updateDocTitle(target, argument, docID, new Timestamp(System.currentTimeMillis()) );
						break;
					case 2: 
						dummy.updateDocBody(target, argument, docID, new Timestamp(System.currentTimeMillis()) );
						break;
					case 3: 
						dummy.updateDocId_user(target, usrID, docID, new Timestamp(System.currentTimeMillis()) );
						break;
					default:
						break;
				}

				convertedOption=docID;
			}
			catch(NumberFormatException nfe){
				//o user nao colocou numeros como input.
				System.out.println("Please, input a number.");
			}
			catch(SQLException nfe){
				//houve falha com a base de dados.
				System.out.println("Database error. Hint: User may not exist.");
				convertedOption=-1;
			}
			catch(Exception e){
				//houve falha com a base de dados.
				System.out.println(e.getMessage());
				convertedOption=-1;
			}
		

		System.out.println("-----------Document updated sucessfully.----------");
		System.out.println("--------------------------------------------------");
		
	}
	

	//pesquisa na base de dados. O que pesquisar depende de mode:
	//1: titulo 
	//2: body
	//3: titul ou body
	//4: date 
	//5: user id
	private static void searchByOnDatabase(DBAccess target, int mode, Scanner input) throws Exception {
		
		System.out.println("----------------Search Documents: ----------------");

		String argument;
		DocSearch database=new DocSearch(target);
		
		switch(mode){
			case 1:
				System.out.println("Insert the text to search.");
				argument=input.next();
				System.out.println(database.searchTitles(argument).toString());
				break;
			case 2:
				System.out.println("Insert the text to search.");
				argument=input.next();
				System.out.println(database.searchBodies(argument).toString());
				break;
			case 3:
				System.out.println("Insert the text to search.");
				argument=input.next();
				System.out.println(database.searchGeneric(argument).toString());
				break;
			case 4:
				System.out.println("All documents must be later than (DD/MM/YYYY):");
				while(true)
					try{
						argument=input.next();
						if(argument.length()!=10){
							System.out.println("Wrong date format.");
							continue;
						}
						SimpleDateFormat date=new SimpleDateFormat("dd/MM/yyyy");
						long equivalent = date.parse(argument).getTime();
						
						System.out.println(database.searchByDate(new Timestamp(equivalent)).toString());
						break;
						}
					catch(ParseException pe){
						//o user nao colocou uma data bem formatada.
						System.out.println("Date format wrong. Try again.");
					}
				break;
			case 5:
				int convertedOption=-1;
				while(convertedOption==-1)
					try{
						System.out.println("Insert the document's ID.");
						argument=input.next();
						int argumentInt=Integer.parseInt(argument);
						convertedOption=1;
						System.out.println(database.searchGeneric(argumentInt).toString());
					}
					catch(NumberFormatException nfe){
						//o user nao colocou numeros como input.
						System.out.println("Please, input a number.");
					}
					catch(Exception e){
						System.out.println(e.getMessage());
					}
				break;
			default:
				break;
		}
		
	
		
	}
	
	
	/* Os flows controlam a execucao do programa ao longo de uma sessao. Comecando em mainMenuExecutionFlow,  
	 * os saltos dependem do input do utilizador. Em qualquer funcao, a opcao 1 permite retornar a anterior,
	 * com excepcao de main, em que 4 termina a execucao.
	 * */
	//Flow de execucao inicial: menu inicial
	//Aqui o utilizador decide o que quer fazer
	//users, documentos ou pesquisas
	private static void mainMenuExecutionFlow(Scanner input, 
			DBAccess sourceDB, 
			int userCount, 
			int documentCount) throws Exception{
		printMainMenu();
		int convertedOption=-1;

		while(convertedOption==-1)
			try{
				String option = input.next();
				convertedOption=Integer.parseInt(option);
				switch(convertedOption){
				case 1: userMenuExecutionFlow(input, sourceDB, userCount, documentCount);
				break;
				case 2: documentMenuExecutionFlow(input, sourceDB, userCount, documentCount);
				break;
				case 3: searchMenuExecutionFlow(input, sourceDB, userCount, documentCount);
				break;
				case 4: System.exit(0);
				break;
				default: convertedOption=-1;
				System.out.println("Invalid option. Insert a number between 1 and 4.");
				break;
				}
			}
		catch(NumberFormatException nfe){
			System.out.println("Please, input a number.");
		}

	}
	//Flow de execucao: menu utilizador
	//Aqui o utilizador decide o que quer fazer com utilizadores
	//adicionar, remover ou actualizar
	private static void userMenuExecutionFlow(Scanner input, 
			DBAccess sourceDB, 
			int userCount, 
			int documentCount) throws Exception{
		printUserMenu();
		int convertedOption=-1;

		while(convertedOption==-1)
			try{
				String option = input.next();
				convertedOption=Integer.parseInt(option);
				switch(convertedOption){
				case 1: mainMenuExecutionFlow(input, sourceDB, userCount, documentCount);
				break;
				case 2:
					addUsersToDatabase(sourceDB, userCount, input);
					userCount++;
					userMenuExecutionFlow(input, sourceDB, userCount, documentCount);
					break;
				case 3: 
					removeUsersFromDatabase(sourceDB, input);
					userMenuExecutionFlow(input, sourceDB, userCount, documentCount);
					break;
				case 4:
					updateUsersOnDatabase(sourceDB, input);
					userMenuExecutionFlow(input, sourceDB, userCount, documentCount);
					break;
				case 5:
					listUsersOnDatabase(sourceDB);
					userMenuExecutionFlow(input, sourceDB, userCount, documentCount);
					break;
				default: convertedOption=-1;
				System.out.println("Invalid option. Insert a number between 1 and 5.");
				break;
				}
			}
		catch(NumberFormatException nfe){
			System.out.println("Please, input a number.");
		}
	}
	//Flow de execucao: menu documento
	//Aqui o utilizador decide o que quer fazer com documentos
	//adicionar, remover ou actualizar
	private static void documentMenuExecutionFlow(Scanner input, 
			DBAccess sourceDB, 
			int userCount, 
			int documentCount) throws Exception{

		printDocumentMenu();
		int convertedOption=-1;

		while(convertedOption==-1)

			try{
				String option = input.next();
				convertedOption=Integer.parseInt(option);
				switch(convertedOption){
				case 1: mainMenuExecutionFlow(input, sourceDB, userCount, documentCount);
				break;
				case 2:
					addDocToDatabase(sourceDB, documentCount, input);
					documentCount++;
					documentMenuExecutionFlow(input, sourceDB, userCount, documentCount);
					break;
				case 3: 
					removeDocFromDatabase(sourceDB, input);
					documentMenuExecutionFlow(input, sourceDB, userCount, documentCount);
					break;
				case 4:
					updateDocMenuExecutionFlow(input, sourceDB, userCount, documentCount);
					break;
				default: convertedOption=-1;
				System.out.println("Invalid option. Insert a number between 1 and 4.");
				break;
				}
			}
		catch(NumberFormatException nfe){
			System.out.println("Please, input a number.");
		}
	}

	//Flow de execucao: menu actualizar
	//Aqui o utilizador decide o que quer actualizar no documento
	//titlulo, body, id do user responsavel
	private static void updateDocMenuExecutionFlow(Scanner input, 
			DBAccess sourceDB, 
			int userCount, 
			int documentCount) throws Exception{
		
		printUpdateDocMenu();
		int convertedOption=-1;

		while(convertedOption==-1)
			try{
				String option = input.next();
				convertedOption=Integer.parseInt(option);
				switch(convertedOption){
				case 1: documentMenuExecutionFlow(input, sourceDB, userCount, documentCount);
				break;
				case 2:
					updateDocOnDatabase(sourceDB, 0, input);
					updateDocMenuExecutionFlow(input, sourceDB, userCount, documentCount);
					break;
				case 3: 
					updateDocOnDatabase(sourceDB, 2, input);
					updateDocMenuExecutionFlow(input, sourceDB, userCount, documentCount);
					break;
				case 4:
					updateDocOnDatabase(sourceDB, 3, input);
					updateDocMenuExecutionFlow(input, sourceDB, userCount, documentCount);
					break;
				default: convertedOption=-1;
				System.out.println("Invalid option. Insert a number between 1 and 4.");
				break;
				}
			}
		catch(NumberFormatException nfe){
			System.out.println("Please, input a number.");
		}
	}
	
	//Flow de execucao: menu pesquisa
	//Aqui o utilizador decide o que quer pesquisar
	//titlulo, body, titulo ou body, data, id do user responsavel
	private static void searchMenuExecutionFlow(Scanner input, 
			DBAccess sourceDB, 
			int userCount, 
			int documentCount) throws Exception{
		printSearchMenu();
		int convertedOption=-1;

		while(convertedOption==-1)
			try{
				String option = input.next();
				convertedOption=Integer.parseInt(option);
				switch(convertedOption){
				case 1: mainMenuExecutionFlow(input, sourceDB, userCount, documentCount);
				break;
				case 2:
					searchByOnDatabase(sourceDB, 1, input);
					searchMenuExecutionFlow(input, sourceDB, userCount, documentCount);
					break;
				case 3:
					searchByOnDatabase(sourceDB, 2, input);
					searchMenuExecutionFlow(input, sourceDB, userCount, documentCount);
					break;
				case 4:
					searchByOnDatabase(sourceDB, 3, input);
					searchMenuExecutionFlow(input, sourceDB, userCount, documentCount);
					break;
				case 5:
					searchByOnDatabase(sourceDB, 4, input);
					searchMenuExecutionFlow(input, sourceDB, userCount, documentCount);
					break;
				case 6:
					searchByOnDatabase(sourceDB, 5, input);
					searchMenuExecutionFlow(input, sourceDB, userCount, documentCount);
					break;
				default: convertedOption=-1;
				System.out.println("Invalid option. Insert a number between 1 and 6.");
				break;
				}
			}
		catch(NumberFormatException nfe){
			System.out.println("Please, input a number.");
		}
	}
}

