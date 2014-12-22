package joephysics62.co.uk.lsystems.examples;

import java.util.Arrays;
import java.util.List;

import joephysics62.co.uk.lsystems.UnparametricLSystem;
import joephysics62.co.uk.lsystems.rules.Rule;


public class FractalPlant extends UnparametricLSystem {

  @Override
  public String axiomString() {
    return "X";
  }

  @Override
  public List<Rule> rules() {
    return Arrays.asList(
        contextFreeRule('F', "FF"),
        contextFreeRule('X', "F-[[X]+X]+F[+FX]-X")
    );
  }

  @Override
  public double angle() {
    return 22.5;
  }

}
