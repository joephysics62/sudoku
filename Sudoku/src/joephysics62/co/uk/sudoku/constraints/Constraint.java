package joephysics62.co.uk.sudoku.constraints;

import java.util.List;

import joephysics62.co.uk.grid.Coord;
import joephysics62.co.uk.sudoku.model.PuzzleGrid;

public interface Constraint {
  boolean eliminateValues(PuzzleGrid cellGrid);
  boolean forSolvedCell(PuzzleGrid cellGrid, Coord coord);
  List<Coord> getCells();
  boolean isSatisfied(PuzzleGrid cellGrid);
}
