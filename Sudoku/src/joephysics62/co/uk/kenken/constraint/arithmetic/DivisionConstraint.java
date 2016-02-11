package joephysics62.co.uk.kenken.constraint.arithmetic;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.IntBinaryOperator;
import java.util.stream.IntStream;

import joephysics62.co.uk.kenken.Answer;
import joephysics62.co.uk.kenken.grid.Cell;
import joephysics62.co.uk.kenken.grid.Coordinate;


public class DivisionConstraint extends NonCommutingArithmeticConstraint {

  public DivisionConstraint(final Set<Coordinate> coords, final int target, final int maximum) {
    super(coords, target, maximum);
  }

  @Override
  protected IntBinaryOperator inverseOperator() {
    return (x, y) -> x * y;
  }

  @Override
  protected IntBinaryOperator binaryOperator() {
    return (x, y) -> x / y;
  }

  @Override
  protected IntStream eliminatedValues() {
    return IntStream
            .rangeClosed(getMaximum() / getTarget() + 1, getMaximum())
            .filter(x -> x % getTarget() != 0);
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
      final Set<Integer> newPossibles = new LinkedHashSet<>();
      final int solvedValue = first.getSolvedValue();
      final int other1 = inverseOperator().applyAsInt(solvedValue, getTarget()); // with s inv(OP) t  if <= max
      if (other1 <= getMaximum()) {
        newPossibles.add(other1);
      }
      if (solvedValue % getTarget() == 0) {
        newPossibles.add(binaryOperator().applyAsInt(solvedValue, getTarget()));  // with s OP t    if > 0 && if s % t == 0
      }
      second.retain(newPossibles);
    }
  }

}
