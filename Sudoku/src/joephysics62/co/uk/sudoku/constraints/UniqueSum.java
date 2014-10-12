package joephysics62.co.uk.sudoku.constraints;

import java.util.ArrayList;
import java.util.List;

import joephysics62.co.uk.sudoku.model.Cell;
import joephysics62.co.uk.sudoku.model.CellGrid;
import joephysics62.co.uk.sudoku.model.Coord;
import joephysics62.co.uk.sudoku.model.Layout;

import org.apache.log4j.Logger;

public class UniqueSum extends Uniqueness {

  private static Logger LOG = Logger.getLogger(UniqueSum.class);

  private final int _sum;

  @Override
  public boolean isSatisfied(CellGrid cellGrid) {
    int sum = 0;
    for (Coord coord : getCells()) {
      int bitValue = cellGrid.getCellValue(coord);
      if (Cell.isSolved(bitValue)) {
        sum += Cell.toNumericValue(bitValue);
      }
      else {
        return true;
      }
    }
    return _sum == sum;
  }

  public int getSum() {
    return _sum;
  }

  @Override
  public String toString() {
    return String.format("UniqueSum(sum=%s, coords=%s)", _sum, getCells());
  }

  private UniqueSum(final int sum, List<Coord> cells) {
    super(cells);
    LOG.debug("Creating unique sum constraint for sum " + sum + " on cells " + cells);
    _sum = sum;
    // if s < n(n+1)/2 impossible to solve
    // max value of nth is s - n(n-1)/2
    // max value = s - n(n-1)/2   not solveable if max value < n
    // min value = s -(n-1)(i - (n-2)/2) not solveable if min value > i - (n-1)
  }

  private static final int minimumSum(List<Coord> coords) {
    final int n = coords.size();
    return (n * (n + 1)) / 2;
  }

  private static final int maximumSum(final Layout layout, List<Coord> coords) {
    int i = layout.getInitialsSize();
    final int n = coords.size();
    return i * n - (n * (n - 1)) / 2;
  }

  public static UniqueSum of(final int originalSum, List<Coord> cells) {
    return new UniqueSum(originalSum, new ArrayList<Coord>(cells));
  }

  @Override
  public boolean forSolvedCell(CellGrid cellGrid, Coord coord) {
    boolean hasChanged = super.forSolvedCell(cellGrid, coord);
    Integer numericValue = Cell.toNumericValue(cellGrid.getCellValue(coord));
    if (null == numericValue) {
      return hasChanged;
    }
    int newSum = _sum;
    final List<Coord> subgroupCoords = new ArrayList<>();
    for (Coord cell : getCells()) {
      int bitwiseValue = cellGrid.getCellValue(cell);
      if (Cell.isSolved(bitwiseValue)) {
        newSum -= Cell.toNumericValue(bitwiseValue);
      }
      else {
        subgroupCoords.add(cell);
      }
    }
    hasChanged |= eliminateValues(cellGrid, subgroupCoords, newSum);
    return hasChanged;
  }

  private static boolean eliminateValues(CellGrid cellGrid, List<Coord> coords, final int sum) {
    int minimumSum = minimumSum(coords);
    if (sum < minimumSum) {
      LOG.debug("The sum " + sum + " is less than the minimum " + minimumSum);
      setAllUnsolveable(cellGrid, coords);
      return true;
    }
    int maximumSum = maximumSum(cellGrid.getLayout(), coords);
    if (sum > maximumSum) {
      LOG.debug("The sum " + sum + " is greater than the maximum " + maximumSum);
      setAllUnsolveable(cellGrid, coords);
      return true;
    }

    int n = coords.size();

    // i + i-1 + i-2 == n*i - (n*(n-1))/2
    final int maxValue = sum - (n * (n - 1)) / 2;
    int i = cellGrid.getLayout().getInitialsSize();
    final int minValue = sum - ((n - 1) * (2 * i - (n - 2))) / 2;
    boolean changed = false;
    for (Coord coord : coords) {
      for (int possible = 1; possible <= i; possible++) {
        if (possible > maxValue || possible < minValue) {
          changed |= removePossible(possible, coord, cellGrid);
        }
      }
      if (sum == 1 + minimumSum) {
        changed |= removePossible(n, coord, cellGrid);
      }
      if (sum == maximumSum - 1) {
        changed |= removePossible(i - n + 1, coord, cellGrid);
      }
      if (n == 2 && sum % 2 == 0) {
        changed |= removePossible(sum / 2, coord, cellGrid);
      }
    }
    return changed;
  }

  @Override
  public boolean eliminateValues(CellGrid cellGrid) {
    return eliminateValues(cellGrid, getCells(), _sum);
  }

  private static boolean removePossible(int possible, Coord coord, CellGrid cellGrid) {
    final int oldValue = cellGrid.getCellValue(coord);
    final int newValue = Cell.remove(oldValue, Cell.cellValueAsBitwise(possible));
    if (oldValue != newValue) {
      LOG.debug("Eliminating possible " + possible + " from cell " + coord);
      cellGrid.setCellValue(newValue, coord);
      return true;
    }
    return false;
  }

  private static void setAllUnsolveable(CellGrid cellGrid, List<Coord> coords) {
    for (Coord cell : coords) {
      cellGrid.setCellValue(0, cell);
    }
  }

}
