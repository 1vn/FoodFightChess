import java.awt.Graphics;
import java.util.ArrayList;


public class BorderPiece extends Piece
{
		protected static int points =  -1;
		final static int COLOUR = 3;
		
		/** Constructs a Border object to ensure pieces do not go out of bounds
		 * 
		 * @param x row of the piece
		 * @param y column of the piece
		 * @param colour the colour of the piece
		 */
		public BorderPiece(int x, int y, int colour) 
		{
			super(x, y, colour);	
		}

		@Override
		public boolean isLegalMove(int dx, int dy) {
			return false;
		}

		@Override
		public void move(int toRow, int toColumn) {
			
		}

		@Override
		public int getType() {
			return -1;
		}

		@Override
		public void draw(Graphics g) {
			
		}

		public String toString()
		{
			return ("BorderPiece: " + super.toString());
		}
		@Override
		public ArrayList<Move> allPossibleMoves() {
			// TODO Auto-generated method stub
			return null;
		


}
		}



