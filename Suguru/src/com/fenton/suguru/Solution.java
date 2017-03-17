package com.fenton.suguru;

import java.util.Optional;

public class Solution {
  private final SolutionType _type;
  private final Optional<int[][]> _grid;

  public Solution(final SolutionType type, final Optional<int[][]> grid) {
    _type = type;
    _grid = grid;
  }

  public Optional<int[][]> getGrid() {
    return _grid;
  }
  public SolutionType getType() {
    return _type;
  }
}
