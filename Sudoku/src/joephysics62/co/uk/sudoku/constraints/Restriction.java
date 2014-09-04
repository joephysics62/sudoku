package joephysics62.co.uk.sudoku.constraints;

import java.util.Set;

import joephysics62.co.uk.sudoku.model.CellGrid;
import joephysics62.co.uk.sudoku.model.Coord;

public interface Restriction<T extends Comparable<T>> {
  boolean eliminateValues(CellGrid<T> cellGrid);
  Set<Coord> getCells();
}
