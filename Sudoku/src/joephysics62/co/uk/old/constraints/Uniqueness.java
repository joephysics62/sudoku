package joephysics62.co.uk.old.constraints;

import java.util.Collections;
import java.util.List;

import joephysics62.co.uk.old.grid.Coord;
import joephysics62.co.uk.old.grid.Grid;
import joephysics62.co.uk.old.sudoku.model.Cell;

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
  public boolean forKnownValue(Grid<Integer> cellGrid, Coord coord) {
    return eliminateSolvedValue(cellGrid, coord);
  }

  private boolean eliminateSolvedValue(Grid<Integer> cellGrid, Coord coord) {
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
