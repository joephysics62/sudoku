package joephysics62.co.uk.sudoku.read;

import java.io.File;
import java.io.IOException;

import joephysics62.co.uk.sudoku.builder.ArrayPuzzleBuilder;
import joephysics62.co.uk.sudoku.builder.SudokuBuilder;
import joephysics62.co.uk.sudoku.model.Cell;
import joephysics62.co.uk.sudoku.model.Puzzle;
import joephysics62.co.uk.sudoku.read.html.HTMLTableParser;
import joephysics62.co.uk.sudoku.read.html.TableParserHandler;

public class SudokuHTMLPuzzleReader implements PuzzleReader {

  private final int _outerSize;
  private final ArrayPuzzleBuilder _sudokuBuilder;
  private final HTMLTableParser _tableParser;

  public SudokuHTMLPuzzleReader(final int subTableHeight, final int subTableWidth, final int outerSize) {
    _outerSize = outerSize;
    _sudokuBuilder = new SudokuBuilder(outerSize, subTableHeight, subTableWidth);
    _tableParser = new HTMLTableParser(_outerSize, outerSize);
  }

  @Override
  public Puzzle read(final File input) throws IOException {
    final Integer[][] givens = new Integer[_outerSize][_outerSize];
    _tableParser.parseTable(input, new TableParserHandler() {
      @Override
      public void cell(String cellInput, int rowIndex, int colIndex) {
        givens[rowIndex][colIndex] = cellInput.isEmpty() ? null : Cell.fromString(cellInput, _outerSize);
      }

      @Override
      public void title(String title) {
        _sudokuBuilder.addTitle(title);
      }
    });
    _sudokuBuilder.addGivens(givens);
    return _sudokuBuilder.build();
  }

}
