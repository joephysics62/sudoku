package joephysics62.co.uk.lsystems.examples;

import static joephysics62.co.uk.lsystems.TurtleElement.drawf;
import static joephysics62.co.uk.lsystems.TurtleElement.pitchDown;
import static joephysics62.co.uk.lsystems.TurtleElement.pop;
import static joephysics62.co.uk.lsystems.TurtleElement.push;
import static joephysics62.co.uk.lsystems.TurtleElement.rollLeft;
import static joephysics62.co.uk.lsystems.TurtleElement.width;

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

  private static final TurtleElement A_MOD = TurtleElement.create('A');
  private static final double DIVERGENCE_1 = 94.74;
  private static final double DIVERGENCE_2 = 132.63;

  private static final double BRANCH_ANGLE = 18.95;
  private static final double ELONGATION_RATE = 1.109;
  private static final double WIDTH_INCREASE_RATE = 1.732;

  @Override
  public List<TurtleElement> axiom() {
    return Arrays.asList(
        width(START_WIDTH),
        drawf(START_LENGTH),
        rollLeft(45),
        A_MOD
    );
  }


  @Override
  public List<Rule> rules() {
    return Arrays.asList(
        new ContextFreeRule('A') {
          @Override
          public List<TurtleElement> replacement(final double... parameters) {
            return Arrays.asList(
                width(WIDTH_INCREASE_RATE),
                drawf(BRANCH_LENGTH),
                push(),
                pitchDown(BRANCH_ANGLE),
                drawf(BRANCH_LENGTH),
                A_MOD,
                pop(),
                rollLeft(DIVERGENCE_1),
                push(),
                pitchDown(BRANCH_ANGLE),
                drawf(BRANCH_LENGTH),
                A_MOD,
                pop(),
                rollLeft(DIVERGENCE_2),
                push(),
                pitchDown(BRANCH_ANGLE),
                drawf(BRANCH_LENGTH),
                A_MOD,
                pop()
            );
          }
        },
        new ContextFreeRule('F') {
          @Override
          public List<TurtleElement> replacement(final double... parameters) {
            final double length = parameters[0];
            return Arrays.asList(drawf(length * ELONGATION_RATE));
          }
        },
        new ContextFreeRule('£') {
          @Override
          public List<TurtleElement> replacement(final double... parameters) {
            final double width = parameters[0];
            return Arrays.asList(width(width * WIDTH_INCREASE_RATE));
          }
        }
    );
  }


}
