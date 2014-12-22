package joephysics62.co.uk.lsystems.examples;

import java.util.Arrays;
import java.util.List;

import joephysics62.co.uk.lsystems.AbstractLSystem;
import joephysics62.co.uk.lsystems.TurtleElement;
import joephysics62.co.uk.lsystems.rules.ContextFreeRule;
import joephysics62.co.uk.lsystems.rules.Rule;

public class MonopodialTrees extends AbstractLSystem {

  private static final TurtleElement ROLL_LEFT_FLAT = new TurtleElement('$', -1);
  private static final TurtleElement POP = new TurtleElement(']', -1);
  private static final TurtleElement PUSH = new TurtleElement('[', -1);
  private static final double TRUNK_BRANCH_ANGLE = 30;
  private static final double LATERAL_BRANCH_ANGLE = -30;

  private static final double TRUNK_CONTRACTION = 0.9;
  private static final double BRANCH_CONTRACTION = 0.7;
  private static final double WIDTH_DECREASE = 0.707;
  private static final double DIVERGENCE = 137.5;


  @Override
  public List<TurtleElement> axiom() {
    return Arrays.asList(new TurtleElement('A', 1, 0.1));
  }


  @Override
  public List<Rule> rules() {
    return Arrays.asList(
        new ContextFreeRule('A') {
          @Override
          public List<TurtleElement> replacement(final double... parameters) {
            final double length = parameters[0];
            final double width = parameters[1];
            return Arrays.asList(
                new TurtleElement('£', width),
                new TurtleElement('F', length),
                PUSH,
                new TurtleElement('&', TRUNK_BRANCH_ANGLE),
                new TurtleElement('B', length * BRANCH_CONTRACTION, width * WIDTH_DECREASE),
                POP,
                new TurtleElement('/', DIVERGENCE),
                new TurtleElement('A', length * TRUNK_CONTRACTION, width * WIDTH_DECREASE)
            );
          }
        },
        new ContextFreeRule('B') {
          @Override
          public List<TurtleElement> replacement(final double... parameters) {
            final double length = parameters[0];
            final double width = parameters[1];
            return Arrays.asList(
                new TurtleElement('£', width),
                new TurtleElement('F', length),
                PUSH,
                new TurtleElement('-', LATERAL_BRANCH_ANGLE),
                ROLL_LEFT_FLAT,
                new TurtleElement('C', length * BRANCH_CONTRACTION, width * WIDTH_DECREASE),
                POP,
                new TurtleElement('C', length * TRUNK_CONTRACTION, width * WIDTH_DECREASE)
            );
          }
        },
        new ContextFreeRule('C') {
          @Override
          public List<TurtleElement> replacement(final double... parameters) {
            final double length = parameters[0];
            final double width = parameters[1];
            return Arrays.asList(
                new TurtleElement('£', width),
                new TurtleElement('F', length),
                PUSH,
                new TurtleElement('+', LATERAL_BRANCH_ANGLE),
                ROLL_LEFT_FLAT,
                new TurtleElement('B', length * BRANCH_CONTRACTION, width * WIDTH_DECREASE),
                POP,
                new TurtleElement('B', length * TRUNK_CONTRACTION, width * WIDTH_DECREASE)
            );
          }
        }
    );
  }


}
