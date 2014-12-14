package joephysics62.co.uk.lsystems.examples;

import joephysics62.co.uk.lsystems.turtle.TurtleLSystem;

public class QuadraticSnowflake implements TurtleLSystem {

  @Override
  public String axiom() {
    return "-F";
  }

  @Override
  public String applyRule(final Character input) {
    if (input.equals('F')) {
      return "F+F-F-F+F";
    }
    return input.toString();
  }

  @Override
  public double angle() {
    return 60;
  }

}
