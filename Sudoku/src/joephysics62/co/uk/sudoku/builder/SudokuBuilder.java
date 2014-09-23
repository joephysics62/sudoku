package joephysics62.co.uk.sudoku.builder;

import joephysics62.co.uk.sudoku.model.ArrayPuzzle;
import joephysics62.co.uk.sudoku.model.Puzzle;
import joephysics62.co.uk.sudoku.model.PuzzleLayout;

/**
 * Builds an empty sudoku, with constraints.
 */
public class SudokuBuilder extends ArrayPuzzleBuilder {

  public SudokuBuilder(PuzzleLayout layout) {
    super(layout);
  }

  @Override
  public Puzzle build() {
    ArrayPuzzle sudoku = puzzleNoConstraints();

    addRowUniqueness();
    addColumnUniqueness();
    addSubTableUniqueness();

    sudoku.setConstraints(getConstraints());
    return sudoku;
  }

}
