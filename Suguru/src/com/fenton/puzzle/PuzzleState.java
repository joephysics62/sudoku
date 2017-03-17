package com.fenton.puzzle;

import com.fenton.puzzle.grid.RectangularCoord;

public class PuzzleState {
  private final int[][] _bitMaskArray;
  private final int _height;
  private final int _width;
  private boolean _valid = true;

  public PuzzleState(final int height, final int width) {
    _height = height;
    _width = width;
    _bitMaskArray = new int[height][width];
  }

  public void setValue(final RectangularCoord coord, final int value) {
    _bitMaskArray[coord.row][coord.col] = (int) Math.pow(2, value - 1);
  }

  public void initializeBitmask(final RectangularCoord coord, final int maxValue) {
    _bitMaskArray[coord.row][coord.col] = (int) Math.pow(2,  maxValue) - 1;
  }

  public int possiblesCount(final RectangularCoord coord) {
    return Integer.bitCount(maskValue(coord));
  }

  public int maskValue(final RectangularCoord coord) {
    return _bitMaskArray[coord.row][coord.col];
  }

  public boolean hasCoord(final RectangularCoord coord) {
    return coord.col >= 0 && coord.col < _width
        && coord.row >= 0 && coord.row < _height;
  }

  public void invalidate() {
    _valid = false;
  }

  public int applyMask(final RectangularCoord eliminee, final int maskValue) {
    return _bitMaskArray[eliminee.row][eliminee.col] ^= maskValue;
  }

}
