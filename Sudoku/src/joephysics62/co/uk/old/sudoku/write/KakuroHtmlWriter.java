package joephysics62.co.uk.old.sudoku.write;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import joephysics62.co.uk.old.constraints.Constraint;
import joephysics62.co.uk.old.constraints.UniqueSum;
import joephysics62.co.uk.old.grid.Coord;
import joephysics62.co.uk.old.sudoku.model.Puzzle;

public class KakuroHtmlWriter extends PuzzleHtmlWriter {

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

  public KakuroHtmlWriter(final Puzzle puzzle) throws URISyntaxException {
    super(puzzle, TEMPLATE_FTL);
  }


  @Override
  protected void addPuzzleSpecificParams(final Map<String, Object> root, final Puzzle puzzle) {
   // nothing here.
  }

  @Override
  protected List<List<Object>> generateTable() {
    final List<List<Object>> table = new ArrayList<>();
    final Map<Coord, Integer> acrosses = new LinkedHashMap<>();
    final Map<Coord, Integer> downs = new LinkedHashMap<>();
    for (final Constraint constraint : getPuzzle().getAllConstraints()) {
      if (constraint instanceof UniqueSum) {
        final UniqueSum uniqueSum = (UniqueSum) constraint;
        final int sum = uniqueSum.getSum();
        final Set<Integer> rows = new LinkedHashSet<>();
        final Set<Integer> cols = new LinkedHashSet<>();
        for (final Coord coord : uniqueSum.getCells()) {
          rows.add(coord.getRow());
          cols.add(coord.getCol());
        }
        if (rows.size() == 1) {
          // is an across
          final List<Integer> colsAsList = new ArrayList<>(cols);
          Collections.sort(colsAsList);
          final Integer firstCol = colsAsList.get(0);
          acrosses.put(Coord.of(rows.iterator().next(), firstCol - 1), sum);
        }
        else if (cols.size() == 1) {
          // is a down
          final List<Integer> rowsAsList = new ArrayList<>(rows);
          Collections.sort(rowsAsList);
          final Integer firstRow = rowsAsList.get(0);
          downs.put(Coord.of(firstRow - 1, cols.iterator().next()), sum);
        }
      }
      else {
        throw new UnsupportedOperationException(constraint.getClass().toString());
      }
    }
    for (int rowNum = 0; rowNum <= getPuzzle().getLayout().getHeight(); rowNum++) {
      final List<Object> rowOut = new ArrayList<>();
      for (int colNum = 0; colNum <= getPuzzle().getLayout().getWidth(); colNum++) {
        final boolean hasValue;
        final Coord coord = Coord.of(rowNum, colNum);
        if (colNum > 0 && rowNum > 0) {
          hasValue = getPuzzle().get(coord) > 0;
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
