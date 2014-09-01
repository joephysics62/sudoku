package joephysics62.co.uk.sudoku.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Uniqueness<T extends Comparable<T>> implements Restriction<T> {

  private static final List<Integer> AB_ELIMINATION_SIZES = Arrays.asList(2, 3);

  private final Set<Coord> _group;

  private Uniqueness(Set<Coord> group) {
    _group = Collections.unmodifiableSet(group);
  }

  public static <T extends Comparable<T>> Uniqueness<T> of(Collection<Coord> group) {
    return new Uniqueness<T>(new LinkedHashSet<>(group));
  }

  @Override
  public boolean satisfied(Puzzle<T> puzzle) {
    final Set<T> solvedValues = new LinkedHashSet<>();
    for (Coord coord : _group) {
      final Cell<T> cell = puzzle.getCell(coord);
      if (cell.isSolved()) {
        if (!solvedValues.add(cell.getValue())) {
          return false;
        }
      }
    }
    return true;
  }

  @Override
  public Set<Coord> getCells() {
    return _group;
  }

  @Override
  public Set<Cell<T>> eliminateValues(Puzzle<T> puzzle) {
    final Set<Cell<T>> changedCells = new LinkedHashSet<>();
    changedCells.addAll(directElimination(puzzle));
    changedCells.addAll(doABElimination(puzzle));
    return changedCells;
  }

  private Set<Cell<T>> directElimination(Puzzle<T> puzzle) {
    final Set<Cell<T>> changedCells = new LinkedHashSet<>();
    for (Coord coord : _group) {
      final Cell<T> cell = puzzle.getCell(coord);
      if (cell.isSolved()) {
        changedCells.addAll(eliminateSolvedValue(cell, puzzle));
      }
    }
    return changedCells;
  }

  private Set<Cell<T>> doABElimination(Puzzle<T> puzzle) {
    final Set<Cell<T>> changedCells = new LinkedHashSet<>();
    Map<Set<T>, Set<Cell<T>>> abEliminationMap = new LinkedHashMap<>();
    for (Coord coord : _group) {
      final Cell<T> cell = puzzle.getCell(coord);
      if (!abEliminationMap.containsKey(cell.getCurrentValues())) {
        abEliminationMap.put(cell.getCurrentValues(), new LinkedHashSet<Cell<T>>());
      }
      abEliminationMap.get(cell.getCurrentValues()).add(cell);
    }
    for (Entry<Set<T>, Set<Cell<T>>> entry : abEliminationMap.entrySet()) {
      Set<Cell<T>> abCells = entry.getValue();
      Set<T> abValue = entry.getKey();
      if (AB_ELIMINATION_SIZES.contains(abCells.size()) && abValue.size() == abCells.size()) {
        for (Coord coord : _group) {
          final Cell<T> cell = puzzle.getCell(coord);
          if (!abCells.contains(cell) && cell.removeAll(abValue)) {
            changedCells.add(cell);
          }
        }
      }
    }
    return changedCells;
  }

  private Set<Cell<T>> eliminateSolvedValue(Cell<T> cell, Puzzle<T> puzzle) {
    final Set<Cell<T>> changed = new LinkedHashSet<>();
    for (Coord innerCoord : _group) {
      final Cell<T> cellInner = puzzle.getCell(innerCoord);
      if (!cellInner.getIdentifier().equals(cell.getIdentifier())) {
        T value = cell.getValue();
        if (null != value) {
          if (cellInner.remove(value)) {
            changed.add(cellInner);
          }
        }
      }
    }
    return changed;
  }

}
