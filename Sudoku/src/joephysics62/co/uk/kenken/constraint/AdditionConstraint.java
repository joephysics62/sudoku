package joephysics62.co.uk.kenken.constraint;

import java.util.Set;

import joephysics62.co.uk.kenken.PuzzleAnswer;
import joephysics62.co.uk.kenken.grid.Coordinate;

public class AdditionConstraint extends ArithmeticConstraint {

  public AdditionConstraint(final Set<Coordinate> coords, final int target, final int maximum) {
    super(coords, target, maximum);
  }

  @Override
  public void applyConstraint(final PuzzleAnswer answer) {

  }

  @Override
  protected int accumulate(final int current, final int solvedValue) {
    return current + solvedValue;
  }


}
