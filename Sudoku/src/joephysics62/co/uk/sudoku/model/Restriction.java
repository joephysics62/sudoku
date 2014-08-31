package joephysics62.co.uk.sudoku.model;

import java.util.Map;
import java.util.Set;

public interface Restriction<T> {
  boolean satisfied();
  Set<Cell<T>> eliminateValues();
  Set<Cell<T>> getCells();
  Restriction<T> copy(Map<Coord, Cell<T>> copiedCells);
}
