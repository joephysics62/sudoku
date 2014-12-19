package joephysics62.co.uk.lsystems.examples;

import joephysics62.co.uk.lsystems.ContextFreeLSystem;

public class HilbertCurve3d extends ContextFreeLSystem {

  @Override
  public String axiomString() {
    return "A";
  }

  @Override
  public String applyRuleString(final Character input) {
    switch (input) {
    case 'A':
      return "B-F+CFC+F-D&F^D-F+&&CFC+F+B//";
    case 'B':
      return "A&F^CFB^F^D^^-F-D^|F^B|FC^F^A//";
    case 'C':
      return "|D^|F^B-F+C^F^A&&FA&F^C+F+B^F^D//";
    case 'D':
      return "|CFB-F+B|FA&F^A&&FB-F+B|FC//";
    default:
      return input.toString();
    }
  }

  @Override
  public double angle() {
    return 90;
  }

}
