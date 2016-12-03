package com.fenton.suguru;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

public class Grid<T> {
  private final Map<Coord, Set<T>> _map;
  private final int _width;
  private final int _height;

  public Grid(final Map<Coord, Set<T>> map, final int width, final int height) {
    _map = map;
    _width = width;
    _height = height;
  }

  public Set<T> values(final Coord coord) {
    return _map.get(coord);
  }

  public void traverse(final GridVisitor<T> visitor) {
    visitor.start();
    for (int row = 1; row <= _height; row++) {
      visitor.startRow(row);
      for (int col = 1; col <= _width; col++) {
        visitor.handleValue(row, col, _map.get(Coord.of(row, col)));
      }
      visitor.endRow(row);
    }
    visitor.end();
  }

  @Override
  public Grid<T> clone() {
    final Map<Coord, Set<T>> newMap = _map.entrySet().stream()
                                          .collect(Collectors.toMap(Entry::getKey, e -> new LinkedHashSet<>(e.getValue())));
    return new Grid<>(newMap, _width, _height);
  }

  public void removeValue(final Coord coord, final Integer fixedValue) {
    _map.get(coord).remove(fixedValue);
  }

}
