package joephysics62.co.uk.sudoku.solver;

import java.io.PrintStream;

import joephysics62.co.uk.grid.Coord;
import joephysics62.co.uk.grid.arrays.IntegerArrayGrid;
import joephysics62.co.uk.sudoku.model.Cell;
import joephysics62.co.uk.sudoku.model.PuzzleLayout;

public class Solution {
  private final IntegerArrayGrid _table;
  private final PuzzleLayout _layout;

  public Solution(final IntegerArrayGrid table, final PuzzleLayout layout) {
    _table = table;
    _layout = layout;
  }

  public int getValue(final Coord cellId) {
    return _table.get(cellId);
  }

  public void write(PrintStream out) {
    for (Coord coord : _table) {
      if (coord.getCol() == 1) {
        out.print("|");
      }
      out.print(asString(_table.get(coord), _layout.getInitialsSize()) + "|");
      if (_layout.getSubTableWidth() > 0 && coord.getCol() < _layout.getWidth() && coord.getCol() % _layout.getSubTableWidth() == 0) {
        out.print("|");
      }
      if (coord.getCol() == _layout.getWidth()) {
        if (_layout.getSubTableHeight() > 0 && coord.getRow() < _layout.getHeight() && coord.getRow() % _layout.getSubTableHeight() == 0) {
          out.println();
          for (int i = 0; i <= _layout.getWidth(); i++) {
            out.print(" -");
          }
        }
        out.println();
      }
    }
    out.println();
  }

  private String asString(final int value, final int possiblesSize) {
    return Cell.asString(value, possiblesSize);
  }
}
