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
  public Set<Coord> forSolvedCell(CellGrid cellGrid, int solvedCell) {
    return eliminateSolvedValue(solvedCell, cellGrid);
  }

//  private boolean applyOnlyPossibleCellElimination(CellGrid cellGrid) {
//    boolean hadEffect = false;
//    for (Coord coord : _group) {
//      Cell cell = cellGrid.getCell(coord);
//      if (!cell.isSolved()) {
//        continue;
//      }
//      int cellValues = cell.getCurrentValues();
//      for (Coord coordInner : _group) {
//        if (!coordInner.equals(coord)) {
//          Cell cellInner = cellGrid.getCell(coordInner);
//          cellValues = cellValues & (~cellInner.getCurrentValues());
//        }
//      }
//      if (Cell.isPower2(cellValues)) {
//        cell.fixValue(cellValues);
//        hadEffect = true;
//      }
//    }
//    return hadEffect;
//  }

  private boolean applyUniquenessToKnownValue(CellGrid cellGrid) {
    boolean changed = false;
    for (Coord coord : _group) {
      final int value = cellGrid.getCellValue(coord);
      if (Cell.isSolved(value)) {
        changed |= !eliminateSolvedValue(value, cellGrid).isEmpty();
      }
    }
    return changed;
  }

  private boolean doABElimination(CellGrid cellGrid) {
    Map<Integer, Set<Coord>> abEliminationMap = new LinkedHashMap<>();
    boolean changed = false;
    for (Coord coord : _group) {
      final int cell = cellGrid.getCellValue(coord);
      if (!abEliminationMap.containsKey(cell)) {
        abEliminationMap.put(cell, new LinkedHashSet<Coord>());
      }
      abEliminationMap.get(cell).add(coord);
    }
    for (Entry<Integer, Set<Coord>> entry : abEliminationMap.entrySet()) {
      Set<Coord> abCells = entry.getValue();
      Integer abValue = entry.getKey();
      if (AB_ELIMINATION_SIZES.contains(abCells.size()) && Integer.bitCount(abValue) == abCells.size()) {
        for (Coord coord : _group) {
          if (!abCells.contains(coord)) {
            final int cellValue = cellGrid.getCellValue(coord);
            int newValue = Cell.remove(cellValue, abValue);
            if (newValue != cellValue) {
              cellGrid.setCellValue(newValue, coord);
              changed = true;
            }
          }
        }
      }
    }
    return changed;
  }

  private Set<Coord> eliminateSolvedValue(int solvedValue, CellGrid cellGrid) {
    assert Cell.isSolved(solvedValue);
    final Set<Coord> forElimination = new LinkedHashSet<>();
    boolean seenSame = false;
    for (Coord otherCoord : _group) {
      final int otherValue = cellGrid.getCellValue(otherCoord);
      if (!seenSame && otherValue == solvedValue) {
        seenSame = true;
      }
      else {
        int newValue = Cell.remove(otherValue, solvedValue);
        if (newValue != otherValue) {
          cellGrid.setCellValue(newValue, otherCoord);
          forElimination.add(otherCoord);
        }
      }
    }
    return forElimination;
  }

}
