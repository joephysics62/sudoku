package joephysics62.co.uk.sudoku.builder;

import joephysics62.co.uk.sudoku.model.Puzzle;

public interface ArrayPuzzleBuilder {
  Puzzle build();

  void addGivens(Integer[][] givenCells);

  void addTitle(String title);
}
