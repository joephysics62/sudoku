package joephysics62.co.uk.lsystems.examples;

import java.util.Arrays;
import java.util.List;

import joephysics62.co.uk.lsystems.UnparametricLSystem;
import joephysics62.co.uk.lsystems.rules.Rule;

public class SierpinskiGasket extends UnparametricLSystem {

  @Override
  public String axiomString() {
    return "F";
  }

  @Override
  public List<Rule> rules() {
    return Arrays.asList(
        contextFreeRule('F', "G-F-G"),
        contextFreeRule('G', "F+G+F")
    );
  }

  @Override
  public double angle() {
    return 60;
  }

}
