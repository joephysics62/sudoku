package joephysics62.co.uk.kenken;

import java.util.Set;


public class UniqueConstraint implements Constraint {
  private final Set<Coordinate> _coords;

  public UniqueConstraint(final Set<Coordinate> coords) {
    _coords = coords;
  }

  @Override
  public Set<Coordinate> getCoords() {
    return _coords;
  }

  public void applyConstraint(final PuzzleAnswer answer) {
    for (final Coordinate coordinate : _coords) {
      final Cell cellUnderConstraint = answer.cellAt(coordinate);
    }
  }
}
