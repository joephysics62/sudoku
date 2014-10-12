package joephysics62.co.uk.sudoku.write;

import java.io.PrintStream;

import joephysics62.co.uk.sudoku.model.Cell;
import joephysics62.co.uk.sudoku.model.Puzzle;

public class TextWriter {

  private final PrintStream _out;

  public TextWriter(final PrintStream out) {
    _out = out;
  }

  public void write(final Puzzle puzzle) {
    int[][] allCells = puzzle.getAllCells();
    for (int[] row: allCells) {
      _out.print("|");
      for (int value : row) {
        _out.print(outString(value, puzzle));
        _out.print("|");
      }
      _out.println();
    }
    _out.println();
  }

  private static String outString(final int value, Puzzle puzzle) {
    if (value < 0) {
      return "X";
    }
    if (Cell.isSolved(value)) {
      return Cell.asString(Cell.toNumericValue(value), puzzle.getLayout().getInitialsSize());
    }
    else {
      return "-";
    }
  }

}
