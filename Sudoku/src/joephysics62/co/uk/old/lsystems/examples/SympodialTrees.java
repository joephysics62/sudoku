package joephysics62.co.uk.old.lsystems.examples;

import static joephysics62.co.uk.old.lsystems.turtle.Module.drawf;
import static joephysics62.co.uk.old.lsystems.turtle.Module.identity;
import static joephysics62.co.uk.old.lsystems.turtle.Module.left;
import static joephysics62.co.uk.old.lsystems.turtle.Module.pitchDown;
import static joephysics62.co.uk.old.lsystems.turtle.Module.pop;
import static joephysics62.co.uk.old.lsystems.turtle.Module.push;
import static joephysics62.co.uk.old.lsystems.turtle.Module.right;
import static joephysics62.co.uk.old.lsystems.turtle.Module.rollLeft;
import static joephysics62.co.uk.old.lsystems.turtle.Module.rollLeftFlat;
import static joephysics62.co.uk.old.lsystems.turtle.Module.width;

import java.util.Arrays;
import java.util.List;

import joephysics62.co.uk.old.lsystems.AbstractLSystem;
import joephysics62.co.uk.old.lsystems.rules.ContextFreeRule;
import joephysics62.co.uk.old.lsystems.rules.Rule;
import joephysics62.co.uk.old.lsystems.turtle.IModule;

public class SympodialTrees extends AbstractLSystem {

  private static final double FIRST_BRANCH_ANGLE = 35;
  private static final double SECOND_BRANCH_ANGLE = 35;

  private static final double FIRST_CONTRACTION = 0.9;
  private static final double SECOND_CONTRACTION = 0.8;
  private static final double WIDTH_DECREASE = 0.707;
  private static final double DIVERGENCE = 180;


  @Override
  public List<IModule> axiom() {
    return Arrays.asList(identity('A', 1, 0.1));
  }


  @Override
  public List<Rule> rules() {
    return Arrays.asList(
        new ContextFreeRule('A') {
          @Override
          public List<IModule> replacement(final double... parameters) {
            final double length = parameters[0];
            final double width = parameters[1];
            return Arrays.asList(
                width(width),
                drawf(length),
                push(),
                pitchDown(FIRST_BRANCH_ANGLE),
                identity('B', length * FIRST_CONTRACTION, width * WIDTH_DECREASE),
                pop(),
                rollLeft(DIVERGENCE),
                push(),
                pitchDown(SECOND_BRANCH_ANGLE),
                identity('B', length * SECOND_CONTRACTION, width * WIDTH_DECREASE),
                pop()
            );
          }
        },
        new ContextFreeRule('B') {
          @Override
          public List<IModule> replacement(final double... parameters) {
            final double length = parameters[0];
            final double width = parameters[1];
            return Arrays.asList(
                width(width),
                drawf(length),
                push(),
                left(FIRST_BRANCH_ANGLE),
                rollLeftFlat(),
                identity('B', length * FIRST_CONTRACTION, width * WIDTH_DECREASE),
                pop(),
                push(),
                right(SECOND_BRANCH_ANGLE),
                rollLeftFlat(),
                identity('B', length * SECOND_CONTRACTION, width * WIDTH_DECREASE),
                pop()
            );
          }
        }
    );
  }


}
