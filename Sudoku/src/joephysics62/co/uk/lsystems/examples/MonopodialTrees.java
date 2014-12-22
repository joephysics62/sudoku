package joephysics62.co.uk.lsystems.examples;

import java.util.Arrays;
import java.util.List;

import joephysics62.co.uk.lsystems.AbstractLSystem;
import joephysics62.co.uk.lsystems.TurtleElement;
import joephysics62.co.uk.lsystems.rules.ContextFreeRule;
import joephysics62.co.uk.lsystems.rules.Rule;

public class MonopodialTrees extends AbstractLSystem {

  private static final TurtleElement POP = new TurtleElement(']', -1);
  private static final TurtleElement PUSH = new TurtleElement('[', -1);
  private static final double A0 = 45;
  private static final double A2 = 45;

  private static final double R1 = 0.9;
  private static final double R2 = 0.6;
  private static final double WR = 0.707;
  private static final double D = 137.5;


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
            final double L = parameters[0];
            final double w = parameters[1];
            return Arrays.asList(
                new TurtleElement('£', w),
                new TurtleElement('F', L),
                PUSH,
                new TurtleElement('&', A0),
                new TurtleElement('B', L * R2, w * WR),
                POP,
                new TurtleElement('/', D),
                new TurtleElement('A', L * R1, w * WR)
            );
          }
        },
        new ContextFreeRule('B') {
          @Override
          public List<TurtleElement> replacement(final double... parameters) {
            final double L = parameters[0];
            final double w = parameters[1];
            return Arrays.asList(
                new TurtleElement('£', w),
                new TurtleElement('F', L),
                PUSH,
                new TurtleElement('-', A2),
                new TurtleElement('C', L * R2, w * WR),
                POP,
                new TurtleElement('C', L * R1, w * WR)
            );
          }
        },
        new ContextFreeRule('C') {
          @Override
          public List<TurtleElement> replacement(final double... parameters) {
            final double L = parameters[0];
            final double w = parameters[1];
            return Arrays.asList(
                new TurtleElement('£', w),
                new TurtleElement('F', L),
                PUSH,
                new TurtleElement('+', A2),
                new TurtleElement('B', L * R2, w * WR),
                POP,
                new TurtleElement('B', L * R1, w * WR)
            );
          }
        }
    );
  }


}
