package joephysics62.co.uk.lsystems.examples;

import java.util.Random;

import joephysics62.co.uk.lsystems.turtle.TurtleLSystem;

public class SimpleStochasticPlant implements TurtleLSystem {
  private final Random _random = new Random();

  @Override
  public String axiom() {
    return "F";
  }

  @Override
  public String applyRule(final Character input) {
    if (input.equals('F')) {
      switch (_random.nextInt(3)) {
      case 0:
        return "F[+F]F[-F]F";
      case 1:
        return "F[+F]F";
      case 2:
        return "F[-F]F";
      }
    }
    return input.toString();
  }

  @Override
  public double angle() {
    return 35;
  }

}
