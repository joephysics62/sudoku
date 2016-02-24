package joephysics62.co.uk.old.sudoku.write;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import joephysics62.co.uk.old.constraints.Constraint;
import joephysics62.co.uk.old.constraints.GreaterThan;
import joephysics62.co.uk.old.grid.Coord;
import joephysics62.co.uk.old.sudoku.model.Cell;
import joephysics62.co.uk.old.sudoku.model.Puzzle;
import joephysics62.co.uk.old.sudoku.model.PuzzleLayout;

public class FutoshikiHtmlWriter extends PuzzleHtmlWriter {

  private static final String TEMPLATE_FTL = "futoshikiTemplate.ftl";

  public FutoshikiHtmlWriter(final Puzzle puzzle) throws URISyntaxException {
    super(puzzle, TEMPLATE_FTL);
  }

  @Override
  protected void addPuzzleSpecificParams(final Map<String, Object> root, final Puzzle puzzle) {
    // None.
  }

  protected String getTemplateLocation() {
    return TEMPLATE_FTL;
  }

  @Override
  protected List<List<Object>> generateTable() {
    final Puzzle puzzle = getPuzzle();
    final PuzzleLayout layout = puzzle.getLayout();
    final List<List<Object>> table = new ArrayList<>();
    for (int rowNum = 1; rowNum <= layout.getHeight(); rowNum++) {
      final List<Object> rowList = new ArrayList<>();
      final List<Object> betweensList = new ArrayList<>();
      for (int colNum = 1; colNum <= layout.getWidth(); colNum++) {
        final int value = puzzle.get(Coord.of(rowNum, colNum));
        if (Cell.isSolved(value)) {
          rowList.add(Cell.asString(Cell.toNumericValue(value), layout.getInitialsSize()));
        }
        else {
          rowList.add(null);
        }
        final Coord thisCell = Coord.of(rowNum, colNum);
        final Coord cellToRight = Coord.of(rowNum, colNum + 1);
        final Coord cellBelow = Coord.of(rowNum + 1, colNum);

        String colCompareValue = null;
        String rowCompareValue = null;
        for (final Constraint constraint : getPuzzle().getConstraints(thisCell)) {
          if (constraint instanceof GreaterThan) {
            final Coord bigger = constraint.getCells().get(0);
            final Coord smaller = constraint.getCells().get(1);
            if (thisCell.equals(bigger) && cellToRight.equals(smaller)) {
              colCompareValue = "&gt;";
            }
            else if (cellToRight.equals(bigger) && thisCell.equals(smaller)) {
              colCompareValue = "&lt;";
            }
            if (thisCell.equals(bigger) && cellBelow.equals(smaller)) {
              rowCompareValue = "&gt;";
            }
            else if (cellBelow.equals(bigger) && thisCell.equals(smaller)) {
              rowCompareValue = "&lt;";
            }
          }
        }
        if (colNum < layout.getWidth()) {
          rowList.add(colCompareValue);
        }
        betweensList.add(rowCompareValue);
        betweensList.add(null);
      }
      table.add(rowList);
      if (rowNum < layout.getHeight()) {
        table.add(betweensList);
      }
    }
    return table;
  }

}
