package joephysics62.co.uk.sudoku.model;

public class Coord {
  private final int _row;
  private final int _col;

  private Coord(final int row, final int col) {
    _row = row;
    _col = col;
  }

  public static Coord of(final int row, final int col) {
    return new Coord(row, col);
  }

  @Override
  public String toString() {
    return String.format("Coord(Row=%s,Col=%s)", _row, _col);
  }

  public int getRow() {
    return _row;
  }
  public int getCol() {
    return _col;
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
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Coord other = (Coord) obj;
    if (_col != other._col)
      return false;
    if (_row != other._row)
      return false;
    return true;
  }

}
