var Board = (function () {
    var instance;
    var grid;
    var ROWS = 10;
    var COLS = 10;

    function createInstance() {
        var board = new Board();
        this.grid = [ROWS][COLS];
        return board;
    }

    return {
        getInstance: function () {
            if (!instance) {
                instance = createInstance();
            }
            return instance;
        }
    };
})();
