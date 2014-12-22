package joephysics62.co.uk.lsystems;

import java.util.List;

import javafx.scene.paint.Color;
import joephysics62.co.uk.lsystems.rules.Rule;


public interface LSystem {

  public static final int MAX_ELEMENTS = 50000;

	List<TurtleElement> axiom();

	List<Rule> rules();

  List<TurtleElement> generate(int iterations);

  default Color indexedColour(final int index) {
    return Color.GREEN;
  }

}