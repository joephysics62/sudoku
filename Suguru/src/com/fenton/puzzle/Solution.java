package com.fenton.puzzle;

import java.util.Optional;

public class Solution {
  private final SolutionType _type;
  private final Optional<PuzzleState> _grid;
  private final int _recurseCount;

  public Solution(final SolutionType type, final Optional<PuzzleState> grid, final int recurseCount) {
    _type = type;
    _grid = grid;
    _recurseCount = recurseCount;
  }

  public Optional<PuzzleState> getGrid() {
    return _grid;
  }
  public SolutionType getType() {
    return _type;
  }
  public int getRecurseCount() {
    return _recurseCount;
  }
}
