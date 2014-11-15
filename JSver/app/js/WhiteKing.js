function WhiteKing(playerColour, row, col){
  var score = 200;
  Piece.call(this, score, playerColour, row, col);

  if (arguments.callee._singletonInstance) //Singleton block
    return arguments.callee._singletonInstance;
  arguments.callee._singletonInstance = this;
}

WhiteKing.prototype.getAllPossibleMoves()
{
  
}
