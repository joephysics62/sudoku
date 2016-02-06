package joephysics62.co.uk.kenken.constraint;

import java.util.Set;
import java.util.stream.Stream;

import joephysics62.co.uk.kenken.PuzzleAnswer;
import joephysics62.co.uk.kenken.grid.Cell;
import joephysics62.co.uk.kenken.grid.Coordinate;

public abstract class CoordinateSetConstraint implements Constraint {
  private final Set<Coordinate> _coords;

  public CoordinateSetConstraint(final Set<Coordinate> coords) {
    _coords = coords;
  }

  @Override
  public final int numElements() {
    return _coords.size();
  }

  @Override
  public final Set<Coordinate> getCoords() {
    return _coords;
  }

  @Override
  public Stream<Coordinate> coords() {
    return _coords.stream();
  }

  @Override
  public Stream<Cell> cells(final PuzzleAnswer answer) {
    return coords().map(answer::cellAt);
  }
}
