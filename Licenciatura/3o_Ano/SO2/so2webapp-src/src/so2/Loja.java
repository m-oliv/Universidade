package so2;

import java.rmi.*;
import java.util.*;

public interface Loja extends Remote{

	// Interface remota.

	public Vector<String> pesquisa(String destino) throws RemoteException;
	public Vector<String> consultaLP(int id_v) throws RemoteException;
	public String comprar(int id_v, String [] nomes, int nLug) throws RemoteException;
	public String consultaRegistos(int id_venda) throws RemoteException;
	
}
