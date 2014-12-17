package joephysics62.co.uk.lsystems.examples;

import javafx.scene.paint.Color;
import joephysics62.co.uk.lsystems.turtle.TurtleLSystem;

public class BushExample3d implements TurtleLSystem {

  @Override
  public String axiom() {
    return "A";
  }

  @Override
  public String applyRule(final Character input) {
    switch (input) {
    case 'A':
      return "[&FL!A]/////'[&FL!A]///////'[&FL!A]";
    case 'F':
      return "S/////F";
    case 'S':
      return "FL";
    default:
      return input.toString();
    }
  }

  @Override
  public double angle() {
    return 22;
  }

  @Override
  public double narrowing() {
    return 0.6;
  }

  @Override
  public Color indexedColour(final int index) {
    return Color.rgb(Math.max(0, 40 - 15 * index), Math.min(255, 60 + 30 * index), Math.max(0, 20 - 5 * index));
  }

}
