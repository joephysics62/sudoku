package joephysics62.co.uk.kenken.constraint.arithmetic;

import java.util.Set;
import java.util.function.IntBinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import joephysics62.co.uk.kenken.Answer;
import joephysics62.co.uk.kenken.grid.Cell;
import joephysics62.co.uk.kenken.grid.Coordinate;


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
  protected void handlePartiallySolved(final Answer answer) {
    check(getLeft(), getRight(), answer);
    check(getRight(), getLeft(), answer);
  }

  private void check(final Coordinate firstC, final Coordinate secondC, final Answer answer) {
    final Cell first = answer.cellAt(firstC);
    final Cell second = answer.cellAt(secondC);
    if (first.isSolved()) {
      final int solvedValue = first.getSolvedValue();
      final Set<Integer> newPossibles = IntStream
        .of(solvedValue - getTarget(), solvedValue + getTarget()) // s OP t   or s INV(OP) t
        .filter(x -> x > 0 && x <= getMaximum())                  // if > 0    if <= maximum
        .boxed()
        .collect(Collectors.toSet());
      second.retain(newPossibles);
    }
  }


}
