function newGame() {

  var SQUARE_DIM = 80;
  NO_ROWS = 10;
  NO_COLS = 10;

  var canvas = document.getElementById("canvas");
  var context = canvas.getContext("2d");
  var colours = ['black', 'white']
  canvas.addEventListener("mousedown", removeThatShit, false);


  var board = new Board();
  board.setUpBoard();
  board.draw();

  function removeThatShit(event) {
    var board = new Board();
    var fromRow = Math.floor((event.pageY / 80) + 1);
    var fromCol = Math.floor((event.pageX / 80) + 1);
    board.remove(fromRow, fromCol);
    board.draw();
  }

}