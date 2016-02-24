package joephysics62.co.uk.old.lsystems.turtle;

import java.util.Stack;

import javafx.geometry.Point3D;

public class Turtle {

  private final Stack<State> _stateStack = new Stack<>();
  private State _currentState;
  private Listener _listener;

  public Turtle(final State start) {
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
    _currentState = _currentState.setWidth(factor * _currentState.getWidth());
  }

  public void move(final double distance, final boolean draw) {
    final Point3D start = _currentState.getCoord();
    _currentState = _currentState.move(distance);
    if (draw && _listener != null) {
      final Point3D end = _currentState.getCoord();
      final int colourIndex = _currentState.getColourIndex();
      final double width = _currentState.getWidth();
      _listener.drawLine(start, end, colourIndex, width);
    }
  }


  public State getState() {
    return _currentState;
  }

  public void incrementColour() {
    _currentState = _currentState.incrementColour();
  }

  public void width(final double width) {
    _currentState = _currentState.setWidth(width);
  }

  public void setListener(final Listener listener) {
    _listener = listener;
  }

}
