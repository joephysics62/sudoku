package joephysics62.co.uk.sudoku.creator;

import joephysics62.co.uk.sudoku.builder.ArrayBuilder;
import joephysics62.co.uk.sudoku.model.Puzzle;
import joephysics62.co.uk.sudoku.model.PuzzleLayout;
import joephysics62.co.uk.sudoku.solver.Solver;

public class SudokuCreator extends ArrayPuzzleCreator {

  public SudokuCreator(final Solver solver, final PuzzleLayout layout, final CreationSpec creationSpec) {
    super(solver, layout, creationSpec);
  }

  @Override
  protected final void addGeometricConstraints(ArrayBuilder puzzleBuilder) {
    puzzleBuilder.addColumnUniquenessConstraints();
    puzzleBuilder.addRowUniquenessConstraints();
    puzzleBuilder.addSubTableUniquenessConstraints();
  }

  @Override
  protected void addVariableConstraints(Puzzle puzzle) {
  }

  @Override
  protected boolean removeVariable(int index, final Puzzle puzzle) {
    throw new UnsupportedOperationException();
  }

}
