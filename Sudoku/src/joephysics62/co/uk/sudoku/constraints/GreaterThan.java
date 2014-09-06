package joephysics62.co.uk.sudoku.constraints;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import joephysics62.co.uk.sudoku.model.Cell;
import joephysics62.co.uk.sudoku.model.CellGrid;
import joephysics62.co.uk.sudoku.model.Coord;

public class GreaterThan implements Restriction {

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
  public Set<Coord> forSolvedCell(CellGrid cellGrid, Cell solvedCell) {
    return Collections.emptySet();
  }

  @Override
  public boolean eliminateValues(CellGrid cellGrid) {
    Cell left = cellGrid.getCell(_left);
    Cell right = cellGrid.getCell(_right);
    if (left.isUnsolvable() || right.isUnsolvable()) {
      return false;
    }
    int lowestBitRight = Integer.lowestOneBit(right.getCurrentValues());
    int highestBitLeft = Integer.highestOneBit(left.getCurrentValues());
    boolean leftRem = left.remove(2 * lowestBitRight - 1);
    boolean rightRem = right.remove(~(highestBitLeft - 1));
    return leftRem || rightRem;
  }

  @Override
  public Set<Coord> getCells() {
    return Collections.unmodifiableSet(new TreeSet<>(Arrays.asList(_left, _right)));
  }

}
