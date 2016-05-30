package so2.web;

import java.io.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import so2.Replicacao;
import so2.ViagensClient;

public class MyLojaPLP extends HttpServlet {
	
	 	String campo1;
	    ViagensClient backup;
	    Replicacao r;
	    
	    public void init () {

	    	String host = "localhost";
	    	String port = "9050";
	    	
	    	r = new Replicacao(); // instancia a classe Replicacao que garante que a informacao retornada e coerente
	    	r.checkOperacionais(); // activa os servidores do backend
	    	
	    	backup = new ViagensClient(host,port); // instancia um cliente que comunica com o server RMI de backup
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
        
        // HTML para o botao que permite voltar ao menu da loja
        
        String buttonN = "Loja";
        String onclick = "ONCLICK=\"window.location.href='loja'\"";
        out.println("<form> <input type=button value="+buttonN+" " +onclick+"></form> </center>");
      
        out.println("<center>");
        out.println("<p style=\"font-family: 'Bookman Old Style', serif; color:#0000CC;font-size:30px;\"><i>Consultar Lista de Passageiros</i></p>");
        out.println("</center>");
        
        // HTML que cria o campo para a insercao do ID do voo
        
        out.println("<b><i>Insira o ID Voo Cuja Lista de Passageiros Pretende Consultar: </i></b>");
        out.println("<p>");

        out.println("<form action=\"loja-listapass\" method=POST>");
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
        String onclickbRP = "ONCLICK=\"window.location.href='loja-listapass'\"";
        out.println("<form> <input type=button value="+bRL+" " +onclickbRL+">");
        out.println("<input type=button value=\"Consultar Lista Passageiros\""+" " +onclickbRP+"></form> </center>");
    	
        out.println("<center>");
        out.println("<p style=\"font-family: 'Bookman Old Style', serif; color:#0000CC;font-size:30px;\"><i>Resultados da Consulta</i></p>");
        out.println("</center>");
      
        
    	
        if(!(request.getParameter("campo1").equals(""))){ // caso sejam inseridos dados, o efectua a consulta
        	int id_Voo = Integer.parseInt(request.getParameter("campo1")); // obtem o int correspondente ao ID do voo
        	 
            Vector<String> resultado = new Vector<String>(); // temporario para os resultados finais da consulta
        	Vector<String> resultadoRep = r.fazPedidoConsultaLP(id_Voo); // temporario para os resultados da consulta retornados pela classe Replicacao
        	Vector<String> resultadoB = backup.consultaListaP(id_Voo); // temporario para os resultados da consulta retornados pelo server de backup
        	
        	if(resultadoRep.size() == 0){ // caso nao existam resultados fornecidos pela classe Replicacao (que retorna os resultados coerentes com todos os servers do backend)
        		resultado = resultadoB;
        	}
        	else{ // caso contrario, retorna os resultados fornecidos pelo server de backup que, em teoria, estara sempre actualizado
        		resultado = resultadoRep;
        	}
    	
        	out.println("<p><b><i>Lista de Passageiros do Voo "+id_Voo+":</b></i></p>");
    	
    		if(resultado.size()!=0){ // escreve os resultados da pesquisa caso estes existam
    			for(int i = 0;i<resultado.size();i++){
    				out.println("<p>"+resultado.get(i)+"</p>");
    			}
    		}
    		else{ // caso contrario, indica que nenhuma lista foi encontrada
    			out.println("Nenhuma lista de passageiros encontrada para o voo indicado.");
    		}
        }
        else{ // caso o ID seja invalido, permite ao utilizador pesquisar voos
        	out.println("<p><b>ID invalido.</b></p>");
        	out.println("<br>");
        	
        	out.println("<p><b>Opcoes Disponiveis:</b></p>");
        	out.println("<p><i>Pesquisar Voos</i></b>");
        	
        	
        	String onclickbP = "ONCLICK=\"window.location.href='loja-pesquisa'\"";
        	out.println("<form><input type=button value=\"Pesquisar Voos\""+" " +onclickbP+"></form>");
        }
    }
}
