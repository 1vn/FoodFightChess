function Pawn(playerColour, row, col) {
  var RANK = 1;
  Piece.call(this, RANK, playerColour, row, col);
}
Pawn.prototype = Object.create(Piece.prototype);
Pawn.prototype.constructor = Pawn;
Pawn.prototype.getName = function() {
  return 'Pawn';
}
