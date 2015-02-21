package joephysics62.co.uk.sudokuNew;

import java.util.Set;

public class Cell<ValueType> {
  private final Set<ValueType> _values;

  public Cell(final Set<ValueType> values) {
    _values = values;
  }
  public Set<ValueType> getValues() {
    return _values;
  }
  public void removeValue(final ValueType value) {
    _values.remove(value);
  }
  public boolean isSolved() {
    return _values.size() == 1;
  }
  public boolean isUnsolveable() {
    return _values.size() == 0;
  }

}
