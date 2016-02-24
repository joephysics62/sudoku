package joephysics62.co.uk.old.lsystems.turtle.modules;

import joephysics62.co.uk.old.lsystems.turtle.Module;
import joephysics62.co.uk.old.lsystems.turtle.Turtle;

public class Pop extends Module {

  public Pop() {
    super(']');
  }

  @Override
  public void apply(final Turtle turtle) {
    turtle.pop();
  }

}
