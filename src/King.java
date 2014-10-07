import javax.swing.ImageIcon;

import java.awt.Point;
import java.util.*;


/** The king class extends the piece class
 * This class creates a king piece for a chess game
 * @author Ivan Zhang
 *
 */
public class King extends Piece
{
    protected static int points = 200;
    protected int noOfMoves;
      
    /** Constructs a King object
     *
     * @param row row of the piece
     * @param col column of the piece
     * @param colour the colour of the piece
     */
    public King(int row, int col, int colour)
    {
        super(row, col, colour);  
        noOfMoves = 0;
     // Load up the appropriate image file for this piece
     		String imageFileName = "" + colour +  points + ".png";
     		imageFileName = "images\\" + imageFileName;
     		this.image = new ImageIcon(imageFileName).getImage();
     		
     		// Set the size of the piece based on the image size
     		setSize(image.getWidth(null), image.getHeight(null));
     		
     		
     		kingPos[colour][0] = row;
     		kingPos[colour][1] = col;
    }
    
   
    public String toString()
    {
    	return ("King: " + super.toString());
    }
     
    /** Determines if the indicated move is valid for a king
     * @author Ivan Zhang
     */
    public boolean isLegalMove (int toRow, int toCol)
    {	
    	
    	//Disregard positions off the board (This may happen in allPossibleMoves())
    	if(toRow < 1 || toRow > 8 || toCol < 1 || toCol > 8)
    		return false;

    	//Make sure we're not going onto a friendly piece
    	if (board[toRow][toCol] != null && board[toRow][toCol].colour == colour)
			return false;
    	
    	
    	for(int dRow = -1; dRow <= 1; dRow++ )
    	{
    		for(int dCol =-1; dCol <=1; dCol++)
    		{
    			//If there is a friendly piece there or if there is a border piece
    			if((board[toRow][toCol] != null && board[toRow][toCol].colour == colour) || (board[toRow][toCol] != null&& board[toRow][toCol].getType() == -1))
    				return false;
    			else if(toRow == row +dRow && toCol == col + dCol)
    			{
    				
    				//See if it's a check, put back pieces after
    				Piece pieceAtLocation = board[toRow][toCol];
    				board[toRow][toCol] = new King(toRow, toCol, colour);
    				board[toRow][toCol].noOfMoves = this.noOfMoves+1;
    				board[row][col] = null;
    				if(board[kingPos[colour][0]][kingPos[colour][1]].isCheck(bottomColour))
    				{
    					board[toRow][toCol] = pieceAtLocation;
    					board[row][col] = new King(row, col, colour);
        				board[row][col].noOfMoves = this.noOfMoves;
    					return false;
    				}
    				else
    				{
    					board[toRow][toCol] = pieceAtLocation;
    					board[row][col] = new King(row,col,colour);
        				board[row][col].noOfMoves = this.noOfMoves;
    					return true;
    				}
    			}
   
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
     * Checks all possible moves for this piece at the moment
     * @author Ivan Zhang
     * @return the possible moves that can be made by this piece
     */
    public ArrayList<Move> allPossibleMoves()
    {
        ArrayList <Move> moves = new ArrayList<Move>();
         
         
        if (isLegalMove (row - 1, col ))
            moves.add(new Move(row, col, row - 1, col));
        if (isLegalMove (row - 1, col - 1 ))
            moves.add(new Move(row, col, row - 1, col - 1));
        if (isLegalMove (row - 1, col + 1 ))
            moves.add(new Move(row, col, row - 1, col + 1));
        if (isLegalMove (row, col + 1 ))
            moves.add(new Move(row, col, row, col + 1));
        if (isLegalMove (row, col - 1 ))
            moves.add(new Move(row, col, row, col - 1));
        if (isLegalMove (row + 1, col + 1 ))
            moves.add(new Move(row, col, row + 1, col + 1));
        if (isLegalMove (row + 1, col ))
            moves.add(new Move(row, col, row + 1, col));
        if (isLegalMove (row + 1, col - 1 ))
            moves.add(new Move(row, col, row + 1, col - 1));
        
        return moves;  
        		}
    
    /** Overriding moveOnBoard to update the king's position
	 * @param the move that was made
	 */
	public void moveOnBoard (Move move)    {
    	
		
		super.moveOnBoard(move);
 		kingPos[colour][0] = move.toRow;
 		kingPos[colour][1] = move.toCol;
    	
    }
    
 
}