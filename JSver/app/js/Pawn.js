function Pawn(playerColour, row, col) {
  var score = 1;
  Piece.call(this, score, playerColour, row, col);
}

Pawn.prototype = Object.create(Piece.prototype);
Pawn.prototype.constructor = Pawn;
Pawn.prototype.getName = function() {
  return 'Pawn';
}
Pawn.prototype.getNoOfMoves = function() {
  return this.noOfMoves;
}
Pawn.prototype.setNoOfMoves = function(noOfMoves) {
  this.noOfMoves = noOfMoves;
}
Pawn.prototype.move = function(move) {
  Piece.prototype.move.call(this, move);
}

Pawn.prototype.copy = function() {
  copy = new Pawn(this.colour, this.row, this.col);
  copy.setNoOfMoves(this.noOfMoves);
  copy.removeDiv()
  return copy
}

Pawn.prototype.getAllPossibleMoves = function() {
  var board = new Board()
  var allPossibleMoves = []
    //Logic for white pieces (bottom)
  if (this.colour == 0) {
    if (this.noOfMoves == 0) {
      two_step = new Move(this.row, this.col, this.row - 2, this.col);
      if (!isCheck(two_step))
        allPossibleMoves.push(two_step)
    }
    //Check for enemy on adjacent diagonal
    for (var d = -1; d <= 1; d += 2) {
      toSpace = board.getPiece(this.row - 1, this.col + d);
      if (toSpace != 0 && toSpace.colour != this.colour) {
        kill = new Move(this.row, this.col, this.row - 1, this.col + d);
        if (!isCheck(kill)) {
          allPossibleMoves.push(kill);
          if (Piece.prototype.bTargets.indexOf(toSpace) <= -1)
            Piece.prototype.bTargets.push(toSpace);
        }
      }
    }
    //Check for empty space in front
    inFront = board.getPiece(this.row - 1, this.col);
    if (inFront == 0)
      front = new Move(this.row, this.col, this.row - 1, this.col)
    if (!isCheck(front))
      allPossibleMoves.push(front)
  } else if (this.colour != 0) { //Logic for black pieces (top)
    if (this.noOfMoves == 0) {
      two_step = new Move(this.row, this.col, this.row + 2, this.col);
      if (!isCheck(two_step))
        allPossibleMoves.push(two_step);
    }
    //Check for enemy on adjacent diagonal
    for (var d = -1; d <= 1; d += 2) {
      toSpace = board.getPiece(this.row + 1, this.col + d);
      if (toSpace != 0 && toSpace.colour != this.colour) {
        kill = new Move(this.row, this.col, this.row + 1, this.col + d);
        if (!isCheck(kill)) {
          allPossibleMoves.push(kill);
          if (Piece.prototype.wTargets.indexOf(toSpace) <= -1)
            Piece.prototype.wTargets.push(toSpace);
        }
      }
    }
    //Check for empty space in front
    inFront = board.getPiece(this.row + 1, this.col);
    if (inFront == 0)
      front = new Move(this.row, this.col, this.row + 1,
        this.col)
    if (!isCheck(front))
      allPossibleMoves.push(front)
  }
  this.allPossibleMoves = allPossibleMoves;
}
