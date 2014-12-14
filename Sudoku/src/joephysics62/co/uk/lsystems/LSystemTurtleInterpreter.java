package joephysics62.co.uk.lsystems;

import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import joephysics62.co.uk.lsystems.turtle.Turtle3D;
import joephysics62.co.uk.lsystems.turtle.TurtleListener;
import joephysics62.co.uk.lsystems.turtle.TurtleState;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

public class LSystemTurtleInterpreter {

  private final Turtle3D _turtle3d;
  private final double _angleStep;
  private final double _drawStep;
  private final double _narrowFactor;

  public LSystemTurtleInterpreter(final TurtleListener listener, final double angleStep, final double drawStep, final double narrowFactor) {
    _angleStep = angleStep;
    _drawStep = drawStep;
    _narrowFactor = narrowFactor;
    final Point3D origin = new Point3D(0, 0, 0);
    final RealMatrix identityMatrix = MatrixUtils.createRealIdentityMatrix(3);
    final TurtleState initialState = new TurtleState(origin, identityMatrix, 0.03, Color.GREEN);
    _turtle3d = new Turtle3D(initialState, listener);
  }

  public void interpret(final String lsystemResult) {
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
        _turtle3d.move(_drawStep, true);
        break;
      case 'f':
      case 'g':
        _turtle3d.move(_drawStep, false);
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
  }

}
