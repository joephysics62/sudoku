package joephysics62.co.uk.old.lsystems.turtle.modules;

import joephysics62.co.uk.old.lsystems.turtle.Module;
import joephysics62.co.uk.old.lsystems.turtle.Turtle;

public class Narrow extends Module {

  public Narrow(final double parameter) {
    super('!', parameter);
  }

  @Override
  public void apply(final Turtle turtle) {
    turtle.narrow(getParameter(0));
  }

}
