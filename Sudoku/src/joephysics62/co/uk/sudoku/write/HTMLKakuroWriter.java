package joephysics62.co.uk.sudoku.write;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import joephysics62.co.uk.sudoku.constraints.Constraint;
import joephysics62.co.uk.sudoku.constraints.UniqueSum;
import joephysics62.co.uk.sudoku.model.Coord;
import joephysics62.co.uk.sudoku.model.Layout;
import joephysics62.co.uk.sudoku.model.Puzzle;

public class HTMLKakuroWriter extends HTMLWriter {

  public static class KakuroCell {
    private final Integer _across;
    private final Integer _down;
    private final boolean _isValue;

    public KakuroCell(final Integer across, final Integer down, final boolean hasValue) {
      _across = across;
      _down = down;
      _isValue = hasValue;
    }

    public Integer getAcross() {
      return _across;
    }
    public Integer getDown() {
      return _down;
    }
    public Boolean getValue() {
      return _isValue;
    }

  }

  private static final String TEMPLATE_FTL = "kakuroTemplate.ftl";

  public HTMLKakuroWriter(Puzzle puzzle) throws URISyntaxException {
    super(puzzle, TEMPLATE_FTL);
  }


  @Override
  protected void addPuzzleSpecificParams(Map<String, Object> root, Layout layout) {
   // nothing here.
  }

  @Override
  protected List<List<Object>> generateTable() {
    final List<List<Object>> table = new ArrayList<>();
    Map<Coord, Integer> acrosses = new LinkedHashMap<>();
    Map<Coord, Integer> downs = new LinkedHashMap<>();
    for (Constraint constraint : getPuzzle().getAllConstraints()) {
      if (constraint instanceof UniqueSum) {
        UniqueSum uniqueSum = (UniqueSum) constraint;
        int sum = uniqueSum.getSum();
        Set<Integer> rows = new LinkedHashSet<>();
        Set<Integer> cols = new LinkedHashSet<>();
        for (Coord coord : uniqueSum.getCells()) {
          rows.add(coord.getRow());
          cols.add(coord.getCol());
        }
        if (rows.size() == 1) {
          // is an across
          List<Integer> colsAsList = new ArrayList<>(cols);
          Collections.sort(colsAsList);
          Integer firstCol = colsAsList.get(0);
          acrosses.put(Coord.of(rows.iterator().next(), firstCol - 1), sum);
        }
        else if (cols.size() == 1) {
          // is a down
          List<Integer> rowsAsList = new ArrayList<>(rows);
          Collections.sort(rowsAsList);
          Integer firstRow = rowsAsList.get(0);
          downs.put(Coord.of(firstRow - 1, cols.iterator().next()), sum);
        }
      }
      else {
        throw new UnsupportedOperationException(constraint.getClass().toString());
      }
    }
    for (int rowNum = 0; rowNum <= getPuzzle().getLayout().getHeight(); rowNum++) {
      List<Object> rowOut = new ArrayList<>();
      for (int colNum = 0; colNum <= getPuzzle().getLayout().getWidth(); colNum++) {
        final boolean hasValue;
        final Coord coord = Coord.of(rowNum, colNum);
        if (colNum > 0 && rowNum > 0) {
          hasValue = getPuzzle().getCellValue(coord) > 0;
        }
        else {
          hasValue = false;
        }
        rowOut.add(new KakuroCell(acrosses.get(coord), downs.get(coord), hasValue));
      }
      table.add(rowOut);
    }
    return table;
  }

}
