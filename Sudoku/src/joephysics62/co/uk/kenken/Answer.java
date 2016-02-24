package joephysics62.co.uk.kenken;

import java.io.PrintStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import joephysics62.co.uk.grid.Cell;
import joephysics62.co.uk.grid.Coordinate;

public class Answer implements Cloneable {
  private final Map<Coordinate, Cell> _valueGrid = new LinkedHashMap<>();
  private final Map<Coordinate, Cell> _unsolvedGrid = new LinkedHashMap<>();
  private int _maximum;

  public Answer(final Set<Coordinate> coords, final int maximum) {
    _maximum = maximum;
    for (final Coordinate coordinate : coords) {
      final Cell cell = Cell.unsolvedCell(maximum);
      _valueGrid.put(coordinate, cell);
      _unsolvedGrid.put(coordinate, cell);
    }
  }

  private Answer() {

  }

  public boolean isSolved() {
    return _unsolvedGrid.isEmpty();
  }

  public Optional<Coordinate> bestUnsolved() {
    int minPossibles = Integer.MAX_VALUE;
    Optional<Coordinate> best = Optional.empty();
    for (final Entry<Coordinate, Cell> entry : _unsolvedGrid.entrySet()) {
      final int size = entry.getValue().getPossibles().size();
      if (size == 2) {
        return Optional.of(entry.getKey());
      }
      if (size < minPossibles) {
        minPossibles = size;
        best = Optional.of(entry.getKey());
      }
    }
    return best;
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

  public boolean isInconsistent() {
    return cells().anyMatch(Cell::isInconsistent);
  }

  @Override
  public Answer clone() {
    final Answer newAnswer = new Answer();
    _valueGrid.forEach((co, c) -> newAnswer._valueGrid.put(co, c.clone()));
    _unsolvedGrid.forEach((co, c) -> newAnswer._unsolvedGrid.put(co, c.clone()));
    newAnswer._maximum = _maximum;
    return newAnswer;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    _valueGrid.keySet()
      .stream()
      .sorted()
      .forEach(co -> sb.append(co + ": " + _valueGrid.get(co) + "\n"));
    return sb.toString();
  }

  public void writeAsGrid(final PrintStream printStream) {
    for (int row = 1; row <= _maximum; row++) {
      for (int col = 1; col <= _maximum; col++) {
        final Cell cellAt = cellAt(Coordinate.of(row, col));
        printStream.print(cellAt.isSolved() ? cellAt.getSolvedValue() : "?");
        printStream.print(" ");
      }
      printStream.println();
    }
  }
}
