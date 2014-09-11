package joephysics62.co.uk.sudoku.write;

import java.io.PrintStream;

import joephysics62.co.uk.sudoku.model.Cell;
import joephysics62.co.uk.sudoku.model.Puzzle;

public class PuzzleWriter {

  private final PrintStream _out;

  public PuzzleWriter(final PrintStream out) {
    _out = out;
  }

  public void write(final Puzzle puzzle) {
    int[][] allCells = puzzle.getAllCells();
    for (int[] row: allCells) {
      _out.print("|");
      for (int value : row) {
        _out.print(Cell.isSolved(value) ? Cell.asString(Cell.convertToNiceValue(value), puzzle.getPuzzleSize()) : "-");
        _out.print("|");
      }
      _out.println();
    }
    _out.println();
  }

}
