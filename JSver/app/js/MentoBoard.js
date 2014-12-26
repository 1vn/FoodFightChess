/**
  Momento for board. Flash copies every piece and space on a singlton board.
*/
var MentoBoard = function() {
  board = new Board();
  this.grid = [
    [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
  ];
  for (var row = 1; row < 9; row++)
    for (var col = 1; col < 9; col++) {
      space = board.getPiece(row, col)
      if (space != 0) {
        this.grid[row][col] = space.copy()
      }
    }
}

MentoBoard.prototype.getPiece = function(row, col) {
  return this.grid[row][col];
}
