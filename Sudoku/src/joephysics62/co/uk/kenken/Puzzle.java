package joephysics62.co.uk.kenken;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Stream;

import joephysics62.co.uk.kenken.constraint.Constraint;
import joephysics62.co.uk.kenken.grid.Coordinate;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

public class Puzzle {

  private final Multimap<Coordinate, Constraint> _coordsToConstraints = HashMultimap.create();
  private final int _height;

  public Puzzle(final int height, final Stream<Constraint> constraints) {
    _height = height;
    constraints.forEach(c -> c.getCoords().forEach(co -> _coordsToConstraints.put(co, c)));
  }

  public int getHeight() {
    return _height;
  }

  public Collection<Constraint> constraintsForCoord(final Coordinate coordinate) {
    return _coordsToConstraints.get(coordinate);
  }

  public Collection<Constraint> getConstraints() {
    return _coordsToConstraints.values();
  }

  public Set<Coordinate> getCoords() {
    return _coordsToConstraints.keySet();
  }

}
