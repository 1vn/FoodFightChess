var Move = function(fromR, fromC, toR, toC) {
  this.fromRow = fromR;
  this.fromCol = fromC;
  this.toRow = toR;
  this.toCol = toC;
  this.enPassant = false;
  this.promote = false;
  Move.prototype.getInfo = function() {
    info = "fromRow: " + this.fromRow + " fromCol: " + this.fromCol +
      " toRow: " + this.toRow + " toCol: " + this.toCol;
    return info;
  }
}
