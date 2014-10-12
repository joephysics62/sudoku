package joephysics62.co.uk.sudoku.read;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import joephysics62.co.uk.sudoku.builder.ArrayBuilder;
import joephysics62.co.uk.sudoku.constraints.UniqueSum;
import joephysics62.co.uk.sudoku.model.Coord;
import joephysics62.co.uk.sudoku.model.Layout;
import joephysics62.co.uk.sudoku.model.Puzzle;
import joephysics62.co.uk.sudoku.read.html.HTMLTableParser;
import joephysics62.co.uk.sudoku.read.html.TableParserHandler;

import org.apache.log4j.Logger;

public class KakuroHTMLReader implements Reader {

  private static final Logger LOG = Logger.getLogger(KakuroHTMLReader.class);

  private final Layout _layout;

  public KakuroHTMLReader(final Layout layout) {
    _layout = layout;
  }

  @Override
  public Puzzle read(File input) throws IOException {
    HTMLTableParser tableParser = new HTMLTableParser(_layout.getHeight() + 1, _layout.getWidth() + 1);
    final ArrayBuilder kakuroBuilder = new ArrayBuilder(_layout);
    final int[][] acrosses = new int[_layout.getHeight() + 1][_layout.getWidth() + 1];
    final int[][] downs = new int[_layout.getWidth() + 1][_layout.getHeight() + 1];
    tableParser.parseTable(input, new TableParserHandler() {
      @Override
      public void title(String title) {
      }

      @Override
      public void cell(final Map<String, String> complexCellInput, Set<String> classValues, final int rowIndex, final int colIndex) {
        LOG.debug(String.format("At html table (%s, %s) found cell with classes %s and complex content %s", rowIndex, colIndex, classValues, complexCellInput));
        boolean isNonValue = checkForNonValue(kakuroBuilder, classValues, rowIndex, colIndex);
        if (!isNonValue) {
          throw new RuntimeException();
        }
        String acrossValue = complexCellInput.get("across");
        acrosses[rowIndex][colIndex] = null == acrossValue ? - 1 : Integer.valueOf(acrossValue);
        String downValue = complexCellInput.get("down");
        downs[colIndex][rowIndex] = null == downValue ? -1 : Integer.valueOf(downValue);
      }

      private boolean checkForNonValue(final ArrayBuilder kakuroBuilder, Set<String> classValues, final int rowIndex, final int colIndex) {
        if (rowIndex > 0 && colIndex > 0) {
          if (classValues.contains("noValue")) {
            kakuroBuilder.addNonValueCell(Coord.of(rowIndex, colIndex));
            return true;
          }
          else {
            return false;
          }
        }
        return true;
      }

      @Override
      public void cell(final String cellInput, Set<String> classValues, final int rowIndex, final int colIndex) {
        if (!cellInput.isEmpty()) {
          throw new UnsupportedOperationException();
        }
        LOG.debug(String.format("At html table (%s, %s) found cell with classes %s and simple cell content '%s'", rowIndex, colIndex, classValues, cellInput));
        boolean isNonValue = checkForNonValue(kakuroBuilder, classValues, rowIndex, colIndex);
        if (isNonValue) {
          acrosses[rowIndex][colIndex] = -1;
          downs[colIndex][rowIndex] = -1;
        }
        else {
          // TODO: this is just to initialise the givens. Shouldn't need to do this!
          kakuroBuilder.addGiven(null, Coord.of(rowIndex, colIndex));
        }
      }
    });
    addConstraints(kakuroBuilder, acrosses, true);
    addConstraints(kakuroBuilder, downs, false);
    return kakuroBuilder.build();
  }

  private void addConstraints(final ArrayBuilder kakuroBuilder, final int[][] directionSumGrid, boolean isAcross) {
    int lineNum = 0;
    for (int[] line : directionSumGrid) {
      int cellNum = 0;
      List<Coord> group = new ArrayList<>();
      int currentSum = 0;
      for (int downSum : line) {
        final Coord coord = isAcross ? Coord.of(lineNum, cellNum) : Coord.of(cellNum, lineNum);
        if (downSum != 0) {
          if (!group.isEmpty()) {
            kakuroBuilder.addConstraint(UniqueSum.of(currentSum, group));
          }
          currentSum = downSum;
          group.clear();
        }
        else {
          group.add(coord);
        }
        cellNum++;
      }
      if (!group.isEmpty()) {
        kakuroBuilder.addConstraint(UniqueSum.of(currentSum, group));
        group.clear();
      }
      lineNum++;
    }
  }

}
