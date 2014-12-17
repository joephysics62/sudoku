package joephysics62.co.uk.lsystems.examples;

import joephysics62.co.uk.lsystems.turtle.TurtleLSystem;

public class NodeRewrier implements TurtleLSystem {

  @Override
  public String axiom() {
    return "X";
  }

  @Override
  public String applyRule(final Character input) {
    if ('X' == input) {
      return "F[&!X]//[&!X]//[&!X]//[&!X]//[&!X]//[&!X]//[&!X]FX";
    }
    else if ('F' == input) {
      return "FF";
    }
    else {
      return input.toString();
    }
  }

  @Override
  public double angle() {
    return 25.7;
  }

  @Override
  public double narrowing() {
    return 0.6;
  }

}
