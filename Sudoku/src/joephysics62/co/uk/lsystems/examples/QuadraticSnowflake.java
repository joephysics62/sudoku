package joephysics62.co.uk.lsystems.examples;

import java.util.Arrays;
import java.util.List;

import joephysics62.co.uk.lsystems.CharacterLSystem;
import joephysics62.co.uk.lsystems.rules.ContextFreeRule;
import joephysics62.co.uk.lsystems.rules.Rule;


public class QuadraticSnowflake extends CharacterLSystem {

  @Override
  public String axiomString() {
    return "-F";
  }

  @Override
  public List<Rule<Character>> rules() {
    return Arrays.asList(
        ContextFreeRule.of('F', "F+F-F-F+F")
    );
  }

  @Override
  public double angle() {
    return 60;
  }

}
