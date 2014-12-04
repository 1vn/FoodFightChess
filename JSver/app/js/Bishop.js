var Bishop = function(playerColour, row, col) {
  var score = 5;
  Piece.call(this, score, playerColour, row, col);
}

Bishop.prototype = Object.create(Piece.prototype);
Bishop.prototype.constructor = Bishop;
Bishop.prototype.copy = function() {
  copy = new Bishop(this.playerColour, this.row, this.col);
  copy.removeDiv();
  return copy;
}

Bishop.prototype.getName = function() {
  return "Bishop"
}

Bishop.getAllPossibleMoves = function(){
  board = new Board();
  allPossibleMoves = [];
  this.allPossibleMoves = allPossibleMoves;
}
