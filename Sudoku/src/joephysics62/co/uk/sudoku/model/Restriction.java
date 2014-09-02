package joephysics62.co.uk.sudoku.model;

import java.util.Set;

public interface Restriction<T extends Comparable<T>> {
  Set<Cell<T>> eliminateValues(CellGrid<T> cellGrid);
  Set<Coord> getCells();
}
