package joephysics62.co.uk.sudoku.solver;

import java.util.Collections;
import java.util.List;

import joephysics62.co.uk.sudoku.model.Cell;
import joephysics62.co.uk.sudoku.model.Coord;
import joephysics62.co.uk.sudoku.model.Puzzle;

public class FirstClosestToSolved implements CellFilter {

  private FirstClosestToSolved() {
    // through static method
  }

  public static CellFilter create() {
    return new FirstClosestToSolved();
  }

  @Override
  public List<Coord> apply(final Puzzle puzzle) {
    int minPossibles = Integer.MAX_VALUE;
    Coord minCell = null;
    int rowNum = 1;
    for (int[] row : puzzle.getAllCells()) {
      int colNum = 1;
      for (int value : row) {
        if (!Cell.isSolved(value)) {
          int possiblesSize = Integer.bitCount(value);
          if (possiblesSize == 2) {
            return Collections.singletonList(Coord.of(rowNum, colNum));
          }
          else if (possiblesSize < minPossibles) {
            minPossibles = possiblesSize;
            minCell = Coord.of(rowNum, colNum);
          }
        }
        colNum++;
      }
      rowNum++;
    }
    if (minCell == null) {
      return Collections.emptyList();
    }
    else {
      return Collections.singletonList(minCell);
    }
  }

}
