package joephysics62.co.uk.lsystems.examples;

import java.util.Arrays;
import java.util.List;

import joephysics62.co.uk.lsystems.CharacterLSystem;
import joephysics62.co.uk.lsystems.ContextFreeRule;
import joephysics62.co.uk.lsystems.Rule;


public class PythagoreanTree3d extends CharacterLSystem {

  @Override
  public String axiomString() {
    return "F";
  }

  @Override
  public List<Rule<Character>> rules() {
    return Arrays.asList(
        ContextFreeRule.of('F', "G[&!F]//[&!F]//[&!F]-!F"),
        ContextFreeRule.of('G', "GG")
    );
  }

  @Override
  public double angle() {
    return 45.0;
  }

  @Override
  public double narrowing() {
    return 0.7;
  }

}
