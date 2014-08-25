package joephysics62.co.uk.sudoku.model;

import java.util.Collections;
import java.util.Set;

public class CellGroup<T> {
  private final Set<Cell<T>> _cells;
  private final String _groupId;
  public CellGroup(final String groupId, Set<Cell<T>> cells) {
    _groupId = groupId;
    _cells = Collections.unmodifiableSet(cells);
  }
  public boolean isSolved() {
    for (Cell<T> cell : _cells) {
      if (!cell.isSolved()) {
        return false;
      }
    }
    return true;
  }
  public boolean isUnsolveable() {
    for (Cell<T> cell : _cells) {
      if (cell.isUnsolveable()) {
        return true;
      }
    }
    return true;
  }
  public Set<Cell<T>> getCells() {
    return _cells;
  }
  public String getGroupId() {
    return _groupId;
  }
}
