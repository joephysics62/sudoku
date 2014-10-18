package joephysics62.co.uk.sudoku.creator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
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

  private enum Direction {
    L {
      @Override
      protected boolean isTouching(Coord coord, Coord otherCoord) {
        return otherCoord.getRow() == coord.getRow() && -1 == otherCoord.getCol() - coord.getCol();
      }
    },
    R {

      @Override
      protected boolean isTouching(Coord coord, Coord otherCoord) {
        return otherCoord.getRow() == coord.getRow() && 1 == otherCoord.getCol() - coord.getCol();
      }

    },
    U {

      @Override
      protected boolean isTouching(Coord coord, Coord otherCoord) {
        return otherCoord.getCol() == coord.getCol() && -1 == otherCoord.getRow() - coord.getRow();
      }

    },
    D {

      @Override
      protected boolean isTouching(Coord coord, Coord otherCoord) {
        return otherCoord.getCol() == coord.getCol() && 1 == otherCoord.getRow() - coord.getRow();
      }

    };

    protected abstract boolean isTouching(final Coord coord, final Coord otherCoord);
  }

  private final Constraint findTouching(final Puzzle puzzle, final Constraint constraint) {
    List<Constraint> otherConstraints = new ArrayList<>(puzzle.getVariableConstraints());
    Collections.shuffle(otherConstraints);
    for (Constraint other : otherConstraints) {
      if (other != constraint) {
        for (Coord coord : constraint.getCells()) {
          for (Coord otherCoord : other.getCells()) {
            if (otherCoord.equals(coord)) {
              throw new RuntimeException("Did not expect overlapping variable constraints. At " + coord);
            }
            final List<Direction> directions = Arrays.asList(Direction.values());
            Collections.shuffle(directions);
            for (Direction direction : directions) {
              if (direction.isTouching(coord, otherCoord)) {
                return other;
              }
            }
          }
        }
      }
    }
    return null;
  }

  @Override
  protected boolean removeVariable(final Constraint constraint, final Puzzle puzzle) {
    final Constraint other = findTouching(puzzle, constraint);
    if (other == null) {
      throw new UnsupportedOperationException();
    }
    UniqueSum merged = UniqueSum.merge((UniqueSum) constraint, (UniqueSum) other);
    if (merged == null) {
      return false;
    }
    int index = puzzle.getVariableConstraints().indexOf(constraint);
    puzzle.getVariableConstraints().add(index, merged);
    puzzle.getVariableConstraints().remove(constraint);
    puzzle.getVariableConstraints().remove(other);
    return true;
  }

  @Override
  protected void postShuffleReorder(List<Constraint> varConstraints) {
    Collections.sort(varConstraints, new Comparator<Constraint>() {

      @Override
      public int compare(Constraint left, Constraint right) {
        int lSize = left.getCells().size();
        int rSize = right.getCells().size();
        if (lSize > rSize) {
          return 1;
        }
        else if (lSize < rSize) {
          return -1;
        }
        return 0;
      }
    });
  }

}
