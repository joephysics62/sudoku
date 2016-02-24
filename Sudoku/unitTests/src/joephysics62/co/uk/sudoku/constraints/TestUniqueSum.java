package joephysics62.co.uk.sudoku.constraints;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import joephysics62.co.uk.old.constraints.UniqueSum;
import joephysics62.co.uk.old.grid.Coord;
import joephysics62.co.uk.old.sudoku.model.Cell;
import joephysics62.co.uk.old.sudoku.model.PuzzleGrid;
import joephysics62.co.uk.old.sudoku.model.PuzzleLayout;

import org.junit.Assert;
import org.junit.Test;

public class TestUniqueSum {

  @Test
  public void testClassicSudokuThreeSizeElimination() {
    runTests(5, 0);
    runTests(6, expectedBitwise(1, 2, 3));
    runTests(7, expectedBitwise(1, 2, 4));
    runTests(9, expectedBitwise(1, 2, 3, 4, 5, 6));

    runTests(21, expectedBitwise(4, 5, 6, 7, 8, 9));
    runTests(23, expectedBitwise(6, 8, 9));
    runTests(24, expectedBitwise(7, 8, 9));
    runTests(25, 0);
  }

  private static int expectedBitwise(final int... nonBitwises) {
    int out = 0;
    for (final int nonBitwise : nonBitwises) {
      out += Cell.cellValueAsBitwise(nonBitwise);
    }
    return out;
  }

  private void runTests(final int sumValue, final Integer expected) {
    final PuzzleLayout layout = PuzzleLayout.CLASSIC_SUDOKU;
    final int groupSize = 3;

    final List<Coord> cells = new ArrayList<>();
    for (int i = 1; i <= groupSize; i++) {
      cells.add(Coord.of(1, i));
    }
    final Map<Coord, Integer> map = new LinkedHashMap<>();
    for (final Coord coord : cells) {
      map.put(coord, layout.getInitialValue());
    }

    final UniqueSum uniqueSum = UniqueSum.of(sumValue, 9, cells);
    final PuzzleGrid cellGrid = new MockCellGrid(layout, map);
    uniqueSum.eliminateValues(cellGrid);
    for (final Coord coord : cells) {
      Assert.assertEquals(expected, cellGrid.get(coord));
    }
  }

  private static class MockCellGrid implements PuzzleGrid {

    private final PuzzleLayout _layout;
    private final Map<Coord, Integer> _values;
    private MockCellGrid(final PuzzleLayout layout, final Map<Coord, Integer> values) {
      _layout = layout;
      _values = values;
    }

    @Override
    public Integer get(final Coord coord) {
      return _values.get(coord);
    }

    @Override
    public void set(final Integer cellValues, final Coord coord) {
      _values.put(coord, cellValues);
    }

    @Override
    public PuzzleLayout getLayout() {
      return _layout;
    }

    @Override
    public Iterator<Coord> iterator() {
      return _values.keySet().iterator();
    }

  }


}
