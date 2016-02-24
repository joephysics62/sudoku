package joephysics62.co.uk.old.constraints;

import java.util.Arrays;
import java.util.List;

import joephysics62.co.uk.old.grid.Coord;
import joephysics62.co.uk.old.grid.Grid;
import joephysics62.co.uk.old.sudoku.model.Cell;

public class GreaterThan implements Constraint {

  private final Coord _left;
  private final Coord _right;

  private GreaterThan(final Coord left, final Coord right) {
    _left = left;
    _right = right;
  }

  public static GreaterThan of(final Coord left, final Coord right) {
    return new GreaterThan(left, right);
  }

  @Override
  public boolean forKnownValue(Grid<Integer> cellGrid, Coord solvedCell) {
    return false;
  }

  @Override
  public boolean isSatisfied(Grid<Integer> grid) {
    return true;
  }

  @Override
  public boolean eliminateValues(Grid<Integer> cellGrid) {
    int left = cellGrid.get(_left);
    int right = cellGrid.get(_right);
    if (left == 0|| right == 0) {
      return false;
    }
    int lowestBitRight = Integer.lowestOneBit(right);
    int highestBitLeft = Integer.highestOneBit(left);

    final int newLeft = Cell.remove(left, 2 * lowestBitRight - 1);
    final int newRight = Cell.remove(right, ~(highestBitLeft - 1));
    cellGrid.set(newLeft, _left);
    cellGrid.set(newRight, _right);
    return newLeft != left || newRight != right;
  }

  @Override
  public List<Coord> getCells() {
    return Arrays.asList(_left, _right);
  }

  @Override
  public String toString() {
    return String.format("GreaterThan(%s,%s)", _left, _right);
  }

}
