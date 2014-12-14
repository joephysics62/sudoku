package joephysics62.co.uk.lsystems.examples;

import joephysics62.co.uk.lsystems.turtle.TurtleLSystem;

public class SierpinskiGasket implements TurtleLSystem {

  @Override
  public String axiom() {
    return "F";
  }

  @Override
  public String applyRule(final Character input) {
    switch (input) {
    case 'F':
      return "G-F-G";
    case 'G':
      return "F+G+F";
    default:
      return input.toString();
    }
  }

  @Override
  public double angle() {
    return 60;
  }

}
