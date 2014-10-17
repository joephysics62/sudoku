package joephysics62.co.uk.sudoku.constraints;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import joephysics62.co.uk.constraints.UniqueSum;
import joephysics62.co.uk.grid.Coord;
import joephysics62.co.uk.sudoku.model.Cell;
import joephysics62.co.uk.sudoku.model.PuzzleGrid;
import joephysics62.co.uk.sudoku.model.PuzzleLayout;

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
    for (int nonBitwise : nonBitwises) {
      out += Cell.cellValueAsBitwise(nonBitwise);
    }
    return out;
  }

  private void runTests(int sumValue, Integer expected) {
    PuzzleLayout layout = PuzzleLayout.CLASSIC_SUDOKU;
    final int groupSize = 3;

    List<Coord> cells = new ArrayList<>();
    for (int i = 1; i <= groupSize; i++) {
      cells.add(Coord.of(1, i));
    }
    Map<Coord, Integer> map = new LinkedHashMap<>();
    for (Coord coord : cells) {
      map.put(coord, layout.getInitialValue());
    }

    UniqueSum uniqueSum = UniqueSum.of(sumValue, 9, cells);
    final PuzzleGrid cellGrid = new MockCellGrid(layout, map);
    uniqueSum.eliminateValues(cellGrid);
    for (Coord coord : cells) {
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
    public Integer get(Coord coord) {
      return _values.get(coord);
    }

    @Override
    public void set(Integer cellValues, Coord coord) {
      _values.put(coord, cellValues);
    }

    @Override
    public PuzzleLayout getLayout() {
      return _layout;
    }

  }


}
