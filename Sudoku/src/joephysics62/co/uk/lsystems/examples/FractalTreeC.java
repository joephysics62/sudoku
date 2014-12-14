package joephysics62.co.uk.lsystems.examples;

import joephysics62.co.uk.lsystems.turtle.TurtleLSystem;


public class FractalTreeC implements TurtleLSystem {

  @Override
  public String axiom() {
    return "F";
  }

  @Override
  public String applyRule(final Character input) {
    switch (input) {
    case 'F':
      return "FF-[-F+F+F]+[+F-F-F]";
    default:
      return input.toString();
    }
  }

  @Override
  public double angle() {
    return 22.5;
  }

}
