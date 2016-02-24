package joephysics62.co.uk.grid;

import java.util.Arrays;

import com.google.common.base.Objects;

public class Coordinate implements Comparable<Coordinate> {
  private final int _row;
  private final int _col;

  public static Coordinate of(final int row, final int col) {
    return new Coordinate(row, col);
  }

  private Coordinate(final int row, final int col) {
    _row = row;
    _col = col;
  }

  public int getCol() { return _col; }
  public int getRow() { return _row; }

  public Coordinate left() {
    return of(_row, _col - 1);
  }

  public Coordinate up() {
    return of(_row - 1, _col);
  }

  public Coordinate right() {
    return of(_row, _col + 1);
  }

  public Coordinate down() {
    return of(_row + 1, _col);
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(new int[] {_col, _row});
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Coordinate)) {
      return false;
    }
    final Coordinate other = (Coordinate) obj;
    return Objects.equal(_col, other._col)
        && Objects.equal(_row, other._row);
  }

  @Override
  public String toString() {
    return "(" + _row + ", " + _col + ")";
  }

  @Override
  public int compareTo(final Coordinate other) {
    final int rowCompare = Integer.compare(_row, other._row);
    if (rowCompare == 0) {
      return Integer.compare(_col, other._col);
    }
    return rowCompare;
  }

}
