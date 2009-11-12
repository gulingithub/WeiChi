package com.game.weichi;

import android.app.Activity;
import android.os.Bundle;
import android.widget.*;

public class Connection extends Activity {
	
	TextView txConnection;
	SeekBar seekBar;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
        Bundle myBundle=this.getIntent().getExtras(); 
        int boardSize =myBundle.getInt("size");
		
		setContentView(R.layout.network);
		txConnection = (TextView)this.findViewById(R.id.txconnect);
//		seekBar = (SeekBar)this.findViewById(R.id.seekbar);
	}

	public Connection() {
		// TODO Auto-generated constructor stub
	}

}
