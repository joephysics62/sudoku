package joephysics62.co.uk.lsystems.turtle.modules;

import joephysics62.co.uk.lsystems.turtle.Module;
import joephysics62.co.uk.lsystems.turtle.Turtle;

public class Draw extends Module {

  public Draw(final char id, final double distance) {
    super(id, distance);
  }

  @Override
  public void apply(final Turtle turtle) {
    turtle.move(getParameters()[0], true);
  }

}
