package joephysics62.co.uk.constraint;

import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Stream;

import joephysics62.co.uk.grid.Cell;
import joephysics62.co.uk.grid.Coordinate;
import joephysics62.co.uk.kenken.Answer;

public abstract class CoordinateSetConstraint implements Constraint {
  private final Set<Coordinate> _coords;

  public CoordinateSetConstraint(final Set<Coordinate> coords) {
    _coords = coords;
  }

  protected final int numElements() {
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
  public Stream<Cell> cells(final Answer answer) {
    return coords().map(answer::cellAt);
  }

  @Override
  public void applyToCells(final Answer answer, final Consumer<? super Cell> action) {
    coords().map(answer::cellAt).forEach(action);
  }
}
