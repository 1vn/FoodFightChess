function Piece(rank, colour, row, col) {
  var rank = rank;
  var colour = colour;
  var row = row;
  var col = col;
  var img = new Image()
  img.src = "/images/" + colour + type + ".png";
}
Piece.prototype.draw = function() {

}

Piece.prototype.getRank = function() {
  return rank;
}

Piece.prototype.getRow = function() {
  return row;
}

Piece.prototype.getCol = function() {
  return col;
}

Piece.prototype.remove = function() {
  Board.getInstance().remove(row, col);
}

Piece.prototype.isLegalMove = function(move) {};
Piece.prototype.allPossibleMoves = function(move) {};