package joephysics62.co.uk.kenken.constraint.arithmetic;

import java.util.Set;
import java.util.stream.IntStream;

import joephysics62.co.uk.kenken.PuzzleAnswer;
import joephysics62.co.uk.kenken.constraint.CoordinateSetConstraint;
import joephysics62.co.uk.kenken.grid.Cell;
import joephysics62.co.uk.kenken.grid.Coordinate;

public abstract class ArithmeticConstraint extends CoordinateSetConstraint {
  private final int _target;
  private final int _maximum;

  public ArithmeticConstraint(final Set<Coordinate> coords, final int target, final int maximum) {
    super(coords);
    _target = target;
    _maximum = maximum;
  }

  public int getTarget() { return _target; }

  public int getMaximum() {
    return _maximum;
  }

  @Override
  public final void applyConstraint(final PuzzleAnswer answer) {
    final IntStream eliminations = eliminatedValues();
    eliminations
      .forEach(x -> cells(answer).forEach(c -> c.remove(x)));
    if (cells(answer).allMatch(Cell::isSolved)) {
      return;
    }
    if (cells(answer).anyMatch(Cell::isSolved)) {
      handlePartiallySolved(answer);
    }
  }

  protected abstract IntStream eliminatedValues();

  protected abstract void handlePartiallySolved(PuzzleAnswer answer);


}