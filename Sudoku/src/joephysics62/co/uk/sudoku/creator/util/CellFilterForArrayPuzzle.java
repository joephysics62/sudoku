package joephysics62.co.uk.sudoku.creator.util;

import java.util.ArrayList;
import java.util.List;

import joephysics62.co.uk.grid.Coord;
import joephysics62.co.uk.sudoku.model.Puzzle;
import joephysics62.co.uk.sudoku.solver.CellFilter;

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
