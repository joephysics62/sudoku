package joephysics62.co.uk.old.sudoku.read;

import joephysics62.co.uk.old.grid.Coord;
import joephysics62.co.uk.old.grid.GridLayout;
import joephysics62.co.uk.old.sudoku.builder.ArrayBuilder;
import joephysics62.co.uk.old.sudoku.model.Cell;
import joephysics62.co.uk.old.sudoku.model.PuzzleLayout;
import joephysics62.co.uk.old.sudoku.read.html.HTMLTableParser.InputCell;

public class SudokuHtmlReader extends HtmlPuzzleReader {

  private final PuzzleLayout _layout;

  public SudokuHtmlReader(final PuzzleLayout layout) {
    _layout = layout;
  }

  @Override protected GridLayout getInputTableLayout() { return _layout; }
  @Override protected PuzzleLayout getPuzzleLayout() { return _layout; }

  @Override
  protected void handleInputCell(InputCell inputCell, final Coord coord, final ArrayBuilder puzzleBuilder) {
    final String textValue = inputCell.getTextValue();
    if (!textValue.isEmpty()) {
      puzzleBuilder.addGiven(Cell.fromString(textValue, _layout.getInitialsSize()), coord);
    }
    if (!inputCell.getComplexValue().isEmpty()) {
      throw new RuntimeException();
    }
  }

}
