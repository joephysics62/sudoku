package joephysics62.co.uk.kenken.constraint.arithmetic;

import java.util.Iterator;
import java.util.Set;
import java.util.stream.IntStream;

import joephysics62.co.uk.kenken.PuzzleAnswer;
import joephysics62.co.uk.kenken.grid.Cell;
import joephysics62.co.uk.kenken.grid.Coordinate;


public class SubtractionConstraint extends ArithmeticConstraint {

  private final Coordinate _left;
  private final Coordinate _right;

  public SubtractionConstraint(final Set<Coordinate> coords, final int target, final int maximum) {
    super(coords, target, maximum);
    final Iterator<Coordinate> iterator = coords.iterator();
    _left = iterator.next();
    _right = iterator.next();
  }

  @Override
  public boolean isSatisfiedBy(final PuzzleAnswer answer) {
    if (cells(answer).anyMatch(Cell::isUnsolved)) {
      return true;
    }
    final int leftValue = answer.cellAt(_left).getSolvedValue();
    final int rightValue = answer.cellAt(_right).getSolvedValue();

    return getTarget() == leftValue - rightValue || getTarget() == rightValue - leftValue;
  }

  @Override
  protected IntStream eliminatedValues() {
    return IntStream.rangeClosed(getMaximum() - getTarget() + 1, getTarget());
  }

  @Override
  protected void handlePartiallySolved(final PuzzleAnswer answer) {
    final Cell leftCell = answer.cellAt(_left);
    final Cell rightCell = answer.cellAt(_right);
    check(leftCell, rightCell);
    check(rightCell, leftCell);
  }

  private void check(final Cell first, final Cell second) {
    if (first.isSolved()) {
      final int solvedValue = first.getSolvedValue();
      final int secondValue = Math.abs(solvedValue - getTarget());
      if (secondValue < 1 || secondValue > getMaximum()) {
        first.setInconsistent();
        second.setInconsistent();
      }
      second.setValue(secondValue);
    }
  }


}
