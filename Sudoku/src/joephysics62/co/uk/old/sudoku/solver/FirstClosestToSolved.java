package joephysics62.co.uk.old.sudoku.solver;

import java.util.Collections;
import java.util.List;

import joephysics62.co.uk.old.grid.Coord;
import joephysics62.co.uk.old.sudoku.model.Cell;
import joephysics62.co.uk.old.sudoku.model.Puzzle;

public class FirstClosestToSolved implements CellFilter {

  private FirstClosestToSolved() {
    // through static method
  }

  public static CellFilter create() {
    return new FirstClosestToSolved();
  }

  @Override
  public List<Coord> apply(final Puzzle puzzle) {
    int minPossibles = Integer.MAX_VALUE;
    Coord minCell = null;
    for (Coord coord : puzzle) {
      int value = puzzle.get(coord);
      if (!Cell.isSolved(value)) {
        int possiblesSize = Integer.bitCount(value);
        if (possiblesSize == 2) {
          return Collections.singletonList(coord);
        }
        else if (possiblesSize < minPossibles) {
          minPossibles = possiblesSize;
          minCell = coord;
        }
      }
    }
    if (minCell == null) {
      return Collections.emptyList();
    }
    else {
      return Collections.singletonList(minCell);
    }
  }

}
