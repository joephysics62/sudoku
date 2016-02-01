package joephysics62.co.uk.kenken;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class PuzzleAnswer {
  private final Map<Coordinate, Cell> _valueGrid = new LinkedHashMap<>();

  public boolean isSolved() {
    return cells().allMatch(Cell::isSolved);
  }

  public Optional<Cell> bestUnsolved() {
    return cells()
             .filter(Cell::isUnsolved)
             .min((left, right) -> Integer.compare(left.numberOfPossibles(), right.numberOfPossibles()));
  }

  public Cell cellAt(final Coordinate coord) {
    return _valueGrid.get(coord);
  }

  private Stream<Cell> cells() {
    return _valueGrid.values().stream();
  }

  public boolean isInconsistent() {
    return cells().anyMatch(Cell::isInconsistent);
  }
}
