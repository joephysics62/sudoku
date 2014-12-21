package joephysics62.co.uk.lsystems.examples;

import java.util.Arrays;
import java.util.List;

import joephysics62.co.uk.lsystems.CharacterLSystem;
import joephysics62.co.uk.lsystems.rules.SimpleContextFreeRule;
import joephysics62.co.uk.lsystems.rules.Rule;

public class HilbertCurve3d extends CharacterLSystem {

  @Override
  public String axiomString() {
    return "A";
  }

  @Override
  public List<Rule<Character>> rules() {
    return Arrays.asList(
        SimpleContextFreeRule.of('A', "B-F+CFC+F-D&F^D-F+&&CFC+F+B//"),
        SimpleContextFreeRule.of('B', "A&F^CFB^F^D^^-F-D^|F^B|FC^F^A//"),
        SimpleContextFreeRule.of('C', "|D^|F^B-F+C^F^A&&FA&F^C+F+B^F^D//"),
        SimpleContextFreeRule.of('D', "|CFB-F+B|FA&F^A&&FB-F+B|FC//")
    );
  }

  @Override
  public double angle() {
    return 90;
  }

}
