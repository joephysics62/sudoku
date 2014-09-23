package joephysics62.co.uk.sudoku.builder;

import java.util.ArrayList;
import java.util.List;

import joephysics62.co.uk.sudoku.constraints.Constraint;
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
    final List<Constraint> allConstraints = new ArrayList<>();
    allConstraints.addAll(getConstraints());
    allConstraints.addAll(createRowUniquenessConstraints(getLayout()));
    allConstraints.addAll(createColumnUniquenessConstraints(getLayout()));
    allConstraints.addAll(createSubTableUniquenessConstraints(getLayout()));
    return newArrayPuzzle(allConstraints);
  }

}
