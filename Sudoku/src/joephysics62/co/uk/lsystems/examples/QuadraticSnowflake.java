package joephysics62.co.uk.lsystems.examples;

import joephysics62.co.uk.lsystems.ContextFreeLSystem;


public class QuadraticSnowflake extends ContextFreeLSystem {

  @Override
  public String axiomString() {
    return "-F";
  }

  @Override
  public String applyRuleString(final Character input) {
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
