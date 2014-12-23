package joephysics62.co.uk.lsystems.examples;

import static joephysics62.co.uk.lsystems.turtle.Module.drawf;
import static joephysics62.co.uk.lsystems.turtle.Module.narrow;
import static joephysics62.co.uk.lsystems.turtle.Module.pitchDown;
import static joephysics62.co.uk.lsystems.turtle.Module.pop;
import static joephysics62.co.uk.lsystems.turtle.Module.push;
import static joephysics62.co.uk.lsystems.turtle.Module.rollLeft;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import joephysics62.co.uk.lsystems.AbstractLSystem;
import joephysics62.co.uk.lsystems.rules.ContextFreeRule;
import joephysics62.co.uk.lsystems.rules.Rule;
import joephysics62.co.uk.lsystems.turtle.Module;

public class TernaryBranchingTrees extends AbstractLSystem {

  private static final double START_LENGTH = 1;
  private static final double START_WIDTH = 0.02;
  private static final double BRANCH_LENGTH = 0.25;

  private static final Module A_MOD = Module.create('A');
  private static final double DIVERGENCE_1 = 94.74;
  private static final double DIVERGENCE_2 = 132.63;

  private static final double BRANCH_ANGLE = 18.95;
  private static final double ELONGATION_RATE = 1.109;
  private static final double WIDTH_INCREASE_RATE = 0.7;

  @Override
  public List<Module> axiom() {
    return Arrays.asList(narrow(WIDTH_INCREASE_RATE), drawf(START_LENGTH), rollLeft(45), A_MOD);
  }

  private static final List<Module> newBranch() {
    return Arrays.asList(push(), pitchDown(BRANCH_ANGLE), drawf(BRANCH_LENGTH), A_MOD, pop());
  }


  @Override
  public List<Rule> rules() {
    return Arrays.asList(
        new ContextFreeRule(A_MOD.getId()) {
          @Override
          public List<Module> replacement(final double... parameters) {
            final List<Module> out = new ArrayList<>();
            out.add(narrow(WIDTH_INCREASE_RATE));
            out.add(drawf(BRANCH_LENGTH));
            out.addAll(newBranch());
            out.add(rollLeft(DIVERGENCE_1));
            out.addAll(newBranch());
            out.add(rollLeft(DIVERGENCE_2));
            out.addAll(newBranch());
            return out;
          }
        },
        new ContextFreeRule(drawf(0).getId()) {
          @Override
          public List<Module> replacement(final double... parameters) {
            final double length = parameters[0];
            return Arrays.asList(drawf(length * ELONGATION_RATE));
          }
        },
        new ContextFreeRule(narrow(0).getId()) {
          @Override
          public List<Module> replacement(final double... parameters) {
            final double width = parameters[0];
            return Arrays.asList(narrow(WIDTH_INCREASE_RATE));
          }
        }
    );
  }


}
