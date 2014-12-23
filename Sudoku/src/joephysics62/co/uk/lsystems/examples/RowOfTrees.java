package joephysics62.co.uk.lsystems.examples;

import static joephysics62.co.uk.lsystems.TurtleElement.drawf;
import static joephysics62.co.uk.lsystems.TurtleElement.left;
import static joephysics62.co.uk.lsystems.TurtleElement.right;

import java.util.Arrays;
import java.util.List;

import joephysics62.co.uk.lsystems.AbstractLSystem;
import joephysics62.co.uk.lsystems.TurtleElement;
import joephysics62.co.uk.lsystems.rules.ContextFreeRule;
import joephysics62.co.uk.lsystems.rules.Rule;

public class RowOfTrees extends AbstractLSystem {

  @Override
  public List<TurtleElement> axiom() {
    return Arrays.asList(drawf(1.0));
  }

  @Override
  public List<Rule> rules() {
    return Arrays.asList(new RowOfTreesRule('F'));
  }

  private class RowOfTreesRule extends ContextFreeRule {

    private static final int ANGLE = 86;

    private RowOfTreesRule(final Character match) {
      super(match);
    }

    @Override
    public List<TurtleElement> replacement(final double... params) {
      final double c = 1;
      final double p = 0.3;
      final double q = c - p;
      final double h = Math.pow(p * q, 0.5);
      final double x = params[0];
      return Arrays.asList(
          drawf(x * p),
          left(ANGLE),
          drawf(x * h),
          right(ANGLE),
          right(ANGLE),
          drawf(x * h),
          left(ANGLE),
          drawf(x * q)
      );
    }

  }

}
