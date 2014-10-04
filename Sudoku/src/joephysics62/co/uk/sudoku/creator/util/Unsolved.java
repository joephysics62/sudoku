package joephysics62.co.uk.sudoku.creator.util;

import joephysics62.co.uk.sudoku.model.Cell;
import joephysics62.co.uk.sudoku.solver.CellFilter;

/**
 */
public class Unsolved extends CellFilterForArrayPuzzle {

  private Unsolved() {
    // no.
  }

  @Override
  protected boolean accept(int cellValue) {
    return !Cell.isSolved(cellValue);
  }

  public static CellFilter create() {
    return new Unsolved();
  }

}
