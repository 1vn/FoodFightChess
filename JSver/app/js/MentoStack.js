/**
  Stack to keep all the board objects. Can be used to for undo feature or
  checking for checkmate.
*/
var MentoStack = function() {
  if (arguments.callee._singletonInstance) //Singleton block
    return arguments.callee._singletonInstance;
  arguments.callee._singletonInstance = this;

  var top = null

  this.push = function(mento) {
    console.log("mento pushed.")
    if(top == null){
        top = mento
        return
    }
    else{
        n = top
        top = mento
        mento.next = top
    }

  }
  this.pop = function() {
     n = top
     top = top.next
    return n
  }
}
