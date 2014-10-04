package joephysics62.co.uk.sudoku.creator;

import joephysics62.co.uk.sudoku.builder.ArrayPuzzleBuilder;
import joephysics62.co.uk.sudoku.model.Puzzle;
import joephysics62.co.uk.sudoku.model.PuzzleLayout;
import joephysics62.co.uk.sudoku.solver.PuzzleSolver;

public class SudokuCreator extends ArrayPuzzleCreator {

  public SudokuCreator(final PuzzleSolver solver, final PuzzleLayout layout, final CreationSpec creationSpec) {
    super(solver, layout, creationSpec);
  }

  @Override
  protected void addGeometricConstraints(ArrayPuzzleBuilder puzzleBuilder) {
    puzzleBuilder.addColumnUniquenessConstraints();
    puzzleBuilder.addRowUniquenessConstraints();
    puzzleBuilder.addSubTableUniquenessConstraints();
  }

  @Override
  protected void addVariableConstraints(Puzzle puzzle) {
    // None to add.
  }

}
