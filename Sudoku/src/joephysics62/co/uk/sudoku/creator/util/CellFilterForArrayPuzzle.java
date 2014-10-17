package joephysics62.co.uk.sudoku.creator.util;

import java.util.ArrayList;
import java.util.List;

import joephysics62.co.uk.grid.Coord;
import joephysics62.co.uk.grid.GridLayout;
import joephysics62.co.uk.sudoku.model.Puzzle;
import joephysics62.co.uk.sudoku.solver.CellFilter;

public abstract class CellFilterForArrayPuzzle implements CellFilter {

  @Override
  public final List<Coord> apply(Puzzle puzzle) {
    List<Coord> unsolved = new ArrayList<>();
    int[][] allCells = puzzle.getAllCells();
    GridLayout layout = puzzle.getLayout();
    for (int rowNum = 1; rowNum <= layout.getHeight(); rowNum++) {
      for (int colNum = 1; colNum <= layout.getWidth(); colNum++) {
        if (accept(allCells[rowNum - 1][colNum -1])) {
          unsolved.add(Coord.of(rowNum, colNum));
        }
      }
    }
    return unsolved;
  }

  protected abstract boolean accept(int cellValue);
}
