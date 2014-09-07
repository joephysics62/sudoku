package joephysics62.co.uk.sudoku.creator;

import joephysics62.co.uk.sudoku.model.ArrayPuzzle;
import joephysics62.co.uk.sudoku.model.Puzzle;
import joephysics62.co.uk.sudoku.solver.PuzzleSolver;

public class PuzzleCreator {
  private final PuzzleSolver _solver;

  public PuzzleCreator(final PuzzleSolver solver) {
    _solver = solver;
  }

  public Puzzle create(final int puzzleSize) {
    ArrayPuzzle arrayPuzzle = ArrayPuzzle.forPossiblesSize(puzzleSize);
    return arrayPuzzle;
  }

}
