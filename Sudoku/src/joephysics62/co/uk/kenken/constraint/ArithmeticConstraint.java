package joephysics62.co.uk.kenken.constraint;

import java.util.Set;

import joephysics62.co.uk.kenken.PuzzleAnswer;
import joephysics62.co.uk.kenken.grid.Cell;
import joephysics62.co.uk.kenken.grid.Coordinate;

public abstract class ArithmeticConstraint implements Constraint {
  private final Set<Coordinate> _coords;
  private final int _target;
  private final int _maximum;

  public ArithmeticConstraint(final Set<Coordinate> coords, final int target, final int maximum) {
    _coords = coords;
    _target = target;
    _maximum = maximum;
  }

  @Override
  public Set<Coordinate> getCoords() { return _coords; }

  public int getTarget() { return _target; }

  public int getMaximum() {
    return _maximum;
  }

  @Override
  public boolean isSatisfiedBy(final PuzzleAnswer answer) {
    int value = 0;
    for (final Coordinate coordinate : getCoords()) {
      final Cell cell = answer.cellAt(coordinate);
      if (cell.isUnsolved()) {
        return true;
      }
      final int solvedValue = cell.getSolvedValue();
      if (value == 0) {
        value = solvedValue;
      }
      else {
        value = accumulate(value, solvedValue);
      }
    }
    return Math.abs(value) == getTarget();
  }

  protected abstract int accumulate(int current, int solvedValue);

}