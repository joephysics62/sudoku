package joephysics62.co.uk.sudokuNew;

import java.util.Set;
import java.util.stream.Stream;

public abstract class Restriction<C, V> {
  private final Set<C> _cellCoords;

  public Restriction(final Set<C> cellCoords) {
    _cellCoords = cellCoords;
  }

  protected Stream<C> coords() {
    return _cellCoords.stream();
  }

  protected abstract boolean isSatisfied(Puzzle<C, V> puzzle);

}
