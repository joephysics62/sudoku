package joephysics62.co.uk.constraint.arithmetic;

import java.util.Set;
import java.util.function.IntBinaryOperator;
import java.util.stream.IntStream;

import joephysics62.co.uk.constraint.Constraint;
import joephysics62.co.uk.grid.Coordinate;

public class MultiplicationConstraint extends CommutingArithmeticConstraint {

  private MultiplicationConstraint(final Set<Coordinate> coords, final int target, final int maximum, final boolean isSub) {
    super(coords, target, maximum, isSub);
  }

  public MultiplicationConstraint(final Set<Coordinate> coords, final int target, final int maximum) {
    this(coords, target, maximum, false);
  }

  @Override
  protected IntStream eliminatedValues() {
    return IntStream
             .rangeClosed(1, Math.min(getTarget(), getMaximum()))
             .filter(x -> (getTarget() % x) != 0);
  }

  @Override
  protected IntBinaryOperator binaryOperator() {
    return (x, y) -> x * y;
  }

  @Override
  protected IntBinaryOperator inverseOperator() {
    return (x, y) -> x / y;
  }

  @Override
  protected int identity() {
    return 1;
  }

  @Override
  protected Constraint newSubConstraint(final Set<Coordinate> remainingCoords, final int remaining) {
    return new MultiplicationConstraint(remainingCoords, remaining, getMaximum(), true);
  }


}
