import java.awt.Graphics;
import java.awt.Point;
import java.util.*;
import java.awt.Image;

import javax.swing.ImageIcon;

/** Creates a pawn piece for a game of chess 
 *  Each piece determines all of its legal moves,  
 * all possible moves and bishop keeps track of  
 * its points 
 * @author Chanuk De Silva 
 * 
 */
public class Pawn extends Piece
{
    protected static int type = 1;
    /** Constructs a Pawn object 
     * 
     * @param x row of the piece
     * @param y column of the piece
     * @param colour the colour of the piece
     */
    public Pawn(int x, int y, int colour) 
    {
        super(x, y, colour);    
        noOfMoves = 0;
        String imageFileName;
		// Load up the appropriate image file for this piece
        if(colour == 1)
        {
        	imageFileName = "" + colour +  type + ".gif";
        }
        else
        {
        	imageFileName = "" + colour +  type + ".png";
        }        
		imageFileName = "images\\" + imageFileName;
		this.image = new ImageIcon(imageFileName).getImage();
		
		// Set the size of the piece based on the image size
		setSize(image.getWidth(null), image.getHeight(null));
    }

    public String toString()
    {
    	String[] colours = {"Donald's", "Wok's"};
    	return "" + colours[colour] + " Pawn: "+super.toString();
    }
    
    
    /**
     * @author Ivan Zhang (Edit and clean up) and Chaunk De Silva
     * Checks if a move to be made by this piece is legal
     * @param toRow the row of the move coordinate
     * @param toCol the column of the move coordinate
     * @return true if it can be moved to the coordinate, false if it can't
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
    	

    	//Allow two space movement at the beginning
    	if(noOfMoves == 0)
    	{
    		//If the piece is from the colour on the bottom of the board and the place is empty
    		if(row -2 > 0 && colour == bottomColour && toRow == row - 2 && toCol == col && board[toRow][toCol] == null)
    		{
				//See if it's a check, put back pieces after
				Piece pieceAtLocation = board[toRow][toCol];
				Piece originalPiece = board[row][col];
				board[toRow][toCol] = originalPiece;
				originalPiece.noOfMoves++;
				board[row][col] = null;
				if(board[kingPos[colour][0]][kingPos[colour][1]].isCheck(bottomColour))
				{
					board[toRow][toCol] = pieceAtLocation;
					board[row][col] = originalPiece;
    				originalPiece.noOfMoves--;
					return false;
				}
				else
				{
					board[toRow][toCol] = pieceAtLocation;
					board[row][col] = originalPiece;
    				originalPiece.noOfMoves--;
					return true;
				}		
				}
    		else if(row+2 < 9 && colour == 1-bottomColour && toRow == row + 2 && toCol == col && board[toRow][toCol] == null)
    		{
				//See if it's a check, put back pieces after
				Piece pieceAtLocation = board[toRow][toCol];
				Piece originalPiece = board[row][col];
				board[toRow][toCol] = originalPiece;
				originalPiece.noOfMoves++;
				board[row][col] = null;
				if(board[kingPos[colour][0]][kingPos[colour][1]].isCheck(bottomColour))
				{
					board[toRow][toCol] = pieceAtLocation;
					board[row][col] = originalPiece;
    				originalPiece.noOfMoves--;
					return false;
				}
				else
				{
					board[toRow][toCol] = pieceAtLocation;
					board[row][col] = originalPiece;
    				originalPiece.noOfMoves--;
					return true;
				}	
				}
    		
    	}
    	
    	if(board[toRow][toCol] == null)
    	{
    		
    	//Check for one space movements
    	//Forward for bottom coloured pieces
    		if(toRow == row - 1 && toCol == col && colour == bottomColour)
    		{

				//See if it's a check, put back pieces after
				Piece pieceAtLocation = board[toRow][toCol];
				Piece originalPiece = board[row][col];
				board[toRow][toCol] = originalPiece;
				originalPiece.noOfMoves++;
				board[row][col] = null;
				if(board[kingPos[colour][0]][kingPos[colour][1]].isCheck(bottomColour))
				{
					board[toRow][toCol] = pieceAtLocation;
					board[row][col] = originalPiece;
    				originalPiece.noOfMoves--;
					return false;
				}
				else
				{
					board[toRow][toCol] = pieceAtLocation;
					board[row][col] = originalPiece;
    				originalPiece.noOfMoves--;
					return true;
				}
    		}
    		
    		//Forward for top coloured pieces
    		if(toRow == row + 1 && toCol == col && colour == 1 - bottomColour )
    		{
				//See if it's a check, put back pieces after
				Piece pieceAtLocation = board[toRow][toCol];
				Piece originalPiece = board[row][col];
				board[toRow][toCol] = originalPiece;
				originalPiece.noOfMoves++;
				board[row][col] = null;
				if(board[kingPos[colour][0]][kingPos[colour][1]].isCheck(bottomColour))
				{
					board[toRow][toCol] = pieceAtLocation;
					board[row][col] = originalPiece;
    				originalPiece.noOfMoves--;
					return false;
				}
				else
				{
					board[toRow][toCol] = pieceAtLocation;
					board[row][col] = originalPiece;
    				originalPiece.noOfMoves--;
					return true;
				}	
				}

    	}
    	//Check for capturing another piece or preventing capturing of allied piece
    	//For bottom coloured
    	else if(board[toRow][toCol] != null)
    	{
    		if(board[toRow][toCol].colour != colour && (board[toRow][toCol].getType() != -1 ))
    		{
    			//Check for bottom pieces
    			if(colour == bottomColour && toRow == row-1 && (toCol == col + 1 || toCol == col - 1))
    			{
    				
    				//See if it's a check, put back pieces after
    				Piece pieceAtLocation = board[toRow][toCol];
    				Piece originalPiece = board[row][col];
    				board[toRow][toCol] = originalPiece;
    				originalPiece.noOfMoves++;
    				board[row][col] = null;
    				if(board[kingPos[colour][0]][kingPos[colour][1]].isCheck(bottomColour))
    				{
    					board[toRow][toCol] = pieceAtLocation;
    					board[row][col] = originalPiece;
        				originalPiece.noOfMoves--;
    					return false;
    				}
    				else
    				{
    					board[toRow][toCol] = pieceAtLocation;
    					board[row][col] = originalPiece;
        				originalPiece.noOfMoves--;
    					return true;
    				}
    			}
    			
    			//Check for top pieces
    			else if (colour == 1-bottomColour && toRow == row+ 1 && (toCol == col + 1 || toCol == col - 1))
    			{
    				//See if it's a check, put back pieces after
    				Piece pieceAtLocation = board[toRow][toCol];
    				Piece originalPiece = board[row][col];
    				board[toRow][toCol] = originalPiece;
    				originalPiece.noOfMoves++;
    				board[row][col] = null;
    				if(board[kingPos[colour][0]][kingPos[colour][1]].isCheck(bottomColour))
    				{
    					board[toRow][toCol] = pieceAtLocation;
    					board[row][col] = originalPiece;
        				originalPiece.noOfMoves--;
    					return false;
    				}
    				else
    				{
    					board[toRow][toCol] = pieceAtLocation;
    					board[row][col] = originalPiece;
        				originalPiece.noOfMoves--;
    					return true;
    				}
    			}
    		}
    		else
    			return false;
    	}
    	//return false if the location is illegal due to having an allied piece there already or too far
    	return false;
        
    }
      
    /**
     * Returns the points of the current piece
     * @return points the value of the piece
     */
    public int getType()
    {
    	return type;
    }
    

    /**
     * Checks all possible moves for this piece at the moment
     * @author Chanuk De Silva
     * @return the possible moves that can be made by this piece
     */
    public ArrayList<Move> allPossibleMoves() 
    {
        ArrayList <Move> moves = new ArrayList<Move>();
          
        if (colour == bottomColour) 
        { 
        	if(isEnPassant(row-1, col -1))
                moves.add(new Move(row, col, row - 1, col - 1, true, false, false)); 
        	if(isEnPassant(row-1, col+1))
                moves.add(new Move(row, col, row - 1, col + 1, true, false, false));             
        	if (isLegalMove (row - 2, col)) 
                moves.add (new Move (row, col, row - 2, col)); 
        	if (isLegalMove (row -1 , col)) 
            {
            	if(isPromote(row-1,col))
                    moves.add(new Move(row, col, row - 1, col, false, false, true)); 
            	else
                moves.add(new Move(row, col, row - 1, col)); 
            }
            if (isLegalMove (row - 1, col - 1)) 
            {
            	if(isPromote(row-1,col-1))
                    moves.add(new Move(row, col, row - 1, col - 1, false, false, true));
            	else
            		moves.add(new Move(row, col, row - 1, col - 1)); 
            }
            if (isLegalMove (row - 1, col + 1)) 
            {
               	if(isPromote(row-1,col+1))
                    moves.add(new Move(row, col, row - 1, col + 1, false, false, true));
               	else
               		moves.add(new Move(row, col, row - 1, col + 1)); 
            }

        } 
        else if (colour == 1- bottomColour) 
        { 

        	if(isEnPassant(row+1, col -1))
                moves.add(new Move(row, col, row + 1, col - 1, true, false, false)); 
        	if(isEnPassant(row+1, col+1))
                moves.add(new Move(row, col, row + 1, col + 1, true, false, false)); 
            if (isLegalMove (row + 2, col)) 
                moves.add (new Move (row, col, row + 2, col)); 
            if (isLegalMove (row + 1, col)) 
            {
            	if(isPromote(row+1,col))
                    moves.add(new Move(row, col, row + 1, col, false, false, true)); 
            	else
                moves.add(new Move(row, col, row + 1, col)); 
            }
            if (isLegalMove (row + 1, col - 1)) 
            {
            	if(isPromote(row+1,col-1))
                    moves.add(new Move(row, col, row + 1, col - 1, false, false, true));
            	else
            		moves.add(new Move(row, col, row + 1, col - 1)); 
            }
            if (isLegalMove (row + 1, col + 1)) 
            {
               	if(isPromote(row+1,col+1))
                    moves.add(new Move(row, col, row + 1, col + 1, false, false, true));
               	else
               		moves.add(new Move(row, col, row + 1, col + 1)); 
            }

          
        }
       return moves;
        } 

    
} 