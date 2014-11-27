var BoardStack = function(){

  if (arguments.callee._singletonInstance) //Singleton block
    return arguments.callee._singletonInstance;
  arguments.callee._singletonInstance = this;

  var stack = []

  this.push = function(board){
    stack.push(board)
  }
  this.pop = function(){
    stack.pop(board)
  }
}
