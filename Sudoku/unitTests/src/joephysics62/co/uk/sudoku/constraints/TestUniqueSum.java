package joephysics62.co.uk.sudoku.constraints;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import joephysics62.co.uk.sudoku.model.Cell;
import joephysics62.co.uk.sudoku.model.CellGrid;
import joephysics62.co.uk.sudoku.model.Coord;
import joephysics62.co.uk.sudoku.model.Layout;

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

  private void runTests(int sumValue, int expected) {
    Layout layout = Layout.CLASSIC_SUDOKU;
    final int groupSize = 3;

    List<Coord> cells = new ArrayList<>();
    for (int i = 1; i <= groupSize; i++) {
      cells.add(Coord.of(1, i));
    }
    Map<Coord, Integer> map = new LinkedHashMap<>();
    for (Coord coord : cells) {
      map.put(coord, layout.getInitialValue());
    }

    UniqueSum uniqueSum = UniqueSum.of(sumValue, cells);
    final CellGrid cellGrid = new MockCellGrid(layout, map);
    uniqueSum.eliminateValues(cellGrid);
    for (Coord coord : cells) {
      Assert.assertEquals(expected, cellGrid.getCellValue(coord));
    }
  }

  private static class MockCellGrid implements CellGrid {

    private final Layout _layout;
    private final Map<Coord, Integer> _values;
    private MockCellGrid(final Layout layout, final Map<Coord, Integer> values) {
      _layout = layout;
      _values = values;
    }

    @Override
    public int getCellValue(Coord coord) {
      return _values.get(coord);
    }

    @Override
    public void setCellValue(int cellValues, Coord coord) {
      _values.put(coord, cellValues);
    }

    @Override
    public Layout getLayout() {
      return _layout;
    }

  }


}
