import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.io.*;

import javax.swing.*;

/**
 * A class for display and handling interaction with all components of Food Fight,
 * a brand new take on the classic game of chess with 100% mouse drawn graphics,
 * interactive elements, and creative pieces
 * @author Ivan Zhang
 * @version January 21st, 2014
 */
public class ChessGame extends JFrame implements ActionListener {

	protected PlayArea playArea; // The Main drawing panel which handles drawing the board, pieces
								 // Manual pages, Game Over message, and owns the menu bar
	protected static int playerColour; // 0 is white(MegaDonald), 1 is
										// black(ManChowWok) set when the new game button
										// is pressed in Main Menu
	
	final int WOK = 1;			//WOK, the chinese food side which follows the same rules as
								//black in conventional chess
	
	final int DONALD = 0;

	private boolean invalidMove; // Keeps track of whether the move is
									// legitimate to give feedback to the player

	private int winner; //0 means Donald won, 1 means Wok won
	private boolean check,gameOver; //Keeps track of whether game is over. true = game over, false = game not over
									//check keeps track of whether a player is in check for infoPanel
	protected static int currentTurn; // Keeps track of the current turn 0 Donald, 1 for Wok
	
	protected Piece[][] board;// The chess board 10x10 chess board of Piece objects, each keeps
							  // track of its row, column, colour(allegiance), and type
	

	private Point lastPoint; //The last point click which helps dragging for some panels

	// Board state stuff (will be added in future versions, the idea was to have a change
							// in how the board looks as the game went on)
	private int noOfMoves; // The number of moves determines the state of the
							// battlefield
							// After 10 moves the board will be slightly rugged
							// 20 moves, there will be some ketchup and soup
							// spilled
							// 30 moves, there will be blood
	private int state; // The current state of the board
	private Image[] states; // An array to keep the state pictures
	//End of board state stuff
	
	//Constants
	final int SQUARE_SIZE = 80; // The length (in pixels) of the side of each
					// square on the board
	final int BORDER = -1; // The points for border pieces
	final int KING = 200; // The points for Kings
	final int COMPONENT_SQUARE_WIDTH = 300; //How big the graveYards are
	final int COMPONENT_SQUARE_HEIGHT = 320;
	final int NO_OF_ROWS = 8; // Number of rows and columns in the game board
	final int NO_OF_COLUMNS = 8;
	private boolean mainMenu; // Sees if Main Menu is being viewed

	// Manual stuff
	private int pageNo; //Keeps track of what page we're looking at
	private Image page1, page2, page3, page4, page5, page6, page7, page8; // Manual
																			// pages
	private boolean p1, p2, p3, p4, p5, p6, p7, p8, manualView;
	private Image[] manual; // Manual images
	// End of Manual stuff

	

	private int fromRow, fromColumn; // The board row and column that
	// the piece is moving from.
	
	//Menu items: newOption starts a new game in the same game mode(Two-player or Single-player)
	//			  saveOption(WIP) saves the game into a file that can be loaded
	//            loadOption(WIP) loads a game file on the disk
	//            wExitOption exit to windows
	//            exitOption exits to main menu
	//            instructionOption shows the manual
	//            aboutOption shows the about box which displays the author and version
	private JMenuItem newOption, saveOption, loadOption, wExitOption,
			exitOption, instructionOption, aboutOption; // for menu and menu
														// events

	// Button stuff
	// Only used in manual mode or main menu
	//Buttons: newGameButton starts a new game
	//         instructionsButton shows the manual
	//         nextPage flips to the next page in the manual
	//         lastPage goes to the last page in the manual, will return to main menu if it is pressed
	//         on the first page
	//         home goes back to the main menu. Used only when manual is accessed from the main menu
	//         lastPageInGame used when you look at manual in game so that you don't go back to main menu
	//		   exit used ingame so that we can go back to the game on any page
	
	private JButton newGameButton, instructionsButton, nextPage, lastPage,
			home, lastPageInGame, exit;
	
	//Icons for the next and back buttons
	private ImageIcon buttonBack, buttonNext;

	//Image for the title screen
	private Image title;
	
	//The container for all the panels and components
	protected Container contentPane;

	// ArrayList of captured pieces which corresponds to each side
	ArrayList<Piece> eatenDonalds, eatenWoks;

	// Possible moves for both sides, Wok's moves in index 1, Donald's moves in
	// index 0
	private ArrayList<Move> donaldsMoves;
	private ArrayList<Move> woksMoves;
	private ArrayList<ArrayList<Move>> allMoves;

	//Panel for drawing and handling mouse interactions
	// Shows each player's dead pieces which are can be dragged around on their turn
	private GraveYard graveYardPanel;

	// Keeps track of what "Stage" the image of the player is, 0-based
	private int[] stageCounts; 
	//Stage descriptions
	//Stage 1: no pieces captured, players are waiting for food
	//Stage 2: 2 pieces captured, players are drooling at the mouth after delicious appetizer
	//Stage 3: 4 pieces captured, players are feeling the slight side-effects of MSG/"real meat"
	//Stage 4: 6 pieces captured, players feel more super natural effects
	//Stage 5: 8 pieces captured, players have begun  their metamorphosis into their true form
	//Stage 6: 10 pieces captured, a built up of preservatives inside their bodies has turned
	//them into their true form, liberated from all societal pressures to maintain a "healthy" image
	//They have reached their most divine state and can dine amongst the gods of fast food.
	
	//The message I'm trying to send is that eating too much junk food is bad

	// Variable for keeping track of single player mode vs. AI or two player
	// mode
	private boolean singlePlayer;

	// Cursor variables
	BufferedImage cursorImg;
	Cursor fork, chopsticks;
	//Array to keep cursor setting dynamic
	Cursor[] cursors;

	//The AI that would be initiated if single-player option was chosen
	ComputerPlayer computerPlayer;
	//The move the computer will make after miniMax
	Move computerMove;

	//Panel for showing information about the game
	//Displays whose turn it is
	//Displays any invalid moves the player tries to make
	//Displays check
	private InfoArea infoPanel;

	/**
	 * A constructor for a new chess game
	 * Sets its size and frame
	 * adds main panel to the frame
	 * extra panels not added till later when newGame is called
	 * @author Ivan Zhang
	 */
	public ChessGame() {

		super("Food War: Modern Warfare 1");
		setBounds(50, 25, 640, 640);
		setLocation(50, 25);
		setResizable(false);
		loadResources();

		// Initialize the 2D array which represents the board.
		board = new Piece[NO_OF_ROWS + 2][NO_OF_COLUMNS + 2];
		// Sync the board with the Piece class
		// Board state is updated by moveOnBoard method
		Piece.setBoard(board);
		
		//Initialize the board
		Board.getInstance();
		
		//Initialize contentPane with null layout
		contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		
		//Initialize the playArea which is the board panel for chess
		playArea = new PlayArea();
		mainMenu = true;
		//Set the playArea on the left side
		contentPane.add(playArea, BorderLayout.WEST);

		//Construct buttons
		//New Game button
		newGameButton = new JButton("New Game");
		newGameButton.setBounds(265, 280, 100, 30);
		playArea.setLayout(null);
		newGameButton.addActionListener(this);
		playArea.add(newGameButton);

		//Instructions button
		instructionsButton = new JButton("Rules");
		instructionsButton.setBounds(265, 310, 100, 30);
		playArea.setLayout(null);
		instructionsButton.addActionListener(this);
		playArea.add(instructionsButton);

		//a "next page" button to use in manual state
		nextPage = new JButton("Next");
		nextPage.setBounds(530, 600, 100, 30);
		playArea.setLayout(null);
		nextPage.addActionListener(this);
		nextPage.setIcon(buttonNext);

		//a "lastPage" button to use in manual state
		lastPage = new JButton("Back");
		lastPage.setBounds(10, 600, 100, 30);
		playArea.setLayout(null);
		lastPage.addActionListener(this);
		lastPage.setIcon(buttonBack);

		//Last page button to use while viewing manual in game
		lastPageInGame = new JButton("Back");
		lastPageInGame.setBounds(10, 600, 100, 50);
		playArea.setLayout(null);
		lastPageInGame.addActionListener(this);
		lastPageInGame.setIcon(buttonBack);

		//Home button to return back to the main menu
		home = new JButton("Home");
		home.setBounds(0, 0, 100, 30);
		playArea.setLayout(null);
		home.addActionListener(this);

		//Exit button to get back into the game
		exit = new JButton("Exit");
		exit.setBounds(0, 0, 100, 30);
		playArea.setLayout(null);
		exit.addActionListener(this);


		//Initialize the grave yards and list of dead pieces
		//This fixes an issue we had before where the grave yards
		//would not update after you pressed new game from the menu bar
		//And pieces wouldn't be dragged around
		eatenDonalds = new ArrayList<Piece>();
		eatenWoks = new ArrayList<Piece>();
		graveYardPanel = new GraveYard();

		
		//Initialize the info area
		infoPanel = new InfoArea();

	} // ChessGame constructor


	/**
	 * Add the menus to the menu bar
	 */
	private void addMenus() {
		// Sets up the Game MenuItems.
		newOption = new JMenuItem("New"); //New game Option
		newOption.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				InputEvent.CTRL_MASK));
		newOption.addActionListener(this);

		saveOption = new JMenuItem("Save"); // Save Option
		newOption.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				InputEvent.CTRL_MASK));
		newOption.addActionListener(this);

		loadOption = new JMenuItem("Load"); // Load Option
		newOption.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,
				InputEvent.CTRL_MASK));
		newOption.addActionListener(this);

		exitOption = new JMenuItem("Exit to Main Menu"); // Close to Main Menu
															// Option
		exitOption.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M,
				InputEvent.CTRL_MASK));
		exitOption.addActionListener(this);

		wExitOption = new JMenuItem("Exit to Windows"); // Close to windows
														// Option
		wExitOption.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
				InputEvent.CTRL_MASK));
		wExitOption.addActionListener(this);

		// Sets up the Help MenuItems.
		instructionOption = new JMenuItem("Instructions");
		instructionOption.setMnemonic('I');
		instructionOption.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I,
				InputEvent.CTRL_MASK));
		instructionOption.addActionListener(this);

		aboutOption = new JMenuItem("About");
		aboutOption.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,
				InputEvent.CTRL_MASK));
		aboutOption.setMnemonic('I');
		aboutOption.addActionListener(this);

		// Sets up the Game and Help Menus.
		JMenu gameMenu = new JMenu("Game");
		gameMenu.setMnemonic('G');
		JMenu helpMenu = new JMenu("Help");
		helpMenu.setMnemonic('H');

		// Add each MenuItem to the Game Menu (with a separator).
		gameMenu.add(newOption);
		gameMenu.add(saveOption);
		gameMenu.addSeparator();
		gameMenu.add(exitOption);
		gameMenu.add(wExitOption);

		// Add each MenuItem to the Help Menu (with a separator).
		helpMenu.add(instructionOption);
		helpMenu.addSeparator();
		helpMenu.add(aboutOption);

		// Adds the GameMenu and HelpMenu to the JMenuBar.
		JMenuBar mainMenu = new JMenuBar();
		mainMenu.add(gameMenu);
		mainMenu.add(helpMenu);

		// Set the menus
		setJMenuBar(mainMenu);
	}

	/**
	 * Check if the game is over by checking whether there are moves left for the player
	 * @author Ivan Zhang
	 * @return boolean if game is over
	 */
	public void isGameOver() {
		//Checking whether the length of the possible moves list is 0 for this turn
		gameOver = (allMoves.get(currentTurn).size() == 0);
		if(gameOver)
		{
			//Declare the winner
			winner = 1-currentTurn;
		}
		
	}

	

	/**
	 * A method to create smack talk for a more immersive experience for the
	 * user (WIP)
	 * 
	 * @param colour the colour the computer is
	 * @return an ArrayList of strings read from a file that contain smack talk
	 * @throws FileNotFoundException
	 *             If file isn't found
	 */
	public void talkSmack(int colour) throws FileNotFoundException {
		//Read the smack talk
		BufferedReader smackList = new BufferedReader(new FileReader(
				"smacList.txt"));
		//Code for talking smack which I will add later

	}


	/**
	 * Load images for components and objects of the game, specific descriptions below
	 * @author Ivan Zhang
	 */
	private void loadResources() {

		// Button stuff
		buttonBack = new ImageIcon("buttonBack.png");
		buttonNext = new ImageIcon("buttonNext.png");

		//Title image
		title = new ImageIcon("title.gif").getImage();

		// Load the manual pages
		page1 = new ImageIcon("manual\\page1.gif").getImage();
		page2 = new ImageIcon("manual\\page2.png").getImage();
		page3 = new ImageIcon("manual\\page3.png").getImage();
		page4 = new ImageIcon("manual\\page4.png").getImage();
		page5 = new ImageIcon("manual\\page5.png").getImage();
		page6 = new ImageIcon("manual\\page6.png").getImage();
		page7 = new ImageIcon("manual\\page7.png").getImage();
		page8 = new ImageIcon("manual\\page8.png").getImage();

		//Bind them to a book (Put them in an array in order)
		manual = new Image[] { page1, page2, page3, page4, page5, page6, page7,
				page8 };

		// Create cursors and load their pictures
		chopsticks = Toolkit.getDefaultToolkit().createCustomCursor(
				new ImageIcon("chopsticks.png").getImage(), new Point(0, 30),
				"chopsticks");
		fork = Toolkit.getDefaultToolkit().createCustomCursor(
				new ImageIcon("fork.png").getImage(), new Point(0, 30), "fork");

		cursors = new Cursor[] { fork, chopsticks };

		// Load state pictures(WIP)
		states = new Image[3];
		for (int state = 1; state <= 3; state++) {
			states[state - 1] = new ImageIcon("boardStates\\boardstate" + state
					+ ".png").getImage();
		}

		//Personally I felt like the burger is more appropriate as the 
		//icon for this game. It was a tough choice between the Donald king and
		//the burger, but the burger was more identifiable at a smaller scale
		//Due to its shape and colour scheme. Also, the burger is a piece that
		//interacts with the player(s) more than the king due to the fact that
		// they are on the front lines and there is an immense overnumbering
		//of burgers to Donald kings. Burgers are also more marketable due to their
		//iconic form.
		setIconImage(Toolkit.getDefaultToolkit().getImage("images\\01.png"));
	}

	/**
	 * Starts or restarts a game
	 * @author Ivan Zhang
	 */
	private void newGame() {

		//Set game over being false so paintComponent knows and doesn't do
		//game over procedure
		gameOver = false;
		
		//Initialize the stage of the players on each respective side
		stageCounts = new int[2];
		
		//Initialize the king points to avoid nullPointers
		Piece.intializeKingPoints();
		
		//DONALD always goes first, it is the white piece equivalent for this Chess
		currentTurn = 0;
		
		//Set the cursor to the fork because Donald always goes first
		//We had to do this because otherwise there was a bug where
		//The chopstick would stay even though we started a new game
		//And it was Donald's turn
		setCursor(cursors[0]);
		
		//State of the board (WIP)
		state = 0;
		
		//Counts move to store state of the board (WIP)
		noOfMoves = 0;
		
		//Clear the board just in case people forgot to put away pieces
		clearBoard(board);

		//Set the board perimeter with "borderPieces" which return type -1 so we don't check
		//Out of bounds when we check for legal moves for pieces
		setBoardPerimeter();
		
		//Set pieces on the board for players because we have yet to make the players set the pieces
		//themselves which could be a possible feature in the future
		setPieces(board);
		
		//Update all the moves both players can make
		checkAllMoves();
	
		
		//Since we're not looking at the menu we set it to false so playArea doesn't paint
		//it anymore
		mainMenu = false;
		
		//Set the bounds to fit and display the infoArea and graveYard as well
		setBounds(50, 25, 1240, 700);
		
		//Add the graveYard and infoPanel to the frame
		contentPane.add(graveYardPanel, BorderLayout.EAST);
		contentPane.add(infoPanel, BorderLayout.CENTER);
		
		//Wash the dirty dishes/bowls from last game 
		//Or else customers will complain
		//Which happened on demo day
		graveYardPanel.washDishes();
		
		//Repaint the infoPanel and graveYard with the new settings
		infoPanel.repaint();
		graveYardPanel.repaint();

		//Since if it was an invalid move the infoPanel would print something we'll set this to false
		//By default
		invalidMove = false;

		repaint();
		
		//Makes sure the computer player goes first if single player mode was selected and
		//the player chose to fight for Wok
		if (singlePlayer && playerColour == WOK) {
			computerMove = computerPlayer.bestMove();
			System.out.println("The computer wants to do " + computerMove);
			if(computerMove != null && board[computerMove.fromRow][computerMove.fromCol] != null )
			humanHand(board[computerMove.fromRow][computerMove.fromCol], computerMove);
		}
		

	}

	/**
	 * Sets the board perimeter with a bunch of borderPieces
	 * @author Ivan Zhang
	 */
	private void setBoardPerimeter() {
		// Sets values around the PERIMETER of the board to block movement off
		// the board
		// and/or to prevent the game's logic from checking squares outside the
		// board
		for (int row = 0; row <= NO_OF_ROWS + 1; row++) {
			for (int column = 0; column <= NO_OF_COLUMNS + 1; column++) {
				if (row == 0 || row == NO_OF_ROWS + 1)
					board[row][column] = new BorderPiece(row, column, BORDER);
				if (column == 0 || column == NO_OF_COLUMNS + 1)
					board[row][column] = new BorderPiece(row, column, BORDER);
			}
		}
	}

	/**
	 * Clears the game board (sets each square to null)
	 * @author Ivan Zhang
	 * @param board
	 *            the board to clear
	 */
	private void clearBoard(Piece[][] board) {
		// Clear only the playing area of the board, NOT the border perimeter
		// (there is a perimeter row/column surrounding all 4 sides of the
		// playing board,
		// similar to in ConnectFour demo)
		for (int row = 1; row <= NO_OF_ROWS; row++)
			for (int column = 1; column <= NO_OF_COLUMNS; column++)
				board[row][column] = null;
	}

	/**
	 * Set each piece on the board according to the standard chess setup
	 * Alternate King/Queen position depending orientation of the board
	 * Also as pieces are placed we check all their possible moves
	 * @author Ivan Zhang
	 * @param board the board to set the pieces on
	 */
	private void setPieces(Piece[][] board) {

		// Place the first player's pieces
		// Place the pawns
		for (int col = 1; col <= NO_OF_COLUMNS; col++) {
			board[7][col] = new Pawn(7, col, playerColour);
			board[7][col].allPossibleMoves();

		}
		// Place the Rooks
		for (int rooks = 0; rooks < 2; rooks++) {
			board[8][8 - 7 * rooks] = new Rook(8, 8 - 7 * rooks, playerColour);

		}
		// Place the Knights
		for (int knights = 0; knights < 2; knights++) {
			board[8][7 - 5 * knights] = new Knight(8, 7 - 5 * knights,
					playerColour);
			board[8][7 - 5 * knights].allPossibleMoves();

		}
		// Place the Bishops
		for (int bishops = 0; bishops < 2; bishops++) {
			board[8][6 - 3 * bishops] = new Bishop(8, 6 - 3 * bishops,
					playerColour);
			board[8][6 - 3 * bishops].allPossibleMoves();

		}
		if (playerColour == DONALD) {
			// Place the King and Queen
			for (int royalty = 0; royalty < 2; royalty++) {
				board[8][4] = new Queen(8, 4, playerColour);
				board[8][5] = new King(8, 5, playerColour);
				board[8][4].allPossibleMoves();
				board[8][5].allPossibleMoves();

			}
		} else
			// Place the King and Queen
			for (int royalty = 0; royalty < 2; royalty++) {
				board[8][5] = new Queen(8, 5, playerColour);
				board[8][4] = new King(8, 4, playerColour);
				board[8][4].allPossibleMoves();
				board[8][5].allPossibleMoves();

			}

		// Place the second player's or the computer's pieces
		// Place the pawns
		for (int col = 1; col <= NO_OF_COLUMNS; col++) {
			board[2][col] = new Pawn(2, col, 1 - playerColour);
			board[2][col].allPossibleMoves();

		}
		// Place the Rooks
		for (int rooks = 0; rooks < 2; rooks++) {
			board[1][8 - 7 * rooks] = new Rook(1, 8 - 7 * rooks,
					1 - playerColour);
			board[1][8 - 7 * rooks].allPossibleMoves();

		}
		// Place the Knights
		for (int knights = 0; knights < 2; knights++) {
			board[1][7 - 5 * knights] = new Knight(1, 7 - 5 * knights,
					1 - playerColour);
			board[1][7 - 5 * knights].allPossibleMoves();

		}
		// Place the Bishops
		for (int bishops = 0; bishops < 2; bishops++) {
			board[1][6 - 3 * bishops] = new Bishop(1, 6 - 3 * bishops,
					1 - playerColour);
			board[1][6 - 3 * bishops].allPossibleMoves();
		}
		if (playerColour == DONALD) {
			// Place the King and Queen if player chose McDonald's (white)
			for (int royalty = 0; royalty < 2; royalty++) {
				board[1][4] = new Queen(1, 4, 1 - playerColour);
				board[1][5] = new King(1, 5, 1 - playerColour);
				board[1][4].allPossibleMoves();
				board[1][5].allPossibleMoves();

			}
		} else
			// Place the King and Queen if player chose Wok's (black)
			for (int royalty = 0; royalty < 2; royalty++) {
				board[1][5] = new Queen(1, 5, 1 - playerColour);
				board[1][4] = new King(1, 4, 1 - playerColour);
				board[1][4].allPossibleMoves();
				board[1][5].allPossibleMoves();

			}

	}

	


	/**
	 * Updates all the moves possible for each piece on the board
	 * and then adds it to the list of possible legal moves for each colour
	 * @author Ivan Zhang
	 */
	public ArrayList<ArrayList<Move>> checkAllMoves() {
		allMoves = new ArrayList<ArrayList<Move>>();
		woksMoves = new ArrayList<Move>();
		donaldsMoves = new ArrayList<Move>();
		allMoves.add(donaldsMoves);
		allMoves.add(woksMoves);

		for (int row = 1; row < board.length - 1; row++)
			for (int col = 1; col < board[0].length - 1; col++)
				if (board[row][col] != null) {
					ArrayList<Move> possibleMoves = board[row][col]
							.allPossibleMoves();
					for (Move move : possibleMoves) {
						if (board[move.fromRow][move.fromCol] != null)
						{
							allMoves.get(
									board[move.fromRow][move.fromCol].colour)
									.add(move);
							if(move.enPassant)
								System.out.println("En passant at " + move);
						}

					}

				}

		return allMoves;
	}
	
	/**
	 * A method for the robot to move
	 * @param move the move to move
	 */
	public void robotHand(Move move)
	{
		isGameOver();
		int toRow = move.toRow;
		int toCol = move.toCol;
		int fromRow = move.fromRow;
		int fromCol = move.fromCol;
		
		if(move.enPassant)
		{
			System.out.println("En passant");
			board[fromRow][fromCol].noOfMoves++;
			board[fromRow][fromCol].moveOnBoard(move);
			board[toRow][toCol] = board[fromRow][fromCol];
			// Update captured pieces based on what pieces were captured
			if (board[move.fromRow][toCol].getColour() == WOK) {
				eatenWoks.add(board[move.fromRow][toCol]);
				
				//Check if we are eligible for an evolution of player image
				if (eatenWoks.size() == 2 + 2 * stageCounts[DONALD]
						&& stageCounts[DONALD] <= 4)
					stageCounts[DONALD]++;
				
				//Add dead pieces to display for shame/entertainment
				graveYardPanel.setDeadPieces();

			} else if (board[move.fromRow][toCol].getColour() == DONALD) {
				eatenDonalds.add(board[move.fromRow][toCol]);
				
				//Check if we are eligible for an evolution of player image
				if (eatenDonalds.size() == 2 + 2 * stageCounts[WOK]
						&& stageCounts[WOK] <= 4)
					stageCounts[WOK]++;
				
				//Add dead pieces to display for shame/entertainment
				graveYardPanel.setDeadPieces();
			}
			board[move.fromRow][toCol] = null;
			currentTurn = 1-currentTurn;
			isGameOver();
			
		}
		else if(board[toRow][toCol] == null)
		{
			board[fromRow][fromCol].moveOnBoard(move);
			board[toRow][toCol] = board[fromRow][fromCol];
			board[fromRow][fromCol] = null;
			currentTurn = 1-currentTurn;
		}
		else if(board[toRow][toCol] != null)
		{
			// Update captured pieces based on what pieces were captured
			if (board[toRow][toCol].getColour() == WOK) {
				eatenWoks.add(board[toRow][toCol]);
				
				//Check if we are eligible for an evolution of player image
				if (eatenWoks.size() == 2 + 2 * stageCounts[DONALD]
						&& stageCounts[DONALD] <= 4)
					stageCounts[DONALD]++;
				
				//Add dead pieces to display for shame/entertainment
				graveYardPanel.setDeadPieces();

			} else if (board[toRow][toCol].getColour() == DONALD) {
				eatenDonalds.add(board[toRow][toCol]);
				
				//Check if we are eligible for an evolution of player image
				if (eatenDonalds.size() == 2 + 2 * stageCounts[WOK]
						&& stageCounts[WOK] <= 4)
					stageCounts[WOK]++;
				
				//Add dead pieces to display for shame/entertainment
				graveYardPanel.setDeadPieces();
				
			}
			board[toRow][toCol] = null;
			if(board[fromRow][fromCol] != null)
			board[fromRow][fromCol].moveOnBoard(move);
			board[toRow][toCol] = board[fromRow][fromCol];
			board[fromRow][fromCol] = null;
			currentTurn = 1-currentTurn;
			repaint();
		}
	}
	

	/**
	 * Places a piece in the given row and column on the board and updates the
	 * board
	 * @author Ivan Zhang
	 * @param card
	 *            the piece to place
	 * @param row
	 *            the row to place the piece
	 * @param column
	 *            the column to place the piece
	 * @Precondition row and column are on the board
	 */
	private void humanHand(Piece selectedPiece, Move move) {
		
		//Get the toRow and toCol of the move to use when we move the piece on the ChessGame board
		int toRow = move.toRow;
		int toColumn = move.toCol;
		
		// Make sure the move on the list of possible moves for this player
		if (allMoves.get(currentTurn).indexOf(move) >= 0) {
			
			if(move.enPassant)
			{
				System.out.println("En passant");
				selectedPiece.noOfMoves++;
				selectedPiece.moveOnBoard(move);
				board[toRow][toColumn] = selectedPiece;
				// Update captured pieces based on what pieces were captured
				if (board[move.fromRow][toColumn].getColour() == WOK) {
					eatenWoks.add(board[move.fromRow][toColumn]);
					
					//Check if we are eligible for an evolution of player image
					if (eatenWoks.size() == 2 + 2 * stageCounts[DONALD]
							&& stageCounts[DONALD] <= 4)
						stageCounts[DONALD]++;
					
					//Add dead pieces to display for shame/entertainment
					graveYardPanel.setDeadPieces();

				} else if (board[move.fromRow][toColumn].getColour() == DONALD) {
					eatenDonalds.add(board[move.fromRow][toColumn]);
					
					//Check if we are eligible for an evolution of player image
					if (eatenDonalds.size() == 2 + 2 * stageCounts[WOK]
							&& stageCounts[WOK] <= 4)
						stageCounts[WOK]++;
					
					//Add dead pieces to display for shame/entertainment
					graveYardPanel.setDeadPieces();
				}
				board[move.fromRow][toColumn] = null;
				currentTurn = 1-currentTurn;
				isGameOver();
				
			}
			
			// If place to move is empty
			else if (board[toRow][toColumn] == null) {

				invalidMove = false;
				infoPanel.repaint();

				//Update the turn
				currentTurn = 1 - currentTurn;
				
				//Update the number of moves for this piece
				selectedPiece.noOfMoves ++;
				
				//Move the piece on the Piece board
				selectedPiece.moveOnBoard(move);
				
				//Update the target position with the piece
				board[toRow][toColumn] = selectedPiece;
				
				//Board state stuff (WIP)
				noOfMoves++;
				if (noOfMoves > 10 + state * 10) {
					state++;
				}
				
				//Check if the game is over for the opponent(Since we changed the turn already)
				isGameOver();
			} else if (board[toRow][toColumn] != null
					&& board[toRow][toColumn].colour != selectedPiece.colour)// If
																				// not,
																				// then
																				// capture
			{
				// Update captured pieces based on what pieces were captured
				if (board[toRow][toColumn].getColour() == WOK) {
					eatenWoks.add(board[toRow][toColumn]);
					
					//Check if we are eligible for an evolution of player image
					if (eatenWoks.size() == 2 + 2 * stageCounts[DONALD]
							&& stageCounts[DONALD] <= 4)
						stageCounts[DONALD]++;
					
					//Add dead pieces to display for shame/entertainment
					graveYardPanel.setDeadPieces();

				} else if (board[toRow][toColumn].getColour() == DONALD) {
					eatenDonalds.add(board[toRow][toColumn]);
					
					//Check if we are eligible for an evolution of player image
					if (eatenDonalds.size() == 2 + 2 * stageCounts[WOK]
							&& stageCounts[WOK] <= 4)
						stageCounts[WOK]++;
					
					//Add dead pieces to display for shame/entertainment
					graveYardPanel.setDeadPieces();
				}
				
				//Update number of moves for that piece
				selectedPiece.noOfMoves ++;
				
				//Put the selected piece to
				board[toRow][toColumn] = selectedPiece;
				
				//Update selected Piece's position on the Piece board
				selectedPiece.moveOnBoard(move);
				
				//Update turns
				currentTurn = 1 - currentTurn;
								
				//Check if the game is over
				isGameOver();			
				}

		 if (move.promote) {
			selectedPiece.noOfMoves ++;
			Object[] possibilities = { "Rook", "Knight", "Bishop", "Queen" };
			String s = (String) JOptionPane.showInputDialog(playArea,
					"That piece you put down was up for promotion.\n"
							+ "What would you like it to be?", "Promotion",
					JOptionPane.PLAIN_MESSAGE, null, possibilities, "Rook");

			// This handles Promotion for human players
			// If a string was returned, say so.
			// Create the piece the layer chose
			if ((s != null) && (s.length() > 0)) {
				if (s.equals("Rook")) {
					board[toRow][toColumn] = new Rook(toRow, toColumn,
							1 - currentTurn);
				} else if (s.equals("Knight")) {
					board[toRow][toColumn] = new Knight(toRow, toColumn,
							1 - currentTurn);
				} else if (s.equals("Bishop")) {
					board[toRow][toColumn] = new Bishop(toRow, toColumn,
							1 - currentTurn);
				} else if (s.equals("Queen")) {
					board[toRow][toColumn] = new Queen(toRow, toColumn,
							1 - currentTurn);
				}
			}
			
			
			//Check if game is over
			isGameOver();
		}
		}

		else
		// If not a possible move, then move back to the original location
		{
			//Display invalid move message
			invalidMove = true;
			infoPanel.repaint();
			
			//Put back the piece
			board[fromRow][fromColumn] = selectedPiece;
		}
		checkAllMoves();
	}

	/**
	 * This method is called by Java when a menu option is chosen
	 * 
	 * @author Ivan Zhang
	 * @param event
	 *            The event that triggered this method.
	 */
	public void actionPerformed(ActionEvent event) {
		// If the new option is selected, the board is reset and a new game
		// begins.
		if (event.getSource() == newOption) {
			newGame();
		}

		// Goes back to home screen and add back all the buttons and
		// put back the boundaries
		if (event.getSource() == exitOption) {
			mainMenu = true;
			setJMenuBar(null);
			playArea.add(newGameButton);
			playArea.add(instructionsButton);
			setBounds(50, 25, 640, 660);
		}
		// Closes the game screen if the exit to windows option is selected.
		else if (event.getSource() == wExitOption) {
			dispose();
			System.exit(0);
		}
		// Displays the instructions if the instruction option is selected.
		// This does not reset bounds because I assume most players will 
		// Only want to look at the manual for the first page just to 
		// Remind themselves of what piece is which
		// Also adds a special lastPage button which returns the player back to the game
		// instead of main menu
		else if (event.getSource() == instructionOption) {
			manualView = true;
			playArea.add(exit);
			playArea.add(nextPage);
			playArea.add(lastPageInGame);
			pageNo = 0;
		}
		// Displays developer information if the about option is selected.
		else if (event.getSource() == aboutOption) {
			JOptionPane
					.showMessageDialog(
							this,
							"\u00a9 By Ivan Zhang and Chanuk De Silva \n"
									+ "The food may not be gourmet, but the graphics are. ",
							"About Food Fight", JOptionPane.INFORMATION_MESSAGE);
		}
		//Starts a new game and prompts players to choose single player or two player and then
		//which allegiance they'd like to be
		//Creates a new computer player based on the colour the player chose
		else if (event.getSource() == newGameButton) {
			Object[] gameOptions = { "Two Player" , "Single Player"};
			int gameMode = JOptionPane.showOptionDialog(playArea,
					"What mode would you like to play?", "Game Mode",
					JOptionPane.CLOSED_OPTION, JOptionPane.QUESTION_MESSAGE,
					null, // do not use a custom Icon
					gameOptions, // the titles of buttons
					gameOptions[0]); // default button title
			if (gameMode == JOptionPane.YES_OPTION) {
				singlePlayer = false;

			} else
				singlePlayer = true;
			
			Object[] sideOptions = { "MegaDonald!", "ManChowWok!" };
			int n = JOptionPane.showOptionDialog(playArea, "Who is your King?",
					"Allegiance?", JOptionPane.CLOSED_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, // do not use a custom
														// Icon
					sideOptions, // the titles of buttons
					sideOptions[0]); // default button title
			if (n == JOptionPane.YES_OPTION) {
				playerColour = 0;
				if (singlePlayer)
					try {
						computerPlayer = new ComputerPlayer(1);
					} catch (FileNotFoundException e) {
						//
						e.printStackTrace();
					}
			} else {
				playerColour = 1;
				if (singlePlayer)
					try {
						computerPlayer = new ComputerPlayer(0);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
			}
			
			//Update the playerColour in piece class
			//For determining the board orientation that pawns should check themselves in
			//When looking for legal move
			Piece.setPlayerColour(playerColour);

			//Turn off menu and remove buttons and start new game
			mainMenu = false;
			newGame();
			playArea.remove(newGameButton);
			playArea.remove(instructionsButton);
			addMenus();

		} 
		//Displays the manual
		//Adds the home, next page and back page buttons
		else if (event.getSource() == instructionsButton) {
			manualView = true;
			playArea.remove(instructionsButton);
			playArea.remove(newGameButton);
			playArea.add(nextPage);
			playArea.add(lastPage);
			playArea.add(home);
			addMenus();
			pageNo = 0;
			mainMenu = false;

		}
		//Turns the page unless there are no pages left
		else if (event.getSource() == nextPage) {
			playArea.add(nextPage);
			if (pageNo < manual.length - 1)
				pageNo++;
			repaint();
		}
		//Turns the page back unless there is no more pages so then we go back to the main menu
		//while adding and removing all the appropriate buttons
		else if (event.getSource() == lastPage) {
			playArea.add(lastPage);
			if (pageNo > 0)
				pageNo--;
			else {
				manualView = false;
				mainMenu = true;
				playArea.remove(lastPage);
				playArea.remove(nextPage);
				playArea.add(newGameButton);
				playArea.add(instructionsButton);
			}
			repaint();
		} 
		
		//Flips pages backwards 
		//Returns the user back to the game if they were browsing the manual in game
		else if (event.getSource() == lastPageInGame) {
			if (pageNo > 0)
				pageNo--;
			else {
				manualView = false;
				playArea.remove(lastPageInGame);
				playArea.remove(nextPage);
			}
			repaint();

		} 
		//Returns the user back to the main menu if they were browsing the manual from
		//the main menu
		else if (event.getSource() == home) {
			manualView = false;
			mainMenu = true;
			playArea.remove(lastPageInGame);
			playArea.remove(lastPage);
			playArea.remove(nextPage);
			playArea.remove(home);
			playArea.add(newGameButton);
			playArea.add(instructionsButton);

		}
		//Returns the user back to the game if they were browsing the menu in game
		else if (event.getSource() == exit) {
			manualView = false;
			mainMenu = false;
			playArea.remove(lastPageInGame);
			playArea.remove(lastPage);
			playArea.remove(nextPage);
			playArea.remove(exit);

		}

	}

	/**
	 * Displays character profiles and game feedback
	 * @author Ivan Zhang
	 * 
	 */
	private class InfoArea extends JPanel {

		/*
		 * Constructor that sets the size of the infoArea
		 */
		public InfoArea() {
			this.setPreferredSize(new Dimension(COMPONENT_SQUARE_WIDTH, SQUARE_SIZE * NO_OF_ROWS));
			this.setFocusable(true);
			this.requestFocusInWindow();

		}

		/**
		 * Repaint the drawing panel.
		 * 
		 * @param g
		 *            The Graphics context.
		 */
		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			// Coordinates to draw relative plates/platters top or bottom
			// depending on the player colour to match allegiances
			int xPosPlayers = 0;
			int yPosBottom = COMPONENT_SQUARE_HEIGHT;
			int yPosTop = 0;

			// Draw playerImages
			g.drawImage(new ImageIcon("playerImg\\stage" + playerColour + ""
					+ stageCounts[playerColour] + ".gif").getImage(),
					xPosPlayers, yPosBottom, this);
			g.drawImage(new ImageIcon("playerImg\\stage" + (1 - playerColour)
					+ stageCounts[1 - playerColour] + ".gif").getImage(),
					xPosPlayers, yPosTop, this);

			//Set the font
			//White is the most appropriate colour I think, I tried black but it was too unclear
			g.setFont(new Font("Times New Roman", Font.PLAIN, 20));
			g.setColor(Color.WHITE);

			// Draw Donald the game over trash talk
			// Not the same smack talk mentioned above
			// Because the smack talk mentioned above
			// was supposed to smack talk throughout the game
			//This is just for when the game is over
				if (gameOver) {
					if (playerColour == DONALD) {
						if (currentTurn == DONALD) {
							g.drawString("What in Tarnation?!", 20, 620);
							g.drawString("A swift victory, as expected.", 20, 300);


						} else {
							g.drawString("I love Chinese food.", 20, 620);
							g.drawString("I was only being modest.", 20, 300);
						}
					} else {
						if (currentTurn == DONALD) {
							g.drawString("Would you like fries with that?.", 20, 300);

						} else {
							g.drawString("Get served, cowboy.", 20, 620);

						}
					}
					return;
				}

				//Tell the player they cannot do an illegal move
			if (playerColour == DONALD) {
				if (invalidMove) {
					g.drawString("You can't do that!", 20,
							620 - currentTurn * 320);

				}

				if (!invalidMove) {
					g.drawString("It's my turn!", 20, 620 - currentTurn * 320);
				}
			} else {
				if (invalidMove) {
					g.drawString("You can't do that!", 20,
							620 - (1 - currentTurn) * 320);

				}

				if (!invalidMove) {
					g.drawString("It's my turn!", 20,
							620 - (1 - currentTurn) * 320);
				}
			}

		}

	}

/**
 * An interactive "grave yard" that shows the dead pieces for each player
 * on the other player's plate/bowl
 * and lets the user interact with them by draggin them around
 * @author Ivan Zhang
 */
	private class GraveYard extends JPanel {
		ArrayList<ArrayList<Piece>> eatenFood;
		Piece selectedDeadPiece;
		private Point lastDeadPoint;

		/**
		 * Constructor for the GraveYard
		 * sets the size and adds mouse listeners
		 * makes the selectedDeadPiece null on default
		 * and resets eatenFood
		 */
		public GraveYard() {
			this.setPreferredSize(new Dimension(COMPONENT_SQUARE_WIDTH, NO_OF_ROWS * SQUARE_SIZE));
			// Add mouse listeners to the drawing panel
			this.addMouseListener(new MouseHandler());
			this.addMouseMotionListener(new MouseMotionHandler());
			this.setFocusable(true);
			this.requestFocusInWindow();
			selectedDeadPiece = null;
			eatenFood = new ArrayList<ArrayList<Piece>>();
			setDeadPieces();

		}

		/**
		 * Washes dishes
		 * in other words, remove all the dead pieces from eaten food
		 */
		public void washDishes() {
			for (ArrayList<Piece> scrapPlate : eatenFood)
				for (int piece = scrapPlate.size() - 1; piece >= 0; piece--) {
					scrapPlate.remove(piece);
				}
		}

		/**
		 * Set the dead pieces up for the users to play with and look at
		 */
		public void setDeadPieces() {

			eatenFood.add(eatenDonalds);
			eatenFood.add(eatenWoks);

			/**
			 * Position the dead pieces to be drawn
			 * We set their location with random intervals
			 * So the player would notice the new pieces updating
			 * If we didn't the pieces would be on top of each other and no one would
			 * notice this neat feature. Also, in this day and age when people see
			 * icons overlapping each other they are conditioned to move them
			 * due to the fact that touch screens have this feature and we use it daily
			 * 
			 * We use setLocation because in the Piece class we have a .contains method to
			 * detect click points. Since it's a Rectangle this is useful when we try to handle
			 * dragging. This was based off of MoveAPieceFrameDemo.
			 */
			for (int side = 0; side <= 1; side++) {
				if (eatenFood.get(side).size() != 0) {
					// We only set the location of the last piece eaten
					if (side == playerColour) {
						eatenFood
								.get(side)
								.get(eatenFood.get(side).size() - 1)
								.setLocation(
										(int) (130 + Math.random() * 80 + 3),
										(int) (NO_OF_ROWS * SQUARE_SIZE / 4
												- Math.random() * 100 + 3));
					} else {
						eatenFood
								.get(side)
								.get(eatenFood.get(side).size() - 1)
								.setLocation(
										(int) (130 + Math.random() * 80 + 3),
										(int) (NO_OF_ROWS * SQUARE_SIZE * 3 / 4
												- Math.random() * 100 + 3));

					}
				}

			}

		}

		/**
		 * Repaint the drawing panel
		 * 
		 * @param g
		 *            The Graphics context
		 */
		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			//Positions for drawing the graves
			int yPosBottom = COMPONENT_SQUARE_HEIGHT;
			int xPosGraves = 0;
			int yPosTop = 0;

			// Draw the "graveyards" or plate/bowl relative to the player colour
			g.drawImage(new ImageIcon("playerImg\\grave" + playerColour
					+ ".png").getImage(), xPosGraves, yPosBottom, this);
			g.drawImage(new ImageIcon("playerImg\\grave" + (1 - playerColour)
					+ ".png").getImage(), xPosGraves, yPosTop, this);

			
			//Draw each dead piece
			for (ArrayList<Piece> pieceList : eatenFood)
				for (Piece piece : pieceList) {
					piece.drawDrag(g);
				}
			
			//Handle dragging
			if (selectedDeadPiece != null)
				selectedDeadPiece.drawDrag(g);

		} // paint component method

		// Inner class to handle mouse events
		private class MouseHandler extends MouseAdapter {
			
			/**
			 * Handles mouse pressed events
			 * @param event the mouse pressed event
			 */
			public void mousePressed(MouseEvent event) {
				Point pressedPoint = event.getPoint();
				
				//Ignore if the user clicks out of bounds
				if (pressedPoint.x < 0 || pressedPoint.x > COMPONENT_SQUARE_WIDTH
						|| pressedPoint.y < 0 || pressedPoint.y > 640)
					return;

				//See if any of the pieces contains where the user pressed
				//Again, obviously inspired by MoveAPieceFrameDemo
				for (int piece = 0; piece < eatenFood.get(1 - currentTurn)
						.size(); piece++) {
					if (eatenFood.get(1 - currentTurn).get(piece)
							.contains(pressedPoint)) {
						
						//Update positions
						lastDeadPoint = new Point(event.getPoint());
						selectedDeadPiece = eatenFood.get(1 - currentTurn).get(
								piece);
						System.out.println("Selecting: " + selectedDeadPiece);

					}
				}
			}

			/**
			 * Handles mouse released events in graveYardPanel
			 * @param event the event that triggered this
			 */
			public void mouseReleased(MouseEvent event) {
				
				//Puts down piece of there is one
				if (selectedDeadPiece != null) {
					selectedDeadPiece = null;
					graveYardPanel.repaint();

				}
			}
		}

		// Inner Class to handle mouse movements
		private class MouseMotionHandler implements MouseMotionListener {
			
			/**
			 * Does nothing, here because it has to be
			 */
			public void mouseMoved(MouseEvent event) {
					//nothing 
			}

			/**
			 * Handles mouse dragged events
			 * @param event the event that triggered this 
			 */
			public void mouseDragged(MouseEvent event) {
				Point currentPoint = event.getPoint();
				
				//Makes sure players can only drag their own pieces because that's fair
				//Althought I'm still debating because it does seem unintuitive when you're playing 
				//vs ai. 
				//Possibly greater fun factor if I added a "hot pot" where players can add 
				//pieces they feel they are ready to share with the other player
				if (currentTurn == playerColour) {
					
					if (currentPoint.x <= 0 || currentPoint.x > COMPONENT_SQUARE_WIDTH
							|| currentPoint.y <= 320 || currentPoint.y >= 640)
						return;
				} else if (currentPoint.x <= 0 || currentPoint.x > 300
						|| currentPoint.y <= 0 || currentPoint.y >= 320)
					return;

				
				//Drag the piece
				if (selectedDeadPiece != null) {
					selectedDeadPiece.move(lastDeadPoint, currentPoint);
					lastDeadPoint = currentPoint;
					graveYardPanel.repaint();
				}
			}
		}
	}

	/**
	 * Inner class for the main panel drawing area
	 * which is responsible for drawing the game, manual,
	 * and game over screen
	 * @author Ivan Zhang
	 *
	 */
	private class PlayArea extends JPanel {

		private Piece selectedPiece;// Keeps track of the currently selected piece
		// (each piece has a unique type which is represented by an integer)
		// 1 - Pawn, 3 - Bishop, 4 - Knight, 5 - Rook, 8 - Queen, 200 - King

		public PlayArea() {
			this.setPreferredSize(new Dimension(SQUARE_SIZE * NO_OF_ROWS,
					SQUARE_SIZE * NO_OF_ROWS));
			// Add mouse listeners to the drawing panel
			this.addMouseListener(new MouseHandler());
			this.addMouseMotionListener(new MouseMotionHandler());
			this.setFocusable(true);
			this.requestFocusInWindow();

		}

		/**
		 * Repaint the drawing panel.
		 * 
		 * @param g
		 *            The Graphics context.
		 */
		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			//Set the background white because of our manual's page 1
			//Even though we set the dimension in photoshop
			//When we made it into a gif, even though we set the setting to
			//100% of original size, it still became a bit smaller
			//To deal with this problem, we simply set the background to white
			setBackground(Color.WHITE);

			//Draw the manual if we're looking at it
			if (manualView) {
				g.drawImage(manual[pageNo], 0, 0, this);
			}
			//Or draw the board
			else if (!mainMenu) { // Game board drawing

				// Draw the board with current pieces.
				for (int row = 1; row <= NO_OF_ROWS; row++)
					for (int column = 1; column <= NO_OF_COLUMNS; column++) {
						// Find the x and y positions for each row and column.
						int xPos = (column - 1) * SQUARE_SIZE;
						int yPos = (row - 1) * SQUARE_SIZE;

						// Draw board squares
						if ((column % 2 == 0 && row % 2 == 0)
								|| (column % 2 != 0 && row % 2 != 0)) {
							g.setColor(Color.WHITE);
							g.fillRect(xPos, yPos, SQUARE_SIZE, SQUARE_SIZE);
						} else {
							g.setColor(Color.BLACK);
							g.fillRect(xPos, yPos, SQUARE_SIZE, SQUARE_SIZE);
						}

						// g.drawImage(states[state], 0, 0, this);

						// Draw Pieces by calling the inner draw method in each piece
						if (board[row][column] != null) {
							board[row][column].draw(g);
						}

					}
				
				// Draw the selected piece being DRAGGED. Draw this last
				// so
				// it appears over top everything.
				if (selectedPiece != null)
					selectedPiece.drawDrag(g);

				
				//Draw the game over message
				if (gameOver) {
					g.setFont(new Font("Times New Roman", Font.PLAIN, 150));
					g.setColor(Color.RED);
					g.drawString("GET FIT", 20, 320);
				}
			}
			// Main Menu drawing
			else if (mainMenu) {
				g.drawImage(title, 0, 0, this);
			}
		}

		// Inner class to handle mouse-pressed and mouse-released events.
		private class MouseHandler extends MouseAdapter {

			/**
			 * Finds out which piece was selected
			 * 
			 * @param event
			 *            in formation about the mouse pressed event
			 */
			public void mousePressed(MouseEvent event) {
				if (!mainMenu) {

					// Convert mouse-pressed location to board row and column
					Point pressedPoint = event.getPoint();
					fromColumn = pressedPoint.x / SQUARE_SIZE + 1; // add one
																	// because
																	// of
																	// the
																	// border
																	// surrounding
																	// the 2D
																	// 'board'
																	// array)
					fromRow = pressedPoint.y / SQUARE_SIZE + 1;

					// Ignore any clicks off the board area
					if (fromRow < 0 || fromRow > 8 || fromColumn < 0
							|| fromColumn > 8)
						return;

					
					//Handles a click on a piece
					//Only allow it to be selected if it is the correct turn
					//And if there is a piece there
					//Make it an invalid move otherwise
					if (board[fromRow][fromColumn] != null) {
						lastPoint = new Point(event.getPoint());
						System.out.println("This square has " + board[fromRow][fromColumn]);
						if ((board[fromRow][fromColumn].colour == currentTurn && !singlePlayer)
								|| (playerColour == currentTurn
										&& board[fromRow][fromColumn].colour == playerColour && singlePlayer)) {
							selectedPiece = board[fromRow][fromColumn];
							
							//Useful for dragging
							selectedPiece.setLocation((fromColumn - 1)
									* SQUARE_SIZE, (fromRow - 1) * SQUARE_SIZE);
							board[fromRow][fromColumn] = null;
							invalidMove = false;

						} else {
							invalidMove = true;
							infoPanel.repaint();
						}
					}

				}
				repaint();
			}

			/**
			 * Finds where the mouse was released and moves the piece, if
			 * allowed
			 * 
			 * @param event
			 *            information about the mouse released event
			 */
			public void mouseReleased(MouseEvent event) {
				if (!mainMenu) {
					// Convert mouse-released location to board row and column
					Point releasedPoint = event.getPoint();
					int toColumn = (int) ((double) releasedPoint.x / SQUARE_SIZE) + 1; // casting
																						// double
																						// to
																						// int
																						// preserves
																						// fractional
																						// negative
																						// values
					int toRow = (int) ((double) releasedPoint.y / SQUARE_SIZE) + 1;

					
			
					Move nextMove = new Move(fromRow, fromColumn, toRow, toColumn);
					//Make an en passant move
					if(selectedPiece != null && selectedPiece.getType() == 1)
					{
						if(selectedPiece.isEnPassant(toRow, toColumn)) //Pseudo legal en passant
						nextMove = new Move(fromRow, fromColumn, toRow, toColumn, true, false, false);
						
						if(selectedPiece.isPromote(toRow, toColumn))
							nextMove = new Move(fromRow,fromColumn,toRow,toColumn, false, false, true);

					}
					// Only handle piece moving if a piece is selected
					if (selectedPiece != null) {

						humanHand(selectedPiece, nextMove);
						repaint();

					}
					if (singlePlayer && currentTurn != playerColour && !gameOver) //Let the computer move
					{
						computerMove = computerPlayer.bestMove();
						System.out.println("Computer wants to make "+computerMove);
						robotHand(computerMove);
						checkAllMoves();
					}
					
					isGameOver();
					invalidMove = false;
					System.out.println("mouseReleased(): put down "
							+ selectedPiece);
					selectedPiece = null;
					setCursor(cursors[currentTurn]);
					repaint();
					infoPanel.repaint();
					graveYardPanel.repaint();

				}
				// End of New Code

			}
		}

		// Inner Class to handle mouse movements over the DrawingPanel
		private class MouseMotionHandler extends MouseMotionAdapter {
			/**
			 * This used to managed cursor changes unti we thought of the idea of changing the cursor
			 * into chopsticks and forks
			 * @param event
			 *            information about the mouse released event
			 */
			public void mouseMoved(MouseEvent event) {
				//Uneeded
			}

			/**
			 * Moves the selected piece when the mouse is dragged
			 * 
			 * @param event
			 *            information about the mouse released event
			 */
			public void mouseDragged(MouseEvent event) {
				//Get the current mouse dragging point
				Point currentPoint = event.getPoint();
				
				//Make sure we don't drag off screen
				if (currentPoint.x <= 0 || currentPoint.x > 640
						|| currentPoint.y <= 0 || currentPoint.y >= 640)
					return;
				if (!mainMenu && selectedPiece != null) {
					
					//Call this move to help with dragging
					selectedPiece.move(lastPoint, currentPoint);
					lastPoint = currentPoint;
					repaint();
				}
			}
		}
	}// Player Area Class

	public static void main(String[] args) {

		//Create a new chess game
		ChessGame game = new ChessGame();
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.pack();
		game.setVisible(true);

	}

}