package joephysics62.co.uk.lsystems.examples;

import java.util.Arrays;
import java.util.List;

import joephysics62.co.uk.lsystems.CharacterLSystem;
import joephysics62.co.uk.lsystems.rules.SimpleContextFreeRule;
import joephysics62.co.uk.lsystems.rules.Rule;


public class PythagoreanTree extends CharacterLSystem {

  @Override
  public String axiomString() {
    return "F";
  }

  @Override
  public List<Rule<Character>> rules() {
    return Arrays.asList(
        SimpleContextFreeRule.of('F', "G[+F]-F"),
        SimpleContextFreeRule.of('G', "GG")
    );
  }

  @Override
  public double angle() {
    return 45.0;
  }

}
