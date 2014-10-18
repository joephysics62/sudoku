package joephysics62.co.uk.sudoku.model;

import java.util.List;

import joephysics62.co.uk.constraints.Constraint;
import joephysics62.co.uk.grid.Coord;

public interface Puzzle extends PuzzleGrid {

  String getTitle();

  List<Constraint> getAllConstraints();

  List<Constraint> getVariableConstraints();

  List<Constraint> getConstraints(Coord coord);

  boolean isSolved();

  boolean isUnsolveable();

  int completeness();

  Puzzle deepCopy();

}