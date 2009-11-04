package com.game.weichi;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import android.widget.TextView;
import android.widget.Toast;

 
public class WeiChi extends Activity {
	WeiChiView wcview = new WeiChiView(null);
	
//	private String[] alert_list_items;
//	private Resources app_resources;
	private static final int MENU_RESTART = Menu.FIRST;
	private static final int MENU_PASS = Menu.FIRST+1;
	private static final int MENU_UNDO = Menu.FIRST+2;
	private static final int MENU_GAMEINFO = Menu.FIRST+3;
	private static int GRID_SIZE = 25; 
	 private static int GRID_WIDTH = 10; 
	 private static int CHESS_DIAMETER = 5; 
	  GoPlayer gp = new GoPlayer(GRID_SIZE-1);
	    GoGame g= new GoGame(GRID_SIZE-1); 
	
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
	        	Toast.makeText(WeiChi.this, "undo", Toast.LENGTH_SHORT).show();
	        	 wcview.undo();
	        	 
	               return true;
	        }  
	        case MENU_GAMEINFO:
	        {
	        	
	        	
	        	 AlertDialog.Builder infoAlert=new AlertDialog.Builder(this);
	             
	             infoAlert.setTitle("Game information");
	             String b_Score = null;
	             String w_Score = null;
	             if(gp.myCurrentGame.secondPass)       { 
	            	 int black_Score=gp.myCurrentGame.calculateBFinalScore();
	            	 int white_Score=gp.myCurrentGame.calculateWFinalScore(); 
	            	 if(black_Score>white_Score)
	            	 {
	            		 infoAlert.setMessage("Black is captured by : " +black_Score+ " points, " + "White is captured by :"+white_Score+"points,"+"White wins");  
			         }
	            	 else if(black_Score==white_Score) 
	            	 {
	            		 infoAlert.setMessage("Black is captured by: " +black_Score+ " points,"+  "White is captured by : "+white_Score+" points,"+"You are neck by neck");  
	 	            	 
	            	 }
	            	 else
	            		 infoAlert.setMessage("Black is captured by : " +black_Score+ " points, " + "White is captured by : "+white_Score+" points,"+"Black wins");  
		            	
	             }
	             else
	            	 infoAlert.setMessage("It is coming soon...");  
	            	
	             
//	             infoAlert.setMessage(b_Score);
	             
	             
//           	  infoAlert.setMessage(w_Score);
	             

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
         setContentView(wcview); 
         Bundle myBundle=this.getIntent().getExtras(); 
         int boardSize =myBundle.getInt("size");
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
        
         
         
//         wcview = (WeiChiView)this.findViewById(R.id.wcview);
   
        
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
		       
		    	if(blah)
		    		{
		    		blah = false;
		    		return true;
		    		}
		    	blah=true;
		                int x;
		                int y;
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
		               
		                	     	 if(!playComputer || gp.myCurrentGame.isBsTurn)
		                	    	 {
//		                	    		 gp.playMoveOnCurrentGame(new GoMove(x,y));
		                	    		 gp.playOneMoveOnCurrentGame(new GoMove(x,y));
		                	    		 
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
		        this.invalidate();

		        
		        return true;
	  	    }
	  
		   @Override
		    public void onDraw(Canvas canvas) {
	            
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