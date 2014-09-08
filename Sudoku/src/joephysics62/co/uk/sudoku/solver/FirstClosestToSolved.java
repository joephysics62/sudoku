package joephysics62.co.uk.sudoku.solver;

import joephysics62.co.uk.sudoku.model.Cell;
import joephysics62.co.uk.sudoku.model.Coord;
import joephysics62.co.uk.sudoku.model.Puzzle;

public class FirstClosestToSolved implements CellGuessingStrategy {

  private FirstClosestToSolved() {
    // through static method
  }

  public static CellGuessingStrategy create() {
    return new FirstClosestToSolved();
  }

  @Override
  public Coord cellToGuess(final Puzzle puzzle) {
    int minPossibles = Integer.MAX_VALUE;
    Coord minCell = null;
    int rowNum = 1;
    for (int[] row : puzzle.getAllCells()) {
      int colNum = 1;
      for (int value : row) {
        if (!Cell.isSolved(value)) {
          int possiblesSize = Integer.bitCount(value);
          if (possiblesSize == 2) {
            return new Coord(rowNum, colNum);
          }
          else if (possiblesSize < minPossibles) {
            minPossibles = possiblesSize;
            minCell = new Coord(rowNum, colNum);
          }
        }
        colNum++;
      }
      rowNum++;
    }
    return minCell;
  }

}
