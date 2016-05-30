package com.example.blockit;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;

import com.android.internal.telephony.ITelephony;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.PhoneLookup;
import android.telephony.*;
import android.util.Log;

public class PhoneCallReceiver extends BroadcastReceiver {
 Context context = null;
 private static final String TAG = "Phone call";
 private ITelephony telephonyService;

 @Override
 public void onReceive(Context context, Intent intent) {
  
	Log.v(TAG, "A receber chamada.");
	TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
	String num = "";

	if(intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)!=null){ //Obter numero
		num = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
	}

	Log.v(TAG,"###########################################");
	Log.v(TAG,"Inicio.");
	Log.v(TAG,"###########################################");
	
	int state = telephony.getCallState(); //obtem o estado
	
	if(state == TelephonyManager.CALL_STATE_RINGING){
		Log.v(TAG, "A receber "+num);
		// obter whitelist
		LinkedList<String> temp = (LinkedList<String>) MainActivity.whitelist; // whitelist
		LinkedList<String> temp2 = (LinkedList<String>) MainActivity.noblock; // numeros que nunca sao bloqueados
		Log.v(TAG,"whitelists obtidas");
	
		if(!contactExists(context,num) || num.equals("")){ // se e numero desconhecido ou nao esta nos contactos
			Log.v(TAG,"Bloqueado");
			blockCall(telephony);
		}
		
		if(contactExists(context,num)&&!temp.contains(num)&&!temp2.contains(num)){ // caso o numero esteja so nos contactos
			blockCall(telephony);
			sendSMS(num,false,0,"");
			Log.v(TAG,"Bloqueado. Enviar SMS sem contexto");
		}
		
		if(contactExists(context,num)&&temp.contains(num)&&!temp2.contains(num)){ // caso o numero esteja na whitelist
			blockCall(telephony);
			int numCompromisso = 0;
			
			//escolher contexto a utilizar
			if(!getContextCalendar(context).equals("")){
				numCompromisso = 1;	
			}
			
			if(numCompromisso == 0){ //quando nao ha eventos na agenda
				sendSMS(num,true,1,"");
				Log.v(TAG,"Bloqueado. Enviar SMS com contexto (localizacao).");
			}
			
			if(numCompromisso ==1){ //quando ha eventos na agenda
				String x = getContextCalendar(context);
				Log.v(TAG,"Compromisso:"+x);
				sendSMS(num,true,2,x);
				Log.v(TAG,"Bloqueado. Enviar SMS com contexto (event). Compromisso:"+x);
			}
			
		}
	}
	
	Log.v(TAG,"###########################################");
	Log.v(TAG,"Fim");
	Log.v(TAG,"###########################################");
 }

 public String getContextCalendar(Context context){ // obter evento actual a partir do calendario
	 ContentResolver contentResolver = context.getContentResolver();  
	 //obter a hora actual em milisegundos
	 long ntime = System.currentTimeMillis();
	 String titulo = "";
	 //ler dos calendarios (local e google)
	 Cursor cursor = contentResolver.query(Uri.parse("content://calendar/events"), 
			 new String[]{ "calendar_id", "title", "description", "dtstart", "dtend", "eventLocation" }, null, null, null);
     cursor.moveToFirst();
     Log.v(TAG,"Num Elementos:"+cursor.getCount());
     String[] CalNames = new String[cursor.getCount()];  
     int[] CalIds = new int[cursor.getCount()];  
     
     for (int i = 0; i < CalNames.length; i++) {  
        CalIds[i] = cursor.getInt(0);            
        // obter eventos
        CalNames[i] = "Evento"+cursor.getInt(0)+": \nTitulo: "+ cursor.getString(1)+"\nDescricao: "+cursor.getString(2)+"\nData Inicio: "+cursor.getLong(3)+"\nData Fim: "+cursor.getLong(4)+"\nLocation : "+cursor.getString(5);
        Log.v(TAG,"Titulo Evento: "+cursor.getString(1));
        // obter hora de inicio e hora de fim
        long StartTime = cursor.getLong(3);  
        long EndTime = cursor.getLong(4);  
        //se esta a decorrer um evento, obter o titulo  
        if ((StartTime<ntime)&&(ntime<EndTime)) {  
             titulo = cursor.getString(1);  
             break;  
        }                  
        cursor.moveToNext();
     }
     cursor.close();
     return titulo;
 }
 
 
 public void blockCall(TelephonyManager telephony){
	 // Bloquear as chamadas que forem recebidas.
	 try {
		 Class c = Class.forName(telephony.getClass().getName());
		 Method m = c.getDeclaredMethod("getITelephony");
		 m.setAccessible(true);
		 telephonyService = (ITelephony) m.invoke(telephony);
		 telephonyService.silenceRinger();	// silenciar
		 telephonyService.endCall();	// terminar a chamada
	 } catch (Exception e) {
	  e.printStackTrace();
  }
 }
 
 public boolean contactExists(Context context, String number) {
	 if(number.equals("")){
		 return false;
	 }
	 Log.v(TAG,"a procurar nos contactos");
	// verificar se o numero esta nos contactos
	Uri lookupUri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI,Uri.encode(number));
	// parametros da query
	String[] mPhoneNumberProjection = { PhoneLookup._ID, PhoneLookup.NUMBER, PhoneLookup.DISPLAY_NAME };
	//query
	Cursor cur = context.getContentResolver().query(lookupUri,mPhoneNumberProjection, null, null, null);
	try {
	   if (cur.moveToFirst()) { // se foram encontrados resultados, o contacto existe
	      return true;
	   }
	} finally {
	if (cur != null)
	   cur.close();
	}
	return false;
 }
 
 public void sendSMS(String num, boolean context, int caso, String infoContext){ //Enviar SMS com mensagem predefinida de acordo com contexto.
	 String message_text ="";
	 SmsManager sm = SmsManager.getDefault();
	 
	 if(context == false){ //caso o contacto nao esteja num grupo que deve receber o contexto
		message_text = "De momento nao posso atender. Ligo de volta assim que possivel.";
		ArrayList<String> m_parts = sm.divideMessage(message_text); //Separa a mensagem em partes com 160 caracteres ou menos
		sm.sendMultipartTextMessage(num, null, m_parts, null, null); //envia SMS
		Log.v(TAG, "SMS enviado (no context).");
	 }
	 
	 if(context ==true && caso == 1){ // Na whitelist sem eventos
		String location = "Estou em Evora <exemplo>.";
		message_text = "De momento nao posso atender. "+location+" Ligo de volta assim que possivel.";
		Log.v(TAG,message_text);
		ArrayList<String> m_parts = sm.divideMessage(message_text); //Separa a mensagem em partes com 160 caracteres ou menos
		sm.sendMultipartTextMessage(num, null, m_parts, null, null);//envia SMS
		Log.v(TAG, "SMS enviado. Contexto = localizacao.");
	 }
	 
	 if(context ==true && caso == 2){ // Na whitelist com eventos
		String event = infoContext;
		message_text = "De momento nao posso atender. "+event+" Ligo de volta assim que possivel.";
		Log.v(TAG,message_text);
		ArrayList<String> m_parts = sm.divideMessage(message_text); //Separa a mensagem em partes com 160 caracteres ou menos
		sm.sendMultipartTextMessage(num, null, m_parts, null, null);//envia SMS
		Log.v(TAG, "SMS enviado. Contexto = Eventos calendario.");
	 }
	 
 }
 
}