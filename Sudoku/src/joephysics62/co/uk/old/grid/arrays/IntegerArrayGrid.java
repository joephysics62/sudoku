package joephysics62.co.uk.old.grid.arrays;

import joephysics62.co.uk.old.grid.Coord;
import joephysics62.co.uk.old.grid.FullGridIterable;
import joephysics62.co.uk.old.grid.GridLayout;

public class IntegerArrayGrid extends FullGridIterable<Integer> {

  private final int[][] _cells;

  public IntegerArrayGrid(final GridLayout gridLayout) {
    super(gridLayout);
    _cells = new int[gridLayout.getHeight()][gridLayout.getWidth()];
  }

  public IntegerArrayGrid(IntegerArrayGrid other) {
    super(other.getLayout());
    _cells = new int[other.getLayout().getHeight()][other.getLayout().getWidth()];
    for (int rowIndex = 0; rowIndex < other.getLayout().getHeight(); rowIndex++) {
      _cells[rowIndex] = other._cells[rowIndex].clone();
    }
  }

  @Override
  public final Integer get(Coord coord) {
    return _cells[coord.getRow() - 1][coord.getCol() - 1];
  }

  @Override
  public final void set(Integer value, Coord coord) {
    _cells[coord.getRow() - 1][coord.getCol() - 1] = value;
  }

}
