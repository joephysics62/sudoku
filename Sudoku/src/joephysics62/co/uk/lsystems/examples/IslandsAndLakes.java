package joephysics62.co.uk.lsystems.examples;

import joephysics62.co.uk.lsystems.ContextFreeLSystem;

public class IslandsAndLakes extends ContextFreeLSystem {

  @Override
  public String axiomString() {
    return "F+F+F+F";
  }

  @Override
  public String applyRuleString(final Character input) {
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
