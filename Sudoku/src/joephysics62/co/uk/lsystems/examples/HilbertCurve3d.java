package joephysics62.co.uk.lsystems.examples;

import java.util.Arrays;
import java.util.List;

import joephysics62.co.uk.lsystems.UnparametricLSystem;
import joephysics62.co.uk.lsystems.rules.Rule;

public class HilbertCurve3d extends UnparametricLSystem {

  @Override
  public String axiomString() {
    return "A";
  }

  @Override
  public List<Rule> rules() {
    return Arrays.asList(
        contextFreeRule('A', "B-F+CFC+F-D&F^D-F+&&CFC+F+B//"),
        contextFreeRule('B', "A&F^CFB^F^D^^-F-D^|F^B|FC^F^A//"),
        contextFreeRule('C', "|D^|F^B-F+C^F^A&&FA&F^C+F+B^F^D//"),
        contextFreeRule('D', "|CFB-F+B|FA&F^A&&FB-F+B|FC//")
    );
  }

  @Override
  public double angle() {
    return 90;
  }

}
