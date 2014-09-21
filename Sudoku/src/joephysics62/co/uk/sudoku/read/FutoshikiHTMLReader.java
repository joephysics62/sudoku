package joephysics62.co.uk.sudoku.read;

import java.io.File;
import java.io.IOException;

import joephysics62.co.uk.sudoku.builder.FutoshikiBuilder;
import joephysics62.co.uk.sudoku.constraints.GreaterThan;
import joephysics62.co.uk.sudoku.model.Cell;
import joephysics62.co.uk.sudoku.model.Coord;
import joephysics62.co.uk.sudoku.model.Puzzle;
import joephysics62.co.uk.sudoku.model.PuzzleLayout;
import joephysics62.co.uk.sudoku.read.html.HTMLTableParser;
import joephysics62.co.uk.sudoku.read.html.TableParserHandler;

public class FutoshikiHTMLReader implements PuzzleReader {

  private final HTMLTableParser _tableParser;
  private final PuzzleLayout _layout;

  public FutoshikiHTMLReader(final PuzzleLayout layout) {
    _layout = layout;
    _tableParser = new HTMLTableParser(2 * layout.getHeight() - 1, 2 * layout.getWidth() - 1);
  }

  @Override
  public Puzzle read(final File input) throws IOException {
    FutoshikiBuilder futoshikiBuilder = new FutoshikiBuilder(_layout);
    readGivens(input, futoshikiBuilder);
    return futoshikiBuilder.build();
  }

  private void readGivens(final File input, final FutoshikiBuilder futoshikiBuilder) throws IOException {
    final TableParserHandler handler = new TableParserHandler() {
      @Override
      public void title(String title) {
        futoshikiBuilder.addTitle(title);
      }

      @Override
      public void cell(final String cellInput, int rowIndex, int colIndex) {
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
    _tableParser.parseTable(input, handler);
  }

}
