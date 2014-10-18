package joephysics62.co.uk.grid;

import java.util.Iterator;

public abstract class FullGridIterable<T> implements Grid<T> {

  private final GridLayout _layout;

  public FullGridIterable(final GridLayout layout) {
    _layout = layout;
  }

  @Override
  public GridLayout getLayout() {
    return _layout;
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


}
