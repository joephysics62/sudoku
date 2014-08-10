package joephysics62.co.uk.sudoku.model;

import java.util.Collections;
import java.util.Set;

public class InitialValues<T> {
  private final Set<T> _values;
  public InitialValues(Set<T> initialValues) {
    _values = Collections.unmodifiableSet(initialValues);
  }

  public Set<T> getValues() {
    return _values;
  }
}
