package joephysics62.co.uk.lsystems.examples;

import java.util.Arrays;
import java.util.List;

import joephysics62.co.uk.lsystems.CharacterLSystem;
import joephysics62.co.uk.lsystems.ContextFreeRule;
import joephysics62.co.uk.lsystems.Rule;

public class FractalTreeC extends CharacterLSystem {

  @Override
  public String axiomString() {
    return "F";
  }

  @Override
  public List<Rule<Character>> rules() {
    return Arrays.asList(
        ContextFreeRule.of('F', "FF-[-F+F+F]+[+F-F-F]")
    );
  }

  @Override
  public double angle() {
    return 22.5;
  }

}
