package com.game.weichi;

import android.content.Context;
import android.content.res.Resources;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

 class WeiChiView extends View{
	 WeiChi weichi;
	
	    protected static int GRID_SIZE = 10;
	    protected static int GRID_WIDTH = 30; 
	    protected static int CHESS_DIAMETER = 26; 
	    protected static int mStartX;// 
	    protected static int mStartY;// 

	    private boolean playComputer =false;
//	    private static int[][] mGridArray; 
	   
	   
	    private final int BLACK=1;
	    private final int WHITE=-1;
	    private final int ILLEGAL=-2;
	    private final int MARK_WHITE=4;//White stone with black dot
	    private final int WHITE_DOT=-3;
	    private final int MARK_BLACK=-4;
	    private final int BLACK_DOT=3;
	   
	    
	    Canvas canvas;
//	    private Paint linePaint;
	    
//	   
	   
	   
//	    
	    CharSequence mText;
	    CharSequence White_Win = "White Win!";
	    CharSequence Black_Win = "Black Win";
	   
	    //private TextView mStatusTextView; 
	    public TextView mStatusTextView; 
	   
	    GoPlayer gp = new GoPlayer(GRID_SIZE-1);
	    GoGame g= new GoGame(GRID_SIZE-1);
	    
	    public WeiChiView(Context context, AttributeSet attrs, int defStyle) {
	        super(context, attrs, defStyle);
	      }

	    public WeiChiView(Context context, AttributeSet attrs) { 
	        super(context, attrs);
	        this.setFocusable(true);  //20090530
	        this.setFocusableInTouchMode(true);
	        init();
	        

	    }

	    public void init() {
	      
	        
	       
	        
//           Resources myresources = getResources();
//           linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//	       linePaint.setColor(myresources.getColor(R.color.notepad_lines)); 
       
	       
	       
	       
	  }
	    public void setBoardSize(int size)
	    {
	    	GRID_SIZE=size;
	    }
	  
	    
        
	    public void setTextView(TextView tv){
	        mStatusTextView =tv;
	        mStatusTextView.setVisibility(View.INVISIBLE);
	    }

	    @Override
	    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
	        mStartX = w / 2 - GRID_SIZE * GRID_WIDTH / 2;
	        mStartY = h / 2 - GRID_SIZE * GRID_WIDTH / 2;
	        
	    }
	   
	    
	    
	     @Override
	    public boolean onTouchEvent(MotionEvent event){
	       
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
	                	    		 gp.playMoveOnCurrentGame(GoGame.getGoMove(x, y));
	                	    		 
//	                	    		 if(playComputer)
//	                	    		 {
//	                	    			 GoMove bestMove =
//	                	    				 sendMoveToComputerAndGetBestMove(x,y);
//	                	    			 gp.playMoveOnCurrentGame(bestMove);
//	                	    		 }
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
//	                    	canvas.drawLine(mRright, mBottom, mRright, mBottom, linePaint);
	                    }
	                    else if ((i+1)==GRID_SIZE || (j+1)==GRID_SIZE){
	                    	canvas.drawRect(mLeft, mTop, mLeft, mTop, paintRect);
//	                    	canvas.drawLine(mRright, mBottom, mRright, mBottom, linePaint);
	                    }
	                    else{
	                    canvas.drawRect(mLeft, mTop, mRright, mBottom, paintRect);
//	                    canvas.drawLine(mRright, mBottom, mRright, mBottom, linePaint);
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
//	                        paintCircle.setColor(Color.BLACK);
//	                        canvas.drawCircle(mStartX + (i+1) * GRID_WIDTH, mStartY + (j+1)* GRID_WIDTH, CHESS_DIAMETER/4, paintCircle);
                        
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

	    	gp = new GoPlayer(GRID_SIZE-1);
	    	
	    	this.invalidate();
		 }
	    int timesCalled = 0;
	    public void pass()
		 {
//	    	System.exit(0);
//	    	timesCalled++;
//	    	gp.myCurrentGame.board= null;
//	    	gp.myCurrentGame.board[0][0]=5;
	    	boolean currentMove = gp.myCurrentGame.isBsTurn;
	    	gp.playMoveOnCurrentGame(GoMove.pass);
	    	if(currentMove == gp.myCurrentGame.isBsTurn) gp.myCurrentGame.board[-1][0] = 2;
	    	this.invalidate();
//			 g = g.move(new GoMove(-1,0));
			// this.setVisibility(View.VISIBLE);
//			 this.mStatusTextView.setVisibility(View.INVISIBLE);
	    	
			
		 }
		 
		 public void undo()
		 {

		    	
			 boolean bTurn = gp.myCurrentGame.isBsTurn;
			 
			gp.undoMove();
			//if(bTurn == gp.myCurrentGame.isBsTurn) System.exit(0);
			this.invalidate();
		 }
		   
		 public void showTextView(CharSequence mT){
		        this.mStatusTextView.setText(mT);
		        mStatusTextView.setVisibility(View.VISIBLE);
		       
		    }
	}