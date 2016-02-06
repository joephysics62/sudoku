package joephysics62.co.uk.kenken;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import joephysics62.co.uk.kenken.grid.Cell;
import joephysics62.co.uk.kenken.grid.Coordinate;

public class PuzzleAnswer {
  private final Map<Coordinate, Cell> _valueGrid = new LinkedHashMap<>();
  private final Map<Coordinate, Cell> _unsolvedGrid = new LinkedHashMap<>();

  public PuzzleAnswer(final Set<Coordinate> coords, final int maximum) {
    for (final Coordinate coordinate : coords) {
      final Cell cell = Cell.unsolvedCell(maximum);
      _valueGrid.put(coordinate, cell);
      _unsolvedGrid.put(coordinate, cell);
    }
  }

  public boolean isSolved() {
    return !_unsolvedGrid.isEmpty();
  }

  public Optional<Cell> bestUnsolved() {
    return unsolvedCells()
             .min((left, right) -> Integer.compare(left.numberOfPossibles(), right.numberOfPossibles()));
  }

  public Cell cellAt(final Coordinate coord) {
    return _valueGrid.get(coord);
  }

  public Set<Integer> possiblesAt(final int row, final int col) {
    return _valueGrid.get(Coordinate.of(row, col)).getPossibles();
  }

  public void setSolvedValue(final Coordinate coord, final Integer value) {
    _valueGrid.get(coord).setValue(value);
    _unsolvedGrid.remove(coord);
  }

  private Stream<Cell> cells() {
    return _valueGrid.values().stream();
  }

  private Stream<Cell> unsolvedCells() {
    return _unsolvedGrid.values().stream();
  }

  public boolean isInconsistent() {
    return cells().anyMatch(Cell::isInconsistent);
  }
}
