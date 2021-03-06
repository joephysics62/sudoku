package joephysics62.co.uk.old.lsystems.examples;

import java.util.Arrays;
import java.util.List;

import joephysics62.co.uk.old.lsystems.UnparametricLSystem;
import joephysics62.co.uk.old.lsystems.rules.Rule;

public class SimpleStochasticPlant extends UnparametricLSystem {

  @Override
  public String axiomString() {
    return "F";
  }

  @Override
  public List<Rule> rules() {
    return Arrays.asList(
        stochasticContextFreeRule(
            'F',
            new double[] {0.33, 0.33, 0.34},
            new String[] {"F[+F]F[-F]F", "F[+F]F", "F[-F]F"})
        );

  }

  @Override
  public double angle() {
    return 35;
  }

}
