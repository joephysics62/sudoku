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
        out.print(Cell.isSolved(value) ? Cell.asString(Cell.convertToNiceValue(value), puzzle.getPuzzleSize()) : "-");
        out.print("|");
      }
      out.println();
    }
    out.println();
  }

}
