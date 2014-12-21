package joephysics62.co.uk.lsystems.examples;

import java.util.Arrays;
import java.util.List;

import joephysics62.co.uk.lsystems.CharacterLSystem;
import joephysics62.co.uk.lsystems.ContextFreeRule;
import joephysics62.co.uk.lsystems.Rule;


public class DragonCurve extends CharacterLSystem {

  @Override
  public String axiomString() {
    return "FX";
  }

  @Override
  public List<Rule<Character>> rules() {
    return Arrays.asList(
        ContextFreeRule.of('X', "X+YF"),
        ContextFreeRule.of('Y', "FX-Y")
    );
  }

  @Override
  public double angle() {
    return 90.0;
  }

}
