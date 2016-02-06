package joephysics62.co.uk.kenken.constraint;

import java.util.Set;

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
}
