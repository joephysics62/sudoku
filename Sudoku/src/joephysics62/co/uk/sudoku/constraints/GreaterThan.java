package joephysics62.co.uk.sudoku.constraints;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import joephysics62.co.uk.sudoku.model.Cell;
import joephysics62.co.uk.sudoku.model.CellGrid;
import joephysics62.co.uk.sudoku.model.Coord;

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
  public Set<Coord> forSolvedCell(CellGrid cellGrid, int solvedCell) {
    return Collections.emptySet();
  }

  @Override
  public boolean eliminateValues(CellGrid cellGrid) {
    int left = cellGrid.getCellValue(_left);
    int right = cellGrid.getCellValue(_right);
    if (left == 0|| right == 0) {
      return false;
    }
    int lowestBitRight = Integer.lowestOneBit(right);
    int highestBitLeft = Integer.highestOneBit(left);

    final int newLeft = Cell.remove(left, 2 * lowestBitRight - 1);
    final int newRight = Cell.remove(right, ~(highestBitLeft - 1));
    cellGrid.setCellValue(newLeft, _left);
    cellGrid.setCellValue(newRight, _right);
    return newLeft != left || newRight != right;
  }

  @Override
  public Set<Coord> getCells() {
    return Collections.unmodifiableSet(new TreeSet<>(Arrays.asList(_left, _right)));
  }

}
