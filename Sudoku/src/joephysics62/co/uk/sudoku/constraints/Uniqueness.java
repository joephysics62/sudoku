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

public class Uniqueness implements Restriction {

  private static final List<Integer> AB_ELIMINATION_SIZES = Arrays.asList(2, 3);

  private final Set<Coord> _group;

  private Uniqueness(Set<Coord> group) {
    _group = Collections.unmodifiableSet(group);
  }

  public static Uniqueness of(Collection<Coord> group) {
    return new Uniqueness(new TreeSet<>(group));
  }

  @Override
  public Set<Coord> getCells() {
    return _group;
  }

  @Override
  public boolean eliminateValues(CellGrid cellGrid) {
    boolean eliminationHadEffect = false;
    //eliminationHadEffect |= applyOnlyPossibleCellElimination(cellGrid);
    eliminationHadEffect |= applyUniquenessToKnownValue(cellGrid);
    eliminationHadEffect |= doABElimination(cellGrid);
    return eliminationHadEffect;
  }

  @Override
  public Set<Coord> forSolvedCell(CellGrid cellGrid, Cell solvedCell) {
    return eliminateSolvedValue(solvedCell, cellGrid);
  }

  private boolean applyOnlyPossibleCellElimination(CellGrid cellGrid) {
    boolean hadEffect = false;
    for (Coord coord : _group) {
      Cell cell = cellGrid.getCell(coord);
      if (!cell.isSolved()) {
        continue;
      }
      int cellValues = cell.getCurrentValues();
      for (Coord coordInner : _group) {
        if (!coordInner.equals(coord)) {
          Cell cellInner = cellGrid.getCell(coordInner);
          cellValues = cellValues & (~cellInner.getCurrentValues());
        }
      }
      if (Cell.isPower2(cellValues)) {
        cell.fixValue(cellValues);
        hadEffect = true;
      }
    }
    return hadEffect;
  }

  private boolean applyUniquenessToKnownValue(CellGrid cellGrid) {
    boolean changed = false;
    for (Coord coord : _group) {
      final Cell cell = cellGrid.getCell(coord);
      if (cell.isSolved()) {
        changed |= !eliminateSolvedValue(cell, cellGrid).isEmpty();
      }
    }
    return changed;
  }

  private boolean doABElimination(CellGrid cellGrid) {
    Map<Integer, Set<Cell>> abEliminationMap = new LinkedHashMap<>();
    boolean changed = false;
    for (Coord coord : _group) {
      final Cell cell = cellGrid.getCell(coord);
      if (!abEliminationMap.containsKey(cell.getCurrentValues())) {
        abEliminationMap.put(cell.getCurrentValues(), new LinkedHashSet<Cell>());
      }
      abEliminationMap.get(cell.getCurrentValues()).add(cell);
    }
    for (Entry<Integer, Set<Cell>> entry : abEliminationMap.entrySet()) {
      Set<Cell> abCells = entry.getValue();
      Integer abValue = entry.getKey();
      if (AB_ELIMINATION_SIZES.contains(abCells.size()) && Integer.bitCount(abValue) == abCells.size()) {
        for (Coord coord : _group) {
          final Cell cell = cellGrid.getCell(coord);
          if (!abCells.contains(cell) && cell.remove(abValue)) {
            changed = true;
          }
        }
      }
    }
    return changed;
  }

  private Set<Coord> eliminateSolvedValue(Cell cell, CellGrid cellGrid) {
    final Set<Coord> forElimination = new LinkedHashSet<>();
    for (Coord otherCoord : _group) {
      final Cell otherCell = cellGrid.getCell(otherCoord);
      if (!otherCell.getCoord().equals(cell.getCoord())) {
        final int values = cell.getCurrentValues();
        if (Cell.isPower2(values) && !otherCell.isSolved()) {
          otherCell.remove(values);
          if (otherCell.canApplyElimination()) {
            forElimination.add(otherCoord);
          }
        }
      }
    }
    return forElimination;
  }

}
