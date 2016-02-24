package joephysics62.co.uk.old.lsystems.turtle.modules;

import joephysics62.co.uk.old.lsystems.turtle.Module;
import joephysics62.co.uk.old.lsystems.turtle.Turtle;

public class UTurn extends Module {

  public UTurn() {
    super('|');
  }

  @Override
  public void apply(final Turtle turtle) {
    turtle.turn(180);
  }

}
