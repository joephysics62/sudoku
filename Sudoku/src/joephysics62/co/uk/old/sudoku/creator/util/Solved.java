package joephysics62.co.uk.old.sudoku.creator.util;

import joephysics62.co.uk.old.sudoku.model.Cell;
import joephysics62.co.uk.old.sudoku.solver.CellFilter;

/**
 * Pick randomly a cell to guess the value of. Primary for creating puzzles rather than solving them.
 */
public class Solved extends CellFilterForArrayPuzzle {

  private Solved() {
    // no.
  }

  @Override
  protected boolean accept(int cellValue) {
    return Cell.isSolved(cellValue);
  }


  public static CellFilter create() {
    return new Solved();
  }

}
