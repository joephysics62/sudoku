package joephysics62.co.uk.sudoku.creator;

import java.util.Collections;
import java.util.List;

import joephysics62.co.uk.constraints.Constraint;
import joephysics62.co.uk.constraints.UniqueSum;
import joephysics62.co.uk.grid.Coord;
import joephysics62.co.uk.sudoku.model.Cell;
import joephysics62.co.uk.sudoku.model.Puzzle;
import joephysics62.co.uk.sudoku.model.PuzzleLayout;
import joephysics62.co.uk.sudoku.solver.Solver;

public class KillerSudokuCreator extends SudokuCreator {

  public KillerSudokuCreator(final Solver solver, final PuzzleLayout layout, final int maxVarConstraints, final int maxDepth) {
    super(solver, layout, new CreationSpec(0, maxVarConstraints, maxDepth, false));
  }

  @Override
  protected void addVariableConstraints(Puzzle puzzle) {
    List<Constraint> variableConstraints = puzzle.getVariableConstraints();
    for (Coord coord : puzzle) {
      variableConstraints.add(UniqueSum.of(Cell.toNumericValue(puzzle.get(coord)), puzzle.getLayout().getInitialsSize(), Collections.singletonList(coord)));
    }
  }

  private final Constraint findTouching(final Puzzle puzzle, final Constraint constraint) {
    for (Constraint other : puzzle.getVariableConstraints()) {
      if (other != constraint) {
        for (Coord coord : constraint.getCells()) {
          for (Coord otherCoord : other.getCells()) {
            if (otherCoord.equals(coord)) {
              throw new RuntimeException("Did not expect overlapping variable constraints. At " + coord);
            }
            boolean isTouchingHoriz = otherCoord.getRow() == coord.getRow() && 1 == Math.abs(otherCoord.getCol() - coord.getCol());
            boolean isTouchingVert = otherCoord.getCol() == coord.getCol() && 1 == Math.abs(otherCoord.getRow() - coord.getRow());
            if (isTouchingHoriz || isTouchingVert) {
              return other;
            }
          }
        }
      }
    }
    return null;
  }

  @Override
  protected boolean removeVariable(int index, final Puzzle puzzle) {
    final Constraint constraint = puzzle.getVariableConstraints().get(index);
    final Constraint other = findTouching(puzzle, constraint);
    if (other == null) {
      throw new UnsupportedOperationException();
    }
    UniqueSum merged = UniqueSum.merge((UniqueSum) constraint, (UniqueSum) other);
    if (merged == null) {
      return false;
    }
    puzzle.getVariableConstraints().add(index, merged);
    puzzle.getVariableConstraints().remove(constraint);
    puzzle.getVariableConstraints().remove(other);
    return true;
  }

}
