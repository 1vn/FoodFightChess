var King = function(playerColour, row, col) {
  var score = 200;
  Piece.call(this, score, playerColour, row, col)
}
King.prototype = Object.create(Piece.prototype);
King.prototype.constructor = King;
King.prototype.getName = function() {
  return 'King';
}


King.protoype.copy = function() {
  copy = new King(this.colour, this.row, this.col)
  return copy;
}

King.prototype.updateKingPos = function() {
  return [this.row, this.col]
}
