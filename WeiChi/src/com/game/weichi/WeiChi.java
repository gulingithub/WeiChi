package com.game.weichi;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class WeiChi extends Activity {
	WeiChiView wcview;
    /** Called when the activity is first created. */
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
            wcview.setTextView((TextView)this.findViewById(R.id.text));
            final Button restart = (Button)findViewById(R.id.restart);
            final Button pass = (Button)findViewById(R.id.pass);
            final Button undo = (Button)findViewById(R.id.undo);
            
            restart.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    
                	wcview.restart();
                	Toast.makeText(WeiChi.this, "restart", Toast.LENGTH_SHORT).show();
                	
                }
            });

            pass.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Perform action on click
                	Toast.makeText(WeiChi.this, "pass", Toast.LENGTH_SHORT).show();
                    
                    wcview.pass();

                

                }
            });
            
            undo.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                	wcview.undo();
                   Toast.makeText(WeiChi.this, "undo", Toast.LENGTH_SHORT).show();
                }
            });


    }
}