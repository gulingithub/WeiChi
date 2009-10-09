package com.game.weichi;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;


public class WeiChi extends Activity {
	WeiChiView wcview;
    /** Called when the activity is first created. */
    @Override

    	public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.main);

           
            wcview = (WeiChiView)this.findViewById(R.id.wcview);
//            wcview.setTextView((TextView)this.findViewById(R.id.text));

    }
}