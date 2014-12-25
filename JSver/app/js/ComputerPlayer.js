function ComputerPlayer(colour){
this.colour = colour;
var board = new Board();
var pieces = board.getPieces();
if (arguments.callee._singletonInstance) //Singleton block
  return arguments.callee._singletonInstance;
arguments.callee._singletonInstance = this;

this.makeMove = function{
  return move
}

this.evaluateBoard = function(){
  board = new Board();
  score = 0
  for (var row = 1; row < 9; row++)
  {
    for(var col = 1; col < 9; col++)
      {
        space = board.getPiece(row, col);
        if(space != 0)
          {
            if(space.getColour() == this.colour)
              score+=space.getScore();
            else if (space.getColour() != this.colour)
              score-=space.getScore();
          }
      }
  }
}
}
