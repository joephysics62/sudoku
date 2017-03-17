package com.fenton.puzzle.constraint;

import java.util.Arrays;

public class Group {

  private int[][] _coords = {};

  public void addCell(final int row, final int col) {
    _coords = concat(_coords, new int[][] {{row, col}});
  }

  public int size() {
    return _coords.length;
  }

  public int[][] getCoords() {
    return _coords;
  }

  public static <T> T[] concat(final T[] first, final T[] second) {
    final T[] result = Arrays.copyOf(first, first.length + second.length);
    System.arraycopy(second, 0, result, first.length, second.length);
    return result;
  }

}
