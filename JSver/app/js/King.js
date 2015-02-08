var King = function(playerColour, row, col) {
    var score = 200;
    Piece.call(this, score, playerColour, row, col)
}
King.prototype = Object.create(Piece.prototype);
King.prototype.constructor = King;
King.prototype.getName = function() {
    return 'King';
}


King.prototype.copy = function() {
    copy = new King(this.colour, this.row, this.col)
    copy.allPossibleMoves = this.allPossibleMoves
    copy.removeDiv()
    return copy
}

King.prototype.updateKingPos = function() {
    return [this.row, this.col]
}

King.prototype.getAllPossibleMoves = function() {
    allPossibleMoves = []
    board = new Board();
    for (var dx = -1; dx <= 1; dx++) {
        dCol = this.col + dx
        for (var dy = -1; dy <= 1; dy++) {
            dRow = this.row + dy
            if ((board.getPiece(dRow, dCol) != 0 &&
                    board.getPiece(dRow, dCol).getColour() != this.colour) ||
                board.getPiece(dRow, dCol) == 0) {
                move = new Move(this.row, this.col, dRow, dCol)
                allPossibleMoves.push(move)
                var toSpace = board.getPiece(dRow, dCol)
                if (this.colour == 0 && Piece.prototype.bTargets.indexOf(
                        toSpace) <=
                    -1)
                    Piece.prototype.bTargets.push(toSpace);
                else if (this.colour == 1 && Piece.prototype.wTargets.indexOf(
                        toSpace) <=
                    -1)
                    Piece.prototype.wTargets.push(toSpace);
            }
        }
    }
    this.allPossibleMoves = allPossibleMoves
}
