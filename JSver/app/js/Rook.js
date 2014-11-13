function Rook(playerColour, row, col) {
  var RANK = 5;
  Piece.call(this, RANK, playerColour, row, col);
}

Rook.prototype = Object.create(Piece.prototype);
Rook.prototype.constructor = Rook;
Rook.prototype.getName = function() {
  return 'Rook';
}

Rook.prototype.getAllPossibleMoves = function(){
  //Check logic for white pieces (bottom)
  if(this.colour == 0)
    {
      for(var dRow = -1; dRow <= 1; dRow += 2)
    }
}
