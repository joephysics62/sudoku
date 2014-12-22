package joephysics62.co.uk.lsystems.examples;

import java.util.Arrays;
import java.util.List;

import joephysics62.co.uk.lsystems.UnparametricLSystem;
import joephysics62.co.uk.lsystems.rules.Rule;


public class GeneralKochCurve extends UnparametricLSystem {

  private final String _drawRewrite;

  public GeneralKochCurve(final String drawRewrite) {
    _drawRewrite = drawRewrite;
  }

  @Override
  public String axiomString() {
    return "F-F-F-F";
  }

  @Override
  public List<Rule> rules() {
    return Arrays.asList(
        contextFreeRule('F', _drawRewrite)
    );
  }

  @Override
  public double angle() {
    return 90;
  }

}
