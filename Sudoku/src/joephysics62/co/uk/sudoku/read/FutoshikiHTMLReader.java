package joephysics62.co.uk.sudoku.read;

import java.io.File;
import java.io.IOException;

import joephysics62.co.uk.sudoku.builder.FutoshikiBuilder;
import joephysics62.co.uk.sudoku.model.Cell;
import joephysics62.co.uk.sudoku.model.Coord;
import joephysics62.co.uk.sudoku.model.Puzzle;
import joephysics62.co.uk.sudoku.read.html.HTMLTableParser;
import joephysics62.co.uk.sudoku.read.html.TableParserHandler;

public class FutoshikiHTMLReader implements PuzzleReader {

  private final int _puzzleSize;
  private final HTMLTableParser _tableParser;

  public FutoshikiHTMLReader(final int puzzleSize) {
    _puzzleSize = puzzleSize;
    _tableParser = new HTMLTableParser(2 * puzzleSize - 1, 2 * puzzleSize - 1);
  }

  @Override
  public Puzzle read(final File input) throws IOException {
    FutoshikiBuilder futoshikiBuilder = new FutoshikiBuilder(_puzzleSize);
    readGivens(input, futoshikiBuilder);
    return futoshikiBuilder.build();
  }

  private void readGivens(final File input, final FutoshikiBuilder futoshikiBuilder) throws IOException {
    final Integer[][] givens = new Integer[_puzzleSize][_puzzleSize];
    final TableParserHandler handler = new TableParserHandler() {
      @Override
      public void cell(final String cellInput, int rowIndex, int colIndex) {
        int rowNum = rowIndex / 2 + 1;
        int colNum = colIndex / 2 + 1;
        if (rowIndex % 2 == 0 && colIndex % 2 == 0) {
          givens[rowNum - 1][colNum - 1] = cellInput.isEmpty() ? null : Cell.fromString(cellInput, _puzzleSize);
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
            futoshikiBuilder.addGreaterThan(thisCell, otherCell);
          }
          else if ("<".equals(cellInput)) {
            futoshikiBuilder.addGreaterThan(otherCell, thisCell);
          }
        }
      }
    };
    _tableParser.parseTable(input, handler);
    futoshikiBuilder.addGivens(givens);
  }

}
