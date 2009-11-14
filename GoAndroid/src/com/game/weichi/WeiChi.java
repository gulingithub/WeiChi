package com.game.weichi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.*;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import android.widget.*;

import android.net.*;
import java.io.*;
import java.net.*;


public class WeiChi extends Activity {
	WeiChiView wcview = new WeiChiView(null);
	LinearLayout layout;
	TextView statusText;
	private final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;
	private final int FP = ViewGroup.LayoutParams.FILL_PARENT;

	//	private String[] alert_list_items;
	//	private Resources app_resources;
	private static final int MENU_RESTART = Menu.FIRST;
	private static final int MENU_PASS = Menu.FIRST+1;
	private static final int MENU_UNDO = Menu.FIRST+2;
	private static final int MENU_GAMEINFO = Menu.FIRST+3;
	private static int GRID_SIZE = 25; 
	private static int GRID_WIDTH = 10; 
	private static int CHESS_DIAMETER = 5; 
	 private boolean playComputer =false;
	private static CharSequence gameStatus = ""; 

	GoPlayer gp;
	GoGame g; 
	
	boolean isBlack;

	Network net = new Network();
	private TextView tvConnect;
	private ProgressBar pbConnect;
	private Handler hl = new Handler();

	/** Called when the activity is first created. */
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
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
        	Toast.makeText(WeiChi.this, "undo", Toast.LENGTH_SHORT).show();
        	 wcview.undo();
        	 
               return true;
        }  
        case MENU_GAMEINFO:
        {
        	
        	
        	 AlertDialog.Builder infoAlert=new AlertDialog.Builder(this);
             
             infoAlert.setTitle("Score caculation");
             String b_Score = null;
             String w_Score = null;
             if(gp.myCurrentGame.secondPass)       { 
            	 int black_Score=gp.myCurrentGame.calculateBFinalScore();
            	 int white_Score=gp.myCurrentGame.calculateWFinalScore(); 
            	 if(black_Score>white_Score)
            	 {
            		 infoAlert.setMessage("Black: " +black_Score+ " points, " + "White: "+white_Score+" points, "+"Black wins");  
		         }
            	 else if(black_Score==white_Score) 
            	 {
            		 infoAlert.setMessage("Black: " +black_Score+ " points, "+  "White : "+white_Score+" points,"+"You are neck by neck");  
 	            	 
            	 }
            	 else
            		 infoAlert.setMessage("Black: " +black_Score+ " points, " + "White: "+white_Score+" points, "+"White wins");  
	            	
             }
             else
            	 infoAlert.setMessage("You need to finish the game before calculating scores.");  
            	
             
             

//           infoAlert.setPositiveButton("exit", this);
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

		setContentView(R.layout.network);
		tvConnect = (TextView)this.findViewById(R.id.txconnect);
		pbConnect = (ProgressBar)this.findViewById(R.id.progressbar);

		layout= new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams param= new LinearLayout.LayoutParams(WC,WC);
        statusText = new TextView(this);
       
        statusText.setHeight(30);
        statusText.setWidth(500);
        statusText.setBackgroundColor(Color.GRAY);
        statusText.setTextColor(Color.YELLOW);
        statusText.setTextSize(20);
        statusText.setIncludeFontPadding(true);
        param = new LinearLayout.LayoutParams(WC,WC);
        layout.addView(statusText, param);
        param = new LinearLayout.LayoutParams(FP,FP);
        layout.addView(wcview, param);
       
		Bundle myBundle=this.getIntent().getExtras(); 
		int boardSize =myBundle.getInt("size");
		 boolean playmode=myBundle.getBoolean("playmode");
		playComputer=playmode;
		if (boardSize<=10)
		{
			GRID_SIZE=boardSize+1;
			GRID_WIDTH = 30;
			CHESS_DIAMETER = 26;

		}
		else if(boardSize<=15)
		{
			GRID_SIZE=boardSize+1;
			GRID_WIDTH = 21;
			CHESS_DIAMETER = 18;
		}
		else if(boardSize<=20)
		{
			GRID_SIZE=boardSize+1;
			GRID_WIDTH = 16;
			CHESS_DIAMETER = 14; 
		}

		gp = new GoPlayer(GRID_SIZE-1);
		g= new GoGame(GRID_SIZE-1); 

		Log.i("INFO", "in OnCreate");

		new Thread(new Runnable() {
			public void run() {
				netConnection();



				//		         setContentView(wcview); 

			}
		}).start();


		//         wcview = (WeiChiView)this.findViewById(R.id.wcview);


	}
	private void netConnection() {
		// TODO Auto-generated method stub

		Log.i("INFO", "in netConnect");
		int i;
		for (i = 0; i < Network.NET_CONNECTNUM; i++) {
			int status = net.connect();
			if (status == Network.NET_CONNECTED) {
				isBlack = true;
				hl.post(new Runnable(){
					public void run(){
						tvConnect.setText("Matching another player on Go server ...");
					}
				});
				int j;
				for (j = 0; j < Network.NET_MATCHNUM; j++) {
					int status2 = net.match();
					if (status2 == Network.NET_MATCHED) {
						hl.post(new Runnable(){
							public void run(){
								setContentView(layout);
							}
						});
						break;
					}
				}
				if (j == Network.NET_MATCHNUM) {
					System.exit(0);
				}else
					break;
				//      		 tv.setText("Connected to Go server successfully!");
				//      		 pb.setIndeterminate(false);
				//      		 pb.setProgress(10000);
				//      		 Toast
				//      		 .makeText(
				//      				 Welcome.this,
				//      				 "Connected to server successfully! Matching another player...",
				//      				 Toast.LENGTH_LONG).show();
				//      		 int j;
				//      		 for (j = 0; j < Network.NET_MATCHNUM; j++) {
				//      			 int status2 = net.match();
				//      			 if (status2 == Network.NET_MATCHED) {
				//      				 Toast
				//      				 .makeText(
				//      						Welcome.this,
				//      						 "Matched to a player successfully! Game starts...",
				//      						 Toast.LENGTH_LONG).show();
				//      				 break;
				//      			 }
				//      		 }
				//      		 if (j == Network.NET_MATCHNUM) {
				//      			 Toast.makeText(Welcome.this,
				//      					 "Failed to match a player on the server.",
				//      					 Toast.LENGTH_LONG).show();
				//      			 System.exit(0);
				//      		 }else 
				//      			 break;
				//      	 } else if (status == Network.NET_MATCHED) {
				//      		 Toast.makeText(Welcome.this,
				//      				 "Matched to a player successfully! Game starts...",
				//      				 Toast.LENGTH_SHORT).show();

			}else if (status == Network.NET_MATCHED){
				isBlack = false;
				hl.post(new Runnable(){
					public void run(){
						setContentView(layout);
					}
				});
				break;
			}
		}

		if (i == Network.NET_CONNECTNUM) {
			// Toast.makeText(Welcome.this, "Failed to connect to the server!",
			// Toast.LENGTH_LONG).show();

			//    	   tv.setText("Failed to connect to Go server! Restart!");
			//    	   pb.setIndeterminate(false);
			//    	   pb.setProgress(0);
			System.exit(0);
		}
	}
	private class WeiChiView extends View{

		//		int GRID_SIZE = boardSize; 
		//		private static final int GRID_SIZE = 10;
		//		 private static final int GRID_WIDTH = 30; 
		//		 private static final int CHESS_DIAMETER = 26; 
		int mStartX;// 
		int mStartY;// 

		private boolean playComputer =false;

		private final int BLACK=1;
		private final int WHITE=-1;
		private final int ILLEGAL=-2;
		private final int MARK_WHITE=4;//White stone with black dot
		private final int WHITE_DOT=-3;
		private final int MARK_BLACK=-4;
		private final int BLACK_DOT=3;



		CharSequence mText;
		public TextView mStatusTextView; 

		//		    GoPlayer gp = new GoPlayer(GRID_SIZE-1);
		//		    GoGame g= new GoGame(GRID_SIZE-1); 

		public WeiChiView(Context context) {

			super(context);
			// TODO Auto-generated constructor stub

			Log.i("INFO", "in WeiChiView()");
		}
		//		    public WeiChiView(Context context, AttributeSet attrs, int defStyle) {
		//		        super(context, attrs, defStyle);
		//		      }
		//
		//		    public WeiChiView(Context context, AttributeSet attrs) { 
		//		        super(context, attrs);
		//		        this.setFocusable(true);  //20090530
		//		        this.setFocusableInTouchMode(true);
		//		        
		//		        
		//
		//		    }


		public void setTextView(TextView tv){
			mStatusTextView =tv;
			mStatusTextView.setVisibility(View.INVISIBLE);
		}


		@Override
		protected void onSizeChanged(int w, int h, int oldw, int oldh) {
			mStartX = w / 2 - GRID_SIZE * GRID_WIDTH / 2;
			mStartY = h / 2 - GRID_SIZE * GRID_WIDTH / 2;

		}
		boolean blah = true;
		@Override
		public boolean onTouchEvent(MotionEvent event){
			
			Log.i("INFO", ""+blah);
			Log.i("INFO", "inonTouchEvent");

			if(blah)
			{
				blah = false;
				return true;
			}
			blah=true;
			
			final int x;
			final int y;
			float x0 = GRID_WIDTH - (event.getX() - mStartX) % GRID_WIDTH;
			float y0 = GRID_WIDTH - (event.getY() - mStartY) % GRID_WIDTH;
			if (x0 < GRID_WIDTH / 2) {
				x = (int) ((event.getX() - mStartX) / GRID_WIDTH);
			} else {
				x = (int) ((event.getX() - mStartX) / GRID_WIDTH) - 1;
			}
			if (y0 < GRID_WIDTH / 2) {
				y = (int) ((event.getY() - mStartY) / GRID_WIDTH);
			} else {
				y = (int) ((event.getY() - mStartY) / GRID_WIDTH) - 1;
			}
			if ((x >= 0 && x < GRID_SIZE-1 )
					&& (y >= 0 && y < GRID_SIZE-1)) {

				if(!playComputer && ( isBlack && gp.myCurrentGame.isBsTurn || !isBlack && !gp.myCurrentGame.isBsTurn))
				{
					gp.playOneMoveOnCurrentGame(new GoMove(x,y));
					 if (gp.myCurrentGame.isBsTurn)
    	        	 {
    	        	 Log.i("ddd",gp.myCurrentGame.isBsTurn+"" );
    	        	 gameStatus = "Black's Turn";
    	           
    	        	 }
    	         else
    	         {
    	        	 gameStatus="White's Turn";
    	         }
    	    		 statusText.setText(gameStatus); 
					Log.i("INFO", ""+gp.myCurrentGame.isBsTurn);
					
					new Thread(new Runnable(){
						public void run(){
							net.sendRemoteMove(new GoMove(x, y));
							GoMove gm = net.getRemoteMove();
							gp.playOneMoveOnCurrentGame(gm);
							
							hl.post(new Runnable(){
								public void run(){
									 if (gp.myCurrentGame.isBsTurn)
	                	        	 {
	                	        	 Log.i("ddd",gp.myCurrentGame.isBsTurn+"" );
	                	        	 gameStatus = "Black's Turn";
	                	           
	                	        	 }
	                	         else
	                	         {
	                	        	 gameStatus="White's Turn";
	                	         }
	                	    		 statusText.setText(gameStatus); 
									invalidate();
								}
							});
						}
					}).start();
					
					this.invalidate();

					//		                	    		 if(playComputer)
						//		                	    		 {
						//		                	    			 GoMove bestMove =
							//		                	    				 sendMoveToComputerAndGetBestMove(x,y);
						//		                	    			 gp.playMoveOnCurrentGame(bestMove);
					//		                	    		 }
					// g = g.move(new GoMove(x,y));
					// send move to server
				}

			}
//			this.invalidate();


			return true;
		}

		protected void onAttachedToWindow() {
			// TODO Auto-generated method stub
			super.onAttachedToWindow();
			
			Log.i("INFO", "in onAttachedToWindow");
			Log.i("INFO", ""+net.sock.isConnected());
			
			if (!isBlack) {
				new Thread(new Runnable() {
					public void run() {

						GoMove gm = net.getRemoteMove();
						gp.playOneMoveOnCurrentGame(gm);

						hl.post(new Runnable() {
							public void run() {
								invalidate();
							}
						});
					}
				}).start();
			}
		}
		
		@Override
		public void onDraw(Canvas canvas) {

			Log.i("INFO","in onDraw");

			canvas.drawColor(Color.YELLOW);

			//start to draw game board
			{
				Paint paintRect = new Paint();
				paintRect.setColor(Color.GRAY);
				paintRect.setStrokeWidth(2);
				paintRect.setStyle(Style.STROKE);

				for (int i = 0; i < GRID_SIZE; i++) {
					for (int j = 0; j < GRID_SIZE; j++) {
						int mLeft = i * GRID_WIDTH + mStartX;
						int mTop = j * GRID_WIDTH + mStartY;
						int mRright = mLeft + GRID_WIDTH;
						int mBottom = mTop + GRID_WIDTH;
						if (i==0 || j==0){
							canvas.drawRect(mRright, mBottom, mRright, mBottom, paintRect);
							//		                    	canvas.drawLine(mRright, mBottom, mRright, mBottom, linePaint);
						}
						else if ((i+1)==GRID_SIZE || (j+1)==GRID_SIZE){
							canvas.drawRect(mLeft, mTop, mLeft, mTop, paintRect);
							//		                    	canvas.drawLine(mRright, mBottom, mRright, mBottom, linePaint);
						}
						else{
							canvas.drawRect(mLeft, mTop, mRright, mBottom, paintRect);
							//		                    canvas.drawLine(mRright, mBottom, mRright, mBottom, linePaint);
						}

					}
				}


				paintRect.setStrokeWidth(4);
				canvas.drawRect(mStartX+ GRID_WIDTH, mStartY+ GRID_WIDTH, mStartX + GRID_WIDTH*(GRID_SIZE-1), mStartY + GRID_WIDTH*(GRID_SIZE-1), paintRect);
			}

			//start to draw stones  
			for (int i = 0; i < GRID_SIZE-1; i++) {
				for (int j = 0; j < GRID_SIZE-1; j++) {

					int[][] theBoard = gp.myCurrentGame.board;
					if(theBoard[i][j] == BLACK){

						//canvas.drawBitmap(mChessBW[0], mStartX + (i+1) * GRID_WIDTH - CHESS_DIAMETER/2 , mStartY + (j+1)* GRID_WIDTH - CHESS_DIAMETER/2 , mPaint);

						{

							Paint paintCircle = new Paint();
							paintCircle.setColor(Color.BLACK);
							canvas.drawCircle(mStartX + (i+1) * GRID_WIDTH, mStartY + (j+1)* GRID_WIDTH, CHESS_DIAMETER/2, paintCircle);

						}

					}
					else if(theBoard[i][j] == WHITE){

						//canvas.drawBitmap(mChessBW[1], mStartX + (i+1) * GRID_WIDTH - CHESS_DIAMETER/2 , mStartY + (j+1)* GRID_WIDTH - CHESS_DIAMETER/2 , mPaint);


						{
							Paint paintCircle = new Paint();
							paintCircle.setColor(Color.WHITE);
							canvas.drawCircle(mStartX + (i+1) * GRID_WIDTH, mStartY + (j+1)* GRID_WIDTH, CHESS_DIAMETER/2, paintCircle);
						}
					}
					else if( theBoard[i][j] ==ILLEGAL)
					{
						Paint paintCircle = new Paint();
						paintCircle.setColor(Color.RED);
						canvas.drawCircle(mStartX + (i+1) * GRID_WIDTH, mStartY + (j+1)* GRID_WIDTH, CHESS_DIAMETER/2, paintCircle);


					}
					else if( theBoard[i][j] ==MARK_WHITE)
					{
						Paint paintCircle = new Paint();
						paintCircle.setColor(Color.WHITE);
						canvas.drawCircle(mStartX + (i+1) * GRID_WIDTH, mStartY + (j+1)* GRID_WIDTH, CHESS_DIAMETER/2, paintCircle);
						paintCircle.setColor(Color.BLACK);
						canvas.drawCircle(mStartX + (i+1) * GRID_WIDTH, mStartY + (j+1)* GRID_WIDTH, CHESS_DIAMETER/4, paintCircle);


					}
					else if( theBoard[i][j] ==MARK_BLACK)
					{
						Paint paintCircle = new Paint();
						paintCircle.setColor(Color.BLACK);
						canvas.drawCircle(mStartX + (i+1) * GRID_WIDTH, mStartY + (j+1)* GRID_WIDTH, CHESS_DIAMETER/2, paintCircle);
						paintCircle.setColor(Color.WHITE);
						canvas.drawCircle(mStartX + (i+1) * GRID_WIDTH, mStartY + (j+1)* GRID_WIDTH, CHESS_DIAMETER/4, paintCircle);


					}
					else if( theBoard[i][j] ==WHITE_DOT)
					{
						Paint paintCircle = new Paint();
						paintCircle.setColor(Color.WHITE);
						canvas.drawCircle(mStartX + (i+1) * GRID_WIDTH, mStartY + (j+1)* GRID_WIDTH, CHESS_DIAMETER/3, paintCircle);


					}
					else if( theBoard[i][j] ==BLACK_DOT)
					{
						Paint paintCircle = new Paint();
						paintCircle.setColor(Color.BLACK);
						canvas.drawCircle(mStartX + (i+1) * GRID_WIDTH, mStartY + (j+1)* GRID_WIDTH, CHESS_DIAMETER/3, paintCircle);

					}


				}
			}


		}
		public void restart()
		{

			for (int i = 0; i < GRID_SIZE-1; i++) {
				for (int j = 0; j < GRID_SIZE-1; j++) {

					gp = new GoPlayer(GRID_SIZE-1);

					this.invalidate();
					//		            this.showTextView(White_Win); 

				}

			}


		}
		int timesCalled = 0;
		public void pass()
		{
			//		    	System.exit(0);
			//		    	timesCalled++;
			//		    	gp.myCurrentGame.board= null;
			//		    	gp.myCurrentGame.board[0][0]=5;
			boolean currentMove = gp.myCurrentGame.isBsTurn;
			gp.playMoveOnCurrentGame(GoMove.pass);
			 if (gp.myCurrentGame.isBsTurn)
        	 {
        	 Log.i("ddd",gp.myCurrentGame.isBsTurn+"" );
        	 gameStatus = "Black's Turn";
           
        	 }
         else
         {
        	 gameStatus="White's Turn";
         }
    		 statusText.setText(gameStatus); 
			if(currentMove == gp.myCurrentGame.isBsTurn) gp.myCurrentGame.board[-1][0] = 2;
			this.invalidate();
			//this.invalidate();
			//				 g = g.move(new GoMove(-1,0));
			// this.setVisibility(View.VISIBLE);
			//				 this.mStatusTextView.setVisibility(View.INVISIBLE);

		}

		public void undo()
		{

			//System.exit(0);

			boolean turn = gp.myCurrentGame.isBsTurn;

			gp.undoMove();
			//if(turn == gp.myCurrentGame.isBsTurn) System.exit(0);
			this.invalidate();
		}

		public void showTextView(CharSequence mT){
			this.mStatusTextView.setText(mT);
			mStatusTextView.setVisibility(View.VISIBLE);

		}
	}



}