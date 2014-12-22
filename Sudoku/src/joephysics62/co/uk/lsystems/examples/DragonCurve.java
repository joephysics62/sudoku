package joephysics62.co.uk.lsystems.examples;

import java.util.Arrays;
import java.util.List;

import joephysics62.co.uk.lsystems.UnparametricLSystem;
import joephysics62.co.uk.lsystems.rules.Rule;


public class DragonCurve extends UnparametricLSystem {

  @Override
  public String axiomString() {
    return "FX";
  }

  @Override
  public List<Rule> rules() {
    return Arrays.asList(
        contextFreeRule('X', "X+YF"),
        contextFreeRule('Y', "FX-Y")
    );
  }

  @Override
  public double angle() {
    return 90.0;
  }

}
