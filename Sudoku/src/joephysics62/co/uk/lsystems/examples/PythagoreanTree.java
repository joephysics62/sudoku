package joephysics62.co.uk.lsystems.examples;

import java.util.Arrays;
import java.util.List;

import joephysics62.co.uk.lsystems.UnparametricLSystem;
import joephysics62.co.uk.lsystems.rules.Rule;


public class PythagoreanTree extends UnparametricLSystem {

  @Override
  public String axiomString() {
    return "F";
  }

  @Override
  public List<Rule> rules() {
    return Arrays.asList(
        contextFreeRule('F', "G[+F]-F"),
        contextFreeRule('G', "GG")
    );
  }

  @Override
  public double angle() {
    return 45.0;
  }

}
