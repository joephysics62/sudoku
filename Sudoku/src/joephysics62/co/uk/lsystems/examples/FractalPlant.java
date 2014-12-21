package joephysics62.co.uk.lsystems.examples;

import java.util.Arrays;
import java.util.List;

import joephysics62.co.uk.lsystems.CharacterLSystem;
import joephysics62.co.uk.lsystems.rules.ContextFreeRule;
import joephysics62.co.uk.lsystems.rules.Rule;


public class FractalPlant extends CharacterLSystem {

  @Override
  public String axiomString() {
    return "X";
  }

  @Override
  public List<Rule<Character>> rules() {
    return Arrays.asList(
        ContextFreeRule.of('F', "FF"),
        ContextFreeRule.of('X', "F-[[X]+X]+F[+FX]-X")
    );
  }

  @Override
  public double angle() {
    return 22.5;
  }

}
