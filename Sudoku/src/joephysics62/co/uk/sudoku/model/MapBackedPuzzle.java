package joephysics62.co.uk.sudoku.model;

import java.io.PrintStream;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public abstract class MapBackedPuzzle<T> implements Puzzle<T> {
  private final Map<Cell<T>, Set<Restriction<T>>> _constraints = new LinkedHashMap<>();

  protected abstract Set<T> getInits();

  @Override
  public Set<Cell<T>> getAllCells() {
    return _constraints.keySet();
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
  public Set<Restriction<T>> getRestrictions(final Cell<T> cell) {
    return Collections.unmodifiableSet(_constraints.get(cell));
  }

  @Override
  public boolean isSolved() {
    Set<Cell<T>> allCells = getAllCells();
    for (Cell<T> cell : allCells) {
      if (!cell.isSolved()) {
        return false;
      }
    }
    return true;
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
    for (Cell<T> cell : restriction.getCells()) {
      if (!_constraints.containsKey(cell)) {
        _constraints.put(cell, new LinkedHashSet<Restriction<T>>());
      }
      _constraints.get(cell).add(restriction);
    }
  }

  @Override
  public void write(PrintStream out) {
    int maxRow = 0;
    int maxCol = 0;
    for (Cell<T> cell : _constraints.keySet()) {
      maxRow = Math.max(cell.getIdentifier().getRow(), maxRow);
      maxCol = Math.max(cell.getIdentifier().getCol(), maxCol);
    }
    Object[][] array = new Object[maxRow][maxCol];
    for (Cell<T> cell : _constraints.keySet()) {
      Coord coord = cell.getIdentifier();
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
