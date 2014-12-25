package joephysics62.co.uk.lsystems.turtle.modules;

import joephysics62.co.uk.lsystems.turtle.Module;
import joephysics62.co.uk.lsystems.turtle.Turtle;

public class Left extends Module {

  public Left(final double parameter) {
    super('+', parameter);
  }

  @Override
  public void apply(final Turtle turtle) {
    turtle.turn(getParameter(0));
  }

}