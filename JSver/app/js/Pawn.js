function Pawn(playerColour, row, col) {
  var RANK = 1;
  Piece.call(this, RANK, playerColour, row, col);
  this.allPossibleMoves = this.getAllPossibleMoves()
  console.log("allPossibleMoves called");
  this.noOfMoves = 0
}

Pawn.prototype = Object.create(Piece.prototype);
Pawn.prototype.constructor = Pawn;
Pawn.prototype.getName = function() {
  return 'Pawn';
}

Pawn.prototype.noOfMoves = 0
Pawn.prototype.move = function(move) {
  this.noOfMoves++;
  if (this.allPossibleMoves.indexOf(move) > 0)
    Piece.move(move);
  else
    this.noMove();
}

Pawn.prototype.getAllPossibleMoves = function() {
  console.log("allPossibleMoves initiated")
  var board = new Board()
  var allPossibleMoves = []
  if (this.playerColour == 0) {
    if (noOfMoves == 0 && board.getPiece(this.row + 2, this.col) == 0)
      allPossibleMoves.push(newMove = new move(this.row, this.col, this.row -
        2, this.col))
    if (board.getPiece(this.row - 1, this.col) == 0)
      allPossibleMoves.push(new move(this.row, this.col, this.row - 1,
        this.col))
    for (var dCol = -1; dCol <= 1; dCol += 2) {
      var thingAtDestination = board.getPiece(row - 1, col + dCol)
      if (thingAtDestination != 0 && thingAtDestination.getColour != this
        .colour)
        allPossibleMoves.push(new move(this.row, this.col, this.row - 1,
          col + dCol))
    }
  }
  console.log("All possible moves are: " + allPossibleMoves);
  return allPossibleMoves;
}
