package com.fenton.suguru;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Coord {
  private final int _row;
  private final int _col;
  private final int _hashCode;

  private static Map<Integer, Coord> INTERNED = new LinkedHashMap<>();

  public Coord(final int row, final int col) {
    _row = row;
    _col = col;
    _hashCode = hashKey(row, col);
    INTERNED.put(_hashCode, this);
  }

  private static int hashKey(final int row, final int col) {
    return Arrays.hashCode(new int[] {row, col});
  }

  public static Coord of(final int row, final int col) {
    return INTERNED.getOrDefault(hashKey(row, col), new Coord(row, col));
  }

  public Stream<Coord> surroundsWithDiagonals(final int width, final int height) {
    return Stream.of(of(_row, _col - 1), of(_row, _col + 1),
                     of(_row - 1, _col), of(_row + 1, _col),
                     of(_row - 1, _col - 1), of(_row + 1, _col + 1),
                     of(_row - 1, _col + 1), of(_row + 1, _col - 1))
          .filter(x -> x._row <= height && x._row > 0)
          .filter(x -> x._col <= width && x._col > 0);
  }

  public static Stream<Coord> overGrid(final int width, final int height) {
    return IntStream.rangeClosed(1, height)
                    .mapToObj(Integer::valueOf)
                    .flatMap(row -> IntStream.rangeClosed(1, width)
                                             .mapToObj(col -> of(row, col)));
  }

  public <T> T fromArray(final T[][] array) {
    return array[_row - 1][_col - 1];
  }

  public int fromIntArray(final int[][] array) {
    return array[_row - 1][_col - 1];
  }

  @Override
  public String toString() {
    return String.format("(%s, %s)", _row, _col);
  }

  @Override
  public int hashCode() {
    return _hashCode;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Coord)) {
      return false;
    }
    final Coord other = (Coord) obj;
    return _hashCode == other._hashCode;
  }


}
