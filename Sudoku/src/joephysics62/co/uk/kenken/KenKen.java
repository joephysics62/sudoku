package joephysics62.co.uk.kenken;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

public class KenKen {

  private final Multimap<Coordinate, Constraint> _coordsToConstraints = HashMultimap.create();

  public KenKen(final int height, final List<ArithmeticConstraint> arithmetics) {
    for (final ArithmeticConstraint arithmeticConstraint : arithmetics) {
      for (final Coordinate coordinate : arithmeticConstraint.getCoords()) {
        _coordsToConstraints.put(coordinate, arithmeticConstraint);
      }
    }
    IntStream.rangeClosed(1, height).forEach(axis -> {
      createUniquenessConstraints(height, col -> Coordinate.of(axis, col));
      createUniquenessConstraints(height, row -> Coordinate.of(row, axis));
    });
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

  private void createUniquenessConstraints(final int height, final IntFunction<Coordinate> intFunction) {
    final Set<Coordinate> lineCoords = IntStream.rangeClosed(1, height).mapToObj(intFunction).collect(Collectors.toSet());
    final UniqueConstraint uniqueConstraint = new UniqueConstraint(lineCoords);
    for (final Coordinate coord : lineCoords) {
      _coordsToConstraints.put(coord, uniqueConstraint);
    }
  }

}
