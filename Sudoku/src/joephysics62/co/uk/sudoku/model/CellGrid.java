package joephysics62.co.uk.sudoku.model;

import java.util.Set;

public interface CellGrid<T extends Comparable<T>> {

  Cell<T> getCell(Coord coord);

  Set<T> getInits();
}