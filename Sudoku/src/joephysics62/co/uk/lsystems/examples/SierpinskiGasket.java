package joephysics62.co.uk.lsystems.examples;

import joephysics62.co.uk.lsystems.ContextFreeLSystem;


public class SierpinskiGasket extends ContextFreeLSystem {

  @Override
  public String axiomString() {
    return "F";
  }

  @Override
  public String applyRuleString(final Character input) {
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
