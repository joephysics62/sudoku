package joephysics62.co.uk.sudoku.read;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import joephysics62.co.uk.constraints.UniqueSum;
import joephysics62.co.uk.grid.Coord;
import joephysics62.co.uk.grid.Grid;
import joephysics62.co.uk.grid.GridLayout;
import joephysics62.co.uk.sudoku.builder.ArrayBuilder;
import joephysics62.co.uk.sudoku.model.Puzzle;
import joephysics62.co.uk.sudoku.model.PuzzleLayout;
import joephysics62.co.uk.sudoku.read.html.HTMLTableParser;
import joephysics62.co.uk.sudoku.read.html.HTMLTableParser.InputCell;

import org.apache.log4j.Logger;

public class KakuroHtmlReader implements PuzzleHtmlReader {

  private static final Logger LOG = Logger.getLogger(KakuroHtmlReader.class);

  private final PuzzleLayout _layout;

  public KakuroHtmlReader(final PuzzleLayout layout) {
    _layout = layout;
  }

  @Override
  public Puzzle read(File input) throws IOException {
    final GridLayout extended = new GridLayout(_layout.getHeight() + 1, _layout.getWidth() + 1);
    HTMLTableParser tableParser = new HTMLTableParser(extended);
    final ArrayBuilder kakuroBuilder = new ArrayBuilder(_layout);
    final int[][] acrosses = new int[_layout.getHeight() + 1][_layout.getWidth() + 1];
    final int[][] downs = new int[_layout.getWidth() + 1][_layout.getHeight() + 1];

    final Grid<InputCell> table = tableParser.parseTable(input);
    for (Coord coord : table) {
      final InputCell inputCell = table.get(coord);
      final Set<String> classValues = inputCell.getClasses();
      final Map<String, String> complexCellInput = inputCell.getComplexValue();
      final String textValue = inputCell.getTextValue();
      if (!complexCellInput.isEmpty()) {
        LOG.debug(String.format("At html table (%s) found cell with classes %s and complex content %s", coord, classValues, complexCellInput));
        boolean isNonValue = checkForNonValue(kakuroBuilder, classValues, coord);
        if (!isNonValue) {
          throw new RuntimeException();
        }
        String acrossValue = complexCellInput.get("across");
        acrosses[coord.getRow() - 1][coord.getCol() - 1] = null == acrossValue ? - 1 : Integer.valueOf(acrossValue);
        String downValue = complexCellInput.get("down");
        downs[coord.getCol() - 1][coord.getRow() - 1] = null == downValue ? -1 : Integer.valueOf(downValue);
      }
      else {
        if (!textValue.isEmpty()) {
          throw new UnsupportedOperationException();
        }
        LOG.debug(String.format("At html table (%s) found cell with classes %s and simple cell content '%s'", coord, classValues, textValue));
        boolean isNonValue = checkForNonValue(kakuroBuilder, classValues, coord);
        if (isNonValue) {
          acrosses[coord.getRow() - 1][coord.getCol() - 1] = -1;
          downs[coord.getCol() - 1][coord.getRow() - 1] = -1;
        }
        else {
          // TODO: this is just to initialise the givens. Shouldn't need to do this!
          kakuroBuilder.addGiven(null, coord);
        }
      }
    }
    addConstraints(kakuroBuilder, acrosses, true);
    addConstraints(kakuroBuilder, downs, false);
    kakuroBuilder.addTitle("Kakuro");
    return kakuroBuilder.build();
  }

  private boolean checkForNonValue(final ArrayBuilder kakuroBuilder, Set<String> classValues, final Coord coord) {
    if (coord.getRow() > 0 && coord.getCol() > 0) {
      if (classValues.contains("noValue")) {
        kakuroBuilder.addNonValueCell(coord);
        return true;
      }
      else {
        return false;
      }
    }
    return true;
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
            kakuroBuilder.addConstraint(UniqueSum.of(currentSum, _layout.getInitialsSize(), group));
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
        kakuroBuilder.addConstraint(UniqueSum.of(currentSum, _layout.getInitialsSize(), group));
        group.clear();
      }
      lineNum++;
    }
  }

}
