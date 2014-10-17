package joephysics62.co.uk.sudoku.model;

import joephysics62.co.uk.grid.Grid;

public interface PuzzleGrid extends Grid<Integer> {

  @Override
  PuzzleLayout getLayout();

}