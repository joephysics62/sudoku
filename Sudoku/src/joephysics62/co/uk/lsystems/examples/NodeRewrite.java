package joephysics62.co.uk.lsystems.examples;

import java.util.Arrays;
import java.util.List;

import joephysics62.co.uk.lsystems.CharacterLSystem;
import joephysics62.co.uk.lsystems.ContextFreeRule;
import joephysics62.co.uk.lsystems.Rule;

public class NodeRewrite extends CharacterLSystem {

  @Override
  public String axiomString() {
    return "X";
  }

  @Override
  public List<Rule<Character>> rules() {
    return Arrays.asList(
        ContextFreeRule.of('X', "F[&!X]//[&!X]//[&!X]//[&!X]//[&!X]//[&!X]//[&!X]FX"),
        ContextFreeRule.of('F', "FF")
    );
  }

  @Override
  public double angle() {
    return 25.7;
  }

  @Override
  public double narrowing() {
    return 0.6;
  }

}
