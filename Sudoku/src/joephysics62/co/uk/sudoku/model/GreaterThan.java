package joephysics62.co.uk.sudoku.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

public class GreaterThan<T extends Comparable<T>> implements Restriction<T> {

  private final Coord _left;
  private final Coord _right;

  public GreaterThan(final Coord left, final Coord right) {
    _left = left;
    _right = right;
  }

  @Override
  public Set<Cell<T>> eliminateValues(Puzzle<T> puzzle) {
    Set<Cell<T>> changed = new LinkedHashSet<>();
    Cell<T> leftCell = puzzle.getCell(_left);
    Cell<T> rightCell = puzzle.getCell(_right);
    Set<T> leftValues = leftCell.getCurrentValues();
    Set<T> rightValues = rightCell.getCurrentValues();
    T maxLeft = Collections.max(leftValues);
    T minRight = Collections.min(rightValues);
    for (T rightVal : rightValues) {
      if (rightVal.compareTo(maxLeft) >= 0) {
        // remove right Value
        // if changed changed.add(rightCell)
      }
    }
    for (T leftVal : leftValues) {
      if (leftVal.compareTo(minRight) <= 0) {
        // remove left value.
        // if changed changed.add(leftCell)
      }
    }
    return changed;
  }

  @Override
  public Set<Coord> getCells() {
    return Collections.unmodifiableSet(new TreeSet<>(Arrays.asList(_left, _right)));
  }

}
