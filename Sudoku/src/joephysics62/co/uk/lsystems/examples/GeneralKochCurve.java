package joephysics62.co.uk.lsystems.examples;

import joephysics62.co.uk.lsystems.turtle.TurtleLSystem;

public class GeneralKochCurve implements TurtleLSystem {

  private final String _drawRewrite;

  public GeneralKochCurve(final String drawRewrite) {
    _drawRewrite = drawRewrite;
  }

  @Override
  public String axiom() {
    return "F-F-F-F";
  }

  @Override
  public String applyRule(final Character input) {
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
