package joephysics62.co.uk.lsystems.turtle;

import java.util.Stack;

public class Turtle {

  private final Stack<TurtleState> _stateStack = new Stack<>();
  private TurtleState _currentState;

  public Turtle(final TurtleState start) {
    _currentState = start;
  }

  public void push() {
    _stateStack.push(_currentState);
  }

  public void pop() {
    _currentState = _stateStack.pop();
  }

  public void turn(final double degrees) {
    _currentState = _currentState.turn(degrees);
  }

  public void roll(final double degrees) {
    _currentState = _currentState.roll(degrees);
  }

  public void pitch(final double degrees) {
    _currentState = _currentState.pitch(degrees);
  }

  public void narrow(final double factor) {
    _currentState = _currentState.changeWidth(factor);
  }

  public void move(final double distance) {
    _currentState = _currentState.move(distance);
  }


  public TurtleState getState() {
    return _currentState;
  }

  public void incrementColour() {
    _currentState = _currentState.incrementColour();
  }

}
