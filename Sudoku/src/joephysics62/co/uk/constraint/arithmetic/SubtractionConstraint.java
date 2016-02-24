package joephysics62.co.uk.constraint.arithmetic;

import java.util.Set;
import java.util.function.IntBinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import joephysics62.co.uk.grid.Coordinate;


public class SubtractionConstraint extends NonCommutingArithmeticConstraint {

  public SubtractionConstraint(final Set<Coordinate> coords, final int target, final int maximum) {
    super(coords, target, maximum);
  }

  @Override
  protected IntBinaryOperator inverseOperator() {
    return Integer::sum;
  }

  @Override
  protected IntBinaryOperator binaryOperator() {
    return (x, y) -> x - y;
  }

  @Override
  protected IntStream eliminatedValues() {
    return IntStream.rangeClosed(getMaximum() - getTarget() + 1, getTarget());
  }

  @Override
  protected Set<Integer> newPossibles(final int solvedValue) {
    final Set<Integer> newPossibles = IntStream
      .of(solvedValue - getTarget(), solvedValue + getTarget()) // s OP t   or s INV(OP) t
      .filter(x -> x > 0 && x <= getMaximum())                  // if > 0    if <= maximum
      .boxed()
      .collect(Collectors.toSet());
    return newPossibles;
  }


}
