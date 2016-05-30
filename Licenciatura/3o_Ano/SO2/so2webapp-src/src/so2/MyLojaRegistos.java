package so2.web;

import java.io.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import so2.ViagensClient;


public class MyLojaRegistos extends HttpServlet {
	
    String campo1;

    ViagensClient backup;
    
    public void init () {
    	String host = "localhost";
    	String port = "9050";
 
    	backup = new ViagensClient(host,port); // instancia do client rmi que serve comunica com o server backup
    	backup.assocObjRem();
		campo1= getInitParameter("campo1");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException{

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head>");
        
        // HTML para cabecalho da pagina
        
        String title = "Viagens Aereas do Alentejo";
        out.println("<title>" + title + "</title>");
        out.println("</head>");
        out.println("<body bgcolor=#85C2FF>");

        out.println("<center><b><h1>" + title + "</h1></b></center>");
        
        out.println("<center><i>Sistema de Venda de Viagens Aereas</i></center>");

        out.println("<hr>");
        
        // botao para regressar ao menu da loja
        
        String buttonN = "Loja";
        String onclick = "ONCLICK=\"window.location.href='loja'\"";
        out.println("<form> <input type=button value="+buttonN+" " +onclick+"></form> </center>");
      
        out.println("<center>");
        out.println("<p style=\"font-family: 'Bookman Old Style', serif; color:#0000CC;font-size:30px;\"><i>Consultar Registo de Vendas</i></p>");
        out.println("</center>");
        
        // HTML para o campo que ira receber o codigo da venda cujo registo se pretende consultar
        
        out.println("<b><i>Insira o Codigo do Registo Pretendido: </b></i>");
        out.println("<p>");

        out.println("<form action=\"loja-registos\" method=POST>");
        out.println("<input type=text length=20 name=campo1><br>");
        out.println("<input type=submit value=\"enviar dados\">");
        out.println("</form>");
        out.println("</p>");

        
        out.println("</body>");
        out.println("</html>");
    }
    
public void doPost(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException{
    	
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html>");
        out.println("<head>");

        // HTML para o cabecalho da pagina
        
        String title = "Viagens Aereas do Alentejo";
        out.println("<title>" + title + "</title>");
        out.println("</head>");
        out.println("<body bgcolor=#85C2FF>");

        out.println("<center><b><h1>" + title + "</h1></b></center>");
        
        out.println("<center><i>Sistema de Venda de Viagens Aereas</i></center>");

        out.println("<hr>");
        
        // HTML para o botao que permite voltar ao menu da loja

        String bRL = "Loja";
        String onclickbRL = "ONCLICK=\"window.location.href='loja'\"";
        String onclickbRP = "ONCLICK=\"window.location.href='loja-registos'\"";
        out.println("<form> <input type=button value="+bRL+" " +onclickbRL+">");
        out.println("<input type=button value=\"Consultar Registos\""+" " +onclickbRP+"></form> </center>");
    	
        out.println("<center>");
        out.println("<p style=\"font-family: 'Bookman Old Style', serif; color:#0000CC;font-size:30px;\"><i>Resultados da Consulta</i></p>");
        out.println("</center>");
        
        String resultados = ""; // temporario para armazenar a informacao obtida
      
        if(!(request.getParameter("campo1").equals(""))){ // se tiver sido inserida informacao no parametro, consulta os registos
        	int id_venda = Integer.parseInt(request.getParameter("campo1")); // obtem o ID da venda
        	resultados = backup.consultaRegistos(id_venda); // consulta os registos
        	
        	if(!resultados.equals("")){ // se existirem resultados, faz o print
        		out.println("<p>"+resultados+"</p>");
        	}
        	else{
        		out.println("<p> Nao foram encontrados resultados. O registo indicado nao existe. </p>"); // caso contrario, retorna uma mensagem
        	}
        }
        else{ // caso o ID seja invalido, retorna uma mensagem de erro
        	out.println("<p><b>ID invalido.</b></p>");
        	out.println("<br>");
        }
	}
}
