package joephysics62.co.uk.sudoku.solver;

import java.io.PrintStream;

import joephysics62.co.uk.sudoku.model.Coord;

public class SolvedPuzzle {
  private final int[][] _table;

  public SolvedPuzzle(final int[][] table) {
    _table = table;
  }

  public int getValue(final Coord cellId) {
    return _table[cellId.getRow() - 1][cellId.getCol() - 1];
  }

  public void write(PrintStream out) {
    for (int[] row : _table) {
      out.print("|");
      for (int value : row) {
        out.print(value + "|");
      }
      out.println("");
    }
  }
}
