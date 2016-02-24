package joephysics62.co.uk.old.sudoku.creator;

import java.util.List;

import joephysics62.co.uk.old.constraints.Constraint;
import joephysics62.co.uk.old.sudoku.builder.ArrayBuilder;
import joephysics62.co.uk.old.sudoku.model.Puzzle;
import joephysics62.co.uk.old.sudoku.model.PuzzleLayout;
import joephysics62.co.uk.old.sudoku.solver.Solver;

public class SudokuCreator extends ArrayPuzzleCreator {

  public SudokuCreator(final Solver solver, final PuzzleLayout layout, final CreationSpec creationSpec) {
    super(solver, layout, creationSpec);
  }

  @Override
  protected final void addGeometricConstraints(final ArrayBuilder puzzleBuilder) {
    puzzleBuilder.addColumnUniquenessConstraints();
    puzzleBuilder.addRowUniquenessConstraints();
    puzzleBuilder.addSubTableUniquenessConstraints();
  }

  @Override
  protected void addVariableConstraints(final Puzzle puzzle) {
  }

  @Override
  protected boolean removeVariable(final Constraint constraint, final Puzzle puzzle) {
    throw new UnsupportedOperationException();
  }

  @Override
  protected void postShuffleReorder(final List<Constraint> varConstraints) {
    throw new UnsupportedOperationException();
  }

}
