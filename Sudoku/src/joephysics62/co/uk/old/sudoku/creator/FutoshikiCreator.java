package joephysics62.co.uk.old.sudoku.creator;

import java.util.List;

import joephysics62.co.uk.old.constraints.Constraint;
import joephysics62.co.uk.old.constraints.GreaterThan;
import joephysics62.co.uk.old.grid.Coord;
import joephysics62.co.uk.old.sudoku.builder.ArrayBuilder;
import joephysics62.co.uk.old.sudoku.model.Puzzle;
import joephysics62.co.uk.old.sudoku.model.PuzzleLayout;
import joephysics62.co.uk.old.sudoku.solver.Solver;

import org.apache.log4j.Logger;

public class FutoshikiCreator extends ArrayPuzzleCreator {

  private static final Logger LOG = Logger.getLogger(FutoshikiCreator.class);

  public FutoshikiCreator(final Solver solver, final PuzzleLayout layout, final CreationSpec creationSpec) {
    super(solver, layout, creationSpec);
  }

  @Override
  protected void addGeometricConstraints(final ArrayBuilder puzzleBuilder) {
    puzzleBuilder.addColumnUniquenessConstraints();
    puzzleBuilder.addRowUniquenessConstraints();
  }

  @Override
  protected void addVariableConstraints(final Puzzle puzzle) {
    final List<Constraint> variableConstraints = puzzle.getVariableConstraints();
    for (final Coord coord : puzzle) {
      final Coord toRight = coord.right();
      final Coord below = coord.down();
      final int thisValue = puzzle.get(coord);
      if (coord.getCol() < puzzle.getLayout().getWidth()) {
        variableConstraints.add(thisValue > puzzle.get(toRight) ? GreaterThan.of(coord, toRight) : GreaterThan.of(toRight, coord));
      }
      if (coord.getRow() < puzzle.getLayout().getHeight()) {
        variableConstraints.add(thisValue > puzzle.get(below) ? GreaterThan.of(coord, below) : GreaterThan.of(below, coord));
      }
    }
  }

  @Override
  protected boolean removeVariable(final Constraint toRemove, final Puzzle puzzle) {
    LOG.debug("Removing variable constraint " + toRemove);
    return puzzle.getVariableConstraints().remove(toRemove);
  }

  @Override
  protected void postShuffleReorder(final List<Constraint> varConstraints) {
    // no need.
  }


}
