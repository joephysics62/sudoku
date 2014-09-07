package joephysics62.co.uk.sudoku.model;

import java.util.Set;

import joephysics62.co.uk.sudoku.constraints.Restriction;

public interface Puzzle extends CellGrid {

  Set<Restriction> getAllRestrictions();

  Set<Restriction> getRestrictions(Coord coord);

  boolean isSolved();

  boolean isUnsolveable();

  int completeness();

  Puzzle deepCopy();

  int[][] getAllCells();

  int getPuzzleSize();

}