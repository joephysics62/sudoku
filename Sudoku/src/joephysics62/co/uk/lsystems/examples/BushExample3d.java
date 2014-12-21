package joephysics62.co.uk.lsystems.examples;

import java.util.Arrays;
import java.util.List;

import javafx.scene.paint.Color;
import joephysics62.co.uk.lsystems.CharacterLSystem;
import joephysics62.co.uk.lsystems.rules.ContextFreeRule;
import joephysics62.co.uk.lsystems.rules.Rule;

public class BushExample3d extends CharacterLSystem {

  @Override
  public String axiomString() {
    return "A";
  }

  @Override
  public List<Rule<Character>> rules() {
    return Arrays.asList(
        ContextFreeRule.of('A', "[&F'L!A]/////[&F'L!A]///////[&F'L!A]"),
        ContextFreeRule.of('F', "S/////F"),
        ContextFreeRule.of('S', "FL")
    );
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
    return Color.rgb(Math.max(0, 70 - 15 * index), Math.min(255, 60 + 30 * index), Math.max(0, 20 - 5 * index));
  }

}
