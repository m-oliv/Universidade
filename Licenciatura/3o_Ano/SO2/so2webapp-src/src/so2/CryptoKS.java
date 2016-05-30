package so2;

import java.io.*;
import java.util.*;
import javax.crypto.*;
import java.security.*;

/****** EXTRA ******/

public class CryptoKS{
	
	// Classe que gera o keystore e o grava num ficheiro.
	
	public static void main(String args[]) throws KeyStoreException,NoSuchAlgorithmException{
		byte b[] = new byte[1024];
		
		char [] pass;
		
		File temp = new File("temp");     // ficheiro temporario para fornecer o caminho  
        String cam2  = temp.getAbsolutePath();  // string com o caminho ate a pasta src
        int aux = cam2.length();            // Int com o tamanho da string do caminho
        String cam = cam2.substring(0, aux-4); // String com caminho ate a pasta so2webapp-src
		
		try{ // le a password do keystore a partir de um ficheiro
			FileInputStream fi = new FileInputStream(cam+"/KSpwd");
			int lidos = fi.read(b);
			fi.close();
			String pwd = new String(b);
			String details [] = pwd.split(" ");
			pass = details[0].toCharArray(); // obtem a password do keystore
		
		KeyStore ks = KeyStore.getInstance("JCEKS");// cria uma instancia de um KeyStore do tipo JCEKS (Java Cryptography Extension KeyStore)
		KeyGenerator kg = KeyGenerator.getInstance("AES"); // cria uma instancia de um gerador de chaves
		SecretKey sk = kg.generateKey(); // gera chave secreta
		KeyStore.SecretKeyEntry ske = new KeyStore.SecretKeyEntry(sk); // cria a entrada da chave secreta no KS
		
		// faz o load do keystore
		
		ks.load(null,pass);
		
		ks.setEntry("chaveSecreta", ske, new KeyStore.PasswordProtection(pass)); // guarda a chave no keystore
		
		// Escreve o keystore para um ficheiro
		
		FileOutputStream fos = null;
		
		
			fos = new FileOutputStream("KStore");
			ks.store(fos,pass);
			
			if(fos != null){
				fos.close();
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
