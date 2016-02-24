package joephysics62.co.uk.old.lsystems;

import java.util.List;

import javafx.scene.paint.Color;
import joephysics62.co.uk.old.lsystems.rules.Rule;
import joephysics62.co.uk.old.lsystems.turtle.IModule;


public interface LSystem {

  public static final int MAX_ELEMENTS = 50000;

	List<IModule> axiom();

	List<Rule> rules();

  List<IModule> generate(int iterations);

  default Color indexedColour(final int index) {
    return Color.GREEN;
  }

}