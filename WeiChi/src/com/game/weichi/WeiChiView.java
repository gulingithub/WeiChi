package com.game.weichi;

import android.content.Context;
import android.content.res.Resources;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

 class WeiChiView extends View{

	 protected static int GRID_SIZE = 8;
	    protected static int GRID_WIDTH = 30; 
	    protected static int CHESS_DIAMETER = 26; 
	    protected static int mStartX;// 
	    protected static int mStartY;// 

	    private static int[][] mGridArray; 
	   
	    private final int BLACK=1;
	    private final int WHITE=2;
	    int wbflag = 1; //
	    boolean key = false;
	   
	    int mGameState = GAMESTATE_RUN; 
	    static final int GAMESTATE_PRE = 0;
	    static final int GAMESTATE_RUN = 1;
	    static final int GAMESTATE_PAUSE = 2;
	    static final int GAMESTATE_END = 3;
	   
	    //private TextView mStatusTextView; 
	    public TextView mStatusTextView; 
	   
	    
	   
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
	        mGameState = 1; 
	        wbflag = BLACK; 
	        
	        mGridArray = new int[GRID_SIZE-1][GRID_SIZE-1];
	       
	       
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
	                if ((x >= 0 && x < GRID_SIZE - 1)
	                        && (y >= 0 && y < GRID_SIZE - 1)) {
	                	 if (mGridArray[x][y] == 0) {
	                         if (wbflag == BLACK) {
	                             putChess(x, y, BLACK);
	                             wbflag = WHITE;
	                         } 
	                         else if (wbflag == WHITE) {
	                             putChess(x, y, WHITE);
	                             wbflag = BLACK;
	                         }
	                     }
	            
	                 }
	   	         
	   	           
	   	   
	        this.invalidate();
	        return true;
	       
	    }
	   
	 
	   
	    @Override
	    public void onDraw(Canvas canvas) {

	        canvas.drawColor(Color.YELLOW);

	  
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
	                    canvas.drawRect(mLeft, mTop, mRright, mBottom, paintRect);
	                }
	            }
	           
	          
	            paintRect.setStrokeWidth(4);
	            canvas.drawRect(mStartX, mStartY, mStartX + GRID_WIDTH*GRID_SIZE, mStartY + GRID_WIDTH*GRID_SIZE, paintRect);
	        }

	      
	      
	        for (int i = 0; i < GRID_SIZE-1; i++) {
	            for (int j = 0; j < GRID_SIZE-1; j++) {
	                if(mGridArray[i][j] == BLACK){
	                    
	                    //canvas.drawBitmap(mChessBW[0], mStartX + (i+1) * GRID_WIDTH - CHESS_DIAMETER/2 , mStartY + (j+1)* GRID_WIDTH - CHESS_DIAMETER/2 , mPaint);
	                   
	                 
	                     {
	                        Paint paintCircle = new Paint();
	                        paintCircle.setColor(Color.BLACK);
	                        canvas.drawCircle(mStartX + (i+1) * GRID_WIDTH, mStartY + (j+1)* GRID_WIDTH, CHESS_DIAMETER/2, paintCircle);
	                    }
	                   
	                }else if(mGridArray[i][j] == WHITE){
	                   
	                    //canvas.drawBitmap(mChessBW[1], mStartX + (i+1) * GRID_WIDTH - CHESS_DIAMETER/2 , mStartY + (j+1)* GRID_WIDTH - CHESS_DIAMETER/2 , mPaint);
	                   
	               
	                    {
	                        Paint paintCircle = new Paint();
	                        paintCircle.setColor(Color.WHITE);
	                        canvas.drawCircle(mStartX + (i+1) * GRID_WIDTH, mStartY + (j+1)* GRID_WIDTH, CHESS_DIAMETER/2, paintCircle);
	                    }
	                }
	            }
	        }
	    }
	   
	    public void putChess(int x, int y, int blackwhite){
	        mGridArray[x][y] = blackwhite;
	    }
	   
	   
	   
	  
	  
	}