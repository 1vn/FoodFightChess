import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.*;

// http://chessprogramming.wikispaces.com
///https://github.com/Arwid/chess/blob/master/source/chess/algorithms/MiniMax.java
//http://www.jrgray.net/jrgray/chess/Chesspiece.html#_top_
// Reference site

/**
 * This abstract class creates pieces on a chess board Each piece will know its
 * location on the board, colour, number of moves, and the location of the king.
 * Each piece will be able to move through the board, look for checks and castle
 * through this clasas.
 * 
 * Ivan Zhang
 * 
 */
public abstract class Piece extends Rectangle {
	protected int colour; // 0 - White, 1 - Black
	protected boolean isCaptured;
	protected int row;
	protected int col;
	protected int noOfMoves;
	protected static Piece[][] board;
	protected Image image;
	protected static int bottomColour;
	protected boolean captured;

	protected boolean enPassantPossible;
	protected boolean castlingMove;

	// Integer arrays to keep track of the King's position
	// Values at index 0 are the Donald king's, index 1 are the Wok king's
	protected static int[][] kingPos;

	/**
	 * Creates a piece object with the Color (white, black) and records how much
	 * it is worth
	 * 
	 * @param colour
	 *            the color the player picked
	 * @param points
	 *            the number of points allocated for the piece
	 */
	public Piece(int row, int col, int colour) {
		super((col - 1) * 80, (row - 1) * 80, 80, 80);
		this.row = row;
		this.col = col;
		this.colour = colour;
	}

	/**
	 * Initialize king points to avoid nullPointerException
	 */
	public static void intializeKingPoints() {
		kingPos = new int[2][2];
	}

	public static void setPlayerColour(int playerColour) {
		bottomColour = playerColour;
	}

	/**
	 * Finds and returns the colour of the piece
	 * 
	 * @return the colour of the piece
	 */
	public int getColour() {
		return colour;
	}

	/**
	 * Finds if a castle is possible
	 * 
	 * @author Ivan Zhang
	 * 
	 */
	public boolean isCastlePossible(int toRow, int toCol) {
		if (noOfMoves == 0) {
			// Castling with the left rook
			if (toRow == row && toCol == col - 2
					&& board[toRow][col - 1] == null
					&& board[toRow][col - 2] == null
					&& board[toRow][col - 3] == null
					&& board[toRow][col - 4] != null
					&& board[toRow][col - 4].getType() == 5)
				return true;
			// Castling with the right rook
			if (toRow == row && toCol == col + 2
					&& board[toRow][col + 1] == null
					&& board[toRow][col + 2] == null
					&& board[toRow][col + 3] != null
					&& board[toRow][col + 3].getType() == 5)
				return true;
		}
		return false;
	}

	/**
	 * Syncs the Piece reference board with the real board
	 * 
	 * @param board
	 */
	public static void setBoard(Piece[][] board) {
		Piece.board = board;
	}

	/**
	 * Draws a card in a Graphics context
	 * 
	 * @param g
	 *            Graphics to draw the card in
	 */
	public void draw(Graphics g) {
		// Get xPosition and yPosition derived from the row and column times
		// square size(80)
		int xPos = (col - 1) * 80;
		int yPos = (row - 1) * 80;
		g.drawImage(image, xPos, yPos, null);
	}

	/**
	 * Draws a piece in a Graphics context based on its x,y
	 * 
	 * @param g
	 *            Graphics to draw the card in
	 */
	public void drawDrag(Graphics g) {

		g.drawImage(image, x, y, null);
	}

	/**
	 * Check to see if the point is on the piece
	 */
	public boolean contains(Point point) {
		int distance = (int) (Math.sqrt(Math.pow(point.x - x - 40, 2)
				+ Math.pow(point.y - y - 40, 2)));
		return (distance < 80 / 2);
	}

	/**
	 * This method helps drag pieces
	 * 
	 * @param initialPos
	 *            the initial position of the piece
	 * @param finalPos
	 *            the final position of the piece
	 */
	public void move(Point initialPos, Point finalPos) {
		translate(finalPos.x - initialPos.x, finalPos.y - initialPos.y);

	}

	/**
	 * Method for moving a piece's row and column on the board
	 * 
	 * @param move
	 *            the move to make
	 */
	public void moveOnBoard(Move move) {
		this.row = move.toRow;
		this.col = move.toCol;
	}

	/**
	 * Method for moving a piece's row and column on the board
	 * 
	 * @author Ivan Zhang
	 * @param row
	 *            the row to move to
	 * @param col
	 *            the col to move to
	 */
	public void moveOnBoard(int row, int col) {
		this.row = row;
		this.col = col;
	}

	/**
	 * A method to display the piece's class , row and column
	 * 
	 * @author Ivan Zhang
	 */
	public String toString() {
		return " Row " + row + " Col: " + col + " noOfMoves: " + noOfMoves;
	}

	/**
	 * Checks if the king is checked Since a King cannot check another king,
	 * this always returns false
	 * 
	 * @author Ivan Zhang
	 * @param board
	 *            the board containing all pieces
	 * @param playerColour
	 *            to keep track of pieces at the bottom
	 * @return false because a king cannot check another king
	 */
	public boolean isCheck(int playerColour) {

		// KNIGHTS CHECKING KING
		int checkRow = row - 2;
		int checkCol = col - 1;
		if (checkRow > 0 && checkCol > 0 && checkRow >= 1 && checkCol >= 1) {
			if (board[checkRow][checkCol] != null)
				if (board[checkRow][checkCol].getType() == 4
						&& board[checkRow][checkCol].getColour() != colour)
					return true;

		}

		checkCol = col + 1;
		if (checkCol < 9 && checkRow >= 1 && checkCol <= 8) {
			if (board[checkRow][checkCol] != null)
				if (board[checkRow][checkCol].getType() == 4
						&& board[checkRow][checkCol].getColour() != colour)
					return true;

		}

		checkRow = row + 2;
		checkCol = col - 1;
		if (checkRow <= 8 && checkCol >= 1) {
			if (board[checkRow][checkCol] != null)
				if (board[checkRow][checkCol].getType() == 4
						&& board[checkRow][checkCol].getColour() != colour)
					return true;

		}

		checkCol = col + 1;
		if (checkRow <= 8 && checkCol >= 1) {
			if (board[checkRow][checkCol] != null)
				if (board[checkRow][checkCol].getType() == 4
						&& board[checkRow][checkCol].getColour() != colour)
					return true;

		}

		checkRow = row - 1;
		checkCol = col + 2;
		if (checkRow >= 1 && checkCol <= 8) {
			if (board[checkRow][checkCol] != null)
				if (board[checkRow][checkCol].getType() == 4
						&& board[checkRow][checkCol].getColour() != colour)
					return true;

		}

		checkRow = row + 1;
		if (checkRow <= 8 && checkCol <= 8) {
			if (board[checkRow][checkCol] != null)
				if (board[checkRow][checkCol].getType() == 4
						&& board[checkRow][checkCol].getColour() != colour)
					return true;

		}

		checkRow = row - 1;
		checkCol = col - 2;
		if (checkRow >= 1 && checkCol >= 1) {
			if (board[checkRow][checkCol] != null)
				if (board[checkRow][checkCol].getType() == 4
						&& board[checkRow][checkCol].getColour() != colour)
					return true;

		}

		checkRow = row + 1;
		if (checkRow <= 8 && checkCol >= 1) {
			if (board[checkRow][checkCol] != null)
				if (board[checkRow][checkCol].getType() == 4
						&& board[checkRow][checkCol].getColour() != colour)
					return true;

		}

		// Checks if a rook or queen is in a position to check the king
		// Column constant, row increases
		for (int toRow = row + 1; toRow <= 8; toRow++) {
			if (board[toRow][col] == null) {

			} else if (board[toRow][col].getType() == 5
					&& board[toRow][col].getColour() != colour
					|| board[toRow][col].getType() == 8
					&& board[toRow][col].getColour() != colour)
				return true;
			else
				break;

		}

		// Column constant, row decreases
		for (int toRow = row - 1; toRow >= 1; toRow--) {
			if (board[toRow][col] == null) {
			} else if (board[toRow][col].getType() == 5
					&& board[toRow][col].getColour() != colour
					|| board[toRow][col].getType() == 8
					&& board[toRow][col].getColour() != colour) {
				return true;
			} else {
				break;
			}

		}

		// Row constant, column increases
		for (int toCol = col + 1; toCol <= 8; toCol++) {
			if (board[row][toCol] == null) {

			} else if (board[row][toCol].getType() == 5
					&& board[row][toCol].getColour() != colour
					|| board[row][toCol].getType() == 8
					&& board[row][toCol].getColour() != colour)
				return true;
			else
				break;

		}

		// x constant, y decreases
		for (int toCol = col - 1; toCol >= 1; toCol--) {
			if (board[row][toCol] == null) {

			} else if (board[row][toCol].getType() == 5
					&& board[row][toCol].getColour() != colour
					|| board[row][toCol].getType() == 8
					&& board[row][toCol].getColour() != colour)
				return true;
			else
				break;

		}

		// Checks if a pawn checks the king
		// Bottom pawn checking top king

		if (row < 9 && col < 9 && board[row + 1][col + 1] != null
				&& board[row + 1][col + 1].getType() == 1
				&& board[row + 1][col + 1].getColour() == bottomColour
				&& bottomColour != colour || row < 9 && col > 0
				&& board[row + 1][col - 1] != null
				&& board[row + 1][col - 1].getType() != -1
				&& board[row + 1][col - 1].getType() == 1
				&& board[row + 1][col - 1].getColour() == bottomColour
				&& bottomColour != colour)
			return true;

		// Top pawn checking bottom king
		if (row > 0 && col < 9 && board[row - 1][col + 1] != null
				&& board[row - 1][col + 1].getType() == 1
				&& board[row - 1][col + 1].getColour() == 1 - bottomColour
				&& colour != 1 - bottomColour || row > 0 && col > 0
				&& board[row - 1][col - 1] != null
				&& board[row - 1][col - 1].getType() == 1
				&& board[row - 1][col - 1].getColour() == 1 - bottomColour
				&& colour != 1 - bottomColour)
			return true;

		// Checks if a bishop or queen is in a position to check the king
		// Checks diagonally north east
		int toRow = row + 1;
		int toCol = col + 1;
		// Checks until a border piece is reached
		while (toRow <= 8 && toCol <= 8) {
			if (board[toRow][toCol] == null) {
			} else if (board[toRow][toCol].getType() == 3
					&& board[toRow][toCol].getColour() != colour
					|| board[toRow][toCol].getType() == 8
					&& board[toRow][toCol].getColour() != colour)
				return true;
			else if (board[toRow][toCol] != null)
				break;
			toRow++;
			toCol++;
		}

		// Checks diagonally north west
		toRow = row - 1;
		toCol = col + 1;
		while (toRow >= 1 && toCol <= 8) {
			if (board[toRow][toCol] == null) {
			} else if (board[toRow][toCol].getType() == 3
					&& board[toRow][toCol].getColour() != colour
					|| board[toRow][toCol].getType() == 8
					&& board[toRow][toCol].getColour() != colour)
				return true;
			else if (board[toRow][toCol] != null)
				break;
			toRow--;
			toCol++;
		}

		// Checks diagonally north west
		toRow = row - 1;
		toCol = col - 1;
		while (toRow >= 1 && toCol >= 1) {
			if (board[toRow][toCol] == null) {
			} else if (board[toRow][toCol].getType() == 3
					&& board[toRow][toCol].getColour() != colour
					|| board[toRow][toCol].getType() == 8
					&& board[toRow][toCol].getColour() != colour)
				return true;
			else if (board[toRow][toCol] != null)
				break;
			toRow--;
			toCol--;
		}

		// Checks diagonally south east
		toRow = row + 1;
		toCol = col - 1;
		while (toRow <= 8 && toCol >= 1) {
			if (board[toRow][toCol] == null) {
			} else if (board[toRow][toCol].getType() == 3
					&& board[toRow][toCol].getColour() != colour
					|| board[toRow][toCol].getType() == 8
					&& board[toRow][toCol].getColour() != colour)
				return true;
			else if (board[toRow][toCol] != null)
				break;
			toRow++;
			toCol--;
		}

		return false;
	}

	/**
	 * Check if the piece is going to be promoted
	 * @param toRow the row to check
	 * @param toCol the col to check
	 * @return whether it is a promotion
	 */
	 public boolean isPromote(int toRow, int toCol)
	    {
	    	//Disregard positions off the board (This may happen in allPossibleMoves())
	    	if(toRow != 8 || toRow != 1 || toCol != 1 || toCol != 8)
	    		return false;
	    	
	    	//Make sure we're not going onto a friendly piece
	    	if (board[toRow][toCol] != null && board[toRow][toCol].colour == colour)
				return false;
	    	
	    	if(this.getType() != 1)
	    		return false;
	    	
	    	if(colour == bottomColour)
	    	{
	    		if(toRow == 1 && toRow == row-1);
	    		{
	    			// See if it's a check, put back pieces after
					Piece pieceAtLocation = board[toRow][toCol];
					Piece originalPiece = board[row][col];
					board[toRow][toCol] = originalPiece;
					board[row][col] = null;
					if (board[kingPos[colour][0]][kingPos[colour][1]]
							.isCheck(bottomColour)) {
						board[toRow][toCol] = pieceAtLocation;
						board[row][col] = originalPiece;
						return false;
					} else {
						board[toRow][toCol] = pieceAtLocation;
						board[row][col] = originalPiece;
						return true;
					}
	    		}
	    	}
	    	
	    	if(colour == 1-bottomColour)
	    	{
	    		if(toRow == 8 && toRow == row+1);
	    		{
	    			// See if it's a check, put back pieces after
					Piece pieceAtLocation = board[toRow][toCol];
					Piece originalPiece = board[row][col];
					board[toRow][toCol] = originalPiece;
					board[row][col] = null;
					if (board[kingPos[colour][0]][kingPos[colour][1]]
							.isCheck(bottomColour)) {
						board[toRow][toCol] = pieceAtLocation;
						board[row][col] = originalPiece;
						return false;
					} else {
						board[toRow][toCol] = pieceAtLocation;
						board[row][col] = originalPiece;
						return true;
					}
	    		}
	    	}
	    	return false;
	    }
	 
	 /**
	  * Checks if the piece can en passant at that location
	  * @param toRow the row to en passant
	  * @param toCol the row to en passant
	  * @return whether an en passant is possible
	  */
	public boolean isEnPassant(int toRow, int toCol) {
		if (noOfMoves < 2)
			return false;
		if (toRow < 0 || toRow > 8 || toCol < 0 || toCol > 8)
			return false;
		if (board[toRow][toCol] != null && board[row][toCol] != null
				&& board[row][toCol].colour == colour)
			return false;

		// Check for en passant for bottom pieces
		if (colour == bottomColour) {
			if (toRow == row - 1 && toCol == col - 1
					&& board[toRow + 1][toCol] != null
					&& board[toRow + 1][toCol].colour == 1 - colour
					&& board[toRow + 1][toCol].noOfMoves == 1) // Left side
																// diagonal
			{
				// See if it's a check, put back pieces after
				Piece originalPiece = board[row][col];
				board[toRow][toCol] = originalPiece;
				noOfMoves++;
				board[row][col] = null;
				if (board[kingPos[colour][0]][kingPos[colour][1]]
						.isCheck(bottomColour)) {
					board[row][col] = originalPiece;
					noOfMoves--;
					return false;
				} else {
					board[row][col] = originalPiece;
					noOfMoves--;
					return true;
				}

			}

			if (toRow == row - 1 && toCol == col + 1
					&& board[toRow + 1][toCol] != null
					&& board[toRow + 1][toCol].colour == 1 - colour
					&& board[toRow + 1][toCol].noOfMoves == 1) // Right side
																// diagonal
			{
				// See if it's a check, put back pieces after
				Piece originalPiece = board[row][col];
				board[toRow][toCol] = originalPiece;
				noOfMoves++;
				board[row][col] = null;
				if (board[kingPos[colour][0]][kingPos[colour][1]]
						.isCheck(bottomColour)) {
					board[row][col] = originalPiece;
					noOfMoves--;
					return false;
				} else {
					board[row][col] = originalPiece;
					noOfMoves--;
					return true;
				}

			}
		}
		// Check for en passant for non pieces
		if (colour == 1- bottomColour) {
			if (toRow == row + 1 && toCol == col - 1
					&& board[toRow - 1][toCol] != null
					&& board[toRow - 1][toCol].colour == 1 - colour
					&& board[toRow - 1][toCol].noOfMoves == 1) // Left side
																// diagonal
			{
				// See if it's a check, put back pieces after
				Piece originalPiece = board[row][col];
				board[toRow][toCol] = originalPiece;
				noOfMoves++;
				board[row][col] = null;
				if (board[kingPos[colour][0]][kingPos[colour][1]]
						.isCheck(bottomColour)) {
					board[row][col] = originalPiece;
					noOfMoves--;
					return false;
				} else {
					board[row][col] = originalPiece;
					noOfMoves--;
					return true;
				}

			}

			if (toRow == row + 1 && toCol == col + 1
					&& board[toRow - 1][toCol] != null
					&& board[toRow - 1][toCol].colour == 1 - colour
					&& board[toRow - 1][toCol].noOfMoves == 1) // Right side
																// diagonal
			{
				// See if it's a check, put back pieces after
				Piece originalPiece = board[row][col];
				board[toRow][toCol] = originalPiece;
				noOfMoves++;
				board[row][col] = null;
				if (board[kingPos[colour][0]][kingPos[colour][1]]
						.isCheck(bottomColour)) {
					board[row][col] = originalPiece;
					noOfMoves--;
					return false;
				} else {
					board[row][col] = originalPiece;
					noOfMoves--;
					return true;
				}

			}
		}
		return false;
	}

	public abstract boolean isLegalMove(int dx, int dy);

	public abstract int getType();

	public abstract ArrayList<Move> allPossibleMoves();

}
