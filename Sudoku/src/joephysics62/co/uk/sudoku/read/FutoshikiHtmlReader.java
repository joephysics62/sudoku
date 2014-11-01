package joephysics62.co.uk.sudoku.read;

import joephysics62.co.uk.constraints.GreaterThan;
import joephysics62.co.uk.grid.Coord;
import joephysics62.co.uk.grid.GridLayout;
import joephysics62.co.uk.sudoku.builder.ArrayBuilder;
import joephysics62.co.uk.sudoku.model.Cell;
import joephysics62.co.uk.sudoku.model.PuzzleLayout;
import joephysics62.co.uk.sudoku.read.html.HTMLTableParser.InputCell;

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
  @Override protected boolean hasColumnUniqueness() { return true; }
  @Override protected boolean hasRowUniqueness() { return true; }
  @Override protected boolean hasSubtableUniqueness() { return false; }

  @Override
  protected void handleInputCell(InputCell inputCell, Coord coord, final ArrayBuilder builder) {
    int rowIndex = coord.getRow() - 1;
    int colIndex = coord.getCol() - 1;
    int rowNum = rowIndex / 2 + 1;
    int colNum = colIndex / 2 + 1;
    String textValue = inputCell.getTextValue();
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
