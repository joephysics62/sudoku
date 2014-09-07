package joephysics62.co.uk.sudoku.constraints;

import java.util.List;

import joephysics62.co.uk.sudoku.model.CellGrid;
import joephysics62.co.uk.sudoku.model.Coord;

public interface Constraint {
  boolean eliminateValues(CellGrid cellGrid);
  List<Coord> forSolvedCell(CellGrid cellGrid, int solvedCell);
  List<Coord> getCells();
}
