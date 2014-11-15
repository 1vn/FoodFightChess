function Pawn(playerColour, row, col) {
  var score = 1;
  Piece.call(this, score, playerColour, row, col);
  var noOfMoves = 0;
}

Pawn.prototype = Object.create(Piece.prototype);
Pawn.prototype.constructor = Pawn;
Pawn.prototype.getName = function() {
  return 'Pawn';
}
Pawn.prototype.getNoOfMoves = function() {
  return this.noOfMoves;
}
Pawn.prototype.move = function(move) {
  Piece.prototype.move.call(this, move);
  this.noOfMoves++;
}

Pawn.prototype.getAllPossibleMoves = function() {
  console.log("allPossibleMoves initiated")
  var board = new Board()
  var allPossibleMoves = []
  console.log("This piece has " + this.noOfMoves);
  //Logic for white pieces (bottom)
  if (this.colour == 0) {
    if (this.noOfMoves == 0) {
      console.log("Two-piece possible for piece " +
        this.getId())
      two_step = new Move(this.row, this.col, this.row - 2, this.col);
      allPossibleMoves.push(two_step)
    }
    //Check for enemy on adjacent diagonal
    for (var d = -1; d <= 1; d += 2) {
      toSpace = board.getPiece(this.row - 1, this.col + d);
      if (toSpace != 0 && toSpace.colour != this.colour) {
        kill = new Move(this.row, this.col, this.row - 1, this.col + d);
        allPossibleMoves.push(kill);
        bTargets.push(toSpace)
      }
    }
    //Check for empty space in front
    inFront = board.getPiece(this.row - 1, this.col);
    if (inFront == 0)
      allPossibleMoves.push(new Move(this.row, this.col, this.row - 1,
        this.col))
  } else if (this.colour != 0) { //Logic for black pieces (top)
    if (this.noOfMoves == 0) {
      console.log("Two-piece possible for piece " +
        this.getId())
      two_step = new Move(this.row, this.col, this.row + 2, this.col);
      allPossibleMoves.push(two_step);
    }
    //Check for enemy on adjacent diagonal
    for (var d = -1; d <= 1; d += 2) {
      toSpace = board.getPiece(this.row + 1, this.col + d);
      if (toSpace != 0 && toSpace.colour != this.colour) {
        kill = new Move(this.row, this.col, this.row + 1, this.col + d);
        allPossibleMoves.push(kill);
        wTargets.push(toSpace);
      }
    }
    //Check for empty space in front
    inFront = board.getPiece(this.row + 1, this.col);
    if (inFront == 0)
      allPossibleMoves.push(new Move(this.row, this.col, this.row + 1,
        this.col))
  }
  return allPossibleMoves;
}
