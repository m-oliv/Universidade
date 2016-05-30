package so2;

import java.io.*;
import java.util.*;
import java.security.*;
import javax.crypto.*;

/****** EXTRA ******/

public class Crypto{
	
	// classe que implementa os metodos necessarios para a encriptacao e desencriptacao de informacao
	
	public Crypto(){
		
	}
	
	public void cifrar(String inFile,SecretKey sk){ // metodo que efectua a encriptacao da informacao da venda
    	byte[] plaintext= new byte[1024];
    	byte[] ciphertext= new byte[1024];
    	int lidos;

    	try {
    		File fi = new File(inFile);
    		File fo = new File(inFile+"_enc");
    	    FileInputStream fis= new FileInputStream(fi);
    	    FileOutputStream fos= new FileOutputStream(fo);
    	    
    	    // le a informacao do ficheiro desencriptado
    	    
    	    lidos= fis.read(plaintext);
                Cipher cipher;
                cipher = Cipher.getInstance("AES"); // obtem uma instancia da cifra AES (Advanced Encryption Standard)
    	    cipher.init(Cipher.ENCRYPT_MODE, sk); // inicializa a cifra com a chave secreta (modo de encriptacao)
                
    		while (lidos>0) {
    		// escrever os bytes no outro ficheiro
    		
                	ciphertext = cipher.update(plaintext,0,lidos);
                	fos.write(ciphertext); 
    		
    		// e continuar
    		lidos= fis.read(plaintext);
    	    }
    	    ciphertext=cipher.doFinal();
    	    fos.write(ciphertext);
    	    fis.close();
    	    fos.close();
    	    
    	    fi.delete(); // elimina ficheiro original
    	   
    	}
    	catch (Exception e) {
    	    e.printStackTrace();
    	}
    	
    }

	
	public String decifrar(String inFile,SecretKey sk){// metodo que efectua a encriptacao da informacao da venda
		byte[] plaintext= new byte[1024];
		byte[] ciphertext= new byte[1024];
		int lidos;
		String pathOut = inFile+"_dec";
		String content = "";
		// le a informacao do ficheiro desencriptado
		try {
			File fi = new File(inFile);
    		File fo = new File(pathOut);
    	    FileInputStream fis= new FileInputStream(fi);
    	    FileOutputStream fos= new FileOutputStream(fo);
		    
		    lidos= fis.read(ciphertext);
	            Cipher cipher;
	            cipher = Cipher.getInstance("AES");// obtem uma instancia da cifra AES (Advanced Encryption Standard)
		    cipher.init(Cipher.DECRYPT_MODE, sk); // inicializa a cifra com a chave secreta (modo de desencriptacao)
	            
			while (lidos>0) {
			// escrever os bytes no outro ficheiro
			
	            	plaintext = cipher.update(ciphertext,0,lidos);
	            	fos.write(plaintext); 
			
			// e continuar
			lidos= fis.read(ciphertext);
		    }
		    plaintext=cipher.doFinal();
		    fos.write(plaintext);
		    fis.close();
		    fos.close();
		    
		    // le a informacao do ficheiro desencriptado
		    	byte[] b = new byte[1024];
		    	File toRead = new File(pathOut);
		    	FileInputStream fii = new FileInputStream(toRead);
		    	fii.read(b);
		    	fii.close();
		    	toRead.delete(); // elimina o ficheiro desencriptado
		    	content = new String(b); // coloca a informacao lida numa string	    
		}
		catch (Exception e) {
		    e.printStackTrace();
		    
		}
		return content; // retorna a informacao pretendida
		}
}
