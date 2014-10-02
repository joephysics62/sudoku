package joephysics62.co.uk.sudoku.creator;

import joephysics62.co.uk.sudoku.model.Puzzle;
import joephysics62.co.uk.sudoku.model.PuzzleLayout;

public interface PuzzleCreator {

  Puzzle create(PuzzleLayout layout, CreationSpec creationSpec);

}
