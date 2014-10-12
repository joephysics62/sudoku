package joephysics62.co.uk.sudoku.constraints;

import java.util.Collections;
import java.util.List;

import joephysics62.co.uk.sudoku.model.Cell;
import joephysics62.co.uk.sudoku.model.CellGrid;
import joephysics62.co.uk.sudoku.model.Coord;

public abstract class Uniqueness implements Constraint {

  private final List<Coord> _group;

  protected Uniqueness(List<Coord> group) {
    _group = Collections.unmodifiableList(group);
  }

  @Override
  public final List<Coord> getCells() {
    return _group;
  }

  @Override
  public boolean forSolvedCell(CellGrid cellGrid, Coord coord) {
    return eliminateSolvedValue(coord, cellGrid);
  }

  private boolean eliminateSolvedValue(Coord coord, CellGrid cellGrid) {
    int solvedValue = cellGrid.getCellValue(coord);
    if (!Cell.isSolved(solvedValue)) {
      return false;
    }
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
