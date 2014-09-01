package joephysics62.co.uk.sudoku.model;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public abstract class MapBackedPuzzle<T> implements Puzzle<T> {
  private final Map<Coord, Set<Restriction<T>>> _constraints = new LinkedHashMap<>();
  private final Map<Coord, Cell<T>> _cells = new LinkedHashMap<>();

  private MapBackedPuzzle(MapBackedPuzzle<T> old) {
    for (Entry<Coord, Cell<T>> entry : old._cells.entrySet()) {
      _cells.put(entry.getKey(), new Cell<T>(entry.getValue()));
    }
    _constraints.putAll(old._constraints);
  }

  public MapBackedPuzzle() {
    // normal one
  }

  protected abstract Set<T> getInits();

  @Override
  public final Set<Cell<T>> getAllCells() {
    return new LinkedHashSet<>(_cells.values());
  }

  @Override
  public final Cell<T> getCell(Coord coord) {
    return _cells.get(coord);
  }

  @Override
  public Puzzle<T> deepCopy() {
    return new MapBackedPuzzle<T>(this) {

      @Override public void loadValues(File input) throws IOException { throw new UnsupportedOperationException(); }
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

  protected void addConstraint(Restriction<T> restriction) {
    for (Coord cellCoord : restriction.getCells()) {
      if (!_constraints.containsKey(cellCoord)) {
        _constraints.put(cellCoord, new LinkedHashSet<Restriction<T>>());
      }
      _constraints.get(cellCoord).add(restriction);
    }
  }

  protected void addCells(List<List<Cell<T>>> wholePuzzle) {
    for (List<Cell<T>> row : wholePuzzle) {
      for (Cell<T> cell : row) {
        _cells.put(cell.getIdentifier(), cell);
      }
    }
  }


  @Override
  public void write(PrintStream out) {
    int maxRow = 0;
    int maxCol = 0;
    for (Coord coord : _constraints.keySet()) {
      maxRow = Math.max(coord.getRow(), maxRow);
      maxCol = Math.max(coord.getCol(), maxCol);
    }
    Object[][] array = new Object[maxRow][maxCol];
    for (Coord coord : _constraints.keySet()) {
      final Cell<T> cell = _cells.get(coord);
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
