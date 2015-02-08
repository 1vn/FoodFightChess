var Knight = function(playerColour, row, col) {
  var score = 4;
  Piece.call(this, score, playerColour, row, col);
}

Knight.prototype = Object.create(Piece.prototype);
Knight.prototype.constructor = Knight;
Knight.prototype.copy = function() {
  copy = new Knight(this.colour, this.row, this.col);
  copy.allPossibleMoves = this.allPossibleMoves
  copy.removeDiv();
  return copy
}

Queen.prototype.getName = function() {
  return 'Knight';
}

Knight.prototype.getAllPossibleMoves = function() {
  var board = new Board();
  var allPossibleMoves = [];

  //Check north and south
  for (var dy = -2; dy <= 2; dy += 4) {
    dRow = dy + this.row;
    if (dRow < 9 && dRow > 0) {
      //Check both sides
      for (var dx = -1; dx <= 1; dx += 2) {
        dCol = dx + this.col;
        if (dCol < 9 && dCol > 0 && (board.getPiece(dRow, dCol) == 0 ||
            (board.getPiece(dRow, dCol) != 0 && board.getPiece(dRow, dCol).getColour() !=
              this.colour))) {
          move = new Move(this.row, this.col, dRow, dCol);
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
  }

  for (var dx = -2; dx <= 2; dx += 4) {
    dCol = dx + this.col;
    if (dCol < 9 && dCol > 0) {
      for (var dy = -1; dy <= 1; dy += 2) {
        dRow = dy + this.row;
        if (dRow > 0 && dRow < 9 && (board.getPiece(dRow, dCol) == 0 ||
            (board.getPiece(dRow, dCol) != 0 && board.getPiece(dRow, dCol).getColour() !=
              this.colour))) {
          move = new Move (this.row, this.col, dRow, dCol)
          allPossibleMoves.push(move)
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
  }
  this.allPossibleMoves = allPossibleMoves;

}
