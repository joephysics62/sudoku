package joephysics62.co.uk.lsystems.examples;

import joephysics62.co.uk.lsystems.turtle.TurtleLSystem;

public class BushExample3d implements TurtleLSystem {

  @Override
  public String axiom() {
    return "A";
  }

  @Override
  public String applyRule(final Character input) {
    switch (input) {
    case 'A':
      return "[&FL!A]/////'[&FL!A]///////'[&FL!A]";
    case 'F':
      return "S/////F";
    case 'S':
      return "FL";
    default:
      return input.toString();
    }
  }

  @Override
  public double angle() {
    return 22;
  }

}
