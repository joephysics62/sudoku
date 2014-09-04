package joephysics62.co.uk.sudoku.constraints;

import java.util.Set;

import joephysics62.co.uk.sudoku.model.Cell;
import joephysics62.co.uk.sudoku.model.CellGrid;
import joephysics62.co.uk.sudoku.model.Coord;

public interface Restriction<T extends Comparable<T>> {
  Set<Cell<T>> eliminateValues(CellGrid<T> cellGrid);
  Set<Coord> getCells();
}
