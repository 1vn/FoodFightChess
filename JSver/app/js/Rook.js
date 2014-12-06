function Rook(playerColour, row, col) {
  var score = 5;
  Piece.call(this, score, playerColour, row, col);
}

Rook.prototype = Object.create(Piece.prototype);
Rook.prototype.constructor = Rook;
Rook.prototype.getName = function() {
  return 'Rook';
}

Rook.prototype.copy = function() {
  copy = new Rook(this.colour, this.row, this.col);
  copy.removeDiv()
  return copy
}

Rook.prototype.test = function() {
  targets = [Piece.prototype.wTargets, Piece.prototype.bTargets]
  console.log("Test ran" + targets[0]);
}

Rook.prototype.getAllPossibleMoves = function() {
  var board = new Board()
  var allPossibleMoves = []
  for (var dy = -1; dy <= 1; dy += 2) {
    dRow = this.row + dy;
    while (dRow > 0 && dRow < 9 && board.getPiece(dRow, this.col) == 0) {
      allPossibleMoves.push(new Move(this.row, this.col, dRow, this.col));
      dRow += dy;
    }
    if (dRow > 0 && dRow < 9 && board.getPiece(dRow, this.col) != 0 && board.getPiece(
        dRow, this.col).getColour() != this.colour) {
      allPossibleMoves.push(new Move(this.row, this.col, dRow, this.col))
      var toSpace = board.getPiece(dRow, this.col)
      if (this.colour == 0 && Piece.prototype.bTargets.indexOf(toSpace) <= -1)
        Piece.prototype.bTargets.push(toSpace);
      else if (this.colour == 1 && Piece.prototype.wTargets.indexOf(toSpace) <=
        -1)
        Piece.prototype.wTargets.push(toSpace);
    }
  }

  for (var dx = -1; dx <= 1; dx += 2) {
    dCol = this.col + dx;
    while (dCol > 0 && dCol < 9 && board.getPiece(this.row, dCol) == 0) {
      allPossibleMoves.push(new Move(this.row, this.col, this.row, dCol));
      dCol += dx;
    }
    if (dCol > 0 && dCol < 9 && board.getPiece(this.row, dCol) != 0 && board.getPiece(
        this.row, dCol).getColour() != this.colour) {
      allPossibleMoves.push(new Move(this.row, this.col, this.row, dCol))
      var toSpace = board.getPiece(this.row, dCol)
      if (this.colour == 0 && Piece.prototype.bTargets.indexOf(toSpace) <= -1)
        Piece.prototype.bTargets.push(toSpace);
      else if (this.colour == 1 && Piece.prototype.wTargets.indexOf(toSpace) <=
        -1)
        Piece.prototype.wTargets.push(toSpace);
    }
  }
  console.log("Moves updated for " + this.idCode);
  this.allPossibleMoves = allPossibleMoves;
}
