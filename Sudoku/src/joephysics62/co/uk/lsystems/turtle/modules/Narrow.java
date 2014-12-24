package joephysics62.co.uk.lsystems.turtle.modules;

import joephysics62.co.uk.lsystems.turtle.Module;
import joephysics62.co.uk.lsystems.turtle.Turtle;

public class Narrow extends Module {

  public Narrow(final double parameter) {
    super('!', parameter);
  }

  @Override
  public void apply(final Turtle turtle) {
    turtle.narrow(getParameter(0));
  }

}
