package joephysics62.co.uk.lsystems.examples;

import java.util.Arrays;
import java.util.List;

import joephysics62.co.uk.lsystems.CharacterLSystem;
import joephysics62.co.uk.lsystems.rules.Rule;
import joephysics62.co.uk.lsystems.rules.StochasticContextFreeRule;

public class SimpleStochasticPlant extends CharacterLSystem {

  @Override
  public String axiomString() {
    return "F";
  }

  @Override
  public List<Rule<Character>> rules() {
    return Arrays.asList(
        new StochasticContextFreeRule('F', new double[] {0.33, 0.33, 0.34}, new String[] {"F[+F]F[-F]F", "F[+F]F", "F[-F]F"}));

  }

  @Override
  public double angle() {
    return 35;
  }

}
