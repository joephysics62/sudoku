package joephysics62.co.uk.sudoku.creator.util;

import java.util.ArrayList;
import java.util.List;

import joephysics62.co.uk.sudoku.model.Cell;
import joephysics62.co.uk.sudoku.model.Coord;
import joephysics62.co.uk.sudoku.model.Puzzle;
import joephysics62.co.uk.sudoku.model.PuzzleLayout;
import joephysics62.co.uk.sudoku.solver.CellFilter;

/**
 * Pick randomly a cell to guess the value of. Primary for creating puzzles rather than solving them.
 */
public class RandomUnsolved implements CellFilter {

  private RandomUnsolved() {
    // no.
  }

  public static CellFilter create() {
    return new RandomUnsolved();
  }

  @Override
  public List<Coord> apply(Puzzle puzzle) {
    List<Coord> unsolved = new ArrayList<>();
    int[][] allCells = puzzle.getAllCells();
    PuzzleLayout layout = puzzle.getLayout();
    for (int rowNum = 1; rowNum <= layout.getHeight(); rowNum++) {
      for (int colNum = 1; colNum <= layout.getWidth(); colNum++) {
        if (!Cell.isSolved(allCells[rowNum - 1][colNum -1])) {
          unsolved.add(Coord.of(rowNum, colNum));
        }
      }
    }
    return unsolved;
  }

}
