var Queen = function(playerColour, row, col) {
  var score = 8;
  Piece.call(this, score, playerColour, row, col);
}

Queen.prototype = Object.create(Piece.prototype);
Queen.prototype.constructor = Queen;
Queen.prototype.getName = function() {
  return 'Queen';
}

Queen.prototype.copy = function() {
  copy = new Queen(this.colour, this.row, this.col);
  copy.allPossibleMoves = this.allPossibleMoves
  copy.removeDiv()
  return copy
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
      dCol = this.col + dx;
      while (dRow < 9 && dRow > 0 && dCol < 9 && dCol > 0 && board.getPiece(
          dRow, dCol) == 0) {
        console.log("Added for: " + this.idCode + " " + dRow + dCol)
        move = new Move(this.row, this.col, dRow, dCol);
        allPossibleMoves.push(move);
        dRow += dy;
        dCol += dx;
      }
      if (dCol > 0 && dCol < 9 && dRow > 0 && dRow < 9 && board.getPiece(
          dRow, dCol) != 0 && board.getPiece(
          dRow, dCol).getColour() != this.colour) {
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
  this.allPossibleMoves = allPossibleMoves;
}
