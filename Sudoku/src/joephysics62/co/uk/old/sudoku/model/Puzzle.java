package joephysics62.co.uk.old.sudoku.model;

import java.util.List;

import joephysics62.co.uk.old.constraints.Constraint;
import joephysics62.co.uk.old.grid.Coord;

public interface Puzzle extends PuzzleGrid {

  String getTitle();

  List<Constraint> getAllConstraints();

  List<Constraint> getVariableConstraints();

  List<Constraint> getConstraints(Coord coord);

  boolean isSolved();

  boolean isUnsolveable();

  Puzzle deepCopy();

}