package com.fenton.puzzle.constraint;

import java.util.LinkedHashSet;
import java.util.Set;

import com.fenton.puzzle.grid.RectangularCoord;

public class Group {

  private final Set<RectangularCoord> _coords = new LinkedHashSet<>();

  public void addCell(final int row, final int col) {
    _coords.add(RectangularCoord.of(row, col));
  }

  public int size() {
    return _coords.size();
  }

  public Set<RectangularCoord> coords() {
    return _coords;
  }

}
