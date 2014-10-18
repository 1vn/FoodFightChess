var PIECES_NAMES = ['null', 'Pawn']; //debug
var COLOUR_NAMES = ['white', 'black']; //debug
function Piece(rank, colour, row, col) {
  this.rank = rank;
  this.colour = colour;
  this.row = row;
  this.col = col;

}

Piece.prototype.draw = function() {
  var xPos = (this.col - 1) * 80;
  var yPos = (this.row - 1) * 80;
  var canvas = document.getElementById("canvas");
  var context = canvas.getContext("2d");
  var img = new Image();
  img.onload = function() {
    context.drawImage(img, xPos, yPos);
  }
  img.src = "images/" + this.colour + this.rank + ".png";
}



Piece.prototype.getRank = function() {
  return rank;
}

Piece.prototype.getRow = function() {
  return row;
}

Piece.prototype.getCol = function() {
  return col;
}

Piece.prototype.clear = function() {
  var xPos = (this.col - 1) * 80;
  var yPos = (this.row - 1) * 80;
  var canvas = document.getElementById("canvas");
  var context = canvas.getContext("2d");
  var img = new Image();
  img.onload = function() {
    context.drawImage(img, xPos, yPos);
  }
  img.src = "images/clear.png";

}

Piece.prototype.getName = function() {}