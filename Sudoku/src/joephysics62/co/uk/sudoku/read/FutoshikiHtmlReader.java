package joephysics62.co.uk.sudoku.read;

import java.io.File;
import java.io.IOException;

import joephysics62.co.uk.constraints.GreaterThan;
import joephysics62.co.uk.grid.Coord;
import joephysics62.co.uk.grid.Grid;
import joephysics62.co.uk.grid.GridLayout;
import joephysics62.co.uk.sudoku.builder.ArrayBuilder;
import joephysics62.co.uk.sudoku.model.Cell;
import joephysics62.co.uk.sudoku.model.Puzzle;
import joephysics62.co.uk.sudoku.model.PuzzleLayout;
import joephysics62.co.uk.sudoku.read.html.HTMLTableParser;
import joephysics62.co.uk.sudoku.read.html.HTMLTableParser.InputCell;

public class FutoshikiHtmlReader implements PuzzleHtmlReader {

  private final PuzzleLayout _layout;

  public FutoshikiHtmlReader(final PuzzleLayout layout) {
    _layout = layout;
  }

  @Override
  public Puzzle read(final File input) throws IOException {
    HTMLTableParser tableParser = new HTMLTableParser(new GridLayout(2 * _layout.getHeight() - 1, 2 * _layout.getWidth() - 1));
    final ArrayBuilder futoshikiBuilder = new ArrayBuilder(_layout);
    Grid<InputCell> table = tableParser.parseTable(input);
    for (Coord coord : table) {
      int rowIndex = coord.getRow() - 1;
      int colIndex = coord.getCol() - 1;
      int rowNum = rowIndex / 2 + 1;
      int colNum = colIndex / 2 + 1;
      InputCell inputCell = table.get(coord);
      String textValue = inputCell.getTextValue();
      if (rowIndex % 2 == 0 && colIndex % 2 == 0) {
        if (!textValue.isEmpty()) {
          futoshikiBuilder.addGiven(Cell.fromString(textValue, _layout.getInitialsSize()), Coord.of(rowNum, colNum));
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
          futoshikiBuilder.addConstraint(GreaterThan.of(thisCell, otherCell));
        }
        else if ("<".equals(textValue)) {
          futoshikiBuilder.addConstraint(GreaterThan.of(otherCell, thisCell));
        }
      }
    }
    futoshikiBuilder.addRowUniquenessConstraints();
    futoshikiBuilder.addColumnUniquenessConstraints();
    return futoshikiBuilder.build();
  }

}
