function Queen(playerColour, row, col) {
  var score = 8;
  Piece.call(this, score, playerColour, row, col);
}

Queen.prototype = Object.create(Piece.prototype);
Queen.prototype.constructor = Queen;
Queen.prototype.getName = function() {
  return 'Queen';
}

Queen.prototype.getAllPossibleMoves = function() {
  console.log("Getting Queen moves");
  board = new Board();
  allPossibleMoves = []
  for (var dy = -1; dy <= 1; dy++) {
    for (var dx = -1; dx <= 1; dx++) {
      console.log("dy: " + dy)
      console.log("dx: " + dx)
      dRow = this.row + dy;
      dCol = this.row + dx;
      while (dRow < 9 && dRow > 0 && dCol < 9 && dCol > 0 && board.getPiece(
          dRow, dCol) == 0) {
        console.log("Added for: " + this.idCode + " " + dRow + dCol)
        allPossibleMoves.push(new Move(this.row, this.col, dRow, dCol));
        dRow += dy;
        dCol += dx;
      }
    }
  }
  this.allPossibleMoves = allPossibleMoves;
}
