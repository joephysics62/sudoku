package joephysics62.co.uk.sudoku.creator;

import java.util.List;

import joephysics62.co.uk.constraints.Constraint;
import joephysics62.co.uk.constraints.GreaterThan;
import joephysics62.co.uk.grid.Coord;
import joephysics62.co.uk.sudoku.builder.ArrayBuilder;
import joephysics62.co.uk.sudoku.model.Puzzle;
import joephysics62.co.uk.sudoku.model.PuzzleLayout;
import joephysics62.co.uk.sudoku.solver.Solver;

import org.apache.log4j.Logger;

public class FutoshikiCreator extends ArrayPuzzleCreator {

  private static final Logger LOG = Logger.getLogger(FutoshikiCreator.class);

  public FutoshikiCreator(final Solver solver, final PuzzleLayout layout, final CreationSpec creationSpec) {
    super(solver, layout, creationSpec);
  }

  @Override
  protected void addGeometricConstraints(ArrayBuilder puzzleBuilder) {
    puzzleBuilder.addColumnUniquenessConstraints();
    puzzleBuilder.addRowUniquenessConstraints();
  }

  @Override
  protected void addVariableConstraints(Puzzle puzzle) {
    List<Constraint> variableConstraints = puzzle.getVariableConstraints();
    for (Coord coord : puzzle) {
      Coord toRight = coord.right();
      Coord below = coord.down();
      int thisValue = puzzle.get(coord);
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
  protected void postShuffleReorder(List<Constraint> varConstraints) {
    // no need.
  }


}
