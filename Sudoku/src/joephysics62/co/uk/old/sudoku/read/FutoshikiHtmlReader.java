package joephysics62.co.uk.old.sudoku.read;

import joephysics62.co.uk.old.constraints.GreaterThan;
import joephysics62.co.uk.old.grid.Coord;
import joephysics62.co.uk.old.grid.GridLayout;
import joephysics62.co.uk.old.sudoku.builder.ArrayBuilder;
import joephysics62.co.uk.old.sudoku.model.Cell;
import joephysics62.co.uk.old.sudoku.model.PuzzleLayout;
import joephysics62.co.uk.old.sudoku.read.html.HTMLTableParser.InputCell;

public class FutoshikiHtmlReader extends HtmlPuzzleReader {

  private final PuzzleLayout _layout;

  public FutoshikiHtmlReader(final PuzzleLayout layout) {
    _layout = layout;
  }

  @Override
  protected GridLayout getInputTableLayout() {
    return new GridLayout(2 * _layout.getHeight() - 1, 2 * _layout.getWidth() - 1);
  }
  @Override protected PuzzleLayout getPuzzleLayout() { return _layout; }

  @Override
  protected void handleInputCell(final InputCell inputCell, final Coord coord, final ArrayBuilder builder) {
    final int rowIndex = coord.getRow() - 1;
    final int colIndex = coord.getCol() - 1;
    final int rowNum = rowIndex / 2 + 1;
    final int colNum = colIndex / 2 + 1;
    final String textValue = inputCell.getTextValue();
    if (rowIndex % 2 == 0 && colIndex % 2 == 0) {
      if (!textValue.isEmpty()) {
        builder.addGiven(Cell.fromString(textValue, _layout.getInitialsSize()), Coord.of(rowNum, colNum));
      }
    }
    else if (rowIndex % 2 == 1 && colIndex % 2 == 1) {
      if (!textValue.isEmpty()) {
        throw new RuntimeException("Bad futoshiki input, did not expect content at cell " + rowIndex + ", " + colIndex);
      }
    }
    else {
      final Coord thisCell = Coord.of(rowNum, colNum);
      final Coord otherCell = Coord.of(rowNum + rowIndex % 2, colNum + colIndex % 2);
      if (">".equals(textValue)) {
        builder.addConstraint(GreaterThan.of(thisCell, otherCell));
      }
      else if ("<".equals(textValue)) {
        builder.addConstraint(GreaterThan.of(otherCell, thisCell));
      }
    }
  }

}
