package com.fenton.suguru;

import java.util.Optional;

public class Solution<T> {
  private final SolutionType _type;
  private final Optional<Grid<T>> _grid;

  public Solution(final SolutionType type, final Optional<Grid<T>> grid) {
    _type = type;
    _grid = grid;
  }
}
