package joephysics62.co.uk.lsystems.turtle.modules;

import joephysics62.co.uk.lsystems.turtle.Module;
import joephysics62.co.uk.lsystems.turtle.Turtle;

public class Pop extends Module {

  public Pop() {
    super(']');
  }

  @Override
  public void apply(final Turtle turtle) {
    turtle.pop();
  }

}
