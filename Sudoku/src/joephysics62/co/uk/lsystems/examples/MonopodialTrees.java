package joephysics62.co.uk.lsystems.examples;

import static joephysics62.co.uk.lsystems.turtle.Module.create;
import static joephysics62.co.uk.lsystems.turtle.Module.drawf;
import static joephysics62.co.uk.lsystems.turtle.Module.left;
import static joephysics62.co.uk.lsystems.turtle.Module.pitchDown;
import static joephysics62.co.uk.lsystems.turtle.Module.pop;
import static joephysics62.co.uk.lsystems.turtle.Module.push;
import static joephysics62.co.uk.lsystems.turtle.Module.right;
import static joephysics62.co.uk.lsystems.turtle.Module.rollLeft;
import static joephysics62.co.uk.lsystems.turtle.Module.rollLeftFlat;
import static joephysics62.co.uk.lsystems.turtle.Module.width;

import java.util.Arrays;
import java.util.List;

import joephysics62.co.uk.lsystems.AbstractLSystem;
import joephysics62.co.uk.lsystems.rules.ContextFreeRule;
import joephysics62.co.uk.lsystems.rules.Rule;
import joephysics62.co.uk.lsystems.turtle.IModule;

public class MonopodialTrees extends AbstractLSystem {

  private static final double TRUNK_BRANCH_ANGLE = 30;
  private static final double LATERAL_BRANCH_ANGLE = -30;

  private static final double TRUNK_CONTRACTION = 0.9;
  private static final double BRANCH_CONTRACTION = 0.7;
  private static final double WIDTH_DECREASE = 0.707;
  private static final double DIVERGENCE = 137.5;


  @Override
  public List<IModule> axiom() {
    return Arrays.asList(create('A', 1, 0.1));
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
                pitchDown(TRUNK_BRANCH_ANGLE),
                create('B', length * BRANCH_CONTRACTION, width * WIDTH_DECREASE),
                pop(),
                rollLeft(DIVERGENCE),
                create('A', length * TRUNK_CONTRACTION, width * WIDTH_DECREASE)
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
                right(LATERAL_BRANCH_ANGLE),
                rollLeftFlat(),
                create('C', length * BRANCH_CONTRACTION, width * WIDTH_DECREASE),
                pop(),
                create('C', length * TRUNK_CONTRACTION, width * WIDTH_DECREASE)
            );
          }
        },
        new ContextFreeRule('C') {
          @Override
          public List<IModule> replacement(final double... parameters) {
            final double length = parameters[0];
            final double width = parameters[1];
            return Arrays.asList(
                width(width),
                drawf(length),
                push(),
                left(LATERAL_BRANCH_ANGLE),
                rollLeftFlat(),
                create('B', length * BRANCH_CONTRACTION, width * WIDTH_DECREASE),
                pop(),
                create('B', length * TRUNK_CONTRACTION, width * WIDTH_DECREASE)
            );
          }
        }
    );
  }

}
