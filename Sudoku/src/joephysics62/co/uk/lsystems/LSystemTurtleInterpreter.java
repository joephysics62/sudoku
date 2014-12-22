package joephysics62.co.uk.lsystems;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Point3D;
import javafx.scene.transform.Rotate;
import joephysics62.co.uk.lsystems.graphics.Line3D;
import joephysics62.co.uk.lsystems.turtle.Turtle;
import joephysics62.co.uk.lsystems.turtle.TurtleState;

public class LSystemTurtleInterpreter {

  private final LSystem _lSystem;

  public LSystemTurtleInterpreter(final LSystem lSystem) {
    _lSystem = lSystem;
  }

  public List<Line3D> interpret(final List<TurtleElement> lsystemResult) {
    final TurtleState start = new TurtleState(new Point3D(0, 0, 0), Rotate.Z_AXIS, Rotate.X_AXIS, 0.05, 0);
    final Turtle turtle = new Turtle(start);
    final List<Line3D> out = new ArrayList<>();
    for (final TurtleElement c : lsystemResult) {
      final double[] parameters = c.getParameters();
      final double param = parameters[0];
      switch (c.getId()) {
      case '+':
        turtle.turn(param);
        break;
      case '-':
        turtle.turn(-param);
        break;
      case '|':
        turtle.turn(180);
        break;
      case '^':
        turtle.pitch(param);
        break;
      case '&':
        turtle.pitch(-param);
        break;
      case '/':
        turtle.roll(param);
        break;
      case '\\':
        turtle.roll(-param);
        break;
      case '!':
        turtle.narrow(param);
        break;
      case '£':
        turtle.width(param);
        break;
      case '$':
        final Point3D left = turtle.getState().getLeft();
        left.angle(new Point3D(0, 0, 1));
        turtle.roll(90 - left.angle(new Point3D(0, 0, 1)));
        break;
      case '\'':
        turtle.incrementColour();
        break;
      //case 'A':
      //case 'B':
      //case 'C':
      //case 'D':
      case 'F':
      case 'G':
      case 'S':
        final TurtleState before = turtle.getState();
        turtle.move(param);
        final TurtleState end = turtle.getState();
        out.add(new Line3D(before.getCoord(), end.getCoord(), _lSystem.indexedColour(before.getColourIndex()), before.getWidth()));
        break;
      case 'f':
      case 'g':
        turtle.move(param);
        break;
      case '[':
        turtle.push();
        break;
      case ']':
        turtle.pop();
        break;
      default:
        break;
      }
    }
    return out;
  }

}
