package joephysics62.co.uk.lsystems.turtle;

import javafx.scene.paint.Color;
import joephysics62.co.uk.lsystems.LSystem;


public interface TurtleLSystem extends LSystem {
  double angle();
  default double drawStep() {
    return 0.1;
  }
  default double narrowing() {
    return 1.0;
  }
  default Color indexedColour(final int index) {
    return Color.GREEN;
  }
}
