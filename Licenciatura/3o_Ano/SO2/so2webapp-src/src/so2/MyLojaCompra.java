package so2.web;

import java.io.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import so2.ViagensClient;

public class MyLojaCompra extends HttpServlet {
	
    String campo1;
    String campo2;
    String campo3;

    ViagensClient backup;
    
    public void init () {
    	String host = "localhost";
    	String port = "9050";
    	
    	backup = new ViagensClient(host,port); // instancia um cliente RMI que ira comunicar com o server de backup
    	backup.assocObjRem();
    	
		campo1= getInitParameter("param1");
		campo2= getInitParameter("param2");
		campo3= getInitParameter("param3");
		
    }



    public void doGet(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException{

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
       
        String buttonN = "Loja";
        String onclick = "ONCLICK=\"window.location.href='loja'\"";
        out.println("<form> <input type=button value="+buttonN+" " +onclick+"></form> </center>");
      
        out.println("<center>");
        out.println("<p style=\"font-family: 'Bookman Old Style', serif; color:#0000CC;font-size:30px;\"><i>Compra de Lugares num Voo </i></p>");
        out.println("</center>");
        

        // HTML para o campos campos que recebem a informacao necessaria para a compra
        
        out.println("<b><i>Insira o ID do Voo pretendido: </i></b>");
        out.println("<form action=\"loja-compra\" method=POST>");
        out.println("<input type=text length=20 name=campo1><br>");
        
        out.println("<b><i>Insira o numero de lugares a adquirir: </i></b>");
        out.println("<input type=text length=20 name=campo2><br>");
        
       
        out.println("<b><i>Insira os nomes dos passageiros: </i></b>");
        out.println("<i>(Os nomes inseridos deverao ser separados por um ; e devera existir um ; a seguir ao ultimo nome.)</i>");
        out.println("<input type=text length=20 name=campo3><br>");
  
        out.println("<input type=submit value=\"enviar dados\">");
        out.println("</form>");
        
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
        
        // HTML para o botao que permite regressar ao menu da loja
        
        String bRL = "Loja";
        String onclickbRL = "ONCLICK=\"window.location.href='loja'\"";
        String onclickbRP = "ONCLICK=\"window.location.href='loja-compra'\"";
        out.println("<form> <input type=button value="+bRL+" " +onclickbRL+">");
        out.println("<input type=button value="+"Comprar Lugares"+" " +onclickbRP+"></form> </center>");
    	
        out.println("<center>");
        out.println("<p style=\"font-family: 'Bookman Old Style', serif; color:#0000CC;font-size:30px;\"><i>Confirmacao da Compra de Lugares num Voo</i></p>");
        out.println("</center>");
        
        int id_Voo = Integer.parseInt(request.getParameter("campo1")); // obtem o ID do voo
        int nLug = Integer.parseInt(request.getParameter("campo2")); // obtem o numero de lugares da pagina
       
        
        String confirma =backup.compra(id_Voo,request.getParameter("campo3"),nLug); // efectua a compra
    	
    	out.println("<p><b><i>"+confirma+"</b></i></p>"); // imprime a confirmacao

    }
}
