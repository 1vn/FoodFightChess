var PIECES_NAMES = ['null', 'Pawn']; //debug
var COLOUR_NAMES = ['white', 'black']; //debug
function Piece(rank, colour, row, col, no) {
  this.rank = rank;
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
  img.src = "images/" + this.colour + this.rank + ".png";
  this.thisDiv.appendChild(img);
  document.body.appendChild(this.thisDiv);
  this.generateId(rank, colour, row, col);
  this.thisDiv.id = this.idCode;
  this.thisDiv.style.draggable = "true";
  this.outDiv();
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
  this.thisDiv.remove();
}



Piece.prototype.getRank = function() {
  return this.rank;
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
  this.row = move.toRow;
  this.col = move.toCol;
  this.xPos = (move.toCol - 1) * 80;
  this.yPos = (move.toRow - 1) * 80;
  this.outDiv();
}

Piece.prototype.drag = function(xPos, yPos) {
  xPos = document.all ? window.event.clientX : xPos;
  yPos = document.all ? window.event.clientY : yPos;
  this.xPos = xPos - 40;
  this.yPos = yPos - 40;
  this.outDiv();
}

Piece.prototype.getName = function() {}
