package com.fenton.puzzle;

import java.util.ArrayList;
import java.util.List;

import com.fenton.puzzle.grid.RectangularCoord;

public class PuzzleState {
  private final int[][] _bitMaskArray;
  private final int _height;
  private final int _width;

  public PuzzleState(final int height, final int width) {
    this(height, width, new int[height][width]);
  }

  private PuzzleState(final int height, final int width, final int[][] bitMaskArray) {
    _height = height;
    _width = width;
    _bitMaskArray = bitMaskArray;
  }

  @Override
  public PuzzleState clone() {
    final int[][] clonedState = ArrayUtils.clone(_bitMaskArray);
    return new PuzzleState(_height, _width, clonedState);
  }

  public void setGiven(final RectangularCoord coord, final int given) {
    setMask(coord, 1 << (given - 1));
  }

  public void initializeBitmask(final RectangularCoord coord, final int maxValue) {
    setMask(coord, (1 << maxValue) - 1);
  }

  public int possiblesCount(final RectangularCoord coord) {
    return Integer.bitCount(getMask(coord));
  }

  public List<Integer> possibles(final RectangularCoord coord) {
    final List<Integer> powers = new ArrayList<>();
    int n = getMask(coord); // something > 0
    int power = 0;
    while (n != 0) {
        if ((n & 1) != 0) {
            powers.add(power + 1);
        }
        ++power;
        n >>>= 1;
    }
    return powers;
  }

  private int getMask(final RectangularCoord coord) {
    return _bitMaskArray[coord.row][coord.col];
  }

  private void setMask(final RectangularCoord coord, final int mask) {
    _bitMaskArray[coord.row][coord.col] = mask;
  }

  public boolean applyFilter(final RectangularCoord from, final RectangularCoord to) {
    final int toMask = getMask(to);
    final int maskChange = toMask & getMask(from);
    if (maskChange > 0) {
      setMask(to, toMask - maskChange);
      return true;
    }
    return false;
  }


  public RectangularCoord findBestUnsolved() {
    int bestBitcount = Integer.MAX_VALUE;
    RectangularCoord bestCoord = null;
    for (int row = 0; row < _height; row++) {
      for (int col = 0; col < _width; col++) {
        final int possiblesCount = possiblesCount(RectangularCoord.of(row, col));
        if (possiblesCount < 2) {
          continue;
        }
        if (possiblesCount < bestBitcount) {
          bestCoord = RectangularCoord.of(row, col);
          if (possiblesCount == 2) {
            return bestCoord;
          }
          bestBitcount = possiblesCount;
        }
      }
    }
    return bestCoord;
  }

  public String asPossiblesString() {
    final StringBuilder sb = new StringBuilder();
    for (int row = 0; row < _height; row++) {
      sb.append('|');
      for (int col = 0; col < _width; col++) {
        final List<Integer> possibles = possibles(RectangularCoord.of(row, col));
        sb.append(possibles.size() == 1 ? possibles.get(0) : "?");
        sb.append("|");
      }
      sb.append("\n");
    }
    return sb.toString();
  }

}
