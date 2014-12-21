package joephysics62.co.uk.lsystems.examples;

import java.util.Arrays;
import java.util.List;

import joephysics62.co.uk.lsystems.CharacterLSystem;
import joephysics62.co.uk.lsystems.ContextFreeRule;
import joephysics62.co.uk.lsystems.Rule;

public class IslandsAndLakes extends CharacterLSystem {

  @Override
  public String axiomString() {
    return "F+F+F+F";
  }

  @Override
  public List<Rule<Character>> rules() {
    return Arrays.asList(
        ContextFreeRule.of('f', "ffffff"),
        ContextFreeRule.of('F', "F+f-FF+F+FF+Ff+FF-f+FF-F-FF-Ff-FFF")
    );
  }

  @Override
  public double angle() {
    return 90.0;
  }

}
