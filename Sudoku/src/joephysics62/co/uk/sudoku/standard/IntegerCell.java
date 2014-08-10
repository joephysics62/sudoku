package joephysics62.co.uk.sudoku.standard;

import joephysics62.co.uk.sudoku.model.Cell;
import joephysics62.co.uk.sudoku.model.InitialValues;

public class IntegerCell extends Cell<Integer> {

  public IntegerCell(int init, final String id) {
    super(init, id);
    if (init < StandardPuzzle.STANDARD_MIN_VALUE || init > StandardPuzzle.STANDARD_MAX_VALUE) {
      throw new IllegalArgumentException("Bad init: " + init);
    }
  }

  public IntegerCell(final InitialValues<Integer> inits, final String id) {
    super(inits, id);
  }

}
