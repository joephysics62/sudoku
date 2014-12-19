package joephysics62.co.uk.lsystems.examples;

import joephysics62.co.uk.lsystems.ContextFreeLSystem;


public class GeneralKochCurve extends ContextFreeLSystem {

  private final String _drawRewrite;

  public GeneralKochCurve(final String drawRewrite) {
    _drawRewrite = drawRewrite;
  }

  @Override
  public String axiomString() {
    return "F-F-F-F";
  }

  @Override
  public String applyRuleString(final Character input) {
    switch (input) {
    case 'F':
      return _drawRewrite;
    default:
      return input.toString();
    }
  }

  @Override
  public double angle() {
    return 90;
  }

}
