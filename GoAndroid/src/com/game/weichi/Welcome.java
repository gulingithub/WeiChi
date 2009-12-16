package com.game.weichi;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Welcome extends Activity {
	Button playermode,computermode, oneplayer;	
	EditText bSize;
	
	
	        
@Override
public void onCreate(Bundle savedInstanceState){ 
	super.onCreate(savedInstanceState);
	setContentView(R.layout.welcome);
	final boolean gameMode =true;
	final boolean oneplayermode=true;
	bSize= (EditText)this.findViewById(R.id.boardsize); 
	playermode = (Button)this.findViewById(R.id.player);
	computermode = (Button)this.findViewById(R.id.computer);
	oneplayer = (Button)this.findViewById(R.id.oneplayer);
	 playermode.setOnClickListener(new View.OnClickListener() {
         public void onClick(View v) {
        	 int size = Integer.parseInt(bSize.getText().toString());
        	
         	
         	Toast.makeText(Welcome.this, "Player mode", Toast.LENGTH_SHORT).show();
         	Intent intent =new Intent();
         	intent.setClass(Welcome.this, WeiChi.class);
         	Bundle bundle = new Bundle();
         	bundle.putInt("size", size);
         	bundle.putBoolean("gameMode", false);
         	bundle.putBoolean("oneplayermode", false);
         	
         	intent.putExtras(bundle);
         	startActivity(intent);
         }
     });
	
	 computermode.setOnClickListener(new View.OnClickListener() {
         public void onClick(View v) {
        	 int size = Integer.parseInt(bSize.getText().toString());       	
          	

          	Toast.makeText(Welcome.this, "Computer mode", Toast.LENGTH_SHORT).show();
          	Intent intent =new Intent();
          	intent.setClass(Welcome.this, WeiChi.class);
          	Bundle bundle = new Bundle();
          	bundle.putInt("size", size);
          	bundle.putBoolean("gameMode", true);
          	bundle.putBoolean("oneplayermode", false);
          	intent.putExtras(bundle);
          	startActivity(intent);
         	
         	
         }
     });
	 
	 oneplayer.setOnClickListener(new View.OnClickListener() {
         public void onClick(View v) {
        	 int size = Integer.parseInt(bSize.getText().toString());
        	
         	
         	Toast.makeText(Welcome.this, "Player mode", Toast.LENGTH_SHORT).show();
         	Intent intent =new Intent();
         	intent.setClass(Welcome.this, WeiChi.class);
         	Bundle bundle = new Bundle();
         	bundle.putInt("size", size);
         	bundle.putBoolean("gameMode", false);
        	bundle.putBoolean("oneplayermode", true);
         	
         	intent.putExtras(bundle);
         	startActivity(intent);
         }
     });
	
	
}
public int addFunc(int a, int b)
{
	return a + b;

}
}
