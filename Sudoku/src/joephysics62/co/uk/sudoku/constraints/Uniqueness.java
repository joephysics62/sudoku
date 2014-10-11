package joephysics62.co.uk.sudoku.constraints;

import java.util.List;

import joephysics62.co.uk.sudoku.model.Cell;
import joephysics62.co.uk.sudoku.model.CellGrid;
import joephysics62.co.uk.sudoku.model.Coord;

public abstract class Uniqueness implements Constraint {

  private final List<Coord> _group;

  protected Uniqueness(List<Coord> group) {
    _group = group;
  }

  @Override
  public final List<Coord> getCells() {
    return _group;
  }

  @Override
  public final boolean forSolvedCell(CellGrid cellGrid, int solvedCell) {
    return eliminateSolvedValue(solvedCell, cellGrid);
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
