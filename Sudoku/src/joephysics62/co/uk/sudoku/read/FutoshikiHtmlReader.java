package joephysics62.co.uk.sudoku.read;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import joephysics62.co.uk.constraints.GreaterThan;
import joephysics62.co.uk.grid.Coord;
import joephysics62.co.uk.grid.GridLayout;
import joephysics62.co.uk.sudoku.builder.ArrayBuilder;
import joephysics62.co.uk.sudoku.model.Cell;
import joephysics62.co.uk.sudoku.model.Puzzle;
import joephysics62.co.uk.sudoku.model.PuzzleLayout;
import joephysics62.co.uk.sudoku.read.html.HTMLTableParser;
import joephysics62.co.uk.sudoku.read.html.TableParserHandler;

public class FutoshikiHtmlReader implements PuzzleHtmlReader {

  private final PuzzleLayout _layout;

  public FutoshikiHtmlReader(final PuzzleLayout layout) {
    _layout = layout;
  }

  @Override
  public Puzzle read(final File input) throws IOException {
    HTMLTableParser tableParser = new HTMLTableParser(new GridLayout(2 * _layout.getHeight() - 1, 2 * _layout.getWidth() - 1));
    final ArrayBuilder futoshikiBuilder = new ArrayBuilder(_layout);
    final TableParserHandler handler = new TableParserHandler() {
      @Override
      public void title(String title) {
        futoshikiBuilder.addTitle(title);
      }

      @Override
      public void cell(Map<String, String> complexCellInput, Set<String> classValues, int rowIndex, int colIndex) {
        throw new UnsupportedOperationException();
      }

      @Override
      public void cell(final String cellInput, Set<String> classValues, int rowIndex, int colIndex) {
        int rowNum = rowIndex / 2 + 1;
        int colNum = colIndex / 2 + 1;
        if (rowIndex % 2 == 0 && colIndex % 2 == 0) {
          if (!cellInput.isEmpty()) {
            futoshikiBuilder.addGiven(Cell.fromString(cellInput, _layout.getInitialsSize()), Coord.of(rowNum, colNum));
          }
        }
        else if (rowIndex % 2 == 1 && colIndex % 2 == 1) {
          if (!cellInput.isEmpty()) {
            throw new RuntimeException("Bad futoshiki input, did not expect content at cell " + rowIndex + ", " + colIndex);
          }
        }
        else {
          final Coord thisCell = Coord.of(rowNum, colNum);
          final Coord otherCell = Coord.of(rowNum + rowIndex % 2, colNum + colIndex % 2);
          if (">".equals(cellInput)) {
            futoshikiBuilder.addConstraint(GreaterThan.of(thisCell, otherCell));
          }
          else if ("<".equals(cellInput)) {
            futoshikiBuilder.addConstraint(GreaterThan.of(otherCell, thisCell));
          }
        }
      }
    };
    tableParser.parseTable(input, handler);
    futoshikiBuilder.addRowUniquenessConstraints();
    futoshikiBuilder.addColumnUniquenessConstraints();
    return futoshikiBuilder.build();
  }

}
