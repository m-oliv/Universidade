package so2;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ViagensServer {

	public static void main(String [] args){
		//O servico de nomes sera local (semelhante ao utilizado na aula pratica 7 - RMI).
		
		int regPort = 1099; //porto default
		
		if(args.length!=1){ //Tem de existir sempre um argumento (porto relativo ao binder)
			System.out.println("Usage: java so2.rmi_trabalho.ViagensServer registryPort");
			System.exit(1);
		}
		
		try{
			regPort = Integer.parseInt(args[0]);
			
			//cria um objecto remoto.
			
			LojaImpl obj = new LojaImpl();
			
			//exporta objecto. Isto permitira que o objecto receba pedidos.
			
			Loja stub = (Loja) UnicastRemoteObject.exportObject(obj);
			System.out.println("Objecto Remoto criado.");
			
			//regista o objecto no servico de nomes
			Registry registry = LocateRegistry.getRegistry(regPort);
			
			
			//faz o bind
			registry.rebind("viagens", stub);
			
			System.out.println("Objecto RMI Bound no servico de nomes.");
			System.out.println("Servidor Pronto.");
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
