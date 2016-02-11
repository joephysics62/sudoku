package joephysics62.co.uk.kenken.constraint.arithmetic;

import java.util.Iterator;
import java.util.Set;

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
