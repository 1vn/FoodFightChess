function newGame() {

  var SQUARE_DIM = 80;
  NO_ROWS = 10;
  NO_COLS = 10;

  var canvas = document.getElementById("canvas");
  var context = canvas.getContext("2d");
  var colours = ['black', 'white']
  canvas.addEventListener("mousedown", selectPiece, false);
  var selectedPiece = 0;
  var fromRow, fromCol, toRow, toCol
  var board = new Board();
  var pieceSelected = false;
  board.setUpBoard();
  board.draw();
}

function selectPiece(event) {
  var board = new Board();
  fromRow = Math.floor((event.pageY / 80) + 1);
  fromCol = Math.floor((event.pageX / 80) + 1);
  if (board.getPiece(fromRow, fromCol) != 0) {
    selectedPiece = board.getPiece(fromRow, fromCol);
    console.log(selectedPiece.getName());
    canvas.addEventListener("mousedown", movePiece);
    pieceSelected = true;
    canvas.removeEventListener("mousedown", selectPiece);
  }
}

function movePiece(event) {
  var toRow = Math.floor((event.pageY) / 80 + 1);
  var toCol = Math.floor((event.pageX) / 80 + 1);
  console.log("fromRow: " + fromRow);
  console.log("fromCol: " + fromCol);
  console.log("toRow: " + toRow);
  console.log("toCol: " + toCol);
  var canvas = document.getElementById("canvas");
  var context = canvas.getContext("2d");
  if (pieceSelected != false && (toRow != fromRow || toCol != fromCol)) {
    console.log("movePiece intiated.");
    console.log("Something is selected.");
    var toRow = Math.floor((event.pageY) / 80 + 1);
    var toCol = Math.floor((event.pageX) / 80 + 1);
    console.log("Selected piece: " + selectedPiece.getName() + " at row: " +
      fromRow + " col :" + fromCol +
      " will be moved to row: " + toRow + " col: " + toCol);
    var board = new Board();
    var thisMove = new Move(fromRow, fromCol, toRow, toCol);
    board.move(thisMove);
    isSelected = false;
    selectedPiece = 0;
    var canvas = document.getElementById("canvas");
    var context = canvas.getContext("2d");
    context.clearRect(0, 0, canvas.width, canvas.height);
    board.draw();
    console.log("movePiece removed");
  }
  canvas.removeEventListener("mousedown", movePiece);
  canvas.addEventListener("mousedown", selectPiece);
}

function mouseDrag(event) {

}


function removeThatShit(event) {
  var board = new Board();
  var fromRow = Math.floor((event.pageY / 80) + 1);
  var fromCol = Math.floor((event.pageX / 80) + 1);
  board.remove(fromRow, fromCol);
  board.draw();
}
