package joephysics62.co.uk.sudoku.creator;

import joephysics62.co.uk.sudoku.builder.ArrayPuzzleBuilder;
import joephysics62.co.uk.sudoku.solver.PuzzleSolver;

public class FutoshikiCreator extends ArrayPuzzleCreator {

  public FutoshikiCreator(PuzzleSolver solver) {
    super(solver);
  }

  @Override
  protected void addGeometricConstraints(ArrayPuzzleBuilder puzzleBuilder) {
    puzzleBuilder.addColumnUniquenessConstraints();
    puzzleBuilder.addRowUniquenessConstraints();
  }

}
