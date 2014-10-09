
	import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
	  
	/** Computer player 
	 * @author Ivan Zhang   
	 *
	 */
	public class ComputerPlayer
	{
	    protected int colour;
	    protected ArrayList <Move> allComputerMoves;
	    protected ArrayList <Move> allUserMoves;
	    protected Move bestMove;

	    
		/**
		 * Constructor for the computer AI player
		 * @param colour the colour of the AI, opposite to playerColour in Chess Game
		 * @throws FileNotFoundException when the opening isn't found for some reason
		 */
	    public ComputerPlayer(int colour) throws FileNotFoundException 
	    {
	        this.colour = colour;

		}
	    
	            
	    /** This method finds the best move for the AI and moves it
	     *  runs openings if there are still moves in the openings
	     */
	    public Move bestMove ()
	    {
			int realCurrentTurn = ChessGame.currentTurn;
	    	initMaxi(4);
	    	ChessGame.currentTurn = realCurrentTurn;
	    	return bestMove;
	    }
	    
	    /**
	     * @author Ivan Zhang
	     * Shuffles the moves in a list of moves to decrease predictability
	     * @param a list of moves to shuffle
	     * @return a shuffled list of moves
	     */
	    public ArrayList<Move> shuffle(ArrayList<Move> move)
	    {
	    	ArrayList<Move> shuffle = move;
			for (int moveNo = 0; moveNo < move.size() ; moveNo ++)
			{
				int position = (int)(Math.random() *move.size());
				Move tempMove = shuffle.get (position);
				shuffle.set (position, shuffle.get (moveNo));
				shuffle.set(moveNo, tempMove);
			}
			return shuffle;
	    }
	 
		
		/**
		 * Updates all the moves possible for each piece on the board
		 * @author Ivan Zhang
		 * @param board the board to check
		 * @return a shuffled list of moves to combat predictability
		 */
		public ArrayList<Move> allMoves(int colour) {
			
		ArrayList<Move> allMoves = new ArrayList<Move>();
			for (int row = 1; row < Board.length() - 1; row++)
				for (int col = 1; col < Board.width() - 1; col++)
					if (Board.get(row,col) != null && Board.get(row,col).colour == colour) {
						ArrayList<Move> possibleMoves = Board.get(row, col).allPossibleMoves();
						for(Move move:possibleMoves)
						{
							if(move.enPassant)
								System.out.println(move + " is an en passant");
							allMoves.add(move);
						}
					}
			//Shuffle 
			return shuffle(allMoves);
					}
		/**
		 * Root method to determine best move, recursively calls mini to determine worst move for opponent
		 * @param depth how many turns we look ahead
		 * @return the maximum score of the board at the level we make that move
		 */
		public int initMaxi(int depth)
		{
			if(depth == 0)
				return evaluateBoard();
			int max = -1000;
			
			ArrayList<Move> thisLevelComputerMoves = allMoves(colour);
			System.out.println(thisLevelComputerMoves);
			for(Move move: thisLevelComputerMoves)
			{
				Piece originalPos = Board.get(move.fromRow, move.fromCol);
				Piece pieceAtLocation = Board.get(move.toRow, move.toCol);
				Board.set(move.toRow, move.toCol, originalPos); 
				Board.get(move.fromRow, move.fromCol).moveOnBoard(move);
				Board.set(move.fromRow, move.fromCol, null); 
				int score = (mini(-1000, 1000, depth-1));
				Board.set(move.toRow, move.toCol, pieceAtLocation);
				originalPos.moveOnBoard(new Move(move.toRow, move.toCol, move.fromRow, move.fromCol));
				Board.set(move.fromRow, move.fromCol, originalPos); 
				if(score > max)
				{
					
					bestMove = move;
					max = score;
				}
				
			}
			return max;
			
		}
		/**
		 * Method to determine best move, recursively calls mini to determine worst move for opponent
		 * uses alpha beta pruning to eliminate redundant searching
		 * into trees with outcomes that have already been matched
		 * @param alpha the highest evaluation so far
		 * @param beta the lowest evaluation so far
		 * @param depth how many turns we look ahead
		 * @return the maximum score of the board at the level we make that move
		 */
		public int maxi(int alpha, int beta, int depth)
		{
			if(depth == 0)
				return evaluateBoard();
			int max = -1000;
			
			ArrayList<Move> thisLevelComputerMoves = allMoves(colour);
			for(Move move: thisLevelComputerMoves)
			{
				
				
				Piece originalPos = Board.get(move.fromRow, move.fromCol);
				Piece pieceAtLocation = Board.get(move.toRow, move.toCol);
				Board.set(move.toRow, move.toCol, originalPos); 
				Board.get(move.fromRow, move.fromCol).moveOnBoard(move);
				Board.set(move.fromRow, move.fromCol, null); 
				int score = (mini(alpha, beta, depth-1));
				Board.set(move.toRow, move.toCol, pieceAtLocation);
				originalPos.moveOnBoard(new Move(move.toRow, move.toCol, move.fromRow, move.fromCol));
				Board.set(move.fromRow, move.fromCol, originalPos); 
				if(score >= beta)
				{
					return beta; //Eliminate fail trees
				}
				if(score>alpha)
				{
					alpha = score; 
				}

				}
			
			return max;
			
		}
		
		/**
		 * A method to determine the worst move that can be made by the opponent
		 * @param alpha the highest evaluation so far
		 * @param beta the lowest evaluation so far
		 * @param depth how many turns we look ahead		 
		 * @return
		 */
		public int mini(int alpha, int beta, int depth)
		{
			
			if(depth ==0)
				return -evaluateBoard();
			int min = 1000;
			
			ArrayList<Move> playerMoves = allMoves(1-colour);
			for(Move move: playerMoves)
			{
				Piece originalPos = Board.get(move.fromRow, move.fromCol);
				Piece pieceAtLocation = Board.get(move.toRow, move.toCol);
				Board.set(move.toRow, move.toCol, originalPos); 
				Board.get(move.fromRow, move.fromCol).moveOnBoard(move);
				Board.set(move.fromRow, move.fromCol, null); 
				ChessGame.currentTurn = 1-ChessGame.currentTurn;
				int score = (maxi(alpha, beta, depth-1));
				Board.set(move.toRow, move.toCol, pieceAtLocation);
				originalPos.moveOnBoard(new Move(move.toRow, move.toCol, move.fromRow, move.fromCol));
				Board.set(move.fromRow, move.fromCol, originalPos); 
				if(score<=alpha)
				{
					return alpha;
				}
				if(score<beta)
				{
	
					beta = score;
				}

				
			}
			return min;
		}
		
		
		/**
		 * A method to find the king's position to use when evaluating the board
		 * @param colour the colour of the king piece to find, usually called to find the 
		 * player colour's king
		 * @return an integer array of the row and column of the king
		 */
		public int[] findKingPos(int colour, Piece[][] board)
		{
			int[] kingPos = new int[2];
			for(int row = 1; row<9 ; row++)
				for(int col = 1; col<9; col++)
					if(board[row][col] != null)
						if(board[row][col].colour == colour && board[row][col].getType() == 200)
						{
							kingPos[0] = row;
							kingPos[1] = col;
						}
			
			return kingPos;
		}
		
		/**
		 * @author Ivan Zhang
		 * Evaluates the board and returns an integer based on
		 * Whether there is a checked  king +200 for player, -200 for opponent
		 * Counts the "types" on the board (pawn = 1, bishop = 3, knight = 4, rook = 5, queen = 8, king = 200 + for computer, - for player
		 * Counts the number of possible moves available +1 for each if its computer's moves, -1 for each of its player's moves
		 * If special moves were working.
		 * @return
		 */
		public int evaluateBoard()
		{
			int []otherKingPos = findKingPos(1-colour, Board.getBoard());
			int []thisKingPos = findKingPos(colour, Board.getBoard());

			int total = 0;
			
			//Check if the player's king is in check
			if(Board.get(otherKingPos[0], otherKingPos[1]).isCheck(1-colour))
				total+=200;
			
			//Check if our king is king check
			if(Board.get(thisKingPos[0], thisKingPos[1]).isCheck(1-colour))
				total-=200;

			
			for (int row = 1; row <= 8; row++)
				for (int col = 1; col <= 8; col++)
					if (Board.get(row, col) != null)
					{
						if (Board.get(row, col).colour == colour)
						{
							total += Board.get(row, col).getType();
							total += Board.get(row, col).allPossibleMoves().size();

						}
						else if(Board.get(row, col).colour == 1-colour)
						{
							total -= Board.get(row, col).getType();
							total -= Board.get(row, col).allPossibleMoves().size();
						}
					}
			
			ArrayList<Move> playerMoves = allMoves(1-colour);
			for(Move move : playerMoves)
			{
				if(move.isSpecialMove());
					total-=2;
			}
			
			ArrayList<Move> computerMoves = allMoves(colour);
			for(Move move : computerMoves)
			{
				if(move.isSpecialMove());
					total+=2;
			}

			// Take in the value of the pieces
			return total;
		}
	}
