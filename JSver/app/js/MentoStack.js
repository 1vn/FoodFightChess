/**
  Stack to keep all the board objects. Can be used to for undo feature or
  checking for checkmate.
*/
var MentoStack = function() {
  if (arguments.callee._singletonInstance) //Singleton block
    return arguments.callee._singletonInstance;
  arguments.callee._singletonInstance = this;

  var stack = []

  this.push = function(mento) {
    console.log("mento pushed.")
    stack.push(mento)
  }
  this.pop = function() {
    stack.pop()
  }
}
