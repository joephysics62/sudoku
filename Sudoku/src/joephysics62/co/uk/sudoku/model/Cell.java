package joephysics62.co.uk.sudoku.model;

import java.util.Collection;
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
  public Cell(Set<T> inits, Coord identifier) {
    _identifier = identifier;
    _currentValues = new LinkedHashSet<>(inits);
  }
  public Cell(Cell<T> old) {
    _identifier = old._identifier;
    _currentValues = new LinkedHashSet<>(old._currentValues);
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
    else if (_currentValues.isEmpty()) {
      return null;
    }
    throw new UnsupportedOperationException("Cannot get value of an unsolved cell. Id " + _identifier + ". Current values " + _currentValues);
  }

  public boolean isUnsolveable() {
    return _currentValues.isEmpty();
  }

  public Set<T> getCurrentValues() {
    return Collections.unmodifiableSet(_currentValues);
  }

  public boolean remove(final T value) {
    return _currentValues.remove(value);
  }

  public boolean removeAll(final Collection<T> values) {
    return _currentValues.removeAll(values);
  }

  public Coord getIdentifier() {
    return _identifier;
  }
}
