import javax.swing.ImageIcon;

import java.awt.Point;
import java.util.*;


/** Creates a bishop piece for a game of chess
 * Each piece determines all of its legal moves, 
 * all possible moves and bishop keeps track of 
 * its points
 * @author Chanuk and Ivan
 *
 */
public class Bishop extends Piece
{
	//Standard bishop point identifier
    protected static int points = 3;
    protected int noOfMoves;
     
    /** Constructs a Bishop object
     * @param row row of the piece
     * @param col column of the piece
     * @param colour the colour of the piece(Donald or Wok)
     */
    public Bishop (int row, int col, int colour)
    {
        super (row, col, colour);
     // Load up the appropriate image file for this piece
     		StringBuilder imageFileName = new StringBuilder();
     		 if(colour == 0)
             {
             	imageFileName.append(colour);
             	imageFileName.append(points);
             	imageFileName.append(".gif");
             }
             else
             {
            	 imageFileName.append(colour);
              	imageFileName.append(points);
              	imageFileName.append(".png");             
              }
     		imageFileName.append("images\\");
     		this.image = new ImageIcon(imageFileName.toString()).getImage();
     		
     		// Set the size of the piece based on the image size
     		setSize(image.getWidth(null), image.getHeight(null));
       
    }

     
     
    /** Determines whether the indicated move is valid for a bishop
     * @param toRow The row of the position to check
     * @param toCol The column of the position to check
     * @return whether the move is legal true if it is, false if it isn't
     */
    public boolean isLegalMove(int toRow, int toCol)
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
    	
        
        for(int dRow = -1 ; dRow<=1; dRow+=2)
        	for(int dCol= -1; dCol<=1; dCol+=2)
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
        return false;
        
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
     * Tries all combination of moves and stores them into a list if they're legal
     * Legal meaning it is within their ruleset and it doesn't check their king
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
         
       return moves;
    }

}