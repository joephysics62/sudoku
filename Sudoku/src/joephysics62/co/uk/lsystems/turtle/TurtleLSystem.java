package joephysics62.co.uk.lsystems.turtle;

import joephysics62.co.uk.lsystems.LSystem;

public interface TurtleLSystem extends LSystem {
  double angle();
  default double narrowing() {
    return 1.0;
  }
}
