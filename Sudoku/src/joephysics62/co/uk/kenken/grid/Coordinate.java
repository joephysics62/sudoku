package joephysics62.co.uk.kenken.grid;

import java.util.Arrays;
import java.util.Optional;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.Objects;

@JsonSerialize(using = CoordinateSerializer.class)
@JsonDeserialize(using = CoordinateDeserializer.class)
public class Coordinate {
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

  public Optional<Coordinate> left() {
    return _col > 1 ? Optional.of(of(_row, _col - 1)) : Optional.empty();
  }

  public Optional<Coordinate> up() {
    return _row > 1 ? Optional.of(of(_row - 1, _col)) : Optional.empty();
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
    return "Coordinate(row=" + _row + ", col=" + _col + ")";
  }
}
