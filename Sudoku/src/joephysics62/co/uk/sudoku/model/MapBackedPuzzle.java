package joephysics62.co.uk.sudoku.model;

import java.io.PrintStream;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public class MapBackedPuzzle<T extends Comparable<T>> implements Puzzle<T> {
  private final Map<Coord, Set<Restriction<T>>> _constraints = new TreeMap<>();
  private final Map<Coord, Cell<T>> _cells = new TreeMap<>();
  private final Set<T> _inits;

  private MapBackedPuzzle(MapBackedPuzzle<T> old) {
    for (Entry<Coord, Cell<T>> entry : old._cells.entrySet()) {
      _cells.put(entry.getKey(), Cell.copyOf(entry.getValue()));
    }
    _constraints.putAll(old._constraints);
    _inits = old._inits;
  }

  protected MapBackedPuzzle(Collection<T> inits) {
    _inits = Collections.unmodifiableSet(new LinkedHashSet<>(inits));
  }

  public static <T extends Comparable<T>> MapBackedPuzzle<T> forInits(List<T> inits) {
    return new MapBackedPuzzle<>(inits);
  }

  @Override
  public final Set<Cell<T>> getAllCells() {
    return new LinkedHashSet<>(_cells.values());
  }

  protected Set<T> getInits() {
    return _inits;
  }

  @Override
  public final Cell<T> getCell(Coord coord) {
    return _cells.get(coord);
  }

  @Override
  public Puzzle<T> deepCopy() {
    return new MapBackedPuzzle<T>(this) {
      @Override protected Set<T> getInits() { throw new UnsupportedOperationException(); }
    };
  }

  @Override
  public Collection<Restriction<T>> getAllRestrictions() {
    final Set<Restriction<T>> out = new LinkedHashSet<>();
    for (Set<Restriction<T>> set : _constraints.values()) {
      out.addAll(set);
    }
    return Collections.unmodifiableSet(out);
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


  @Override
  public void write(PrintStream out) {
    int maxRow = 0;
    int maxCol = 0;
    for (Coord coord : _cells.keySet()) {
      maxRow = Math.max(coord.getRow(), maxRow);
      maxCol = Math.max(coord.getCol(), maxCol);
    }
    Object[][] array = new Object[maxRow][maxCol];
    for (Entry<Coord, Cell<T>> entry : _cells.entrySet()) {
      final Cell<T> cell = entry.getValue();
      final Coord coord = entry.getKey();
      if (null == cell) {
        throw new RuntimeException("No cell at '" + coord + "'");
      }
      array[coord.getRow() - 1][coord.getCol() - 1] = cell.getCurrentValues().size() == 1 ? cell.getValue() : null;
    }
    for (int i = 0; i < maxRow; i++) {
      for (int j = 0; j < maxCol; j++) {
        if (j == 0) {
          out.print("|");
        }
        Object value = array[i][j];
        out.print(value == null ? "-|" : value + "|");
      }
      out.println("");
    }
  }

}
