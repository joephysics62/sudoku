package joephysics62.co.uk.sudoku.constraints;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import joephysics62.co.uk.sudoku.model.Cell;
import joephysics62.co.uk.sudoku.model.CellGrid;
import joephysics62.co.uk.sudoku.model.Coord;

public class GreaterThan<T extends Comparable<T>> implements Restriction<T> {

  private final Coord _left;
  private final Coord _right;

  private GreaterThan(final Coord left, final Coord right) {
    _left = left;
    _right = right;
  }

  public static <T extends Comparable<T>> GreaterThan<T> of(final Coord left, final Coord right) {
    return new GreaterThan<T>(left, right);
  }

  @Override
  public Set<Cell<T>> eliminateValues(CellGrid<T> cellGrid) {
    Set<Cell<T>> changed = new LinkedHashSet<>();
    Cell<T> left = cellGrid.getCell(_left);
    Cell<T> right = cellGrid.getCell(_right);
    if (left.isUnsolveable() || right.isUnsolveable()) {
      return Collections.emptySet();
    }
    T maxLeft = Collections.max(left.getCurrentValues());
    T minRight = Collections.min(right.getCurrentValues());

    final List<T> toRemoveRight = new ArrayList<>();
    for (T rightVal : right.getCurrentValues()) {
      if (rightVal.compareTo(maxLeft) >= 0) {
        toRemoveRight.add(rightVal);
      }
    }
    if (!toRemoveRight.isEmpty()) {
      right.removeAll(toRemoveRight);
      changed.add(right);
    }
    final List<T> toRemoveLeft = new ArrayList<>();
    for (T leftVal : left.getCurrentValues()) {
      if (leftVal.compareTo(minRight) <= 0) {
        toRemoveLeft.add(leftVal);
      }
    }
    if (!toRemoveLeft.isEmpty()) {
      left.removeAll(toRemoveLeft);
      changed.add(left);
    }
    return changed;
  }

  @Override
  public Set<Coord> getCells() {
    return Collections.unmodifiableSet(new TreeSet<>(Arrays.asList(_left, _right)));
  }

}
