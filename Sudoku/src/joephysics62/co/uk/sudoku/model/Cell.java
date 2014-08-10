package joephysics62.co.uk.sudoku.model;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class Cell<T> {
  private final Set<T> _currentValues;
  private final String _identifier;
  public Cell(T fixedInitialValue, String identifier) {
    _identifier = identifier;
    _currentValues = Collections.singleton(fixedInitialValue);
  }
  public Cell(InitialValues<T> inits, String identifier) {
    _identifier = identifier;
    _currentValues = new LinkedHashSet<>(inits.getValues());
  }
  public boolean isSolved() {
    return _currentValues.size() == 1;
  }
  public T getValue() {
    if (isSolved()) {
      return _currentValues.iterator().next();
    }
    throw new UnsupportedOperationException("Cannot get value of an unsolved cell. Id " + _identifier + ". Current values " + _currentValues);
  }
  public boolean isUnsolveable() {
    return _currentValues.isEmpty();
  }
  public Set<T> getCurrentValues() {
    return _currentValues;
  }
}
