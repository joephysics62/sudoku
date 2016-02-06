package joephysics62.co.uk.kenken.constraint.arithmetic;

import joephysics62.co.uk.kenken.PuzzleAnswer;
import joephysics62.co.uk.kenken.grid.Coordinate;

import com.google.common.collect.Sets;


public class DivisionConstraint extends ArithmeticConstraint {

  public DivisionConstraint(final Coordinate left, final Coordinate right, final int target, final int maximum) {
    super(Sets.newHashSet(left, right), target, maximum);
  }

  @Override
  public void applyConstraint(final PuzzleAnswer answer) {

  }


  @Override
  protected int accumulate(final int current, final int solvedValue) {
    if (current >= solvedValue) {
      if (current % solvedValue == 0) {
        return current / solvedValue;
      }
    }
    else if (solvedValue % current == 0) {
      return solvedValue / current;
    }
    return -1;
  }


}
