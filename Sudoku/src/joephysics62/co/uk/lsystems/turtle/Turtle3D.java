package joephysics62.co.uk.lsystems.turtle;

import java.util.Stack;

import javafx.geometry.Point3D;

public class Turtle3D {

  private final Stack<TurtleState> _stateStack = new Stack<>();
  private TurtleState _currentState;
  private final TurtleListener _listener;

  public Turtle3D(final TurtleState start, final TurtleListener listener) {
    _currentState = start;
    _listener = listener;
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

  public void move(final double distance, final boolean draw) {
    final Point3D start = _currentState.getCoord();
    _currentState = _currentState.move(distance);
    if (draw) {
      _listener.drawLine(start, _currentState.getCoord(), _currentState.getColor(), _currentState.getWidth());
    }
  }




}
