package joephysics62.co.uk.sudoku.model;

import java.util.LinkedHashSet;
import java.util.Set;

public class Uniqueness<T> implements Restriction<T> {

  @Override
  public boolean satisfiedBy(CellGroup<T> group) {
    final Set<T> solvedValues = new LinkedHashSet<>();
    for (Cell<T> cell : group.getCells()) {
      if (cell.isSolved()) {
        if (!solvedValues.add(cell.getValue())) {
          return false;
        }
      }
    }
    return true;
  }

  @Override
  public void eliminateValues(CellGroup<T> group) {
    for (Cell<T> cell : group.getCells()) {
      if (cell.isSolved()) {
        for (Cell<T> cellInner : group.getCells()) {
          if (cellInner != cell) {
            cellInner.getCurrentValues().remove(cell.getValue());
          }
        }
      }
    }
  }

}
