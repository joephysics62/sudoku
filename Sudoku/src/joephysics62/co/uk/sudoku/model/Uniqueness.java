package joephysics62.co.uk.sudoku.model;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class Uniqueness<T> implements Restriction<T> {

  private final Set<Cell<T>> _group;

  private Uniqueness(Set<Cell<T>> group) {
    _group = Collections.unmodifiableSet(group);
  }

  public static <T> Uniqueness<T> of(Collection<Cell<T>> group) {
    return new Uniqueness<T>(new LinkedHashSet<>(group));
  }

  @Override
  public boolean satisfied() {
    final Set<T> solvedValues = new LinkedHashSet<>();
    for (Cell<T> cell : _group) {
      if (cell.isSolved()) {
        if (!solvedValues.add(cell.getValue())) {
          return false;
        }
      }
    }
    return true;
  }

  @Override
  public Set<Cell<T>> getCells() {
    return _group;
  }

  @Override
  public Set<Cell<T>> eliminateValues() {
    final Set<Cell<T>> changedCells = new LinkedHashSet<>();
    for (Cell<T> cell : _group) {
      if (cell.isSolved()) {
        eliminateSolvedValue(changedCells, cell);
      }
    }
    return changedCells;
  }

  private void eliminateSolvedValue(final Set<Cell<T>> changedCells, Cell<T> cell) {
    for (Cell<T> cellInner : _group) {
      if (!cellInner.getIdentifier().equals(cell.getIdentifier())) {
        if (cellInner.getCurrentValues().remove(cell.getValue())) {
          changedCells.add(cellInner);
        }
      }
    }
  }

}
