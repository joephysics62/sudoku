package joephysics62.co.uk.sudoku.builder;

import joephysics62.co.uk.constraints.Constraint;
import joephysics62.co.uk.grid.Coord;
import joephysics62.co.uk.sudoku.model.Puzzle;

public interface Builder {
  Puzzle build();

  void addGiven(Integer value, Coord coord);

  void addTitle(String title);

  void addConstraint(Constraint constraint);

  void addNonValueCell(Coord coord);
}
