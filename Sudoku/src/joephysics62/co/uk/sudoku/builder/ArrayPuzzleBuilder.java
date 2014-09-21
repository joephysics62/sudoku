package joephysics62.co.uk.sudoku.builder;

import joephysics62.co.uk.sudoku.model.Coord;
import joephysics62.co.uk.sudoku.model.Puzzle;

public interface ArrayPuzzleBuilder {
  Puzzle build();

  void addGiven(Integer value, Coord coord);

  void addTitle(String title);
}
