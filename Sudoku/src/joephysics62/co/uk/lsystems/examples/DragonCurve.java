package joephysics62.co.uk.lsystems.examples;

import joephysics62.co.uk.lsystems.turtle.TurtleLSystem;

public class DragonCurve implements TurtleLSystem {

  @Override
  public String axiom() {
    return "FX";
  }

  @Override
  public String applyRule(final Character input) {
    switch (input) {
    case 'X':
      return "X+YF";
    case 'Y':
      return "FX-Y";
    default:
      return input.toString();
    }
  }

  @Override
  public double angle() {
    return 90.0;
  }

}
