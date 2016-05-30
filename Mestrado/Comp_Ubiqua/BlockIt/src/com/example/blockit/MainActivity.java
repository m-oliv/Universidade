package com.example.blockit;

import java.util.LinkedList;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	private static final String TAG = "Interface";
	// Listas
	public static LinkedList<String> whitelist = new LinkedList<String>();
	public static LinkedList<String> noblock = new LinkedList<String>();
	Button b1,b2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		b1 = (Button) findViewById(R.id.button1);
		b2 = (Button) findViewById(R.id.button2);
		
		OnClickListener ocl = new View.OnClickListener(){
			@Override
			public void onClick(View v)
			{
				EditText text = (EditText)findViewById(R.id.editText1);
				TextView tv = (TextView)findViewById(R.id.textView3);
				CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox2);
				switch(v.getId()){
				//adicionar o numero a whitelist
					case R.id.button1:
						Log.v(TAG, "Event created");
						String value = text.getText().toString();
						Log.v(TAG, "String acquired.");
						whitelist.add(value);
						Log.v(TAG, "Number added to whitelist");
						text.setText(" ");
						tv.setTextColor(Color.GREEN);
						tv.setText("Adicionado");
						//se a checkbox "aceitar sempre" estiver assinalada, adicionar numero tambem a lista dos numeros permitidos
						if(checkBox.isChecked()){
							noblock.add(value);
				        }
						
						break;
					
						// Remover o numero da whitelist
					case R.id.button2:
						Log.v(TAG, "Event created");
						String value1 = text.getText().toString();
						Log.v(TAG, "String acquired.");
						whitelist.remove(value1);
						Log.v(TAG, "Number removed from whitelist");
						text.setText(" ");
						tv.setTextColor(Color.RED);
						tv.setText("Removido");
						//se pertencer a lista de permitidos, remover numero tambem desta lista
						if(checkBox.isChecked()){
							noblock.remove(value1);
				        }
						break;
				}
			}
		};	
		b1.setOnClickListener(ocl);
		b2.setOnClickListener(ocl);		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
