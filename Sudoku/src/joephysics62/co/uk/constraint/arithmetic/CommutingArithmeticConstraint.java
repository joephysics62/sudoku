package joephysics62.co.uk.constraint.arithmetic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import joephysics62.co.uk.constraint.Constraint;
import joephysics62.co.uk.grid.Cell;
import joephysics62.co.uk.grid.Coordinate;
import joephysics62.co.uk.kenken.Answer;

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
  protected final Stream<Coordinate> handlePartiallySolved(final Answer answer) {
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
        remainingCoords.forEach(co -> answer.cellAt(co).setInconsistent());
        return Stream.empty();
      }
      final Coordinate coord = remainingCoords.iterator().next();
      answer.cellAt(coord).setValue(remaining);
      return Stream.of(coord);
    }
    else if (!_isSub) {
      return newSubConstraint(remainingCoords, remaining).applyConstraint(answer);
    }
    else {
      return Stream.empty();
    }
  }

  protected abstract int identity();

  protected  abstract Constraint newSubConstraint(Set<Coordinate> remainingCoords, int remaining);

}