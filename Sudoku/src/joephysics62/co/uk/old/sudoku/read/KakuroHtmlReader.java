package joephysics62.co.uk.old.sudoku.read;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import joephysics62.co.uk.old.constraints.UniqueSum;
import joephysics62.co.uk.old.grid.Coord;
import joephysics62.co.uk.old.grid.Grid;
import joephysics62.co.uk.old.grid.GridLayout;
import joephysics62.co.uk.old.sudoku.builder.ArrayBuilder;
import joephysics62.co.uk.old.sudoku.model.Puzzle;
import joephysics62.co.uk.old.sudoku.model.PuzzleLayout;
import joephysics62.co.uk.old.sudoku.read.html.HTMLTableParser;
import joephysics62.co.uk.old.sudoku.read.html.HTMLTableParser.InputCell;

import org.apache.log4j.Logger;

public class KakuroHtmlReader implements PuzzleReader {

  private static final Logger LOG = Logger.getLogger(KakuroHtmlReader.class);

  private final PuzzleLayout _layout;

  public KakuroHtmlReader(final PuzzleLayout layout) {
    _layout = layout;
  }

  @Override
  public Puzzle read(final File input) throws IOException {
    final GridLayout extended = new GridLayout(_layout.getHeight() + 1, _layout.getWidth() + 1);
    final HTMLTableParser tableParser = new HTMLTableParser(extended);
    final ArrayBuilder kakuroBuilder = new ArrayBuilder(_layout);
    final int[][] acrosses = new int[_layout.getHeight() + 1][_layout.getWidth() + 1];
    final int[][] downs = new int[_layout.getWidth() + 1][_layout.getHeight() + 1];

    final Grid<InputCell> table = tableParser.parseTable(input);
    for (final Coord inputCoord : table) {
      final InputCell inputCell = table.get(inputCoord);
      handleInputCell(kakuroBuilder, acrosses, downs, inputCoord, inputCell);
    }
    addConstraints(kakuroBuilder, acrosses, true);
    addConstraints(kakuroBuilder, downs, false);
    kakuroBuilder.addTitle("Kakuro");
    return kakuroBuilder.build();
  }

  private void handleInputCell(final ArrayBuilder kakuroBuilder, final int[][] acrosses, final int[][] downs, final Coord inputCoord, final InputCell inputCell) {
    final Set<String> classValues = inputCell.getClasses();
    final Map<String, String> complexCellInput = inputCell.getComplexValue();
    final String textValue = inputCell.getTextValue();
    if (!complexCellInput.isEmpty()) {
      LOG.debug(String.format("At html table (%s) found cell with classes %s and complex content %s", inputCoord, classValues, complexCellInput));
      final boolean isNonValue = checkForNonValue(kakuroBuilder, classValues, inputCoord);
      if (!isNonValue) {
        throw new RuntimeException();
      }
      final String acrossValue = complexCellInput.get("across");
      acrosses[inputCoord.getRow() - 1][inputCoord.getCol() - 1] = null == acrossValue ? - 1 : Integer.valueOf(acrossValue);
      final String downValue = complexCellInput.get("down");
      downs[inputCoord.getCol() - 1][inputCoord.getRow() - 1] = null == downValue ? -1 : Integer.valueOf(downValue);
    }
    else {
      if (!textValue.isEmpty()) {
        throw new UnsupportedOperationException();
      }
      LOG.debug(String.format("At html table (%s) found cell with classes %s and simple cell content '%s'", inputCoord, classValues, textValue));
      final boolean isNonValue = checkForNonValue(kakuroBuilder, classValues, inputCoord);
      if (isNonValue) {
        acrosses[inputCoord.getRow() - 1][inputCoord.getCol() - 1] = -1;
        downs[inputCoord.getCol() - 1][inputCoord.getRow() - 1] = -1;
      }
      else {
        // TODO: this is just to initialise the givens. Shouldn't need to do this!
        kakuroBuilder.addGiven(null, inputCoord);
      }
    }
  }

  private boolean checkForNonValue(final ArrayBuilder kakuroBuilder, final Set<String> classValues, final Coord inputCoord) {
    if (inputCoord.getRow() > 1 && inputCoord.getCol() > 1) {
      if (classValues.contains("noValue")) {
        kakuroBuilder.addNonValueCell(Coord.of(inputCoord.getRow() - 1, inputCoord.getCol() - 1));
        return true;
      }
      else {
        return false;
      }
    }
    return true;
  }

  private void addConstraints(final ArrayBuilder kakuroBuilder, final int[][] directionSumGrid, final boolean isAcross) {
    int lineNum = 0;
    for (final int[] line : directionSumGrid) {
      int cellNum = 0;
      final List<Coord> group = new ArrayList<>();
      int currentSum = 0;
      for (final int downSum : line) {
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
