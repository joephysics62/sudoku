package joephysics62.co.uk.lsystems.examples;

import java.util.Arrays;
import java.util.List;

import joephysics62.co.uk.lsystems.CharacterLSystem;
import joephysics62.co.uk.lsystems.ContextFreeRule;
import joephysics62.co.uk.lsystems.Rule;

public class HilbertCurve3d extends CharacterLSystem {

  @Override
  public String axiomString() {
    return "A";
  }

  @Override
  public List<Rule<Character>> rules() {
    return Arrays.asList(
        ContextFreeRule.of('A', "B-F+CFC+F-D&F^D-F+&&CFC+F+B//"),
        ContextFreeRule.of('B', "A&F^CFB^F^D^^-F-D^|F^B|FC^F^A//"),
        ContextFreeRule.of('C', "|D^|F^B-F+C^F^A&&FA&F^C+F+B^F^D//"),
        ContextFreeRule.of('D', "|CFB-F+B|FA&F^A&&FB-F+B|FC//")
    );
  }

  @Override
  public double angle() {
    return 90;
  }

}
