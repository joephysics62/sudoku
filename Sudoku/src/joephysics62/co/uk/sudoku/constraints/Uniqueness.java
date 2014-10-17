package joephysics62.co.uk.sudoku.constraints;

import java.util.Collections;
import java.util.List;

import joephysics62.co.uk.grid.Coord;
import joephysics62.co.uk.sudoku.model.Cell;
import joephysics62.co.uk.sudoku.model.PuzzleGrid;

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
  public boolean forSolvedCell(PuzzleGrid cellGrid, Coord coord) {
    return eliminateSolvedValue(coord, cellGrid);
  }

  private boolean eliminateSolvedValue(Coord coord, PuzzleGrid cellGrid) {
    int solvedValue = cellGrid.get(coord);
    if (!Cell.isSolved(solvedValue)) {
      return false;
    }
    boolean hasChanged = false;
    boolean seenSame = false;
    for (Coord otherCoord : _group) {
      final int otherValue = cellGrid.get(otherCoord);
      if (!seenSame && otherValue == solvedValue) {
        seenSame = true;
      }
      else {
        int newValue = Cell.remove(otherValue, solvedValue);
        if (newValue != otherValue) {
          cellGrid.set(newValue, otherCoord);
          hasChanged = true;
        }
      }
    }
    return hasChanged;
  }
}
