package joephysics62.co.uk.sudoku.solver;

import java.io.PrintStream;

import joephysics62.co.uk.sudoku.model.Cell;
import joephysics62.co.uk.sudoku.model.Coord;
import joephysics62.co.uk.sudoku.model.PuzzleLayout;

public class SolvedPuzzle {
  private final int[][] _table;
  private final PuzzleLayout _layout;

  public SolvedPuzzle(final int[][] table, final PuzzleLayout layout) {
    _table = table;
    _layout = layout;
  }

  public int getValue(final Coord cellId) {
    return _table[cellId.getRow() - 1][cellId.getCol() - 1];
  }

  public void write(PrintStream out) {
    int rowNum = 1;
    for (int[] row : _table) {
      int colNum = 1;
      out.print("|");
      for (int value : row) {
        out.print(asString(value) + "|");
        if (_layout.getSubTableWidth() > 0 && colNum < _table.length && colNum % _layout.getSubTableWidth() == 0) {
          out.print("|");
        }
        colNum++;
      }
      if (_layout.getSubTableHeight() > 0 && rowNum < _table.length && rowNum % _layout.getSubTableHeight() == 0) {
        out.println();
        for (int i = 0; i <= _table.length; i++) {
          out.print(" -");
        }
      }
      rowNum++;
      out.println();
    }
    out.println();
  }

  private String asString(final int value) {
    return Cell.asString(value, _table.length);
  }
}
