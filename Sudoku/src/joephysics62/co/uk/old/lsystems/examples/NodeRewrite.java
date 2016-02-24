package joephysics62.co.uk.old.lsystems.examples;

import java.util.Arrays;
import java.util.List;

import joephysics62.co.uk.old.lsystems.UnparametricLSystem;
import joephysics62.co.uk.old.lsystems.rules.Rule;

public class NodeRewrite extends UnparametricLSystem {

  @Override
  public String axiomString() {
    return "X";
  }

  @Override
  public List<Rule> rules() {
    return Arrays.asList(
        contextFreeRule('X', "F[&!X]//[&!X]//[&!X]//[&!X]//[&!X]//[&!X]//[&!X]FX"),
        contextFreeRule('F', "FF")
    );
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
