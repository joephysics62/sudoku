package joephysics62.co.uk.sudoku.builder;

import joephysics62.co.uk.sudoku.model.ArrayPuzzle;
import joephysics62.co.uk.sudoku.model.Puzzle;
import joephysics62.co.uk.sudoku.model.PuzzleLayout;

public class FutoshikiBuilder extends ArrayPuzzleBuilder {

  public FutoshikiBuilder(final PuzzleLayout layout) {
    super(layout);
    if (layout.hasSubtables()) {
      throw new IllegalArgumentException("Futoshikis do not have subtables");
    }
  }

  @Override
  public Puzzle build() {
    ArrayPuzzle futoshiki = puzzleNoConstraints();

    addRowUniqueness();
    addColumnUniqueness();

    futoshiki.setConstraints(getConstraints());
    return futoshiki;
  }

}
