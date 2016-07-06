package joephysics62.co.uk.lexica;

import java.util.List;

import joephysics62.co.uk.grid.Coordinate;

public class WordLexica {
  private final List<Coordinate> _cells;

  public WordLexica(final List<Coordinate> cells) {
    _cells = cells;
  }

  public List<Coordinate> getCells() {
    return _cells;
  }

  @Override
  public String toString() {
    return String.format("WordLexica(cells=%s)", _cells);
  }

}
