var Move = function(fromR, fromC, toR, toC) {
  console.log("Move object created: fromRow: " + fromR + " fromCol: " +
    fromC + " toRow: " + toR + " toCol: " + toC);
  this.fromRow = fromR;
  this.fromCol = fromC;
  this.toRow = toR;
  this.toCol = toC;
  Move.prototype.getInfo = function() {
    info = "fromRow: " + this.fromRow + " fromCol: " + this.fromCol +
      " toRow: " + this.toRow + " toCol: " + this.toCol;
    return info;
  }
}
