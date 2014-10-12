package joephysics62.co.uk.sudoku.constraints;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import joephysics62.co.uk.sudoku.model.Cell;
import joephysics62.co.uk.sudoku.model.CellGrid;
import joephysics62.co.uk.sudoku.model.Coord;

public class AllValuesUniqueness extends Uniqueness {

  private AllValuesUniqueness(List<Coord> group) {
    super(group);
  }

  @Override
  public boolean isSatisfied(CellGrid grid) {
    return true;
  }

  public static AllValuesUniqueness of(Collection<Coord> group) {
    return new AllValuesUniqueness(new ArrayList<>(group));
  }

  @Override
  public boolean eliminateValues(CellGrid cellGrid) {
    boolean eliminationHadEffect = false;
    eliminationHadEffect |= applyUniquenessToKnownValue(cellGrid);
    eliminationHadEffect |= applyOnlyPossibleCellElimination(cellGrid);
    eliminationHadEffect |= doABElimination(cellGrid);
    return eliminationHadEffect;
  }

  private boolean applyOnlyPossibleCellElimination(CellGrid cellGrid) {
    boolean hadEffect = false;
    for (Coord coord : getCells()) {
      int cellValue = cellGrid.getCellValue(coord);
      if (!Cell.isSolved(cellValue)) {
        for (Coord coordInner : getCells()) {
          int cellValueInner = cellGrid.getCellValue(coordInner);
          if (!coordInner.equals(coord)) {
            cellValue = Cell.remove(cellValue, cellValueInner);
          }
        }
      }
      if (Cell.isSolved(cellValue)) {
        cellGrid.setCellValue(cellValue, coord);
        hadEffect = true;
      }
    }
    return hadEffect;
  }

  private boolean applyUniquenessToKnownValue(CellGrid cellGrid) {
    boolean changed = false;
    for (Coord coord : getCells()) {
      final int value = cellGrid.getCellValue(coord);
      if (Cell.isSolved(value)) {
        changed |= !forSolvedCell(cellGrid, coord);
      }
    }
    return changed;
  }

  private boolean doABElimination(CellGrid cellGrid) {
    boolean changed = false;
    for (Coord coord : getCells()) {
      changed |= tryABElim(cellGrid, coord);
    }
    return changed;
  }

  private boolean tryABElim(CellGrid cellGrid, Coord coord) {
    final int value = cellGrid.getCellValue(coord);
    if (Integer.bitCount(value) == 2) {
      for (Coord innerCoord : getCells()) {
        if (!innerCoord.equals(coord)) {
          int innerValue = cellGrid.getCellValue(innerCoord);
          if (value == innerValue) {
            boolean hasEffect = false;
            for (Coord innerInnerCoord : getCells()) {
              if (!innerInnerCoord.equals(innerCoord) && !innerInnerCoord.equals(coord)) {
                int innerInnerValue = cellGrid.getCellValue(innerInnerCoord);
                int newValue = Cell.remove(innerInnerValue, value);
                if (newValue != innerInnerValue) {
                  cellGrid.setCellValue(newValue, innerInnerCoord);
                  hasEffect = true;
                }
              }
            }
            return hasEffect;
          }
        }
      }
    }
    return false;
  }



}
