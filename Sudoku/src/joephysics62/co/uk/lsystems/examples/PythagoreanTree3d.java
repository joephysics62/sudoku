package joephysics62.co.uk.lsystems.examples;

import joephysics62.co.uk.lsystems.ContextFreeLSystem;


public class PythagoreanTree3d extends ContextFreeLSystem {

  @Override
  public String axiomString() {
    return "F";
  }

  @Override
  public String applyRuleString(final Character input) {
    switch (input) {
    case 'F':
      // "[&FL!A]/////'[&FL!A]///////'[&FL!A]";
      return "G[&!F]//[&!F]//[&!F]-!F";
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

  @Override
  public double narrowing() {
    return 0.7;
  }

}
