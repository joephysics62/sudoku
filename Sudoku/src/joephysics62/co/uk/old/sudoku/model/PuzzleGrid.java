package joephysics62.co.uk.old.sudoku.model;

import joephysics62.co.uk.old.grid.Grid;

public interface PuzzleGrid extends Grid<Integer> {

  @Override
  PuzzleLayout getLayout();

}