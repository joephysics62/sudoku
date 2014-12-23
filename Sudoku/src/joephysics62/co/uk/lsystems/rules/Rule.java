package joephysics62.co.uk.lsystems.rules;

import java.util.List;

import joephysics62.co.uk.lsystems.turtle.Module;

public interface Rule {
  boolean matches(int index, List<Module> input);
  List<Module> replacement(double... parameters);
}
