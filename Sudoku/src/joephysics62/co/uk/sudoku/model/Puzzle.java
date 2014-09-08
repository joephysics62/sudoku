package joephysics62.co.uk.sudoku.model;

import java.util.List;

import joephysics62.co.uk.sudoku.constraints.Constraint;

public interface Puzzle extends CellGrid {

  List<Constraint> getAllConstraints();

  List<Constraint> getConstraints(Coord coord);

  boolean isSolved();

  boolean isUnsolveable();

  int completeness();

  Puzzle deepCopy();

  int[][] getAllCells();

  int getPuzzleSize();

  int getSubTableWidth();

  int getSubTableHeight();

}