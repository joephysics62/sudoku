package joephysics62.co.uk.sudoku.constraints;

import java.util.List;

import joephysics62.co.uk.sudoku.model.Cell;
import joephysics62.co.uk.sudoku.model.CellGrid;
import joephysics62.co.uk.sudoku.model.Coord;
import joephysics62.co.uk.sudoku.model.Layout;

import org.apache.log4j.Logger;

public class UniqueSum extends Uniqueness {

  private static Logger LOG = Logger.getLogger(UniqueSum.class);

  private final int _sum;

  private UniqueSum(final int sum, List<Coord> cells) {
    super(cells);
    _sum = sum;
    // if s < n(n+1)/2 impossible to solve
    // max value of nth is s - n(n-1)/2
    // max value = s - n(n-1)/2   not solveable if max value < n
    // min value = s -(n-1)(i - (n-2)/2) not solveable if min value > i - (n-1)
  }

  private final int minimumSum() {
    final int n = getCells().size();
    return (n * (n + 1)) / 2;
  }

  private final int maximumSum(final Layout layout) {
    int i = layout.getInitialsSize();
    final int n = getCells().size();
    return i * n - (n * (n - 1)) / 2;
  }

  public static UniqueSum of(final int originalSum, List<Coord> cells) {
    return new UniqueSum(originalSum, cells);
  }

  @Override
  public boolean eliminateValues(CellGrid cellGrid) {
    int minimumSum = minimumSum();
    if (_sum < minimumSum) {
      LOG.debug("The sum " + _sum + " is less than the minimum " + minimumSum);
      setAllUnsolveable(cellGrid);
      return true;
    }
    int maximumSum = maximumSum(cellGrid.getLayout());
    if (_sum > maximumSum) {
      LOG.debug("The sum " + _sum + " is greater than the maximum " + maximumSum);
      setAllUnsolveable(cellGrid);
      return true;
    }

    int n = getCells().size();
    final int maxValue = _sum - (n * (n - 1)) / 2;
    int i = cellGrid.getLayout().getInitialsSize();
    final int minValue = _sum - ((n - 1) * (2 * i - (n - 2))) / 2;
    LOG.debug("For unique sum, sum = " + _sum + " num cells = " + n + ", min = " + minValue + ", max = " + maxValue);

    boolean changed = false;
    for (int possible = 1; possible <= i; possible++) {
      if (possible > maxValue || possible < minValue) {
        // eliminate
        for (Coord coord : getCells()) {
          final int oldValue = cellGrid.getCellValue(coord);
          final int newValue = Cell.remove(oldValue, Cell.cellValueAsBitwise(possible));
          if (oldValue != newValue) {
            LOG.debug("Eliminating possible " + possible + " from cell " + coord);
            cellGrid.setCellValue(newValue, coord);
            changed = true;
          }
        }
      }
    }
    return changed;
  }

  private void setAllUnsolveable(CellGrid cellGrid) {
    for (Coord cell : getCells()) {
      cellGrid.setCellValue(0, cell);
    }
  }

}
