package joephysics62.co.uk.old.sudokuNew;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class Puzzle<C, V> {
  private final Map<C, Cell<V>> _state;

  public Puzzle(final Map<C, V> startClues, final Stream<C> allCoords) {
    _state = allCoords
      .collect(Collectors.toMap(c -> c, c -> startClues.containsKey(c) ? newCell(startClues.get(c)) : newDefaultCell()));
  }

  public Optional<Cell<V>> getCell(final C coord) {
    return Optional.of(_state.get(coord));
  }

  protected abstract Cell<V> newDefaultCell();

  protected abstract Cell<V> newCell(V given);

  protected abstract Set<Restriction<C, V>> fixedRestrictions();
}
