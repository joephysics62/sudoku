package joephysics62.co.uk.lsystems;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import joephysics62.co.uk.lsystems.turtle.Turtle3D;
import joephysics62.co.uk.lsystems.turtle.TurtleState;

public class LSystemTurtleInterpreter {

  private final Turtle3D _turtle3d;
  private final double _angleStep;
  private final double _drawStep;
  private final double _narrowFactor;

  public LSystemTurtleInterpreter(final double angleStep, final double drawStep, final double narrowFactor) {
    _angleStep = angleStep;
    _drawStep = drawStep;
    _narrowFactor = narrowFactor;
    final Point3D origin = new Point3D(0, 0, 0);
    final TurtleState initialState = new TurtleState(origin, Rotate.Z_AXIS, Rotate.X_AXIS, 0.03, Color.GREEN);
    _turtle3d = new Turtle3D(initialState);
  }

  public List<Line3D> interpret(final String lsystemResult) {
    final List<Line3D> out = new ArrayList<>();
    for (final char c : lsystemResult.toCharArray()) {
      switch (c) {
      case '+':
        _turtle3d.turn(_angleStep);
        break;
      case '-':
        _turtle3d.turn(-_angleStep);
        break;
      case '^':
        _turtle3d.pitch(_angleStep);
        break;
      case '&':
        _turtle3d.pitch(-_angleStep);
        break;
      case '/':
        _turtle3d.roll(_angleStep);
        break;
      case '\\':
        _turtle3d.roll(-_angleStep);
        break;
      case '!':
        _turtle3d.narrow(_narrowFactor);
        break;
      case 'A':
      case 'F':
      case 'G':
      case 'S':
        final TurtleState start = _turtle3d.getState();
        _turtle3d.move(_drawStep);
        final TurtleState end = _turtle3d.getState();
        out.add(new Line3D(start.getCoord(), end.getCoord(), start.getColor(), start.getWidth()));
        break;
      case 'f':
      case 'g':
        _turtle3d.move(_drawStep);
        break;
      case '[':
        _turtle3d.push();
        break;
      case ']':
        _turtle3d.pop();
        break;
      default:
        break;
      }
    }
    return out;
  }

}
