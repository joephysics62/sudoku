package joephysics62.co.uk.kenken.constraint;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import joephysics62.co.uk.kenken.PuzzleAnswer;
import joephysics62.co.uk.kenken.grid.Cell;
import joephysics62.co.uk.kenken.grid.Coordinate;

public class SubtractionConstraint implements Constraint {
  private final Coordinate _left;
  private final Coordinate _right;
  private final Integer _target;

  public SubtractionConstraint(final Integer target, final Coordinate left, final Coordinate right) {
    _target = target;
    _left = left;
    _right = right;
  }

  @Override
  public Set<Coordinate> getCoords() {
    return new LinkedHashSet<Coordinate>(Arrays.asList(_left, _right));
  }

  @Override
  public boolean isSatisfiedBy(final PuzzleAnswer answer) {
    final Cell leftCell = answer.cellAt(_left);
    final Cell rightCell = answer.cellAt(_right);
    if (leftCell.isUnsolved() || rightCell.isUnsolved()) {
      return true;
    }
    return Math.abs(leftCell.getSolvedValue() - rightCell.getSolvedValue()) == _target ;
  }


  @Override
  public void applyConstraint(final PuzzleAnswer answer) {
    final int maximum = answer.getMaximum();

  }
}
