var Board = function() {
  var SQUARE_DIM = 80;
  var NO_ROWS = 10;
  var NO_COLS = 10;
  var instance = null;
  var test;
  img = new Image();
  var drawn;
  var grid = [
    [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
  ];
  if (arguments.callee._singletonInstance) //Singleton block
    return arguments.callee._singletonInstance;
  arguments.callee._singletonInstance = this;

  this.draw = function() {
    console.log("board drawn");
    var canvas = document.getElementById("canvas");
    var context = canvas.getContext("2d");
    for (var row = 1; row < NO_ROWS - 1; row++) {
      for (var col = 1; col < NO_COLS - 1; col++) {
        var xPos = (col - 1) * SQUARE_DIM;
        var yPos = (row - 1) * SQUARE_DIM;
        if ((row % 2 == 0 && col % 2 == 0) || (row % 2 != 0 && col % 2 != 0)) {
          context.fillStyle = 'black';
          context.fillRect(xPos, yPos, SQUARE_DIM, SQUARE_DIM);
        } else {
          context.fillStyle = 'white';
          context.fillRect(xPos, yPos, SQUARE_DIM, SQUARE_DIM);
        }
      }
    }
    drawn = true;
  }

  this.setUpBoard = function() {

    //Place the pawns
    for (var col = 1; col < NO_COLS - 1; col++) {
      grid[7][col] = new Pawn(0, 7, col);
      grid[2][col] = new Pawn(1, 2, col);
    }

    //Place the rooks
    for (var col = 1; col < NO_COLS - 1; col += 7) {
      grid[8][col] = new Rook(0, 8, col);
      grid[1][col] = new Rook(1, 1, col);
    }

    //Place knights
    for (var col = 2; col < NO_COLS - 1; col += 5) {
      grid[8][col] = new Knight(0, 8, col);
      grid[1][col] = new Knight(1, 1, col);
    }

    //Place Bishops
    for (var col = 3; col < 7; col += 3) {
      grid[8][col] = new Bishop(0, 8, col);
      grid[1][col] = new Bishop(1, 1, col);
    }

    //Place the queens
    grid[8][5] = new Queen(0, 8, 5);
    grid[1][4] = new Queen(1, 1, 4);

    this.updateMoves();
  }

  this.tester = function(num) {
    test = num;
    return test;
  }

  this.tester2 = function() {
    return drawn;
  }

  this.getBoard = function() {
    return grid;
  }

  this.remove = function(row, col) {
    grid[row][col] = 0;
  }

  this.getPiece = function(row, col) {
    if (grid[row][col] != 0) {
      return grid[row][col];
    } else if (grid[row][col] == 0) {
      return grid[row][col];
    }
  }

  this.move = function(move, piece) {
    this.remember();
    if (piece.isValidMove(move)) {
      grid[move.fromRow][move.fromCol].move(move);
      grid[move.toRow][move.toCol] = piece;
      grid[move.fromRow][move.fromCol] = 0;
    } else {
      grid[move.fromRow][move.fromCol].noMove();
    }
  }

  /**
  *Precondition: move is a valid move
  *
  */
  this.forceMove = function(move, piece){
    grid[move.fromRow][move.fromCol].move(move);
    grid[move.toRow][move.toCol] = piece;
    grid[move.fromRow][move.fromCol] = 0;
  }

  this.getScore = function(colour) {
    board = new Board(); //getInstance
    score = 0
    for (var row = 1; row < 9; row++) {
      for (var col = 1; col < 9; col++) {
        space = board.getPiece(row, col);
        if (space != 0 && space.getColour != colour)
          score += space.getScore();
      }
    }
  }

  this.updateMoves = function() {
    for (row = 1; row < 9; row++) {
      for (col = 1; col < 9; col++) {
        if (grid[row][col] != 0) {
          grid[row][col].getAllPossibleMoves();
          console.log("Updated moves for: " + grid[row][col].getName())
        }
      }
    }
  }

  /**
    Store the last state of the board
  */
  this.remember = function() {
    mStack = new MentoStack();
    mento = new MentoBoard();
    console.log("mento created");
    mStack.push(mento);
  }

  /**
    Gets last state of the board
  */
  this.undo = function(step) {
    mStack = new MentoStack();
    var s = step
    while (s > 1)
      mStack.pop();
    mento = mStack.pop();
    for (var row = 1; row < 9; row++)
      for (var col = 1; col < 9; col++) {
        space = mento.getPiece(row, col)
        if (space != 0) {
          grid[row][col] = space.copy()
        }
      }
  }
}
