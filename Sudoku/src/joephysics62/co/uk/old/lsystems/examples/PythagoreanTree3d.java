package joephysics62.co.uk.old.lsystems.examples;

import java.util.Arrays;
import java.util.List;

import joephysics62.co.uk.old.lsystems.UnparametricLSystem;
import joephysics62.co.uk.old.lsystems.rules.Rule;


public class PythagoreanTree3d extends UnparametricLSystem {

  @Override
  public String axiomString() {
    return "F";
  }

  @Override
  public List<Rule> rules() {
    return Arrays.asList(
        contextFreeRule('F', "G[&!F]//[&!F]//[&!F]-!F"),
        contextFreeRule('G', "GG")
    );
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
