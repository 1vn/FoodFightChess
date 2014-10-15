import javax.swing.ImageIcon;

import java.util.*;


/** Creates a queen piece for a game of chess
 *  Each piece determines all of its legal moves, 
 * all possible moves and bishop keeps track of 
 * its points
 * @author Ivan Zhang and Chaunk De Silva
 */
public class Queen extends Piece
{
	protected static int points = 8;
	protected int noOfMoves;

	/** Constructs a Queen object
	 *
	 * @param x row of the piece
	 * @param y column of the piece
	 * @param colour the colour of the piece
	 */
	public Queen(int x, int y, int colour)
	{
		super(x, y, colour);   
		noOfMoves = 0;
		
		// Load up the appropriate image file for this piece
				String imageFileName = "" + colour +  points + ".png";
				imageFileName = "images\\" + imageFileName;
				this.image = new ImageIcon(imageFileName).getImage();
				
				// Set the size of the piece based on the image size
				setSize(image.getWidth(null), image.getHeight(null));
	}


	/** Determines if the indicated move is valid for a Queen
	 * @author Ivan Zhang
	 * @param toRow the row to move to
	 * @param toCol the column to move to
	 */
	public boolean isLegalMove (int toRow, int toCol)
	{
		//Disregard positions off the board (This may happen in allPossibleMoves())
    	if(toRow < 1 || toRow > 8 || toCol < 1 || toCol > 8)
    		return false;

    	//Make sure we're not going onto a friendly piece
    	if (board[toRow][toCol] != null && board[toRow][toCol].colour == colour)
			return false;
    	
    	//Or kings
    	if(board[toRow][toCol]!= null && board[toRow][toCol].getType() == 9)
    		return false;
    
    	
    	//Check in all directions for the coordinate to move to
        for(int dRow = -1 ; dRow<=1; dRow++)
        	for(int dCol= -1; dCol<=1; dCol++)
        	{
        		int tempRow = row+dRow;
        		int tempCol = col+dCol;
        		while((tempRow<row || tempRow>row || tempCol<col || tempCol > col))
        		{
        		if (tempRow == toRow && tempCol == toCol)
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
                //Handle "Border pieces"
                if (board [tempRow][tempCol] != null && board [tempRow][tempCol].getType() != -1)
                        break;
                
                if (board [tempRow][tempCol] != null)
                    break;
                
                tempRow+=dRow;
                tempCol+=dCol;
        		
        	}
        	
      }
        //If not found
        return false;
	}
	
	 public String toString()
	    {
	    	return "Queen: "+super.toString();
	    }

	/**
	 * Returns the points of the current piece
	 * @return points the value of the piece
	 */
	public int getType()
	{
		return points;
	}

	
	 /**
     * Checks all possible moves for this piece at the moment
     * @author Chanuk De Silva
     * @return the possible moves that can be made by this piece
     */
	public ArrayList<Move> allPossibleMoves()
    {
        ArrayList <Move> moves = new ArrayList<Move>();
         
        // south east
        for (int drow = row + 1; drow <= 8; drow ++)
            for (int dcol = col + 1; dcol <= 8; dcol ++)
            {
                if (isLegalMove(drow, dcol))
                    moves.add(new Move (row, col, drow, dcol));
            }
         
        // south west
        for (int drow = row + 1; drow <= 8; drow ++)
            for (int dcol = col - 1; dcol >= 1; dcol --)
            {
                if (isLegalMove(drow, dcol))
                    moves.add(new Move (row, col, drow, dcol));
            }
         
        // north west
        for (int drow = row - 1; drow >= 1; drow --)
            for (int dcol = col - 1; dcol >= 1; dcol --)
            {
                if (isLegalMove(drow, dcol))
                    moves.add(new Move (row, col, drow, dcol));
            }
         
        // north east
        for (int drow = row - 1; drow >= 1; drow --)
            for (int dcol = col + 1; dcol <= 8; dcol ++)
            {
                if (isLegalMove(drow, dcol))
                    moves.add(new Move (row, col, drow, dcol));
            }
         
        // North
        for (int drow = row - 1; drow >= 1; drow --)
        {
            if (isLegalMove(drow, col))
                moves.add(new Move (row, col, drow, col));
        }
        // South
        for (int drow = row + 1; drow <= 8; drow ++)
        {
            if (isLegalMove(drow, col))
                moves.add(new Move (row, col, drow, col));
        }
        // East
        for (int dcol = col + 1; dcol <= 8; dcol ++)
        {
            if (isLegalMove(row, dcol))
                moves.add(new Move (row, col, row, dcol));
        }
         
        // West
        for (int dcol = col - 1; dcol >= 1; dcol --)
        {
            if (isLegalMove(row, dcol))
                moves.add(new Move (row, col, row, dcol));
        }
        return moves;
        }
	

}