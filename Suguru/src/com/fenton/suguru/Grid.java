package com.fenton.suguru;

import java.util.Map;
import java.util.Set;

public class Grid<T> {
  private final Map<Coord, Set<T>> _map;
  private final int _width;
  private final int _height;

  public Grid(final Map<Coord, Set<T>> map, final int width, final int height) {
    _map = map;
    _width = width;
    _height = height;
  }

}
