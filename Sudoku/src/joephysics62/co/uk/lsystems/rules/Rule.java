package joephysics62.co.uk.lsystems.rules;

import java.util.List;

import joephysics62.co.uk.lsystems.TurtleElement;

public interface Rule {
  boolean matches(int index, List<TurtleElement> input);
  List<TurtleElement> replacement(double... parameters);
}
