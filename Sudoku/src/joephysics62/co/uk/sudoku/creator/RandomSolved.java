package joephysics62.co.uk.sudoku.creator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import joephysics62.co.uk.sudoku.model.Cell;
import joephysics62.co.uk.sudoku.model.Coord;
import joephysics62.co.uk.sudoku.model.Puzzle;
import joephysics62.co.uk.sudoku.solver.CellPickingStrategy;

/**
 * Pick randomly a cell to guess the value of. Primary for creating puzzles rather than solving them.
 */
public class RandomSolved implements CellPickingStrategy {

  private RandomSolved() {
    // no.
  }

  public static CellPickingStrategy create() {
    return new RandomSolved();
  }

  @Override
  public Coord cellToGuess(Puzzle puzzle) {
    List<Coord> unsolved = new ArrayList<>();
    int[][] allCells = puzzle.getAllCells();
    for (int rowNum = 1; rowNum <= allCells.length; rowNum++) {
      for (int colNum = 1; colNum <= allCells.length; colNum++) {
        if (Cell.isSolved(allCells[rowNum - 1][colNum -1])) {
          unsolved.add(new Coord(rowNum, colNum));
        }
      }
    }
    if (unsolved.isEmpty()) {
      return null;
    }
    Collections.shuffle(unsolved);
    return unsolved.get(0);
  }

}
