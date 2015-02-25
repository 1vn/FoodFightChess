function newGame() {

    SQUARE_DIM = 80;

    var canvas = document.getElementById("canvas");
    var context = canvas.getContext("2d");
    var colours = ['black', 'white']
    document.addEventListener("mousedown", selectPiece, false);
    var selectedPiece = 0;
    var fromRow, fromCol, toRow, toCol
    var board = new Board();
    turn = 0;
    pieceSelected = false;
    board.setUpBoard();
    board.draw();
    grave = []
}

function selectPiece(event) {
    var board = new Board();
    fromRow = Math.floor((event.pageY / SQUARE_DIM) + 1);
    fromCol = Math.floor((event.pageX / SQUARE_DIM) + 1);
    selected = board.getPiece(fromRow, fromCol);
    if (!pieceSelected && selected != 0 && turn == selected.getColour()) {
        console.log("selectPiece is initiated");
        selectedPiece = selected;
        pieceSelected = true;
        var selectedDiv = selectedPiece.getDiv();
        selectedDiv.style.zIndex = 1;
        document.addEventListener("mousemove", dragWrapper);
        document
            .removeEventListener("mousedown", selectPiece, false);
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
    var toRow = Math.floor((event.pageY) / SQUARE_DIM + 1);
    var toCol = Math.floor((event.pageX) / SQUARE_DIM + 1);

    if (pieceSelected) {
        console.log("placePiece intiated.");
        move = new Move(fromRow, fromCol, toRow, toCol);
        console.log("Move created " + move.getInfo())
        board = new Board();
        board.move(move, selectedPiece);
        var selectedDiv = document.getElementById(selectedPiece.getId())
        selectedDiv.style.zIndex = 0;
        console.log("Turn: " + turn);
        board.updateMoves();
        console.log("Dead pieces are: ")
        for (var i = 0; i < grave.length; i++) {
            console.log(grave[i].getId());
        }
        console.log("Targetted white pieces are: ")
        for (var i = 0; i < Piece.prototype.wTargets.length; i++) {
            console.log(Piece.prototype.wTargets[i]);
        }
        console.log("Targetted black pieces are: ")
        for (var i = 0; i < Piece.prototype.bTargets.length; i++) {
            console.log(Piece.prototype.bTargets[i]);
        }
    }
    selectedPiece = 0;
    pieceSelected = false;
}
