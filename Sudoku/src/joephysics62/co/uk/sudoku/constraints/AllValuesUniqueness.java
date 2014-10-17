package joephysics62.co.uk.sudoku.constraints;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import joephysics62.co.uk.grid.Coord;
import joephysics62.co.uk.grid.Grid;
import joephysics62.co.uk.sudoku.model.Cell;

public class AllValuesUniqueness extends Uniqueness {

  private AllValuesUniqueness(List<Coord> group) {
    super(group);
  }

  @Override
  public boolean isSatisfied(Grid<Integer> grid) {
    return true;
  }

  public static AllValuesUniqueness of(Collection<Coord> group) {
    return new AllValuesUniqueness(new ArrayList<>(group));
  }

  @Override
  public boolean eliminateValues(Grid<Integer> cellGrid) {
    boolean eliminationHadEffect = false;
    eliminationHadEffect |= applyUniquenessToKnownValue(cellGrid);
    eliminationHadEffect |= applyOnlyPossibleCellElimination(cellGrid);
    eliminationHadEffect |= doABElimination(cellGrid);
    return eliminationHadEffect;
  }

  private boolean applyOnlyPossibleCellElimination(Grid<Integer> cellGrid) {
    boolean hadEffect = false;
    for (Coord coord : getCells()) {
      int cellValue = cellGrid.get(coord);
      if (!Cell.isSolved(cellValue)) {
        for (Coord coordInner : getCells()) {
          int cellValueInner = cellGrid.get(coordInner);
          if (!coordInner.equals(coord)) {
            cellValue = Cell.remove(cellValue, cellValueInner);
          }
        }
      }
      if (Cell.isSolved(cellValue)) {
        cellGrid.set(cellValue, coord);
        hadEffect = true;
      }
    }
    return hadEffect;
  }

  private boolean applyUniquenessToKnownValue(Grid<Integer> cellGrid) {
    boolean changed = false;
    for (Coord coord : getCells()) {
      final int value = cellGrid.get(coord);
      if (Cell.isSolved(value)) {
        changed |= !forSolvedCell(cellGrid, coord);
      }
    }
    return changed;
  }

  private boolean doABElimination(Grid<Integer> cellGrid) {
    boolean changed = false;
    for (Coord coord : getCells()) {
      changed |= tryABElim(cellGrid, coord);
    }
    return changed;
  }

  private boolean tryABElim(Grid<Integer> cellGrid, Coord coord) {
    final int value = cellGrid.get(coord);
    if (Integer.bitCount(value) == 2) {
      for (Coord innerCoord : getCells()) {
        if (!innerCoord.equals(coord)) {
          int innerValue = cellGrid.get(innerCoord);
          if (value == innerValue) {
            boolean hasEffect = false;
            for (Coord innerInnerCoord : getCells()) {
              if (!innerInnerCoord.equals(innerCoord) && !innerInnerCoord.equals(coord)) {
                int innerInnerValue = cellGrid.get(innerInnerCoord);
                int newValue = Cell.remove(innerInnerValue, value);
                if (newValue != innerInnerValue) {
                  cellGrid.set(newValue, innerInnerCoord);
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
