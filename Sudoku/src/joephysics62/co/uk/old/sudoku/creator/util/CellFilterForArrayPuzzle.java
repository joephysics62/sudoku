package joephysics62.co.uk.old.sudoku.creator.util;

import java.util.ArrayList;
import java.util.List;

import joephysics62.co.uk.old.grid.Coord;
import joephysics62.co.uk.old.sudoku.model.Puzzle;
import joephysics62.co.uk.old.sudoku.solver.CellFilter;

public abstract class CellFilterForArrayPuzzle implements CellFilter {

  @Override
  public final List<Coord> apply(Puzzle puzzle) {
    final List<Coord> retained = new ArrayList<>();
    for (Coord coord : puzzle) {
      if (accept(puzzle.get(coord))) {
        retained.add(coord);
      }
    }
    return retained;
  }

  protected abstract boolean accept(int cellValue);
}
