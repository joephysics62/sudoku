package joephysics62.co.uk.sudoku.model;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import joephysics62.co.uk.sudoku.constraints.Restriction;

public class MapBackedPuzzle<T extends Comparable<T>> implements Puzzle<T> {
  private final Map<Coord, Set<Restriction<T>>> _constraints = new TreeMap<>();
  private final Map<Coord, Cell<T>> _cells = new TreeMap<>();
  private final Set<Restriction<T>> _restrictions = new LinkedHashSet<>();
  private final Set<T> _inits;

  private MapBackedPuzzle(MapBackedPuzzle<T> old) {
    for (Entry<Coord, Cell<T>> entry : old._cells.entrySet()) {
      _cells.put(entry.getKey(), Cell.copyOf(entry.getValue()));
    }
    _constraints.putAll(old._constraints);
    _restrictions.addAll(old._restrictions);
    _inits = old._inits;
  }

  protected MapBackedPuzzle(Collection<T> inits) {
    _inits = Collections.unmodifiableSet(new LinkedHashSet<>(inits));
  }

  public static <T extends Comparable<T>> MapBackedPuzzle<T> forInits(List<T> inits) {
    return new MapBackedPuzzle<>(inits);
  }

  @Override
  public final Collection<Cell<T>> getAllCells() {
    return _cells.values();
  }

  @Override
  public Set<T> getInits() {
    return _inits;
  }

  @Override
  public final Cell<T> getCell(Coord coord) {
    return _cells.get(coord);
  }

  @Override
  public Puzzle<T> deepCopy() {
    return new MapBackedPuzzle<T>(this);
  }

  @Override
  public Set<Restriction<T>> getAllRestrictions() {
    return _restrictions;
  }

  @Override
  public Set<Restriction<T>> getRestrictions(final Coord coord) {
    return Collections.unmodifiableSet(_constraints.get(coord));
  }

  @Override
  public boolean isSolved() {
    for (Cell<T> cell : getAllCells()) {
      if (!cell.isSolved()) {
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean isUnsolveable() {
    for (Cell<T> cell : getAllCells()) {
      if (cell.isUnsolveable()) {
        return true;
      }
    }
    return false;
  }

  @Override
  public int completeness() {
    int completeness = 0;
    for (Cell<T> cell : getAllCells()) {
      completeness += cell.getCurrentValues().size();
    }
    return completeness;
  }

  public void addConstraint(Restriction<T> restriction) {
    _restrictions.add(restriction);
    for (Coord cellCoord : restriction.getCells()) {
      if (!_constraints.containsKey(cellCoord)) {
        _constraints.put(cellCoord, new LinkedHashSet<Restriction<T>>());
      }
      _constraints.get(cellCoord).add(restriction);
    }
  }

  public void addCells(List<List<T>> givenValues) {
    for (int rowIndex = 0; rowIndex < givenValues.size(); rowIndex++) {
      final List<T> row = givenValues.get(rowIndex);
      for (int colIndex = 0; colIndex < row.size(); colIndex++) {
        T value = givenValues.get(rowIndex).get(colIndex);
        Coord coord = new Coord(rowIndex + 1, colIndex + 1);
        _cells.put(coord, value == null ? Cell.of(getInits(), coord) : Cell.of(value, coord));
      }
    }
  }


}
