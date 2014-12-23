package joephysics62.co.uk.lsystems.examples;

import static joephysics62.co.uk.lsystems.turtle.Module.drawf;
import static joephysics62.co.uk.lsystems.turtle.Module.left;
import static joephysics62.co.uk.lsystems.turtle.Module.right;

import java.util.Arrays;
import java.util.List;

import joephysics62.co.uk.lsystems.AbstractLSystem;
import joephysics62.co.uk.lsystems.rules.ContextFreeRule;
import joephysics62.co.uk.lsystems.rules.Rule;
import joephysics62.co.uk.lsystems.turtle.Module;

public class RowOfTrees extends AbstractLSystem {

  @Override
  public List<Module> axiom() {
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
    public List<Module> replacement(final double... params) {
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
