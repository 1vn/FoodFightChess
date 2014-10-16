function Pawn(playerColor, row, col) {
  var RANK = 1;
  var img = new Image();
  var context = img.getContext("2d");
  img.onload = function() {
    context.drawImage(img, this.row * 80, this.col * 80);
  }
  img.src = "../images/" + this.colour + this.rank + ".png";

  Piece.call(this, RANK, playerColour, row, col);

  Pawn.prototype = Object.create(Piece.prototype);
  Pawn.prototype.constructor = Pawn;
}