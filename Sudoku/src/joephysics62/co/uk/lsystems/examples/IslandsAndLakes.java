package joephysics62.co.uk.lsystems.examples;

import joephysics62.co.uk.lsystems.turtle.TurtleLSystem;

public class IslandsAndLakes implements TurtleLSystem {

  @Override
  public String axiom() {
    return "F+F+F+F";
  }

  @Override
  public String applyRule(final Character input) {
    switch (input) {
    case 'f':
      return "ffffff";
    case 'F':
      return "F+f-FF+F+FF+Ff+FF-f+FF-F-FF-Ff-FFF";
    default:
      return input.toString();
    }
  }

  @Override
  public double angle() {
    return 90.0;
  }

}
