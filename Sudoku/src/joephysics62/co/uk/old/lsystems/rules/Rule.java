package joephysics62.co.uk.old.lsystems.rules;

import java.util.List;

import joephysics62.co.uk.old.lsystems.turtle.IModule;

public interface Rule {
  boolean matches(int index, List<IModule> input);
  List<IModule> replacement(double... parameters);
}
