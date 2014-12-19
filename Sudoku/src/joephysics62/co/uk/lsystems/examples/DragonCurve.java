package joephysics62.co.uk.lsystems.examples;


public class DragonCurve extends ContextFreeLSystem {

  @Override
  public String axiomString() {
    return "FX";
  }

  @Override
  public String applyRuleString(final Character input) {
    switch (input) {
    case 'X':
      return "X+YF";
    case 'Y':
      return "FX-Y";
    default:
      return input.toString();
    }
  }

  @Override
  public double angle() {
    return 90.0;
  }

}
