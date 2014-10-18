package joephysics62.co.uk.grid.arrays;

import java.util.Iterator;

import joephysics62.co.uk.grid.Coord;
import joephysics62.co.uk.grid.Grid;
import joephysics62.co.uk.grid.GridLayout;

public class IntegerArrayGrid implements Grid<Integer> {

  private final GridLayout _layout;
  private final int[][] _cells;

  public IntegerArrayGrid(final GridLayout gridLayout) {
    _layout = gridLayout;
    _cells = new int[gridLayout.getHeight()][gridLayout.getWidth()];
  }

  public IntegerArrayGrid(IntegerArrayGrid other) {
    _layout = other._layout;
    _cells = new int[_layout.getHeight()][_layout.getWidth()];
    for (int rowIndex = 0; rowIndex < _layout.getHeight(); rowIndex++) {
      _cells[rowIndex] = other._cells[rowIndex].clone();
    }
  }

  @Override
  public final Iterator<Coord> iterator() {
    return new Iterator<Coord>() {
      private int _rowNum = 1;
      private int _colNum = 1;
      @Override
      public boolean hasNext() {
        return _rowNum <= _layout.getHeight() && _colNum <= _layout.getWidth();
      }

      @Override
      public Coord next() {
        if (!hasNext()) {
          throw new ArrayIndexOutOfBoundsException();
        }
        Coord coord = Coord.of(_rowNum, _colNum);
        if (_colNum == _layout.getWidth()) {
          _colNum = 1;
          _rowNum++;
        }
        else {
          _colNum++;
        }
        return coord;
      }

      @Override
      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
  }

  @Override
  public final Integer get(Coord coord) {
    return _cells[coord.getRow() - 1][coord.getCol() - 1];
  }

  @Override
  public final void set(Integer value, Coord coord) {
    _cells[coord.getRow() - 1][coord.getCol() - 1] = value;
  }

  @Override
  public GridLayout getLayout() {
    return _layout;
  }

}
