package joephysics62.co.uk.lsystems.rules;

import java.util.Arrays;
import java.util.List;

import joephysics62.co.uk.lsystems.TurtleElement;

public class ExampleParametricRule extends ContextFreeRule {

  private static final int ANGLE = 86;

  public ExampleParametricRule(final Character match) {
    super(match);
  }

  @Override
  public List<TurtleElement> replacement(final double x) {
    final double c = 1;
    final double p = 0.3;
    final double q = c - p;
    final double h = Math.pow(p * q, 0.5);
    return Arrays.asList(
        new TurtleElement('F', x * p),
        new TurtleElement('+', ANGLE),
        new TurtleElement('F', x * h),
        new TurtleElement('-', ANGLE),
        new TurtleElement('-', ANGLE),
        new TurtleElement('F', x * h),
        new TurtleElement('+', ANGLE),
        new TurtleElement('F', x * q)
    );
  }

}
