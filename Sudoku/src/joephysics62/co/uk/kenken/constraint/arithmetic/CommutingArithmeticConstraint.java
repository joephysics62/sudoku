package joephysics62.co.uk.kenken.constraint.arithmetic;

import java.util.Set;
import java.util.stream.Collectors;

import joephysics62.co.uk.kenken.Answer;
import joephysics62.co.uk.kenken.constraint.Constraint;
import joephysics62.co.uk.kenken.grid.Cell;
import joephysics62.co.uk.kenken.grid.Coordinate;

public abstract class CommutingArithmeticConstraint extends ArithmeticConstraint {
  private final boolean _isSub;

  public CommutingArithmeticConstraint(final Set<Coordinate> coords, final int target, final int maximum, final boolean isSub) {
    super(coords, target, maximum);
    _isSub = isSub;
  }

  @Override
  public boolean isSatisfiedBy(final Answer answer) {
    if (cells(answer).anyMatch(Cell::isUnsolved)) {
      return true;
    }
    return getTarget() == cells(answer)
                            .mapToInt(Cell::getSolvedValue)
                            .reduce(identity(), binaryOperator());
  }

  @Override
  protected final void handlePartiallySolved(final Answer answer) {
    final int solvedReduction = cells(answer)
        .filter(Cell::isSolved)
        .mapToInt(Cell::getSolvedValue)
        .reduce(identity(), binaryOperator());
    final int remaining = inverseOperator().applyAsInt(getTarget(), solvedReduction);
    final Set<Coordinate> remainingCoords = coords()
                                              .filter(co -> answer.cellAt(co).isUnsolved())
                                              .collect(Collectors.toSet());
    if (remainingCoords.isEmpty()) {
      throw new IllegalArgumentException();
    }
    if (remainingCoords.size() == 1) {
      if (remaining > getMaximum()) {
        throw new RuntimeException();
      }
      answer.cellAt(remainingCoords.iterator().next()).setValue(remaining);
    }
    else if (!_isSub) {
      newSubConstraint(remainingCoords, remaining).applyConstraint(answer);
    }
  }

  protected abstract int identity();

  protected  abstract Constraint newSubConstraint(Set<Coordinate> remainingCoords, int remaining);

}