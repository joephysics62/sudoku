package joephysics62.co.uk.lsystems.examples;

import joephysics62.co.uk.lsystems.turtle.TurtleLSystem;

public class PythagoreanTree3d implements TurtleLSystem {

  @Override
  public String axiom() {
    return "F";
  }

  @Override
  public String applyRule(final Character input) {
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
