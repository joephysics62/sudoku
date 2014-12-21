package joephysics62.co.uk.lsystems.examples;

import java.util.Arrays;
import java.util.List;

import joephysics62.co.uk.lsystems.CharacterLSystem;
import joephysics62.co.uk.lsystems.rules.ContextFreeRule;
import joephysics62.co.uk.lsystems.rules.Rule;


public class GeneralKochCurve extends CharacterLSystem {

  private final String _drawRewrite;

  public GeneralKochCurve(final String drawRewrite) {
    _drawRewrite = drawRewrite;
  }

  @Override
  public String axiomString() {
    return "F-F-F-F";
  }

  @Override
  public List<Rule<Character>> rules() {
    return Arrays.asList(
        ContextFreeRule.of('F', _drawRewrite)
    );
  }

  @Override
  public double angle() {
    return 90;
  }

}
