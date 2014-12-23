package joephysics62.co.uk.lsystems;

import java.util.List;

import javafx.scene.paint.Color;
import joephysics62.co.uk.lsystems.rules.Rule;
import joephysics62.co.uk.lsystems.turtle.Module;


public interface LSystem {

  public static final int MAX_ELEMENTS = 50000;

	List<Module> axiom();

	List<Rule> rules();

  List<Module> generate(int iterations);

  default Color indexedColour(final int index) {
    return Color.GREEN;
  }

}