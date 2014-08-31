package joephysics62.co.uk.sudoku.model;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class Cell<T> {
  private final Set<T> _currentValues;
  private final Coord _identifier;
  private boolean _isSolved;
  public Cell(T fixedInitialValue, Coord identifier) {
    _identifier = identifier;
    _currentValues = Collections.singleton(fixedInitialValue);
  }
  public Cell(InitialValues<T> inits, Coord identifier) {
    _identifier = identifier;
    _currentValues = new LinkedHashSet<>(inits.getValues());
  }

  public void setSolved() {
    _isSolved = true;
  }

  public boolean isSolved() {
    return _isSolved;
  }

  public boolean canApplyElimination() {
    return !isSolved() && _currentValues.size() == 1;
  }

  public T getValue() {
    if (_currentValues.size() == 1) {
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
  public Coord getIdentifier() {
    return _identifier;
  }
}
