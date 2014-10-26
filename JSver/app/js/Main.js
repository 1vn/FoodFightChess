function newGame() {

  var SQUARE_DIM = 80;
  NO_ROWS = 10;
  NO_COLS = 10;

  var canvas = document.getElementById("canvas");
  var context = canvas.getContext("2d");
  var colours = ['black', 'white']
  document.addEventListener("mousedown", selectPiece, false);
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
    console.log(selectedPiece.getName() + "at row: " + selectedPiece.getRow() +
      " col: " + selectedPiece.getCol());
    document.removeEventListener("mousedown", selectPiece);
    document.addEventListener("mousemove", dragPiece);
    pieceSelected = true;

  }
}

function dragPiece(event) {
  if (pieceSelected && selectedPiece != 0) {
    var xPos = event.pageX;
    var yPos = event.pageY;
    selectedPiece.drag(xPos, yPos);
    selectedDiv = document.getElementById("" + selectedPiece.getRank() +
      selectedPiece.getColour() + selectedPiece.getRow() + selectedPiece.getCol()
    );
    selectedDiv.addEventListener("mouseup", placePiece);
  }

}



function placePiece(event) {
  var toRow = Math.floor((event.pageY) / 80 + 1);
  var toCol = Math.floor((event.pageX) / 80 + 1);

  if (pieceSelected && (toRow != fromRow || toCol != fromCol)) {
    console.log("movePiece intiated.");
    console.log("Something is selected.");
    var toRow = Math.floor((event.pageY) / 80 + 1);
    var toCol = Math.floor((event.pageX) / 80 + 1);
    console.log("Selected piece: " + selectedPiece.getName() + " at row: " +
      fromRow + " col :" + fromCol +
      " will be moved to row: " + toRow + " col: " + toCol);
    move = new Move(fromRow, fromCol, toRow, toCol);
    var board = new Board();
    board.move(move)
    selectedPiece = 0;
    pieceSelected = false;
  }
  document.addEventListener("mousedown", selectPiece);
  selectedDiv.removeEventListener("mousemove", dragPiece);
  document.removeEventListener("mouseup", placePiece);
}
