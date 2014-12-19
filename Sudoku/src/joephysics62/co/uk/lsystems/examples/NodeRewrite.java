package joephysics62.co.uk.lsystems.examples;

import joephysics62.co.uk.lsystems.ContextFreeLSystem;

public class NodeRewrite extends ContextFreeLSystem {

  @Override
  public String axiomString() {
    return "X";
  }

  @Override
  public String applyRuleString(final Character input) {
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
