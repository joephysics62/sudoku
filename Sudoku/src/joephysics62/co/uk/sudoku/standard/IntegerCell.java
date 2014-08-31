package joephysics62.co.uk.sudoku.standard;

import java.util.Set;

import joephysics62.co.uk.sudoku.model.Cell;
import joephysics62.co.uk.sudoku.model.Coord;

class IntegerCell extends Cell<Integer> {

  public IntegerCell(int init, final Coord id) {
    super(init, id);
  }

  public IntegerCell(final Set<Integer> inits, final Coord id) {
    super(inits, id);
  }

  @Override
  public String toString() {
    return String.format("IntegerCell(id=%s, vals=%s)", getIdentifier(), getCurrentValues());
  }

}
