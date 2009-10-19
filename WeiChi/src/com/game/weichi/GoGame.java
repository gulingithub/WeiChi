package com.game.weichi;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Random;

public class GoGame {

    public static void main(String[] args)
    {
        GoGame myGo = new GoGame(6);
        System.out.println("start");
        myGo.printBoard();
        System.out.println("XXX");
         myGo=myGo.move( getGoMove( 1,0));
        myGo.printBoard();
        System.out.println("XXX");
         myGo=myGo.move( getGoMove(0,0));
        myGo.printBoard();
        System.out.println("XXX");
        myGo=myGo.move( getGoMove( 0,1) );
        myGo.printBoard();
        System.out.println("XXX");
         myGo=myGo.move( getGoMove( 1, 1));
         myGo.printBoard();
        myGo=myGo.move( getGoMove( 2, 2));
        myGo.printBoard();
        myGo=myGo.move( getGoMove( 0, 2 ));
        myGo.printBoard();
         myGo=myGo.move( getGoMove(4, 4));
        myGo.printBoard();
         myGo=myGo.move( getGoMove( 0, 0));
        myGo.printBoard();
        myGo=myGo.move( getGoMove(3,3));
        myGo.printBoard();
        myGo=myGo.move( getGoMove( 2,1));
        myGo.printBoard();
        myGo=myGo.move( getGoMove(0,1));
        myGo.printBoard();
        
        myGo = new GoGame(5);
        myGo.playRandomGame();
        
//        
//        System.out.println( "NEW GAME");
//        myGo = new GoGame(9);
//         myGo.printBoard();
//         myGo=myGo.move( getGoMove( 1,4));
//        myGo.printBoard();
//         myGo=myGo.move( getGoMove(2,4));
//        myGo.printBoard();
//        myGo=myGo.move( getGoMove( 2,3) );
//        myGo.printBoard();
//         myGo=myGo.move( getGoMove( 1, 5));
//         myGo.printBoard();
//        myGo=myGo.move( getGoMove( 2, 5));
//        myGo.printBoard();
//        myGo=myGo.move( getGoMove(1, 3));
//        myGo.printBoard();
//         myGo=myGo.move( getGoMove(3, 4));
//        myGo.printBoard();
//         myGo=myGo.move( getGoMove( 2, 4));
//        myGo.printBoard();
//        
//        System.out.println( "NEW GAME");
//        myGo = new GoGame(9);
//         myGo.printBoard();
//         myGo=myGo.move( getGoMove( -1,0));
//        myGo.printBoard();
//         myGo=myGo.move( getGoMove(3,5));
//        myGo.printBoard();
//        myGo=myGo.move( getGoMove( -1,0) );
//        myGo.printBoard();
//         myGo=myGo.move( getGoMove( 4, 5));
//         myGo.printBoard();
//        myGo=myGo.move( getGoMove( 5, 5));
//        myGo.printBoard();

        

    }
    
    public  static  GoMove getGoMove(int a, int b)
    {
    	if(a==-1) return GoMove.pass;
    	return allMoves[a][b];
    }
	int boardSize;

	// keeps track of current board state, 1 is b, 0 is empty, -1 is w, 2 is ko
	int[][] board;
	Random myRandom = new Random();

	// all legal moves
	ArrayList<GoMove> legalMoves;

	// illegal move due to ko rules
	GoMove koMove;

	// keeps track if there was a ko
	boolean ko;

	// komi is the free points given to white for going second
	double komi = 6.0;
	int movesSoFar;
	boolean isBsTurn;

	// keeps all possible moves to avoid duplication
	public static GoMove[][] allMoves;

	// keeps track of how many consecutive passes there have been
	boolean firstPass;
	boolean secondPass;

	private GoGame() {

	}

	public GoGame(int size) {
		legalMoves = new ArrayList<GoMove>();
		boardSize = size;
		board = new int[boardSize][boardSize];

		allMoves = new GoMove[boardSize][boardSize];
		ko = false;
		movesSoFar = 0;
		isBsTurn = true;
		for (int i = 0; i < boardSize; ++i) {
			for (int j = 0; j < boardSize; ++j) {
				GoMove theMove = new GoMove(i, j);
				allMoves[i][j] = theMove;
				legalMoves.add(theMove);
			}
		}
	}

	// generate a clone of this game, moves and all
	@Override
	public GoGame clone() {

		GoGame newGame = new GoGame();
		newGame.legalMoves = (ArrayList<GoMove>) this.legalMoves.clone();
		newGame.isBsTurn = this.isBsTurn;
		newGame.boardSize = this.boardSize;
		newGame.board = new int[boardSize][boardSize];
		for (int i = 0; i < boardSize; ++i) {
			for (int j = 0; j < boardSize; ++j) {
				newGame.board[i][j] = this.board[i][j];
			}
		}

		newGame.ko = this.ko;
		if (this.ko)
			newGame.koMove = this.koMove;
		newGame.movesSoFar = this.movesSoFar;
		return newGame;
	}

    public double finalBoardValue()
    {
        double value = 0;
        for(int i = 0; i < boardSize; ++i)
        {
            for(int j = 0; j < boardSize; ++j)
            {

               // System.out.println(i + " " + j + " " + ((board[i][j])%2 ));
                    if( board[i][j] != 0 && board[i][j] !=2 )
                    {
                        
                        value+= board[i][j];
                    }
                    else {                        
//                        int curValue = assignValue(i,j);
//                        if( curValue ==1)
//                        {
//                            value++;
//                            board[i][j] =1;
//                        }
//                        else if( curValue == -1)
//                        {
//                            board[i][j] =-1;
//                            value--;
//                                    
//                        }
                    }
                        
                        
                
            }
        }
     //   System.out.println( "final board " + (value-komi));
       // this.printBoard();
        if(value-komi < -10) return -10;
        if(value-komi > 10) return 10;
        
        return value-komi;
//       if(value - komi > 0) return 1;
//       return 0;
    }
    
    // tries to guess what territory this is part of
    private int assignValue( int i, int j)
    {
        int directionalResults = 0;
     //   System.out.println( "directional results " + directionalResults);
        if( i + 1 < boardSize)
        {
            if( board[i+1][j] == 1 || board[i+1][j] == -1) 
                directionalResults += board[i+1][j];
        }
        if( i > 0)
        {
            if(board[i-1][j] == 1 || board[i-1][j] == -1) 
                directionalResults += board[i-1][j];
        }
        if( j + 1 < boardSize)
        {
            if( board[i][j+1] == 1 || board[i][j+1] == -1) 
                directionalResults += board[i][j+1];
        }
        if( j > 0)
        {
            if(board[i][j-1] == 1 || board[i][j-1] == -1) 
                directionalResults += board[i][j-1];
        }


        if(directionalResults ==0 ) return 0;
        return (directionalResults < 0) ? -1 :1;
        
    }


	public double playRandomGame() {
		
		GoGame testGame = this;
		//System.out.println("computing random move ");
		// System.out.print( myRandom.nextBoolean());

	

		int moves;
		for (moves = 0; testGame.legalMoves.size() > 0
				&& moves < boardSize * boardSize * 1.2;) {
			// System.out.println( "Legalmoves size " + legalMoves.size());
			int move = myRandom.nextInt(testGame.legalMoves.size());
			GoMove theMove = testGame.legalMoves.get(move);

			if (testGame.solidEyeCheck(theMove.x, theMove.y, 1)
					|| testGame.solidEyeCheck(theMove.x, theMove.y,
							-1)) {
				// System.out.println( "NOT PLAYING STUPID MOVE");
				testGame.legalMoves.remove(move);
			} else {
				moves++;
				testGame.moveDestructively(move, theMove);
			
			}
				//testGame.printBoard();
		}
		
		 //testGame.printBoard();

		// System.out.println( "Final of random game: " );


		// testGame.printBoard();
		// double value = 0;
		return testGame.finalBoardValue();
	}
	
	 private boolean solidEyeCheck(int x, int y, int stoneColor)
     {
    //     System.out.println( "Checking eye at " + x + " " + y + " color " + stoneColor);
         if( ponnukiCheck( x,y,stoneColor) )
         {
         //    System.out.println( "has ponnuki");
             int numberNeeded = 3;
             int numberOffEdge = 0;
             if( x + 1 < boardSize)
             {
                 if( y + 1 < boardSize)
                 {
                     if( board[x+1][y+1] == stoneColor ||
                             ponnukiCheck(x+1, y+1, stoneColor) ) numberNeeded--;
                 }
                 else numberOffEdge++;
                 
                 if( y > 0)
                 {
                     if( board[x+1][y-1] == stoneColor ||
                             ponnukiCheck(x+1, y-1, stoneColor) ) numberNeeded--;
                 }
                 else numberOffEdge++;
                 
             }
             else numberOffEdge +=2;
             
             if( x > 0)
             {
                 if( y + 1 < boardSize)
                 {
                     if( board[x-1][y+1] == stoneColor ||
                             ponnukiCheck(x-1, y+1, stoneColor)) numberNeeded--;
                 }
                 else numberOffEdge++;
                 
                 if( y > 0)
                 {
                     if( board[x-1][y-1] == stoneColor ||
                             ponnukiCheck(x-1, y-1, stoneColor)) numberNeeded--;
                 }
                 else numberOffEdge++;
             }
             else numberOffEdge +=2;
             
        //     System.out.println( "numberneeded " + numberNeeded + " numberOffEdge" + numberOffEdge);
             return (numberNeeded <=0) || 
                     ((numberNeeded <= 1 ) && (numberOffEdge ==2)) ||
                     ((numberNeeded <= 2) && (numberOffEdge ==3));
         }
        // System.out.println( "no ponnuki");
         return false;
     }

	public void moveDestructively(int index, GoMove theMove)
	{
		if (theMove.equals(GoMove.pass)) {
			if (ko)
				board[koMove.x][koMove.y] = 0;
			ko = false;

			koMove = GoMove.pass;

			if (firstPass == true) {
				secondPass = true;
			}
			firstPass = true;
			isBsTurn = !isBsTurn;
			
		}

		movesSoFar++;

		// System.out.println( "Size of myLegalMoves " + myLegalMoves.size());
		legalMoves.remove(index);
		// System.out.println( "Size of myLegalMoves " + myLegalMoves.size());
		int stoneColor = isBsTurn ? 1 : -1;

		// add this stone to the board
		board[theMove.x][theMove.y] = stoneColor;

		// System.out.println( "Board after placing stone " );

		// resolve kos
		resolveKos(theMove);

		// check if any opposing groups are captured, and update points which
		// had this as liberty
		checkLibertiesOtherColor(theMove, -stoneColor);

		// System.out.println( "Board after checking other color liberties");
		// this.printBoard();

		// check for suicide, and update any points which had this as last
		// liberty
		checkLibertiesSameColor(theMove, stoneColor);

		// System.out.println( "Board after checking same color liberties");
		// this.printBoard();
		isBsTurn = !isBsTurn;
	}
	
	public void move(int x, int y)
	{
		GoMove theMove=null;
		if(x ==-1) theMove = GoMove.pass;
		else
			theMove = allMoves[x][y];
		if (theMove.equals(GoMove.pass)) {
			if (ko)
				board[koMove.x][koMove.y] = 0;
			ko = false;

			koMove = GoMove.pass;

			if (firstPass == true) {
				secondPass = true;
			}
			firstPass = true;
			isBsTurn = !isBsTurn;
		}
		

		movesSoFar++;

		//int ind = nextGame.legalMoves.indexOf(theMove);
		//System.out.println("index of move " + ind);
		// System.out.println( "Size of myLegalMoves " + nextGame.legalMoves.size());
		legalMoves.remove(theMove);
		//System.out.println( "Size of myLegalMoves " + nextGame.legalMoves.size());
		int stoneColor = isBsTurn ? 1 : -1;

		// add this stone to the board
		board[theMove.x][theMove.y] = stoneColor;

//		 System.out.println( "Board after placing stone " );
//		 nextGame.printBoard();
		 
		// resolve kos
		resolveKos(theMove);
//		System.out.println( "Board after resolve kos");
//		nextGame.printBoard();
		
		
		// check if any opposing groups are captured, and update points which
		// had this as liberty
		checkLibertiesOtherColor(theMove, -stoneColor);

//		System.out.println( "Board after checking other color liberties");
//		nextGame.printBoard();

		// check for suicide, and update any points which had this as last
		// liberty
		checkLibertiesSameColor(theMove, stoneColor);

//		System.out.println( "Board after checking same color liberties");
//		nextGame.printBoard();
		isBsTurn = !isBsTurn;
		

	}
	public GoGame move(GoMove aMove) {
		// aMove.printMove();
//
//		 System.out.println("Moving " );
//		 theMove.printMove();

		if(board[aMove.x][aMove.y] ==1 || board[aMove.x][aMove.y] == -1|| board[aMove.x][aMove.y] == 2) return this;
		GoGame nextGame = this.clone();
//
//		System.out.println("clonedGame ");
//		nextGame.printBoard();

		
		GoMove theMove=null;
		if(aMove.x==-1) theMove = GoMove.pass;
		else
			theMove = allMoves[aMove.x][aMove.y];
		if (theMove.equals(GoMove.pass)) {
			if (ko)
				board[koMove.x][koMove.y] = 0;
			ko = false;

			koMove = GoMove.pass;

			if (firstPass == true) {
				secondPass = true;
			}
			firstPass = true;
			nextGame.isBsTurn = !nextGame.isBsTurn;
			return nextGame;
		}

		nextGame.movesSoFar++;

		//int ind = nextGame.legalMoves.indexOf(theMove);
		//System.out.println("index of move " + ind);
		// System.out.println( "Size of myLegalMoves " + nextGame.legalMoves.size());
		nextGame.legalMoves.remove(theMove);
		//System.out.println( "Size of myLegalMoves " + nextGame.legalMoves.size());
		int stoneColor = nextGame.isBsTurn ? 1 : -1;

		// add this stone to the board
		nextGame.board[theMove.x][theMove.y] = stoneColor;

//		 System.out.println( "Board after placing stone " );
//		 nextGame.printBoard();
		 
		// resolve kos
		nextGame.resolveKos(theMove);
//		System.out.println( "Board after resolve kos");
//		nextGame.printBoard();
		
		
		// check if any opposing groups are captured, and update points which
		// had this as liberty
		nextGame.checkLibertiesOtherColor(theMove, -stoneColor);

//		System.out.println( "Board after checking other color liberties");
//		nextGame.printBoard();

		// check for suicide, and update any points which had this as last
		// liberty
		nextGame.checkLibertiesSameColor(theMove, stoneColor);

//		System.out.println( "Board after checking same color liberties");
//		nextGame.printBoard();
		nextGame.isBsTurn = !isBsTurn;
		return nextGame;

	}

	// checks all groups of stoneColor which could be in trouble
	// because of a move by the opponent of lastMove
	private void checkLibertiesOtherColor(GoMove lastMove, int stoneColor) {

		// check up
		if (lastMove.x > 0) {
			if (board[lastMove.x - 1][lastMove.y] == stoneColor) {
				checkAndMaybeRemoveGroup(lastMove.x - 1, lastMove.y, stoneColor);

			}
		}

		// check down
		if (lastMove.x < boardSize - 1) {
			if (board[lastMove.x + 1][lastMove.y] == stoneColor) {
				checkAndMaybeRemoveGroup(lastMove.x + 1, lastMove.y, stoneColor);

			}
		}

		// check left
		if (lastMove.y > 0) {
			if (board[lastMove.x][lastMove.y - 1] == stoneColor) {
				checkAndMaybeRemoveGroup(lastMove.x, lastMove.y - 1, stoneColor);
			}
		}

		// check right
		if (lastMove.y < boardSize - 1) {
			if (board[lastMove.x][lastMove.y + 1] == stoneColor) {
				checkAndMaybeRemoveGroup(lastMove.x, lastMove.y + 1, stoneColor);
			}
		}

	}

	// check for suicides because of lastMove
	// and update any pieces which lost their representative liberty
	private void checkLibertiesSameColor(GoMove lastMove, int stoneColor) {
		checkAndMaybeRemoveGroup(lastMove.x, lastMove.y, stoneColor);

	}

	private void resolveKos(GoMove aMove) {
		// remove old ko
		if (ko) {
			legalMoves.add(allMoves[koMove.x][koMove.y]);

			board[koMove.x][koMove.y] = 0;
			ko = false;
		}

		int stoneColor = isBsTurn ? 1 : -1;

		// check if opposite color has ponnuki(star shape) around placed stone
		// if not, return because no ko

		if (!ponnukiCheck(aMove.x, aMove.y, -stoneColor)) {
			
			return;
		}

		// now we want exactly one of the surrounding stones to be captured
		int capturedStones = 0;

		if (aMove.x > 0) {
			if (ponnukiCheck(aMove.x - 1, aMove.y, stoneColor)) {
				capturedStones += 1;
				koMove = allMoves[aMove.x - 1][aMove.y];
			}

		}

		if (aMove.x + 1 < boardSize) {
			if (ponnukiCheck(aMove.x + 1, aMove.y, stoneColor)) {
				capturedStones += 1;
				koMove = allMoves[aMove.x + 1][aMove.y];
			}

		}

		if (aMove.y > 0) {
			if (ponnukiCheck(aMove.x, aMove.y - 1, stoneColor)) {
				capturedStones += 1;
				koMove = allMoves[aMove.x][aMove.y - 1];
			}

		}

		if (aMove.y + 1 < boardSize) {
			if (ponnukiCheck(aMove.x, aMove.y + 1, stoneColor)) {
				capturedStones += 1;
				koMove = allMoves[aMove.x][aMove.y + 1];
			}

		}

		// System.out.println("Captured stones " + capturedStones);
		ko = (capturedStones == 1);
		if (ko) {
			board[koMove.x][koMove.y] = 2;
			legalMoves.remove(koMove);
		}

	}

	// remove the @stonecolor group centered at movex,movey, if it has no
	// liberties
	private void checkAndMaybeRemoveGroup(int movex, int movey, int stoneColor) {

		Hashtable<String, GoMove> alreadyChecked = new Hashtable<String, GoMove>();
		ArrayList<GoMove> theGroup = new ArrayList<GoMove>();

		// System.out.println( allMoves);
		theGroup.add(allMoves[movex][movey]);

		GoMove myMove = GoMove.pass;
		int currentLoc = 0;
		while (currentLoc < theGroup.size()) {
			// System.out.println( "theGroup size" + theGroup.size());
			/*
			 * if( theGroup.size() == 100) printBoard(); if( theGroup.size()>
			 * 100) { System.out.println( "currentLoc " + currentLoc);
			 * System.out.println( "myMove");
			 * theGroup.get(currentLoc).printMove();
			 * 
			 * }
			 */
			myMove = theGroup.get(currentLoc);
			int x = myMove.x;
			int y = myMove.y;

			// check up
			if (x > 0) {
				if (board[x - 1][y] == stoneColor) {
					if (!alreadyChecked.contains(allMoves[x - 1][y])) {
						theGroup.add(allMoves[x - 1][y]);
						alreadyChecked.put(allMoves[x - 1][y].toString(),
								allMoves[x - 1][y]);
					}

				} else if (board[x - 1][y] == 0 || board[x - 1][y] == 2)
					return;
			}

			// check down
			if (x < boardSize - 1) {
				if (board[x + 1][y] == stoneColor) {
					if (!alreadyChecked.contains(allMoves[x + 1][y])) {
						alreadyChecked.put(allMoves[x + 1][y].toString(),
								allMoves[x + 1][y]);
						theGroup.add(allMoves[x + 1][y]);
					}

				} else if (board[x + 1][y] == 0 || board[x + 1][y] == 2)
					return;
			}

			// check left
			if (y > 0) {
				if (board[x][y - 1] == stoneColor) {
					if (!alreadyChecked.contains(allMoves[x][y - 1])) {
						theGroup.add(allMoves[x][y - 1]);
						alreadyChecked.put(allMoves[x][y - 1].toString(),
								allMoves[x][y - 1]);
					}

				} else if (board[x][y - 1] == 0 || board[x][y - 1] == 2)
					return;
			}

			// check up
			if (y < boardSize - 1) {
				if (board[x][y + 1] == stoneColor) {
					if (!alreadyChecked.contains(allMoves[x][y + 1])) {
						theGroup.add(allMoves[x][y + 1]);
						alreadyChecked.put(allMoves[x][y + 1].toString(),
								allMoves[x][y + 1]);
					}

				} else if (board[x][y + 1] == 0 || board[x][y + 1] == 2)
					return;
			}
			currentLoc++;
		}

		// if we've reached this point, all the stones in theGroup are dead
//		 System.out.println( "killing group");
		for (GoMove deadStone : theGroup) {
			board[deadStone.x][deadStone.y] = 0;
			legalMoves.add(allMoves[deadStone.x][deadStone.y]);
		}
	}

	// checks if there is a ponuuki around point (x,y) of stonecolor
	// i.e, if the 4 places around (x,y) are either off the board or have
	// the color stoneColor
	private boolean ponnukiCheck(int x, int y, int stoneColor) {
		// System.out.println( "boardSize " + boardSize);
		// System.out.println( "Checking for ponnuki at " + x + " " + y);
		if (x + 1 < boardSize) {
			if (board[x + 1][y] != stoneColor) {
				return false;
			}
		}
		if (x > 0) {
			// System.out.println( board[x-1][y] + "SDFDSFSDF");
			if (board[x - 1][y] != stoneColor) {

				return false;
			}
		}

		if (y + 1 < boardSize) {
			if (board[x][y + 1] != stoneColor) {
				return false;
			}
		}
		if (y > 0) {
			if (board[x][y - 1] != stoneColor) {
				return false;
			}
		}
		// System.out.println( "a ponnuki");
		return true;
	}

	public void printBoard() {

		for (int i = 0; i < boardSize; ++i) {
			for (int j = 0; j < boardSize; ++j) {
				if (board[i][j] == 0) {
					System.out.print(" . ");
				} else if (board[i][j] == 1) {
					System.out.print(" B ");
				} else if (board[i][j] == -1) {
					System.out.print(" W ");
				} else {
					System.out.print(" K ");
				}
			}
			System.out.println();
		}

		System.out.print("It is ");
		if (isBsTurn) {
			System.out.print("B's");

		} else {
			System.out.print("W's");
		}

		System.out.println(" turn");

		System.out.println("Board Value: " );
	}
	
    

}