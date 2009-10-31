package com.game.weichi;


import android.R.string;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

 
public class WeiChi extends Activity {
	WeiChiView wcview;
	
//	private String[] alert_list_items;
//	private Resources app_resources;
	private static final int MENU_RESTART = Menu.FIRST;
	private static final int MENU_PASS = Menu.FIRST+1;
	private static final int MENU_UNDO = Menu.FIRST+2;
	private static final int MENU_GAMEINFO = Menu.FIRST+3;
	
    /** Called when the activity is first created. */
	 @Override
	    public boolean onCreateOptionsMenu(Menu menu)
	 {
		 super.onCreateOptionsMenu(menu);
		 super.onCreateOptionsMenu(menu); 

	        Log.i("info", "NotePad MenuCreate ");
	        menu.add(0, MENU_RESTART, 0, R.string.menu_restart);
	        menu.add(0,MENU_PASS, 0, R.string.menu_pass);
	        menu.add(0,MENU_UNDO, 0, R.string.menu_undo);
	        menu.add(0,MENU_GAMEINFO, 0, R.string.menu_info);



	return true;

	 }
	 @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        switch (item.getItemId()) {
	        case MENU_RESTART:
	            {
	            	wcview.restart();
                	Toast.makeText(WeiChi.this, "restart", Toast.LENGTH_SHORT).show();
                	 return true;
	            }
	           
	        case MENU_PASS:
	        {
	        	Toast.makeText(WeiChi.this, "pass", Toast.LENGTH_SHORT).show();
                
                wcview.pass();
                return true;
	        }
	        case MENU_UNDO:
	        {
               wcview.undo();
               return true;
               
	        }  
	        case MENU_GAMEINFO:
	        {
	        	
	        	
	        	 AlertDialog.Builder infoAlert=new AlertDialog.Builder(this);
	             
	             infoAlert.setTitle("this is my dialog");
	             infoAlert.setMessage("White is captured by...");
	             

//	           infoAlert.setPositiveButton("exit", this);
	             infoAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                     public void onClick(DialogInterface dialog, int whichButton) {
                             //Put your code in here for a positive response
                     }
             });
	             infoAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                     public void onClick(DialogInterface dialog, int whichButton) {
                             //Put your code in here for a positive response
                     }
             });
             

	        	infoAlert.show();
                return true;
	        }  
	        }

	        return false;
	    }
	@Override
    	public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            
            setContentView(R.layout.main); 
//            CharSequence string = "You Win";  
            Bundle bunde=this.getIntent().getExtras(); 
            int boardSize=bunde.getInt("boardsize");
//         
//        
//            wcview.setBoardSize(boardSize);
            wcview = (WeiChiView)this.findViewById(R.id.wcview);
            
//            wcview.setTextView((TextView)this.findViewById(R.id.text));
            
            
           
    }
}