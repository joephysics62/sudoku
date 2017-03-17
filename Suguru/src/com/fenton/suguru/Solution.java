package com.fenton.suguru;

import java.util.Optional;

import com.fenton.puzzle.PuzzleState;

public class Solution {
  private final SolutionType _type;
  private final Optional<PuzzleState> _grid;

  public Solution(final SolutionType type, final Optional<PuzzleState> grid) {
    _type = type;
    _grid = grid;
  }

  public Optional<PuzzleState> getGrid() {
    return _grid;
  }
  public SolutionType getType() {
    return _type;
  }
}
