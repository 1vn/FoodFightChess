function newGame() {

  var SQUARE_DIM = 70;
  NO_ROWS = 10;
  NO_COLS = 10;

  var canvas = document.getElementById("canvas");
  var context = canvas.getContext("2d");
  var colours = ['black', 'white']

  var board = new Board()
  board.draw();

}