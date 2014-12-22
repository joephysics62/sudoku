package joephysics62.co.uk.lsystems.examples;

import java.util.Arrays;
import java.util.List;

import joephysics62.co.uk.lsystems.CharacterLSystem;
import joephysics62.co.uk.lsystems.rules.Rule;
import joephysics62.co.uk.lsystems.rules.SimpleContextFreeRule;
import joephysics62.co.uk.lsystems.rules.SimpleContextSensitiveRule;

public class ContextSensitive2dBushB extends CharacterLSystem {

  @Override
  public double angle() {
    return 22.5;
  }

  @Override
  public List<Rule<Character>> rules() {
    return Arrays.asList(
        new SimpleContextSensitiveRule('0', '0', '0', "+-F", "1"),
        new SimpleContextSensitiveRule('0', '0', '1', "+-F", "1[-F1F1]"),
        new SimpleContextSensitiveRule('1', '0', '0', "+-F", "1"),
        new SimpleContextSensitiveRule('1', '0', '1', "+-F", "1"),
        new SimpleContextSensitiveRule('0', '1', '0', "+-F", "0"),
        new SimpleContextSensitiveRule('0', '1', '1', "+-F", "1F1"),
        new SimpleContextSensitiveRule('1', '1', '0', "+-F", "1"),
        new SimpleContextSensitiveRule('1', '1', '1', "+-F", "0"),
        SimpleContextFreeRule.of('+', "-"),
        SimpleContextFreeRule.of('-', "+")
    );
  }

  @Override
  protected String axiomString() {
    return "F1F1F1";
  }

}
