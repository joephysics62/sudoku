package joephysics62.co.uk.sudoku.solver;

import java.io.PrintStream;

import joephysics62.co.uk.sudoku.model.Cell;
import joephysics62.co.uk.sudoku.model.Coord;

public class SolvedPuzzle {
  private final int[][] _table;
  private final int _subTableHeight;
  private final int _subTableWidth;

  public SolvedPuzzle(final int[][] table, final int subTableHeight, final int subTableWidth) {
    _table = table;
    _subTableHeight = subTableHeight;
    _subTableWidth = subTableWidth;
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
        if (_subTableWidth > 0 && colNum < _table.length && colNum % _subTableWidth == 0) {
          out.print("|");
        }
        colNum++;
      }
      if (_subTableHeight > 0 && rowNum < _table.length && rowNum % _subTableHeight == 0) {
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
