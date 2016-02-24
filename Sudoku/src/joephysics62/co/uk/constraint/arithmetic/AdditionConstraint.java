package joephysics62.co.uk.constraint.arithmetic;

import java.util.Set;
import java.util.function.IntBinaryOperator;
import java.util.stream.IntStream;

import joephysics62.co.uk.constraint.Constraint;
import joephysics62.co.uk.grid.Coordinate;

public class AdditionConstraint extends CommutingArithmeticConstraint {

  public AdditionConstraint(final Set<Coordinate> coords, final int target, final int maximum) {
    this(coords, target, maximum, false);
  }

  private AdditionConstraint(final Set<Coordinate> coords, final int target, final int maximum, final boolean isSub) {
    super(coords, target, maximum, isSub);
    if (target < numElements()) {
      throw new IllegalArgumentException("Target " + target + " is too small for " + coords.size() + " cells");
    }
    if (target > numElements() * getMaximum()) {
      throw new IllegalArgumentException("Target " + target + " is too large for " + coords.size() + " cells");
    }
  }

  @Override
  protected IntStream eliminatedValues() {
    final IntStream upperLimits = IntStream.rangeClosed(getTarget() - numElements() + 2, getMaximum());
    final IntStream lowerLimits = IntStream.rangeClosed(1, getTarget() - (numElements() - 1) * getMaximum() - 1);
    return IntStream.concat(upperLimits, lowerLimits);
  }

  @Override
  protected IntBinaryOperator binaryOperator() {
    return Integer::sum;
  }

  @Override
  protected int identity() {
    return 0;
  }

  @Override
  protected IntBinaryOperator inverseOperator() {
    return (x, y) -> x - y;
  }

  @Override
  protected Constraint newSubConstraint(final Set<Coordinate> remainingCoords, final int remaining) {
    return new AdditionConstraint(remainingCoords, remaining, getMaximum(), true);
  }

}
