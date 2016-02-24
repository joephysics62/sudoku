package joephysics62.co.uk.old.lsystems.turtle.modules;

import joephysics62.co.uk.old.lsystems.turtle.Module;
import joephysics62.co.uk.old.lsystems.turtle.Turtle;

public class Draw extends Module {

  public Draw(final char id, final double distance) {
    super(id, distance);
  }

  @Override
  public void apply(final Turtle turtle) {
    turtle.move(getParameter(0), true);
  }

}
