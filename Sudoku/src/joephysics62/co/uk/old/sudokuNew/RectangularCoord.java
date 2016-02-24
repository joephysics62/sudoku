package joephysics62.co.uk.old.sudokuNew;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class RectangularCoord {
  private final int _row;
  private final int _col;
  public RectangularCoord(final int row, final int col) {
    _row = row;
    _col = col;
  }
  public int getCol() { return _col; }
  public int getRow() { return _row; }

  public static Stream<RectangularCoord> coordGenerators(final int rows, final int cols) {
    return IntStream.rangeClosed(1, rows).boxed()
        .flatMap(row -> IntStream.rangeClosed(1, cols)
            .mapToObj(col -> new RectangularCoord(row, col)));
  }

  @Override
  public String toString() {
    return String.format("Coord(%s, %s)", _row, _col);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + _col;
    result = prime * result + _row;
    return result;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    final RectangularCoord other = (RectangularCoord) obj;
    if (_col != other._col)
      return false;
    if (_row != other._row)
      return false;
    return true;
  }
}
