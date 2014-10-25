function Move(fromR, fromC, toR, toC) {
  console.log("Move object created: fromRow: " + fromR + " fromCol: " +
    fromC + " toRow: " + toR + " toCol: " + toC);
  Move.prototype.fromRow = fromR;
  Move.prototype.fromCol = fromC;
  Move.prototype.toRow = toR;
  Move.prototype.toCol = toC;
}
