package joephysics62.co.uk.sudoku.constraints;

import java.util.List;

import joephysics62.co.uk.grid.Coord;
import joephysics62.co.uk.grid.Grid;

public interface Constraint {
  boolean eliminateValues(Grid<Integer> cellGrid);
  boolean forSolvedCell(Grid<Integer> cellGrid, Coord coord);
  List<Coord> getCells();
  boolean isSatisfied(Grid<Integer> cellGrid);
}
