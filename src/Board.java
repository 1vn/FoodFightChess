
/**
 * Board class which makes sure there is only one board class instantiated,
 * contains methods to make working with the board easier.
 * 
 * @author Ivan Zhang
 *
 */
public class Board {
	
	private static final int NO_OF_ROWS = 10;
	private static final int NO_OF_COLUMNS = 10;
	private static final int DONALD = 0; 
	private static final int WOK = 1;
	private static Board INSTANCE = null; // Board instance
	private static Piece[][] coord; // Coordinate map of the pieces on the board

	private Board() {
		coord = new Piece[NO_OF_ROWS][NO_OF_COLUMNS];
	}

	public static int length() 
	{
		return NO_OF_ROWS;
	}
	
	public static Piece[][] getBoard()
	{
		return coord;
	}
	
	public static int width() 
	{
		return NO_OF_COLUMNS;
	}
	
	/**
	 * Return the only instance of board
	 * 
	 * @return the instance of the board
	 */
	public static Board getInstance() {
		if(INSTANCE == null)
			INSTANCE = new Board();
		return INSTANCE;
	}

	/**
	 * Return the piece at the coordinate [row][col] on the coord
	 * 
	 * @param row
	 *            the row of the piece
	 * @param col
	 *            the column of the piece
	 * @return the piece at the coordinate
	 */
	public static Piece get(int row, int col) {
		return coord[row][col];
	}

	public static void set(int row , int col, Piece piece)
	{
		coord[row][col] = piece; 
	}
	
	/**
	 * Set each piece on the board according to the standard chess setup
	 * Alternate King/Queen position depending orientation of the board Also as
	 * pieces are placed we check all their possible moves
	 * 
	 * @author Ivan Zhang
	 * @param board
	 *            the board to set the pieces on
	 */
	public static void setPieces(int playerColour) {

		// Place the first player's pieces
		// Place the pawns
		for (int col = 1; col <= NO_OF_COLUMNS; col++) {
			coord[7][col] = new Pawn(7, col, playerColour);
			coord[7][col].allPossibleMoves();

		}
		// Place the Rooks
		for (int rooks = 0; rooks < 2; rooks++) {
			coord[8][8 - 7 * rooks] = new Rook(8, 8 - 7 * rooks, playerColour);

		}
		// Place the Knights
		for (int knights = 0; knights < 2; knights++) {
			coord[8][7 - 5 * knights] = new Knight(8, 7 - 5 * knights,
					playerColour);
			coord[8][7 - 5 * knights].allPossibleMoves();

		}
		// Place the Bishops
		for (int bishops = 0; bishops < 2; bishops++) {
			coord[8][6 - 3 * bishops] = new Bishop(8, 6 - 3 * bishops,
					playerColour);
			coord[8][6 - 3 * bishops].allPossibleMoves();

		}
		if (playerColour == DONALD) {
			// Place the King and Queen
			for (int royalty = 0; royalty < 2; royalty++) {
				coord[8][4] = new Queen(8, 4, playerColour);
				coord[8][5] = new King(8, 5, playerColour);
				coord[8][4].allPossibleMoves();
				coord[8][5].allPossibleMoves();

			}
		} else
			// Place the King and Queen
			for (int royalty = 0; royalty < 2; royalty++) {
				coord[8][5] = new Queen(8, 5, playerColour);
				coord[8][4] = new King(8, 4, playerColour);
				coord[8][4].allPossibleMoves();
				coord[8][5].allPossibleMoves();

			}

		// Place the second player's or the computer's pieces
		// Place the pawns
		for (int col = 1; col <= NO_OF_COLUMNS; col++) {
			coord[2][col] = new Pawn(2, col, 1 - playerColour);
			coord[2][col].allPossibleMoves();

		}
		// Place the Rooks
		for (int rooks = 0; rooks < 2; rooks++) {
			coord[1][8 - 7 * rooks] = new Rook(1, 8 - 7 * rooks,
					1 - playerColour);
			coord[1][8 - 7 * rooks].allPossibleMoves();

		}
		// Place the Knights
		for (int knights = 0; knights < 2; knights++) {
			coord[1][7 - 5 * knights] = new Knight(1, 7 - 5 * knights,
					1 - playerColour);
			coord[1][7 - 5 * knights].allPossibleMoves();

		}
		// Place the Bishops
		for (int bishops = 0; bishops < 2; bishops++) {
			coord[1][6 - 3 * bishops] = new Bishop(1, 6 - 3 * bishops,
					1 - playerColour);
			coord[1][6 - 3 * bishops].allPossibleMoves();
		}
		if (playerColour == DONALD) {
			// Place the King and Queen if player chose McDonald's (white)
			for (int royalty = 0; royalty < 2; royalty++) {
				coord[1][4] = new Queen(1, 4, 1 - playerColour);
				coord[1][5] = new King(1, 5, 1 - playerColour);
				coord[1][4].allPossibleMoves();
				coord[1][5].allPossibleMoves();

			}
		} else
			// Place the King and Queen if player chose Wok's (black)
			for (int royalty = 0; royalty < 2; royalty++) {
				coord[1][5] = new Queen(1, 5, 1 - playerColour);
				coord[1][4] = new King(1, 4, 1 - playerColour);
				coord[1][4].allPossibleMoves();
				coord[1][5].allPossibleMoves();

			}

	}

	/**
	 * Removes the piece at the give coordinate
	 * 
	 * @param row
	 *            the row of the piece to remove
	 * @param col
	 *            the column of the piece to remove
	 */
	public static void remove(int row, int col) {
		coord[row][col] = null;
	}

}
