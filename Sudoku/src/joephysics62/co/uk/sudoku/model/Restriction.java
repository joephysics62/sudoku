package joephysics62.co.uk.sudoku.model;

import java.util.Set;

public interface Restriction<T> {
  boolean satisfied(Puzzle<T> puzzle);
  Set<Cell<T>> eliminateValues(Puzzle<T> puzzle);
  Set<Coord> getCells();
}
