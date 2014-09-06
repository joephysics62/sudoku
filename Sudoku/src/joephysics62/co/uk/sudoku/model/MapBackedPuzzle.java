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

public class MapBackedPuzzle implements Puzzle {
  private final Map<Coord, Set<Restriction>> _constraints = new TreeMap<>();
  private final Map<Coord, Cell> _cells = new TreeMap<>();
  private final Set<Restriction> _restrictions = new LinkedHashSet<>();
  private final int _inits;

  private MapBackedPuzzle(MapBackedPuzzle old) {
    for (Entry<Coord, Cell> entry : old._cells.entrySet()) {
      _cells.put(entry.getKey(), Cell.copyOf(entry.getValue()));
    }
    _constraints.putAll(old._constraints);
    _restrictions.addAll(old._restrictions);
    _inits = old._inits;
  }

  @Override
  public Collection<Coord> getAllCoords() {
    return _cells.keySet();
  }

  private MapBackedPuzzle(final int inits) {
    _inits = inits;
  }

  public static MapBackedPuzzle forPossiblesSize(final int possiblesSize) {
    int inits = (1 << possiblesSize) - 1;
    return new MapBackedPuzzle(inits);
  }

  @Override
  public final Collection<Cell> getAllCells() {
    return _cells.values();
  }

  public int getInits() {
    return _inits;
  }

  @Override
  public final Cell getCell(Coord coord) {
    return _cells.get(coord);
  }

  @Override
  public Puzzle deepCopy() {
    return new MapBackedPuzzle(this);
  }

  @Override
  public Set<Restriction> getAllRestrictions() {
    return _restrictions;
  }

  @Override
  public Set<Restriction> getRestrictions(final Coord coord) {
    return Collections.unmodifiableSet(_constraints.get(coord));
  }

  @Override
  public boolean isSolved() {
    for (Cell cell : getAllCells()) {
      if (Integer.bitCount(cell.getCurrentValues()) > 1) {
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean isUnsolveable() {
    for (Cell cell : getAllCells()) {
      if (cell.isUnsolvable()) {
        return true;
      }
    }
    return false;
  }

  @Override
  public int completeness() {
    int completeness = 0;
    for (Cell cell : getAllCells()) {
      completeness += Integer.bitCount(cell.getCurrentValues());
    }
    return completeness;
  }

  public void addConstraint(Restriction restriction) {
    _restrictions.add(restriction);
    for (Coord cellCoord : restriction.getCells()) {
      if (!_constraints.containsKey(cellCoord)) {
        _constraints.put(cellCoord, new LinkedHashSet<Restriction>());
      }
      _constraints.get(cellCoord).add(restriction);
    }
  }

  public void addCells(List<List<Integer>> givenValues) {
    for (int rowIndex = 0; rowIndex < givenValues.size(); rowIndex++) {
      final List<Integer> row = givenValues.get(rowIndex);
      for (int colIndex = 0; colIndex < row.size(); colIndex++) {
        Integer value = givenValues.get(rowIndex).get(colIndex);
        Coord coord = new Coord(rowIndex + 1, colIndex + 1);
        _cells.put(coord, value == null ? Cell.unknown(_inits, coord) : Cell.given(value, coord));
      }
    }
  }


}
