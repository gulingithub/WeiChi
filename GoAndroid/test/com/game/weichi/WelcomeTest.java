package com.game.weichi;



import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;

import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
//import junit.framework.TestCase;

public class WelcomeTest extends ActivityInstrumentationTestCase2<Welcome>{
	EditText text;
	Button button1;
	Button button2;
	
	
	public WelcomeTest() {
		
		super("com.game.weichi", Welcome.class);
		
	}
		
	public void testWelcome()
	{
		
		Welcome welcome = getActivity();
		text= (EditText)welcome.findViewById(R.id.boardsize); 
		button1 = (Button)welcome.findViewById(R.id.player);
		button2 = (Button)welcome.findViewById(R.id.computer);
	
		TouchUtils.tapView(this, text);
		sendKeys("9");
		TouchUtils.tapView(this, button1);

		
		assertEquals(7, welcome.addFunc(3, 4));

		
		
	}
	
	

}

