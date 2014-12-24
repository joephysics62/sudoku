package joephysics62.co.uk.lsystems.turtle.modules;

import javafx.geometry.Point3D;
import joephysics62.co.uk.lsystems.turtle.Module;
import joephysics62.co.uk.lsystems.turtle.Turtle;

public class RollLeftFlat extends Module {

  public RollLeftFlat() {
    super('$');
  }

  @Override
  public void apply(final Turtle turtle) {
    final Point3D left = turtle.getState().getLeft();
    left.angle(new Point3D(0, 0, 1));
    turtle.roll(90 - left.angle(new Point3D(0, 0, 1)));
  }

}
