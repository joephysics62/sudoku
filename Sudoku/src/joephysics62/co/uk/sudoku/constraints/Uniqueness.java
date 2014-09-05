package joephysics62.co.uk.sudoku.constraints;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import joephysics62.co.uk.sudoku.model.Cell;
import joephysics62.co.uk.sudoku.model.CellGrid;
import joephysics62.co.uk.sudoku.model.Coord;

public class Uniqueness<T extends Comparable<T>> implements Restriction<T> {

  private static final List<Integer> AB_ELIMINATION_SIZES = Arrays.asList(2, 3);

  private final Set<Coord> _group;

  private Uniqueness(Set<Coord> group) {
    _group = Collections.unmodifiableSet(group);
  }

  public static <T extends Comparable<T>> Uniqueness<T> of(Collection<Coord> group) {
    return new Uniqueness<T>(new TreeSet<>(group));
  }

  @Override
  public Set<Coord> getCells() {
    return _group;
  }

  @Override
  public boolean eliminateValues(CellGrid<T> cellGrid) {
    boolean eliminationHadEffect = false;
    //eliminationHadEffect |= applyOnlyPossibleCellElimination(cellGrid);
    eliminationHadEffect |= applyUniquenessToKnownValue(cellGrid);
    eliminationHadEffect |= doABElimination(cellGrid);
    return eliminationHadEffect;
  }

  @Override
  public Set<Coord> forSolvedCell(CellGrid<T> cellGrid, Cell<T> solvedCell) {
    return eliminateSolvedValue(solvedCell, cellGrid);
  }

  private boolean applyOnlyPossibleCellElimination(CellGrid<T> cellGrid) {
    boolean hadEffect = false;
    for (Coord coord : _group) {
      Cell<T> cell = cellGrid.getCell(coord);
      if (cell.getCurrentValues().size() == 1) {
        continue;
      }
      final Set<T> cellValues = new LinkedHashSet<>(cell.getCurrentValues());
      for (Coord coordInner : _group) {
        if (!coordInner.equals(coord)) {
          Cell<T> cellInner = cellGrid.getCell(coordInner);
          cellValues.removeAll(cellInner.getCurrentValues());
        }
      }
      if (cellValues.size() == 1) {
        cell.fixValue(cellValues.iterator().next());
        hadEffect = true;
      }
    }
    return hadEffect;
  }

  private boolean applyUniquenessToKnownValue(CellGrid<T> cellGrid) {
    boolean changed = false;
    for (Coord coord : _group) {
      final Cell<T> cell = cellGrid.getCell(coord);
      if (cell.isSolved()) {
        changed |= !eliminateSolvedValue(cell, cellGrid).isEmpty();
      }
    }
    return changed;
  }

  private boolean doABElimination(CellGrid<T> cellGrid) {
    Map<Set<T>, Set<Cell<T>>> abEliminationMap = new LinkedHashMap<>();
    boolean changed = false;
    for (Coord coord : _group) {
      final Cell<T> cell = cellGrid.getCell(coord);
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
          final Cell<T> cell = cellGrid.getCell(coord);
          if (!abCells.contains(cell) && cell.removeAll(abValue)) {
            changed = true;
          }
        }
      }
    }
    return changed;
  }

  private Set<Coord> eliminateSolvedValue(Cell<T> cell, CellGrid<T> cellGrid) {
    final Set<Coord> forElimination = new LinkedHashSet<>();
    for (Coord otherCoord : _group) {
      final Cell<T> otherCell = cellGrid.getCell(otherCoord);
      if (!otherCell.getCoord().equals(cell.getCoord())) {
        T value = cell.getValue();
        if (null != value && !otherCell.isSolved()) {
          otherCell.remove(value);
          if (otherCell.canApplyElimination()) {
            forElimination.add(otherCoord);
          }
        }
      }
    }
    return forElimination;
  }

}
