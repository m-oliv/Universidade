package so2.web;

import java.io.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;


public class MyLoja extends HttpServlet {
	
    

    public void init () {
    
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
        
        // HTML para voltar a homepage
       
        String buttonN = "Home";
        String onclick = "ONCLICK=\"window.location.href='index.html'\"";
        out.println("<form> <input type=button value="+buttonN+" " +onclick+"></form> </center>");
        
        out.println("<center>");
        
        out.println("<p style=\"font-family: 'Bookman Old Style', serif; color:#0000CC;font-size:30px;\"><i>Escolha a operacao: </i></p>");
        
        // HTML para os botoes do menu
       
        String b1 = "Pesquisar Voos";
        String b2 = "Comprar Lugares";
        String b3 = "Consultar Lista Passageiros";
        String b4 = "Consultar Registos de Vendas";
        out.println("</center>");
        
        String clickPesq = "ONCLICK=\"window.location.href='loja-pesquisa'\"";
        out.println("<center>");
        out.println("<p style=\"font-family: Arial, Helvetica, sans-serif; font-size:20px;\">");
        out.println("<i>"+b1+"</i>"+"<form> <input type=button value="+"Go"+" " +clickPesq+"></form> </center>");
        out.println("</p>");
        out.println("</center>");
       
        String clickCompra = "ONCLICK=\"window.location.href='loja-compra'\"";
        out.println("<center>");
        out.println("<p style=\"font-family: Arial, Helvetica, sans-serif; font-size:20px;\">");
        out.println("<i>"+b2+"</i>"+"<form> <input type=button value="+"Go"+" " +clickCompra+"></form> </center>");
        out.println("</p>");
        out.println("</center>");
        
       
        String clickLP = "ONCLICK=\"window.location.href='loja-listapass'\"";
        out.println("<center>");
        out.println("<p style=\"font-family: Arial, Helvetica, sans-serif; font-size:20px;\">");
        out.println("<i>"+b3+"</i>"+"<form> <input type=button value="+"Go"+" " +clickLP+"></form> </center>");
        out.println("</p>");
        out.println("</center>");
        
        
        
        String clickR = "ONCLICK=\"window.location.href='loja-registos'\"";
        out.println("<center>");
        out.println("<p style=\"font-family: Arial, Helvetica, sans-serif; font-size:20px;\">");
        out.println("<i>"+b4+"</i>"+"<form> <input type=button value="+"Go"+" " +clickR+"></form> </center>");
        out.println("</p>");
        out.println("</center>");
        
        out.println("</body>");
        out.println("</html>");
        
    }
}



