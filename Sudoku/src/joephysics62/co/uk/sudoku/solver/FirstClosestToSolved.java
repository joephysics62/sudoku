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
    int[][] allCells = puzzle.getAllCells();
    for (int rowNum = 1; rowNum <= puzzle.getLayout().getHeight(); rowNum++) {
      for (int colNum = 1; colNum <= puzzle.getLayout().getWidth(); colNum++) {
        int value = allCells[rowNum - 1][colNum - 1];
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
      }
    }
    if (minCell == null) {
      return Collections.emptyList();
    }
    else {
      return Collections.singletonList(minCell);
    }
  }

}
