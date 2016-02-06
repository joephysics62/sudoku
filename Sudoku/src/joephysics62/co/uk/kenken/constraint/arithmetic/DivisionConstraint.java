package joephysics62.co.uk.kenken.constraint.arithmetic;

import java.util.Iterator;
import java.util.Set;
import java.util.stream.IntStream;

import joephysics62.co.uk.kenken.PuzzleAnswer;
import joephysics62.co.uk.kenken.grid.Cell;
import joephysics62.co.uk.kenken.grid.Coordinate;


public class DivisionConstraint extends ArithmeticConstraint {

  private final Coordinate _left;
  private final Coordinate _right;

  public DivisionConstraint(final Set<Coordinate> coords, final int target, final int maximum) {
    super(coords, target, maximum);
    final Iterator<Coordinate> iterator = coords.iterator();
    _left = iterator.next();
    _right = iterator.next();
  }

  @Override
  protected IntStream eliminatedValues() {
    return null;
  }

  @Override
  protected void handlePartiallySolved(final PuzzleAnswer answer) {

  }

  @Override
  public boolean isSatisfiedBy(final PuzzleAnswer answer) {
    if (cells(answer).anyMatch(Cell::isUnsolved)) {
      return true;
    }
    final int leftValue = answer.cellAt(_left).getSolvedValue();
    final int rightValue = answer.cellAt(_right).getSolvedValue();

    return rightValue * getTarget() == leftValue || leftValue * getTarget() == rightValue;
  }


}
