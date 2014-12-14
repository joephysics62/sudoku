package joephysics62.co.uk.lsystems.examples;

import joephysics62.co.uk.lsystems.turtle.TurtleLSystem;

public class PythagoreanTree implements TurtleLSystem {

  @Override
  public String axiom() {
    return "F";
  }

  @Override
  public String applyRule(final Character input) {
    switch (input) {
    case 'F':
      return "G[F]F";
    case 'G':
      return "GG";
    default:
      return input.toString();
    }
  }

  @Override
  public double angle() {
    return 45.0;
  }

}
