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
  pieceSelected = false;
  board.setUpBoard();
  board.draw();
}

function selectPiece(event) {
  var board = new Board();
  fromRow = Math.floor((event.pageY / 80) + 1);
  fromCol = Math.floor((event.pageX / 80) + 1);
  if (!pieceSelected && board.getPiece(fromRow, fromCol) != 0) {
    console.log("selectPiece is initiated");
    selectedPiece = board.getPiece(fromRow, fromCol);
    console.log(selectedPiece.getName() + " at row: " + selectedPiece.getRow() +
      " col: " + selectedPiece.getCol());
    document.addEventListener("mousemove", dragWrapper);
    pieceSelected = true;
    var selectedDiv = document.getElementById(selectedPiece.getId())
    selectedDiv.style.zIndex = 1;
    document.removeEventListener("mousedown", selectPiece, false);
  }
}

function dragWrapper(event) {
  dragPiece(event)
  document.addEventListener("mousedown", placeWrapper)
}

function dragPiece(event) {
  if (pieceSelected && selectedPiece != 0) {
    console.log("dragPiece is intiated");
    var xPos = event.pageX;
    var yPos = event.pageY;
    selectedPiece.drag(xPos, yPos);
  }

}

function placeWrapper(event) {
  document.removeEventListener("mousemove", dragPiece);
  placePiece(event)
  document.addEventListener("mousedown", selectPiece);
}

function placePiece(event) {
  var toRow = Math.floor((event.pageY) / 80 + 1);
  var toCol = Math.floor((event.pageX) / 80 + 1);

  if (pieceSelected && (toRow != fromRow || toCol != fromCol)) {
    console.log("placePiece intiated.");
    var toRow = Math.floor((event.pageY) / 80 + 1);
    var toCol = Math.floor((event.pageX) / 80 + 1);
    move = new Move(fromRow, fromCol, toRow, toCol);
    var board = new Board();
    board.move(move)
    var selectedDiv = document.getElementById(selectedPiece.getId())
    selectedDiv.style.zIndex = 0;
  }
  selectedPiece = 0;
  pieceSelected = false;
}
