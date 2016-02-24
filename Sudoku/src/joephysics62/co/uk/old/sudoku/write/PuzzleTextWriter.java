package joephysics62.co.uk.old.sudoku.write;

import java.io.PrintStream;

import joephysics62.co.uk.old.grid.Coord;
import joephysics62.co.uk.old.sudoku.model.Cell;
import joephysics62.co.uk.old.sudoku.model.Puzzle;

public class PuzzleTextWriter {

  private final PrintStream _out;

  public PuzzleTextWriter(final PrintStream out) {
    _out = out;
  }

  public void write(final Puzzle puzzle) {
    for (Coord coord : puzzle) {
      if (coord.getCol() == 1) {
        _out.print("|");
      }
      _out.print(outString(puzzle.get(coord), puzzle));
      _out.print("|");
      if (coord.getCol() == puzzle.getLayout().getWidth()) {
        _out.println();
      }
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
