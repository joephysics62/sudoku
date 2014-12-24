package joephysics62.co.uk.lsystems;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Point3D;
import javafx.scene.transform.Rotate;
import joephysics62.co.uk.lsystems.graphics.Line3D;
import joephysics62.co.uk.lsystems.turtle.IModule;
import joephysics62.co.uk.lsystems.turtle.State;
import joephysics62.co.uk.lsystems.turtle.Turtle;

public class LSystemTurtleInterpreter {

  private static final int INITIAL_COLOUR_INDEX = 0;
  private static final double INTIAL_WIDTH = 0.45;
  private static final Point3D INITIAL_LEFT = Rotate.X_AXIS;
  private static final Point3D INITIAL_HEADING = Rotate.Z_AXIS;
  private static final Point3D ORIGIN = new Point3D(0, 0, 0);
  private final LSystem _lSystem;

  public LSystemTurtleInterpreter(final LSystem lSystem) {
    _lSystem = lSystem;
  }

  public List<Line3D> interpret(final List<IModule> lsystemResult) {
    final State start = new State(ORIGIN, INITIAL_HEADING, INITIAL_LEFT, INTIAL_WIDTH, INITIAL_COLOUR_INDEX);
    final Turtle turtle = new Turtle(start);
    final List<Line3D> out = new ArrayList<>();
    for (final IModule c : lsystemResult) {
      final double[] parameters = c.getParameters();
      final double param = parameters.length == 0 ? - 1 : parameters[0];
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
        final State before = turtle.getState();
        turtle.move(param);
        final State end = turtle.getState();
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
