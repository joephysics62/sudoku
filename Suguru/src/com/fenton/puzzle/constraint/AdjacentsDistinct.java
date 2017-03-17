package com.fenton.puzzle.constraint;

import com.fenton.puzzle.PuzzleState;
import com.fenton.puzzle.grid.RectangularCoord;

public class AdjacentsDistinct implements Constraint {

  @Override
  public void eliminate(final RectangularCoord coord, final PuzzleState state) {
    if (!state.hasCoord(coord)) {
      return;
    }
    final int possibles = state.possiblesCount(coord);
    if (possibles > 1) {
      return;
    }
    if (possibles == 0) {
      state.invalidate();
      return;
    }
    final int maskValue = state.maskValue(coord);
    for (int rowDiff = -1; rowDiff <= 1; rowDiff++) {
      for (int colDiff = -1; colDiff <= 1; colDiff++) {
        if (rowDiff == 0 && colDiff == 0) {
          continue;
        }
        final RectangularCoord eliminee = RectangularCoord.of(coord.row + rowDiff, coord.col + colDiff);
        if (!state.hasCoord(eliminee)) {
          continue;
        }
        state.applyMask(eliminee, maskValue);
      }
    }
  }
}
