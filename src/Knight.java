import javax.swing.ImageIcon;

import java.util.*;

/** Creates a knight piece for a game of chess
 *  Each piece determines all of its legal moves, 
 * all possible moves and bishop keeps track of 
 * its points
 * @author Ivan Zhang
 *
 */
public class Knight extends Piece {
	protected static int points = 4;
	protected int noOfMoves;

	/**
	 * Constructs a Knight object
	 * 
	 * @param x
	 *            row of the piece
	 * @param y
	 *            column of the piece
	 * @param colour
	 */
	public Knight(int x, int y, int colour) {
		super(x, y, colour);
		// Load up the appropriate image file for this piece
		String imageFileName = "" + colour + points + ".png";
		imageFileName = "images\\" + imageFileName;
		this.image = new ImageIcon(imageFileName).getImage();

		// Set the size of the piece based on the image size
		setSize(image.getWidth(null), image.getHeight(null));
	}

	/**
	 * Determines whether the indicated move is valid for a knight
	 * @author Ivan Zhang
	 * @param toRow the row to move to
	 * @param toCol the column to move to
	 * @return whether a legal move can be made
	 */
	public boolean isLegalMove(int toRow, int toCol) {
		
		//Disregard positions off the board (This may happen in allPossibleMoves())
    	if(toRow < 1 || toRow > 8 || toCol < 1 || toCol > 8)
    		return false;

    	//Make sure we're not going onto a friendly piece
    	if (board[toRow][toCol] != null && board[toRow][toCol].colour == colour)
			return false;
    	
    	//Or kings
    	if(board[toRow][toCol]!= null && board[toRow][toCol].getType() == 9)
    		return false;

		//Check vertically to see if the toRow and toCol are legal
		for(int checkRow = row-2; checkRow <= row+2; checkRow+=4)
		{
			for(int checkCol = col-1; checkCol <=col+1; checkCol+=2)
				if(checkRow == toRow&&checkCol == toCol)
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
		
		//Check horizontal positions
		for(int checkRow = row-1; checkRow<=row+1; checkRow+=2)
		{
			for(int checkCol = col-2; checkCol<=col+2;checkCol+=4)
			{
				if(checkRow == toRow&&checkCol == toCol)
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
		}
		
		return false;
	}

	/**
	 * Returns the points of the current piece
	 * 
	 * @return points the value of the piece
	 */
	public int getType() {
		return points;
	}

	/**
	 * @author Ivan Zhang
	 * Determines the list of possible moves by this piece
	 * @return the list of possible moves by this piece
	 */
	public ArrayList<Move> allPossibleMoves() {
		ArrayList<Move> moves = new ArrayList<Move>();

		// Due north
		if (row - 2 > 0 && col - 1 > 0 && isLegalMove(row - 2, col - 1))
			moves.add(new Move(row, col, row - 2, col - 1));
		if (row - 2 > 0 && col + 1 < 9 && isLegalMove(row - 2, col + 1))
			moves.add(new Move(row, col, row - 2, col + 1));
		// Due east
		if (row - 1 > 0 && col + 2 < 9 && isLegalMove(row - 1, col + 2))
			moves.add(new Move(row, col, row - 1, col + 2));
		if (row + 1 < 9 && col + 2 < 9 && isLegalMove(row + 1, col + 2))
			moves.add(new Move(row, col, row + 1, col + 2));
		// Due west
		if (row - 1 > 0 && col - 2 > 0 && isLegalMove(row - 1, col - 2))
			moves.add(new Move(row, col, row - 1, col - 2));
		if (row + 1 < 9 && col - 2 > 0 && isLegalMove(row + 1, col - 2))
			moves.add(new Move(row, col, row + 1, col - 2));
		// Due south
		if (row + 2 < 9 && col - 1 > 0 && isLegalMove(row + 2, col - 1))
			moves.add(new Move(row, col, row + 2, col - 1));
		if (row + 2 < 9 && col + 1 < 9 && isLegalMove(row + 2, col + 1))
			moves.add(new Move(row, col, row + 2, col + 1));

		return moves;
	}

}
