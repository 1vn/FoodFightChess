var Bishop = function(playerColour, row, col) {
  var score = 3;
  Piece.call(this, score, playerColour, row, col);
}

Bishop.prototype = Object.create(Piece.prototype);
Bishop.prototype.constructor = Bishop;
Bishop.prototype.getName = function() {
  return "Bishop"
}

Bishop.prototype.copy = function() {
  copy = new Bishop(this.colour, this.row, this.col);
  copy.allPossibleMoves = this.allPossibleMoves
  copy.removeDiv();
  return copy;
}


Bishop.getAllPossibleMoves = function() {
  console.log("Getting Bishop moves.")
  board = new Board();
  allPossibleMoves = [];
  for (var dy = -1; dy <= 1; dy += 2) {
    for (var dx = -1; dx <= 1; dx += 2) {
      dRow = this.row + dy;
      dCol = this.col + dx;
      while (dRow < 9 && dCol > 0 && dRow > 0 && dRow < 9 && board.getPiece(
          dRow,
          dCol) == 0) {
        allPossibleMoves.push(new Move(this.row, this.col, dRow, dCol));
        dRow += dy;
        dCol += dx;
      }
      if (dCol > 0 && dCol < 9 && dRow > 0 && dRow < 9 && board.getPiece(
          dRow, dCol) != 0 && board.getPiece(
          dRow, dCol).getColour() != this.colour) {
            move = new Move (this.row, this.col, dRow, dCol)
          allPossibleMoves.push(move);
        var toSpace = board.getPiece(dRow, dCol)
        if (this.colour == 0 && Piece.prototype.bTargets.indexOf(toSpace) <=
          -1)
          Piece.prototype.bTargets.push(toSpace);
        else if (this.colour == 1 && Piece.prototype.wTargets.indexOf(
            toSpace) <=
          -1)
          Piece.prototype.wTargets.push(toSpace);
      }
    }
  }
  this.allPossibleMoves = allPossibleMoves;
  console.log(this.allPossibleMoves)
}
