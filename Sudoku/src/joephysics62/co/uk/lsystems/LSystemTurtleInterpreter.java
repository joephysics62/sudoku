package joephysics62.co.uk.lsystems;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Point3D;
import javafx.scene.transform.Rotate;
import joephysics62.co.uk.lsystems.turtle.Turtle3D;
import joephysics62.co.uk.lsystems.turtle.TurtleLSystem;
import joephysics62.co.uk.lsystems.turtle.TurtleState;

public class LSystemTurtleInterpreter {

  private final TurtleLSystem _lSystem;

  public LSystemTurtleInterpreter(final TurtleLSystem lSystem) {
    _lSystem = lSystem;
  }

  public List<Line3D> interpret(final String lsystemResult) {
    final double _angleStep = _lSystem.angle();
    final double _narrowFactor = _lSystem.narrowing();
    final double _drawStep = _lSystem.drawStep();
    final TurtleState start = new TurtleState(new Point3D(0, 0, 0), Rotate.Z_AXIS, Rotate.X_AXIS, 0.05, 0);
    final Turtle3D turtle3d = new Turtle3D(start);
    final List<Line3D> out = new ArrayList<>();
    for (final char c : lsystemResult.toCharArray()) {
      switch (c) {
      case '+':
        turtle3d.turn(_angleStep);
        break;
      case '-':
        turtle3d.turn(-_angleStep);
        break;
      case '^':
        turtle3d.pitch(_angleStep);
        break;
      case '&':
        turtle3d.pitch(-_angleStep);
        break;
      case '/':
        turtle3d.roll(_angleStep);
        break;
      case '\\':
        turtle3d.roll(-_angleStep);
        break;
      case '!':
        turtle3d.narrow(_narrowFactor);
        break;
      case '\'':
        turtle3d.incrementColour();
        break;
      case 'A':
      case 'F':
      case 'G':
      case 'S':
        final TurtleState before = turtle3d.getState();
        turtle3d.move(_drawStep);
        final TurtleState end = turtle3d.getState();
        out.add(new Line3D(before.getCoord(), end.getCoord(), _lSystem.indexedColour(before.getColourIndex()), before.getWidth()));
        break;
      case 'f':
      case 'g':
        turtle3d.move(_drawStep);
        break;
      case '[':
        turtle3d.push();
        break;
      case ']':
        turtle3d.pop();
        break;
      default:
        break;
      }
    }
    return out;
  }

}
