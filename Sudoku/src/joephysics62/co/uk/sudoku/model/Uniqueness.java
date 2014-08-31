package joephysics62.co.uk.sudoku.model;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
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
    changedCells.addAll(directElimination());
    changedCells.addAll(doABElimination());
    return changedCells;
  }

  private Set<Cell<T>> directElimination() {
    final Set<Cell<T>> changedCells = new LinkedHashSet<>();
    for (Cell<T> cell : _group) {
      if (cell.isSolved()) {
        changedCells.addAll(eliminateSolvedValue(cell));
      }
    }
    return changedCells;
  }

  private Set<Cell<T>> doABElimination() {
    final Set<Cell<T>> changedCells = new LinkedHashSet<>();
    Map<Set<T>, Set<Cell<T>>> abEliminationMap = new LinkedHashMap<>();
    for (Cell<T> cell : _group) {
      if (!abEliminationMap.containsKey(cell.getCurrentValues())) {
        abEliminationMap.put(cell.getCurrentValues(), new LinkedHashSet<Cell<T>>());
      }
      abEliminationMap.get(cell.getCurrentValues()).add(cell);
    }
    for (Entry<Set<T>, Set<Cell<T>>> entry : abEliminationMap.entrySet()) {
      Set<Cell<T>> abCells = entry.getValue();
      Set<T> abValue = entry.getKey();
      if (abCells.size() == 2 && abValue.size() == 2) {
        for (Cell<T> cell : _group) {
          if (!abCells.contains(cell) && cell.getCurrentValues().removeAll(abValue)) {
            changedCells.add(cell);
          }
        }
      }
    }
    return changedCells;
  }

  private Set<Cell<T>> eliminateSolvedValue(Cell<T> cell) {
    final Set<Cell<T>> changed = new LinkedHashSet<>();
    for (Cell<T> cellInner : _group) {
      if (!cellInner.getIdentifier().equals(cell.getIdentifier())) {
        if (cellInner.getCurrentValues().remove(cell.getValue())) {
          changed.add(cellInner);
        }
      }
    }
    return changed;
  }

}
