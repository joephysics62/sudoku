package com.fenton.puzzle.constraint;

import com.fenton.puzzle.PuzzleState;
import com.fenton.puzzle.grid.RectangularCoord;

public interface Constraint {

  void eliminate(RectangularCoord coord, PuzzleState state);
}
