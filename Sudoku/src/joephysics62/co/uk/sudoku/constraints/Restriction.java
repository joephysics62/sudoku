package joephysics62.co.uk.sudoku.constraints;

import java.util.Set;

import joephysics62.co.uk.sudoku.model.Cell;
import joephysics62.co.uk.sudoku.model.CellGrid;
import joephysics62.co.uk.sudoku.model.Coord;

public interface Restriction {
  boolean eliminateValues(CellGrid cellGrid);
  Set<Coord> forSolvedCell(CellGrid cellGrid, Cell solvedCell);
  Set<Coord> getCells();
}
