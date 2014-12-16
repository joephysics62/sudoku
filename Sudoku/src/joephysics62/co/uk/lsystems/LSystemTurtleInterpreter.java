package joephysics62.co.uk.lsystems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.scene.paint.Color;
import joephysics62.co.uk.lsystems.turtle.Turtle3D;
import joephysics62.co.uk.lsystems.turtle.TurtleState;

public class LSystemTurtleInterpreter {

  private final double _angleStep;
  private final double _drawStep;
  private final double _narrowFactor;

  public LSystemTurtleInterpreter(final double angleStep, final double drawStep, final double narrowFactor) {
    _angleStep = angleStep;
    _drawStep = drawStep;
    _narrowFactor = narrowFactor;
  }

  public List<Line3D> interpret(final String lsystemResult, final TurtleState initialState) {
    final Turtle3D turtle3d = new Turtle3D(initialState, Arrays.asList(Color.BROWN, Color.DARKGREEN, Color.GREEN));
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
        final TurtleState start = turtle3d.getState();
        turtle3d.move(_drawStep);
        final TurtleState end = turtle3d.getState();
        out.add(new Line3D(start.getCoord(), end.getCoord(), start.getColour(), start.getWidth()));
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
