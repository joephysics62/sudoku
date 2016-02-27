package joephysics62.co.uk.constraint;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import joephysics62.co.uk.grid.Cell;
import joephysics62.co.uk.grid.Coordinate;
import joephysics62.co.uk.kenken.Answer;

public class UniqueConstraint extends CoordinateSetConstraint {

  public UniqueConstraint(final Set<Coordinate> coords) {
    super(coords);
  }

  @Override
  public Stream<Coordinate> applyConstraint(final Answer answer) {
    final Map<Coordinate, Integer> solvedCells = new LinkedHashMap<>();
    for (final Coordinate coordinate : getCoords()) {
      final Cell cellUnderConstraint = answer.cellAt(coordinate);
      if (cellUnderConstraint.isSolved()) {
        solvedCells.put(coordinate, cellUnderConstraint.getPossibles().iterator().next());
      }
    }
    return getCoords()
      .stream()
      .filter(co -> !solvedCells.containsKey(co) && answer.cellAt(co).removeAll(solvedCells.values()));
  }

  @Override
  public boolean isSatisfiedBy(final Answer answer) {
    final Set<Integer> values = new LinkedHashSet<>();
    for (final Coordinate coordinate: getCoords()) {
      final Cell cell = answer.cellAt(coordinate);
      if (cell.isSolved()) {
        if (!values.add(cell.getPossibles().iterator().next())) {
          return false;
        }
      }
    }
    return true;
  }
}