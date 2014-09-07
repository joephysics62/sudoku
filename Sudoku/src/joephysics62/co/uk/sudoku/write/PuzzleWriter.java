package joephysics62.co.uk.sudoku.write;

import java.io.PrintStream;

import joephysics62.co.uk.sudoku.model.Puzzle;

public class PuzzleWriter {

  public void write(Puzzle puzzle, PrintStream out) {
    int[][] allCells = puzzle.getAllCells();
    for (int[] row: allCells) {
      out.print("|");
      for (int value : row) {
        out.print(Integer.bitCount(value) == 1 ? convertToNiceValue(value) : "-");
        out.print("|");
      }
      out.println();
    }
    out.println();
  }

  public static Integer convertToNiceValue(int bitwiseValue) {
    if (Integer.bitCount(bitwiseValue) == 1) {
      return Integer.numberOfTrailingZeros(bitwiseValue) + 1;
    }
    else {
      return null;
    }
  }
}
