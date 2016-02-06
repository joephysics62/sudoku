package joephysics62.co.uk.kenken.constraint.arithmetic;

import joephysics62.co.uk.kenken.PuzzleAnswer;
import joephysics62.co.uk.kenken.grid.Coordinate;

import com.google.common.collect.Sets;


public class SubtractionConstraint extends ArithmeticConstraint {

  public SubtractionConstraint(final Coordinate left, final Coordinate right, final int target, final int maximum) {
    super(Sets.newHashSet(left, right), target, maximum);
  }

  @Override
  public void applyConstraint(final PuzzleAnswer answer) {

  }

  @Override
  protected int accumulate(final int curr, final int solved) {
    return curr - solved;
  }

}
