package com.fenton.puzzle.constraint;

import java.util.LinkedHashSet;
import java.util.Set;

import com.fenton.puzzle.PuzzleState;
import com.fenton.puzzle.grid.RectangularCoord;

public class UniquenessArbitraryGroup implements Constraint {

  private final Set<RectangularCoord> _group = new LinkedHashSet<>();
  private final char _id;

  public UniquenessArbitraryGroup(final char id) {
    _id = id;
  }

  public void addToGroup(final RectangularCoord coord) {
    _group.add(coord);
  }

  public int size() {
    return _group.size();
  }

  public char getId() {
    return _id;
  }

  @Override
  public void eliminate(final RectangularCoord coord, final PuzzleState state) {
    if (!state.hasCoord(coord)) {
      return;
    }
  }

}
