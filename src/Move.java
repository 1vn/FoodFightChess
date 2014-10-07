/**
 * A move object to keep track of moves
 * @author Ivan Zhang and Chanuk De Siva
 *
 */
public class Move 
{
	protected int fromRow;
	protected int fromCol;
	protected int toRow;
	protected int toCol;
	protected boolean enPassant;
	protected boolean castle;
	protected boolean promote;
	
	/**
	 * Constructor for the move object
	 * @param fromRow the row moved from
	 * @param fromCol to column moved from
	 * @param toRow to row moved to
	 * @param toCol to column moved to
	 */
	public Move (int fromRow, int fromCol, int toRow, int toCol)
	{
		this.fromRow = fromRow;
		this.fromCol = fromCol;
		this.toRow = toRow;
		this.toCol = toCol;
	}
	
	/**
	 * Constructor for the move object
	 * @param fromRow the row moved from
	 * @param fromCol to column moved from
	 * @param toRow to row moved to
	 * @param toCol to column moved to
	 */
	public Move (int fromRow, int fromCol, int toRow, int toCol, boolean enPassant, boolean castle, boolean promote)
	{
		this.fromRow = fromRow;
		this.fromCol = fromCol;
		this.toRow = toRow;
		this.toCol = toCol;
		this.enPassant = enPassant;
		this.castle = castle;
		this.promote = promote;
	}
	
	/**
	 * A method to compare two moves to see if they're equal
	 * @param other the move to compare 
	 * @return false if it's not an move we're comparing or the moves aren't the same,
	 * 			true if the moves are the same
	 */
	public boolean equals (Object other)
	{
		if (this.getClass() != other.getClass())
			return false;
		
		Move otherMove = (Move)other;
		return(otherMove.fromRow == fromRow && otherMove.fromCol == fromCol &&
				otherMove.toRow == toRow && otherMove.toCol == toCol);
			
	}
	
	/**
	 * Translates the move into a string
	 * @return the move in a readable format
	 */
	public  String toString()
	{
		return "From Row: " + fromRow + "From Col: "+fromCol + "To Row "  + toRow + "To Col " + toCol;
	}
	
	/**
	 * Used for scoring, checks if this move is special in anyway
	 * @param move the move to check
	 * @return whether the move is special
	 */
	public boolean isSpecialMove()
	{
		return(enPassant||castle||promote);
	}

}
