package com.fenton.puzzle.grid;

import java.util.Arrays;
import java.util.Objects;

public class RectangularCoord implements Coord {

  public final int row;
  public final int col;
  private final int hashCode;

  private RectangularCoord(final int row, final int col) {
    this.row = row;
    this.col = col;
    hashCode = Arrays.hashCode(new int[] {row, col});
  }

  public static final RectangularCoord of(final int row, final int col) {
    return new RectangularCoord(row, col);
  }

  @Override
  public int hashCode() {
    return hashCode;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof RectangularCoord)) {
      return false;
    }
    final RectangularCoord other = (RectangularCoord) obj;
    return Objects.equals(col, other.col)
        && Objects.equals(row, other.row);
  }

  @Override
  public String toString() {
    return String.format("Coord(%s, %s)", row, col);
  }



}
