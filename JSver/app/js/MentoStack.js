/**
  Stack to keep all the board objects. Can be used to for undo feature or
  checking for checkmate.
*/
var MentoStack = function() {
    if (arguments.callee._singletonInstance) //Singleton block
        return arguments.callee._singletonInstance;
    arguments.callee._singletonInstance = this;
    var stack = []
    var top = null

    this.push = function(mento) {
        stack.push(mento)
    }
    this.pop = function() {
        return stack.pop(mento)
    }

    this.isEmpty = function() {
        return top == null
    }
}
