package joephysics62.co.uk.lsystems.examples;

import java.util.List;
import java.util.Random;

import joephysics62.co.uk.lsystems.CharacterLSystem;
import joephysics62.co.uk.lsystems.Rule;

public class SimpleStochasticPlant extends CharacterLSystem {
  private final Random _random = new Random();

  @Override
  public String axiomString() {
    return "F";
  }

  @Override
  public List<Rule<Character>> rules() {
    // TODO Auto-generated method stub
    return null;
  }
/**
  @Override
  public String applyRuleString(final Character input) {
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
*/
  @Override
  public double angle() {
    return 35;
  }

}
