package joephysics62.co.uk.old.sudoku.builder;

import joephysics62.co.uk.old.constraints.Constraint;
import joephysics62.co.uk.old.grid.Coord;
import joephysics62.co.uk.old.sudoku.model.Puzzle;

public interface Builder {
  Puzzle build();

  void addGiven(Integer value, Coord coord);

  void addTitle(String title);

  void addConstraint(Constraint constraint);

  void addNonValueCell(Coord coord);
}
