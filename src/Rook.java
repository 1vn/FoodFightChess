import javax.swing.ImageIcon;

import java.util.*;

/** Creates a rook piece for a game of chess
 *  Each piece determines all of its legal moves, 
 * all possible moves and bishop keeps track of 
 * its points
 * @author Ivan Zhang
 */
public class Rook extends Piece
{
    protected static int points = 5;
    protected int noOfMoves;
     
    /** Constructs a Rook object
     *
     * @param x row of the piece
     * @param y column of the piece
     * @param colour the colour of the piece
     */
    public Rook(int x, int y, int colour)
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

     
    /**
     * Returns the points of the current piece
     * @return points the value of the piece
     */
    public int getType()
    {
    return points;
    }
     
    /** Determines if the indicated move is valid for a Rook
     *
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
    	
    	 // The row is the same, y is lower than dy
        if (toRow == row && col < toCol)
        {
            for (int dCol = col + 1; dCol <= toCol; dCol ++)
            {
                if (dCol == toCol)
                {
                	// See if it's a check, put back pieces after
					Piece pieceAtLocation = board[toRow][toCol];
					Piece originalPiece = board[row][col];
					board[toRow][toCol] = originalPiece;
					originalPiece.noOfMoves++;
					board[row][col] = null;
					if (board[kingPos[colour][0]][kingPos[colour][1]]
							.isCheck(bottomColour)) {
						board[toRow][toCol] = pieceAtLocation;
						board[row][col] = originalPiece;
						originalPiece.noOfMoves--;
						return false;
					} else {
						board[toRow][toCol] = pieceAtLocation;
						board[row][col] = originalPiece;
						originalPiece.noOfMoves--;
						return true;
					}               
        		}

                 
                // If there is a piece blocking the path, it stops the player from making that move
                if (board [row][dCol]!= null)
                    return false;
               
            }
        }
         
        // The row is the same, y is greater than dy
        if (toRow == row && col > toCol)
        {
            for (int dCol = col -1; toCol <= dCol; dCol --)
            {
                if (dCol == toCol)
                {
                	// See if it's a check, put back pieces after
					Piece pieceAtLocation = board[toRow][toCol];
					Piece originalPiece = board[row][col];
					board[toRow][toCol] = originalPiece;
					originalPiece.noOfMoves++;
					board[row][col] = null;
					if (board[kingPos[colour][0]][kingPos[colour][1]]
							.isCheck(bottomColour)) {
						board[toRow][toCol] = pieceAtLocation;
						board[row][col] = originalPiece;
						originalPiece.noOfMoves--;
						return false;
					} else {
						board[toRow][toCol] = pieceAtLocation;
						board[row][col] = originalPiece;
						originalPiece.noOfMoves--;
						return true;
					}            
            		
                }
                 
             // If there is a piece blocking the path, it stops the player from making that move
                if (board [row][dCol]!= null)
                    return false;
            }
        }
         
        // The column is the same, x is lower than dx
        if (toCol == col && toRow > row)
        {
            for (int dRow = row + 1 ; dRow <= toRow; dRow ++)
            {
                if (dRow == toRow)
                {

                	// See if it's a check, put back pieces after
					Piece pieceAtLocation = board[toRow][toCol];
					Piece originalPiece = board[row][col];
					board[toRow][toCol] = originalPiece;
					originalPiece.noOfMoves++;
					board[row][col] = null;
					if (board[kingPos[colour][0]][kingPos[colour][1]]
							.isCheck(bottomColour)) {
						board[toRow][toCol] = pieceAtLocation;
						board[row][col] = originalPiece;
						originalPiece.noOfMoves--;
						return false;
					} else {
						board[toRow][toCol] = pieceAtLocation;
						board[row][col] = originalPiece;
						originalPiece.noOfMoves--;
						return true;
					}
    			}
             // If there is a piece blocking the path, it stops the player from making that move
                if (board [dRow][col]!= null)
                    return false;
            }
        }
         
        // The column is the same, row is greater than dx
        if (toCol == col && toRow < row)
        {
            for (int dRow = row - 1 ; dRow >= toRow; dRow --)
            {
                if (dRow == toRow)
                {

                	// See if it's a check, put back pieces after
					Piece pieceAtLocation = board[toRow][toCol];
					Piece originalPiece = board[row][col];
					board[toRow][toCol] = originalPiece;
					originalPiece.noOfMoves++;
					board[row][col] = null;
					if (board[kingPos[colour][0]][kingPos[colour][1]]
							.isCheck(bottomColour)) {
						board[toRow][toCol] = pieceAtLocation;
						board[row][col] = originalPiece;
						originalPiece.noOfMoves--;
						return false;
					} else {
						board[toRow][toCol] = pieceAtLocation;
						board[row][col] = originalPiece;
						originalPiece.noOfMoves--;
						return true;
					}
                }
                
             // If there is a piece blocking the path, it stops the player from making that move
                if (board [dRow][col]!= null)
                    return false;
            }
            }
        
        
        return false;
}    

    
    
    
    
    
    /**
     * Finds all the possible moves for this piece
     * @param allPossibleMoves all the possible moves this piece can make
     * @return the possible moves
     */
    public ArrayList<Move> allPossibleMoves()
    {
        ArrayList <Move> moves = new ArrayList<Move>();
         
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