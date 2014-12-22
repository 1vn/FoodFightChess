var PIECES_NAMES = ['null', 'Pawn']; //debug
var COLOUR_NAMES = ['white', 'black']; //debug
function Piece(score, colour, row, col, no) {
  this.score = score;
  this.colour = colour;
  this.row = row;
  this.col = col;
  this.xPos = (col - 1) * 80;
  this.yPos = (row - 1) * 80;
  //Create the div to represent this piece
  this.thisDiv = document.createElement("div");
  this.thisDiv.style.width = "80px";
  this.thisDiv.style.height = "80px";
  var img = new Image();
  img.src = "images/" + this.colour + this.score + ".png";
  this.thisDiv.appendChild(img);
  document.body.appendChild(this.thisDiv);
  this.generateId(score, colour, row, col);
  this.thisDiv.id = this.idCode;
  this.outDiv();
  this.noOfMoves = 0;
  var score;
}

Piece.prototype.wTargets = [];
Piece.prototype.bTargets = [];

Piece.prototype.getScore = function() {
  return this.score;
}

Piece.prototype.generateId = function(rank, colour, row, col) {
  this.idCode = "" +
    rank + colour + row + col
  console.log(this.idCode);
}

Piece.prototype.getId = function() {
  return this.idCode
}

Piece.prototype.getDiv = function() {
  return document.getElementbyId(this.idCode);
}

Piece.prototype.outDiv = function() {
  var updateDiv = document.getElementById(this.idCode);
  updateDiv.style.left = this.xPos + 9 + "px";
  updateDiv.style.top = this.yPos + 9 + "px";
  updateDiv.style.position = "absolute";
}

Piece.prototype.removeDiv = function() {
  console.log("removeDiv called.")
  this.thisDiv.remove();
}

Piece.prototype.isValidMove = function(move) {
  for (var i = 0; i < this.allPossibleMoves.length; i++) {
    checkMove = this.allPossibleMoves[i];
    if (checkMove.fromRow === move.fromRow && checkMove.fromCol === move.fromCol &&
      checkMove.toRow === move.toRow && checkMove.toCol === move.toCol && !isCheck(move))
      {
        return true;
      }
  }
}

var isCheck = function(move){
  var board = new Board()
  isCheck = false;
  board.remember()
  board.forceMove(move)
  
  board.undo();
  return isCheck
}

Piece.prototype.getScore = function() {
  return this.score;
}

Piece.prototype.getRow = function() {
  return this.row;
}

Piece.prototype.getCol = function() {
  return this.col;
}

Piece.prototype.getColour = function() {
  return this.colour;
}

Piece.prototype.getDiv = function() {
  return this.thisDiv;
}

Piece.prototype.move = function(move) {
  board = new Board();
  if (move.toRow == this.row && move.toCol == this.Col) {
    this.noMove();
    return;
  }
  prey = board.getPiece(move.toRow, move.toCol);
  if (prey != 0) {
    grave.push(prey);
    prey.removeDiv();
  }
  this.row = move.toRow;
  this.col = move.toCol;
  this.xPos = (move.toCol - 1) * 80;
  this.yPos = (move.toRow - 1) * 80;
  turn = Math.abs(turn - 1)
  Piece.prototype.wTargets = [];
  Piece.prototype.bTargets = [];
  this.outDiv();
  this.noOfMoves++;
}


Piece.prototype.noMove = function() {
  this.row = this.row;
  this.col = this.col;
  this.xPos = (this.col - 1) * 80;
  this.yPos = (this.row - 1) * 80;
  this.outDiv();
}

Piece.prototype.copy = function() {}

Piece.prototype.drag = function(xPos, yPos) {
  xPos = document.all ? window.event.clientX : xPos;
  yPos = document.all ? window.event.clientY : yPos;
  this.xPos = xPos - 40;
  this.yPos = yPos - 40;
  this.outDiv();
}

Piece.prototype.getName = function() {}
Piece.prototype.getAllPossibleMoves = function() {}
