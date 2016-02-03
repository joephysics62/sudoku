package joephysics62.co.uk.kenken.constraint;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import joephysics62.co.uk.kenken.PuzzleAnswer;
import joephysics62.co.uk.kenken.grid.Cell;
import joephysics62.co.uk.kenken.grid.Coordinate;


public class UniqueConstraint implements Constraint {
  private final Set<Coordinate> _coords;

  public UniqueConstraint(final Set<Coordinate> coords) {
    _coords = coords;
  }

  @Override
  public Set<Coordinate> getCoords() {
    return _coords;
  }

  @Override
  public void applyConstraint(final PuzzleAnswer answer) {
    final Map<Coordinate, Integer> solvedCells = new LinkedHashMap<>();
    for (final Coordinate coordinate : _coords) {
      final Cell cellUnderConstraint = answer.cellAt(coordinate);
      if (cellUnderConstraint.isSolved()) {
        solvedCells.put(coordinate, cellUnderConstraint.getPossibles().iterator().next());
      }
    }
    for (final Coordinate coordinate : _coords) {
      if (!solvedCells.containsKey(coordinate)) {
        answer.cellAt(coordinate).removeAll(solvedCells.values());
      }
    }
  }

  @Override
  public boolean isSatisfiedBy(final PuzzleAnswer answer) {
    final Set<Integer> values = new LinkedHashSet<>();
    for (final Coordinate coordinate: _coords) {
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
