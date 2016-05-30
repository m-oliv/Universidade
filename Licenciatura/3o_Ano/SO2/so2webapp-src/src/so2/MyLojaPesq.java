package so2.web;

import java.io.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import so2.ViagensClient;
import so2.Replicacao;

public class MyLojaPesq extends HttpServlet {
	
    String campo1;
    ViagensClient backup;
    Replicacao r;
    
    public void init () {

    	String host = "localhost";
    	String port = "9050";
    	r = new Replicacao(); // instancia da classe Replicacao que retorna a informacao solicitada
    	r.checkOperacionais(); // activa os servers do backend
    	backup = new ViagensClient(host,port); // instancia de um cliente RMI que permite comunicar com o server de backup
    	backup.assocObjRem();
    	
		campo1= getInitParameter("param1");
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
        
        // HTML para o botao que permite regressar ao menu da loja
        
        String buttonN = "Loja";
        String onclick = "ONCLICK=\"window.location.href='loja'\"";
        out.println("<form> <input type=button value="+buttonN+" " +onclick+"></form> </center>");
      
        out.println("<center>");
        out.println("<p style=\"font-family: 'Bookman Old Style', serif; color:#0000CC;font-size:30px;\"><i>Pesquisa de Voos Por Destino</i></p>");
        out.println("</center>");
        
        // HTML para o campo que ira receber a informacao relativa ao destino do voo
        
        out.println("<b><i>Destino pretendido:</i> </b>");
        out.println("<p>");

        out.println("<form action=\"loja-pesquisa\" method=POST>");
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
        
        // HTML para os botoes que permitem regressar ao menu da loja e efectuar uma nova pesquisa
        
        String bRP = "Pesquisar";
        String bRL = "Loja";
        String onclickbRL = "ONCLICK=\"window.location.href='loja'\"";
        String onclickbRP = "ONCLICK=\"window.location.href='loja-pesquisa'\"";
        out.println("<form> <input type=button value="+bRL+" " +onclickbRL+">");
        out.println("<input type=button value="+bRP+" " +onclickbRP+"></form> </center>");
    	
        out.println("<center>");
        out.println("<p style=\"font-family: 'Bookman Old Style', serif; color:#0000CC;font-size:30px;\"><i>Pesquisa de Voos Por Destino</i></p>");
        out.println("</center>"); 
        
        Vector<String> resultado = new Vector<String>(); // vector temporario para armazenar o resultado final da pesquisa
    	Vector<String> resultadoRep = r.fazPedidoPesquisa(request.getParameter("campo1")); // vector temporario para armazenar o resultado da pesquisa que possui a informacao coerente
    	Vector<String> resultadoB = backup.pesquisar(request.getParameter("campo1")); // vector temporario para armazenar o resultado da pesquisa realizada pelo server de backup
    	
    	if(resultadoRep.size() == 0){ // caso existam resultados coerentes
    		resultado = resultadoB;
    	}
    	else{// caso contrario, utiliza os resultados do backup
    		resultado = resultadoRep;
    	}
    	
    	// imprime os resultados da pesquisa, acompanhados pelos botoes que permitem consultar a lista de passageiros e comprar lugares nos voos
    	out.println("<p><b><i>Resultados da Pesquisa:</b></i></p>");
    	
    	if(resultado.size()!=0){
    		for(int i = 0;i<resultado.size();i++){
    			out.println("<p>"+resultado.get(i)+"</p>");
    			String onclickComp = "ONCLICK=\"window.location.href='loja-compra'\"";
    			String onclickCons = "ONCLICK=\"window.location.href='loja-listapass'\"";
    			out.println("<form> <input type=button value=\"Comprar Lugares\""+onclickComp+"></form> </center>");
    			out.println("<form> <input type=button value=\"Consultar Lista de Passageiros\""+onclickCons+"></form> </center>");
    		}
    	}
    	else{
    		out.println("Nao foram encontrados voos para o destino pretendido.");
    	}
    }
}
