package joephysics62.co.uk.lsystems.turtle.modules;

import joephysics62.co.uk.lsystems.turtle.Module;
import joephysics62.co.uk.lsystems.turtle.Turtle;

public class Move extends Module {

  public Move(final char id, final double distance) {
    super(id, distance);
  }

  @Override
  public void apply(final Turtle turtle) {
    turtle.move(getParameter(0), false);
  }

}
