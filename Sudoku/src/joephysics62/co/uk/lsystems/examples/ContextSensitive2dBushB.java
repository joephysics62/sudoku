package joephysics62.co.uk.lsystems.examples;

import java.util.Arrays;
import java.util.List;

import joephysics62.co.uk.lsystems.UnparametricLSystem;
import joephysics62.co.uk.lsystems.rules.Rule;

public class ContextSensitive2dBushB extends UnparametricLSystem {

  @Override
  public double angle() {
    return 22.5;
  }

  @Override
  public List<Rule> rules() {
    return Arrays.asList(
        simpleContextSensitiveRule('0', '0', '0', "+-F", "1"),
        simpleContextSensitiveRule('0', '0', '1', "+-F", "1[-F1F1]"),
        simpleContextSensitiveRule('0', '1', '0', "+-F", "1"),
        simpleContextSensitiveRule('0', '1', '1', "+-F", "1"),
        simpleContextSensitiveRule('1', '0', '0', "+-F", "0"),
        simpleContextSensitiveRule('1', '0', '1', "+-F", "1F1"),
        simpleContextSensitiveRule('1', '1', '0', "+-F", "1"),
        simpleContextSensitiveRule('1', '1', '1', "+-F", "0"),
        contextFreeRule('+', "-"),
        contextFreeRule('-', "+")
    );
  }

  @Override
  protected String axiomString() {
    return "F1F1F1";
  }

}
