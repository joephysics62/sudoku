package joephysics62.co.uk.lsystems.examples;

import java.util.Arrays;
import java.util.List;

import joephysics62.co.uk.lsystems.CharacterLSystem;
import joephysics62.co.uk.lsystems.rules.SimpleContextFreeRule;
import joephysics62.co.uk.lsystems.rules.Rule;


public class DragonCurve extends CharacterLSystem {

  @Override
  public String axiomString() {
    return "FX";
  }

  @Override
  public List<Rule<Character>> rules() {
    return Arrays.asList(
        SimpleContextFreeRule.of('X', "X+YF"),
        SimpleContextFreeRule.of('Y', "FX-Y")
    );
  }

  @Override
  public double angle() {
    return 90.0;
  }

}
