package joephysics62.co.uk.sudoku.model;

import java.util.Set;

public interface Restriction<T> {
  boolean satisfied();
  Set<Cell<T>> eliminateValues();
}
