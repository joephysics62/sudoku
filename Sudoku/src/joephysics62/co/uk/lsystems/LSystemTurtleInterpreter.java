package joephysics62.co.uk.lsystems;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Point3D;
import javafx.scene.transform.Rotate;
import joephysics62.co.uk.lsystems.graphics.Line3D;
import joephysics62.co.uk.lsystems.turtle.IModule;
import joephysics62.co.uk.lsystems.turtle.Listener;
import joephysics62.co.uk.lsystems.turtle.State;
import joephysics62.co.uk.lsystems.turtle.Turtle;

public class LSystemTurtleInterpreter {

  private static final int INITIAL_COLOUR_INDEX = 0;
  private static final double INTIAL_WIDTH = 0.45;
  private static final Point3D INITIAL_LEFT = Rotate.X_AXIS;
  private static final Point3D INITIAL_HEADING = Rotate.Z_AXIS;
  private static final Point3D ORIGIN = new Point3D(0, 0, 0);


  public List<Line3D> interpret(final List<IModule> lsystemResult) {
    final State start = new State(ORIGIN, INITIAL_HEADING, INITIAL_LEFT, INTIAL_WIDTH, INITIAL_COLOUR_INDEX);
    final Turtle turtle = new Turtle(start);
    final List<Line3D> out = new ArrayList<>();
    final Listener listener = new Listener() {
      @Override
      public void drawLine(final Point3D start, final Point3D end, final int colour, final double width) {
        out.add(new Line3D(start, end, colour, width));
      }
    };
    turtle.setListener(listener);
    for (final IModule c : lsystemResult) {
      c.apply(turtle);
    }
    return out;
  }

}
