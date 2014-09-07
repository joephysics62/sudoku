package joephysics62.co.uk.sudoku.constraints;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import joephysics62.co.uk.sudoku.model.Cell;
import joephysics62.co.uk.sudoku.model.CellGrid;
import joephysics62.co.uk.sudoku.model.Coord;

public class Uniqueness implements Constraint {

  private final List<Coord> _group;

  private Uniqueness(List<Coord> group) {
    _group = group;
  }

  public static Uniqueness of(Collection<Coord> group) {
    return new Uniqueness(new ArrayList<>(group));
  }

  @Override
  public List<Coord> getCells() {
    return _group;
  }

  @Override
  public boolean eliminateValues(CellGrid cellGrid) {
    boolean eliminationHadEffect = false;
    eliminationHadEffect |= applyUniquenessToKnownValue(cellGrid);
    eliminationHadEffect |= applyOnlyPossibleCellElimination(cellGrid);
    eliminationHadEffect |= doABElimination(cellGrid);
    return eliminationHadEffect;
  }

  @Override
  public boolean forSolvedCell(CellGrid cellGrid, int solvedCell) {
    return eliminateSolvedValue(solvedCell, cellGrid);
  }

  private boolean applyOnlyPossibleCellElimination(CellGrid cellGrid) {
    boolean hadEffect = false;
    for (Coord coord : _group) {
      int cellValue = cellGrid.getCellValue(coord);
      if (!Cell.isSolved(cellValue)) {
        for (Coord coordInner : _group) {
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
    for (Coord coord : _group) {
      final int value = cellGrid.getCellValue(coord);
      if (Cell.isSolved(value)) {
        changed |= !eliminateSolvedValue(value, cellGrid);
      }
    }
    return changed;
  }

  private boolean doABElimination(CellGrid cellGrid) {
    boolean changed = false;
    for (Coord coord : _group) {
      changed |= tryABElim(cellGrid, coord);
    }
    return changed;
  }

  private boolean tryABElim(CellGrid cellGrid, Coord coord) {
    final int value = cellGrid.getCellValue(coord);
    if (Integer.bitCount(value) == 2) {
      for (Coord innerCoord : _group) {
        if (!innerCoord.equals(coord)) {
          int innerValue = cellGrid.getCellValue(innerCoord);
          if (value == innerValue) {
            boolean hasEffect = false;
            for (Coord innerInnerCoord : _group) {
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

  private boolean eliminateSolvedValue(int solvedValue, CellGrid cellGrid) {
    assert Cell.isSolved(solvedValue);
    boolean hasChanged = false;
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
          hasChanged = true;
        }
      }
    }
    return hasChanged;
  }

}
