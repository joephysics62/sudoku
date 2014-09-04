package joephysics62.co.uk.sudoku.model;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import joephysics62.co.uk.sudoku.constraints.GreaterThan;

import org.junit.Assert;
import org.junit.Test;

public class TestGreaterThan {

  @Test
  public void test() {
    final List<Integer> leftInits = Arrays.asList(1, 2, 3, 4, 5);
    final List<Integer> rightInits = Arrays.asList(1, 2, 3, 4, 5);

    Assert.assertTrue(eliminate(leftInits, rightInits));
  }

  private boolean eliminate(final List<Integer> leftInits, final List<Integer> rightInits) {
    final Coord left = new Coord(1, 2);
    final Coord right = new Coord(2, 2);
    final Cell<Integer> cellLeft = Cell.of(new LinkedHashSet<>(leftInits), left);
    final Cell<Integer> cellRight = Cell.of(new LinkedHashSet<>(rightInits), right);
    final GreaterThan<Integer> gt = GreaterThan.of(left, right);
    return gt.eliminateValues(new CellGrid<Integer>() {
      @Override
      public Cell<Integer> getCell(Coord coord) {
        if (coord == left) {
          return cellLeft;
        }
        else if (coord == right) {
          return cellRight;
        }
        throw new UnsupportedOperationException();
      }
      @Override
      public Set<Integer> getInits() {
        throw new UnsupportedOperationException();
      }
    });
  }

}
