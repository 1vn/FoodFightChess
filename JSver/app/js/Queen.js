function Queen (playerColour, row, col){
  var score = 8;
  Piece.call(this, score, playerColour, row, col);
}

Queen.prototype = Object.create(Piece.prototype);
Queen.prototype.constructor = Queen;
Queen.prototype.getName = function() {
  return 'Queen';
}

Queen.prototype.getAllPossibleMoves = function(){
  board = new Board();
  for (var dy = -1; dy <= 1; dy++){
    for (var dx = -1; dx <= 1; dx++)
      {
        dRow = this.row + fy;
        dCol = this.row + fx;
        
      }
  }
}
