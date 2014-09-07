package joephysics62.co.uk.sudoku.write;

import java.io.PrintStream;

import joephysics62.co.uk.sudoku.model.Cell;
import joephysics62.co.uk.sudoku.model.Puzzle;

public class PuzzleWriter {

  public void write(Puzzle puzzle, PrintStream out) {
    int[][] allCells = puzzle.getAllCells();
    for (int[] row: allCells) {
      out.print("|");
      for (int value : row) {
        out.print(Cell.isSolved(value) ? convertToNiceValue(value) : "-");
        out.print("|");
      }
      out.println();
    }
    out.println();
  }

  public static Integer convertToNiceValue(int bitwiseValue) {
    if (Cell.isSolved(bitwiseValue)) {
      return Integer.numberOfTrailingZeros(bitwiseValue) + 1;
    }
    else {
      return null;
    }
  }
}
