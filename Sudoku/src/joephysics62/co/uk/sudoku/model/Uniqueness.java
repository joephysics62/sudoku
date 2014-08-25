package joephysics62.co.uk.sudoku.model;

import java.util.LinkedHashSet;
import java.util.Set;

public class Uniqueness<T> implements Restriction<T> {

  private final CellGroup<T> _group;

  private Uniqueness(CellGroup<T> group) {
    _group = group;
  }

  public static <T> Uniqueness<T> of(CellGroup<T> group) {
    return new Uniqueness<T>(group);
  }

  @Override
  public boolean satisfied() {
    final Set<T> solvedValues = new LinkedHashSet<>();
    for (Cell<T> cell : _group.getCells()) {
      if (cell.isSolved()) {
        if (!solvedValues.add(cell.getValue())) {
          return false;
        }
      }
    }
    return true;
  }

  @Override
  public Set<Cell<T>> eliminateValues() {
    final Set<Cell<T>> changedCells = new LinkedHashSet<>();
    for (Cell<T> cell : _group.getCells()) {
      if (cell.isSolved()) {
        for (Cell<T> cellInner : _group.getCells()) {
          if (cellInner != cell) {
            if (cellInner.getCurrentValues().remove(cell.getValue())) {
              changedCells.add(cellInner);
            }
          }
        }
      }
    }
    return changedCells;
  }

}
