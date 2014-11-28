var Knight = function(playerColour, row, col){
  var score = 4;
  Piece.call(this, score, playerColour, row, col);
}

Knight.prototype.copy = function(){
  copy = new Queen(this.colour, this.row, this.col);
  return copy
}

Knight.prototype.getAllPossibleMoves = function(){
  var board = new Board();
  var allPossibleMoves = [];
  //Check north and south
  for(var dy = -2; dy <= 2; dy += 4)
  {
      dRow = dy+this.row;
      if(dRow < 9 && dRow > 0){
              //Check both sides
      for(var dx = -1; dx < 1; dx+=2)
        {
          dCol = dx+this.col;
          if(dCol < 9 && dCol > 0 && (board.getPiece(dRow, dCol) == 0 ||
            (board.getPiece(dRow, dCol) != 0 && board.getPiece(dRow, dCol).getColour
            != this.colour)))
            {
              allPossibleMoves.push(new Move(this.row, this.col, dRow, dCol));
            }
        }
      }
  }

  for(var dx = -2; dx <=2; dy +=4)
    {
      dCol = dx + this.col;
      if(dCol < 9 && dCol >0)
        {
          for(var dy = -1; dy < 1; dy +=2)
            {
              dRow = dy + this.row;
              if(dRow > 0 && dRow < 9 &&(board.getPiece(dRow, dCol) == 0 ||
                (board.getPiece(dRow, dCol) != 0 && board.getPiece(dRow, dCol).getColour
                != this.colour)))
                {
                  allPossibleMoves.push(new Move(this.row, this.col, dRow, dCol))
                }
            }
        }
    }

}
