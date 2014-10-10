package joephysics62.co.uk.sudoku.constraints;

import java.util.Collections;
import java.util.List;

import joephysics62.co.uk.sudoku.model.CellGrid;
import joephysics62.co.uk.sudoku.model.Coord;

public class UniqueSum implements Constraint {

  private final int _sum;
  private final List<Coord> _cells;

  private UniqueSum(final int sum, List<Coord> cells) {
    _sum = sum;
    _cells = Collections.unmodifiableList(cells);
    // if s < n(n+1)/2 impossible to solve
    // max value of nth is s - n(n-1)/2
    // max value = s - n(n-1)/2   not solveable if max value < n
    // min value = s -(n-1)(i - (n-2)/2) not solveable if min value > i - (n-1)
  }

  public static UniqueSum of(final int originalSum, List<Coord> cells) {
    return new UniqueSum(originalSum, cells);
  }

  @Override
  public boolean eliminateValues(CellGrid cellGrid) {
    for (Coord coord : _cells) {
      final int cellValue = cellGrid.getCellValue(coord);
    }
    return false;
  }

  @Override
  public boolean forSolvedCell(final CellGrid cellGrid, int solvedCell) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public List<Coord> getCells() {
    return _cells;
  }
}
