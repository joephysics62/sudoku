package joephysics62.co.uk.kenken.constraint.arithmetic;

import java.util.Iterator;
import java.util.Set;
import java.util.stream.Stream;

import joephysics62.co.uk.kenken.Answer;
import joephysics62.co.uk.kenken.grid.Cell;
import joephysics62.co.uk.kenken.grid.Coordinate;

public abstract class NonCommutingArithmeticConstraint extends ArithmeticConstraint {

  private final Coordinate _left;
  private final Coordinate _right;

  public NonCommutingArithmeticConstraint(final Set<Coordinate> coords, final int target, final int maximum) {
    super(coords, target, maximum);
    final Iterator<Coordinate> iterator = coords.iterator();
    _left = iterator.next();
    _right = iterator.next();
  }

  protected Coordinate getLeft() { return _left; }
  protected Coordinate getRight() { return _right; }

  @Override
  protected final Stream<Coordinate> handlePartiallySolved(final Answer answer) {
    return Stream.concat(
            check(getLeft(), getRight(), answer),
            check(getRight(), getLeft(), answer));
  }

  private final Stream<Coordinate> check(final Coordinate firstC, final Coordinate secondC, final Answer answer) {
    final Cell first = answer.cellAt(firstC);
    final Cell second = answer.cellAt(secondC);
    if (first.isSolved()) {
      final int solvedValue = first.getSolvedValue();
      final Set<Integer> newPossibles = newPossibles(solvedValue);
      if (second.retain(newPossibles)) {
        return Stream.of(secondC);
      }
    }
    return Stream.empty();
  }

  protected abstract Set<Integer> newPossibles(int solvedValue);

  @Override
  public final boolean isSatisfiedBy(final Answer answer) {
    if (cells(answer).anyMatch(Cell::isUnsolved)) {
      return true;
    }
    final int leftValue = answer.cellAt(_left).getSolvedValue();
    final int rightValue = answer.cellAt(_right).getSolvedValue();

    return inverseOperator().applyAsInt(rightValue, getTarget())  == leftValue
        || inverseOperator().applyAsInt(leftValue, getTarget()) == rightValue;
  }
}
