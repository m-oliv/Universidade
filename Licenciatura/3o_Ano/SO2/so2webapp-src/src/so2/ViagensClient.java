package so2;
import java.rmi.RMISecurityManager;
import java.util.Vector;

public class ViagensClient {
	
	private String regH;
	private String regP;
	private Loja obj;
	
	public ViagensClient(String regHost, String regPort){
		this.regH = regHost;
		this.regP = regPort;
	}
	
	public void assocObjRem(){
		try{ // objecto que fica associado ao proxy para o objecto remoto
			obj = (Loja) java.rmi.Naming.lookup("rmi://" + regH + ":" + regP + "/viagens");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public Vector<String> pesquisar(String destino){ // efectua a pesquisa invocando o metodo remoto
		Vector<String> results = new Vector<String>();
		try{
			results = obj.pesquisa(destino);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return results;
	}
	
	public String compra(int id_v, String nomes, int nLug){ // efectua a compra invocando o metodo remoto
		String [] n = nomes.split(";");
		String results = "";
		try{
			results = obj.comprar(id_v,n,nLug);
		}catch(Exception e){
			e.printStackTrace();
		}
		return results;
	}
	
	public Vector<String> consultaListaP(int id_v){ // efectua a consulta da lista de passageiros invocando o metodo remoto
		Vector<String> results = new Vector<String>();
		try{
			results = obj.consultaLP(id_v);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return results;
	}
	
	public String consultaRegistos(int id_venda){ // efectua a consulta dos registos invocando o metodo remoto
		String results = "";
		try{
			results = obj.consultaRegistos(id_venda);
		}catch(Exception e){
			e.printStackTrace();
		}
		return results;
	}
	
}
