package joephysics62.co.uk.lsystems.turtle;

import java.util.List;
import java.util.Stack;

import javafx.scene.paint.Color;

public class Turtle3D {

  private final Stack<TurtleState> _stateStack = new Stack<>();
  private TurtleState _currentState;
  private final List<Color> _colours;

  public Turtle3D(final TurtleState start, final List<Color> colours) {
    _currentState = start;
    _colours = colours;
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
    final Color currentColour = _currentState.getColour();
    final int index = _colours.indexOf(currentColour);
    _currentState = _currentState.changeColour(_colours.get(Math.min(_colours.size() - 1, index + 1)));
  }

}
