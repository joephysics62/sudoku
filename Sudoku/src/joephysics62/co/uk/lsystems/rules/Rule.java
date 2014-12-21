package joephysics62.co.uk.lsystems.rules;

import java.util.List;

public interface Rule<T> {
  boolean matches(int index, List<T> input);
  List<T> replacement();
}
