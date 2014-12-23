package joephysics62.co.uk.lsystems.examples;

import java.util.Arrays;
import java.util.List;

import joephysics62.co.uk.lsystems.AbstractLSystem;
import joephysics62.co.uk.lsystems.TurtleElement;
import joephysics62.co.uk.lsystems.rules.ContextFreeRule;
import joephysics62.co.uk.lsystems.rules.Rule;

public class TernaryBranchingTrees extends AbstractLSystem {

  private static final double START_LENGTH = 4;
  private static final double START_WIDTH = 0.02;
  private static final double BRANCH_LENGTH = 1;

  private static final TurtleElement PARAMETERLESS = new TurtleElement('A');
  private static final TurtleElement POP = new TurtleElement(']', -1);
  private static final TurtleElement PUSH = new TurtleElement('[', -1);

  private static final double DIVERGENCE_1 = 94.74;
  private static final double DIVERGENCE_2 = 132.63;

  private static final double BRANCH_ANGLE = 18.95;
  private static final double ELONGATION_RATE = 1.109;
  private static final double WIDTH_INCREASE_RATE = 1.732;

  @Override
  public List<TurtleElement> axiom() {
    return Arrays.asList(
        new TurtleElement('£', START_WIDTH),
        new TurtleElement('F', START_LENGTH),
        new TurtleElement('/', 45),
        PARAMETERLESS
    );
  }


  @Override
  public List<Rule> rules() {
    return Arrays.asList(
        new ContextFreeRule('A') {
          @Override
          public List<TurtleElement> replacement(final double... parameters) {
            return Arrays.asList(
                new TurtleElement('£', WIDTH_INCREASE_RATE),
                new TurtleElement('F', BRANCH_LENGTH),
                PUSH,
                new TurtleElement('&', BRANCH_ANGLE),
                new TurtleElement('F', BRANCH_LENGTH),
                PARAMETERLESS,
                POP,
                new TurtleElement('/', DIVERGENCE_1),
                PUSH,
                new TurtleElement('&', BRANCH_ANGLE),
                new TurtleElement('F', BRANCH_LENGTH),
                PARAMETERLESS,
                POP,
                new TurtleElement('/', DIVERGENCE_2),
                PUSH,
                new TurtleElement('&', BRANCH_ANGLE),
                new TurtleElement('F', BRANCH_LENGTH),
                PARAMETERLESS,
                POP
            );
          }
        },
        new ContextFreeRule('F') {
          @Override
          public List<TurtleElement> replacement(final double... parameters) {
            final double length = parameters[0];
            return Arrays.asList(new TurtleElement('F', length * ELONGATION_RATE));
          }
        },
        new ContextFreeRule('£') {
          @Override
          public List<TurtleElement> replacement(final double... parameters) {
            final double width = parameters[0];
            return Arrays.asList(new TurtleElement('£', width * WIDTH_INCREASE_RATE));
          }
        }
    );
  }


}
