package joephysics62.co.uk.lsystems.examples;

import static joephysics62.co.uk.lsystems.TurtleElement.drawf;
import static joephysics62.co.uk.lsystems.TurtleElement.pop;
import static joephysics62.co.uk.lsystems.TurtleElement.push;
import static joephysics62.co.uk.lsystems.TurtleElement.rollLeftFlat;

import java.util.Arrays;
import java.util.List;

import joephysics62.co.uk.lsystems.AbstractLSystem;
import joephysics62.co.uk.lsystems.TurtleElement;
import joephysics62.co.uk.lsystems.rules.ContextFreeRule;
import joephysics62.co.uk.lsystems.rules.Rule;

public class SympodialTrees extends AbstractLSystem {

  private static final double FIRST_BRANCH_ANGLE = 35;
  private static final double SECOND_BRANCH_ANGLE = 35;

  private static final double FIRST_CONTRACTION = 0.9;
  private static final double SECOND_CONTRACTION = 0.8;
  private static final double WIDTH_DECREASE = 0.707;
  private static final double DIVERGENCE = 180;


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
                drawf(length),
                push(),
                new TurtleElement('&', FIRST_BRANCH_ANGLE),
                new TurtleElement('B', length * FIRST_CONTRACTION, width * WIDTH_DECREASE),
                pop(),
                new TurtleElement('/', DIVERGENCE),
                push(),
                new TurtleElement('&', SECOND_BRANCH_ANGLE),
                new TurtleElement('B', length * SECOND_CONTRACTION, width * WIDTH_DECREASE),
                pop()
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
                drawf(length),
                push(),
                new TurtleElement('+', FIRST_BRANCH_ANGLE),
                rollLeftFlat(),
                new TurtleElement('B', length * FIRST_CONTRACTION, width * WIDTH_DECREASE),
                pop(),
                push(),
                new TurtleElement('-', SECOND_BRANCH_ANGLE),
                rollLeftFlat(),
                new TurtleElement('B', length * SECOND_CONTRACTION, width * WIDTH_DECREASE),
                pop()
            );
          }
        }
    );
  }


}
