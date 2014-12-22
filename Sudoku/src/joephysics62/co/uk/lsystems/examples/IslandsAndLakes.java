package joephysics62.co.uk.lsystems.examples;

import java.util.Arrays;
import java.util.List;

import joephysics62.co.uk.lsystems.UnparametricLSystem;
import joephysics62.co.uk.lsystems.rules.Rule;

public class IslandsAndLakes extends UnparametricLSystem {

  @Override
  public String axiomString() {
    return "F+F+F+F";
  }

  @Override
  public List<Rule> rules() {
    return Arrays.asList(
        contextFreeRule('G', "GGGGGG"),
        contextFreeRule('F', "F+G-FF+F+FF+FG+FF-G+FF-F-FF-FG-FFF")
    );
  }

  @Override
  public double angle() {
    return 90.0;
  }

}
