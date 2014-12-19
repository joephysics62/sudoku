package joephysics62.co.uk.lsystems.examples;


public class FractalPlant extends ContextFreeLSystem {

  @Override
  public String axiomString() {
    return "X";
  }

  @Override
  public String applyRuleString(final Character input) {
    switch (input) {
    case 'F':
      return "FF";
    case 'X':
      return "F-[[X]+X]+F[+FX]-X";
    default:
      return input.toString();
    }
  }

  @Override
  public double angle() {
    return 22.5;
  }

}
