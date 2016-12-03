package com.fenton.suguru;

import java.util.LinkedHashSet;
import java.util.Set;

public class Group {
  private final Set<Coord> _coords = new LinkedHashSet<>();


  public void addToGroup(final Coord coord) {
    _coords.add(coord);
  }

  public int size() {
    return _coords.size();
  }

  public Set<Coord> getCoords() {
    return _coords;
  }

  @Override
  public String toString() {
    return String.format("Group(%s)", _coords);
  }
}
