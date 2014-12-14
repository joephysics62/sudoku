package joephysics62.co.uk.lsystems.examples;

import joephysics62.co.uk.lsystems.turtle.TurtleLSystem;

public class FractalPlant implements TurtleLSystem {

  @Override
  public String axiom() {
    return "X";
  }

  @Override
  public String applyRule(final Character input) {
    switch (input) {
    case 'F':
      return "FF";
    case 'X':
      return "F-[[X]+X]+F[+FX]-X";
    default:
      return input.toString();
    }
  }

  @Override
  public double angle() {
    return 22.5;
  }

}
