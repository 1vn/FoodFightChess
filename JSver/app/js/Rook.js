function Rook(playerColour, row, col) {
  var score = 5;
  Piece.call(this, RANK, playerColour, row, col);
}

Rook.prototype = Object.create(Piece.prototype);
Rook.prototype.constructor = Rook;
Rook.prototype.getName = function() {
  return 'Rook';
}

Rook.prototype.getAllPossibleMoves = function() {
  console.log("allPossibleMoves initiated")
  var board = new Board()
  var allPossibleMoves = []
  for (var dy = -1; dy <= 1; dy += 2) {
    dRow = this.row + dy;
    while (dRow > 0 && dRow < 9 && (board.getPiece(dRow, this.col) == 0 || (
        board.getPiece(dRow, this.col) != 0 && board.getPiece(dRow, this.col)
        .getColour() != this.colour))) {
      allPossibleMoves.push(new Move(this.row, this.col, dRow, this.col))
      dRow += dy;
    }
  }
  for (var dx = -1; dx <= 1; dx += 2) {
    dCol = this.col + dx;
    while (dCol > 0 && dCol < 9 && (board.getPiece(this.row, dCol) == 0 || (
        board.getPiece(this.row, dCol) != 0 && board.getPiece(this.row,
          dCol).getColour() != this.colour))) {
      allPossibleMoves.push(new Move(this.row, this.col, this.row, dCol))
      dCol += dx;
    }
  }


  return allPossibleMoves
}
