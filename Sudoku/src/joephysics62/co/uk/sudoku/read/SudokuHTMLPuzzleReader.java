package joephysics62.co.uk.sudoku.read;

import java.io.File;
import java.io.IOException;

import joephysics62.co.uk.sudoku.builder.ArrayPuzzleBuilder;
import joephysics62.co.uk.sudoku.builder.SudokuBuilder;
import joephysics62.co.uk.sudoku.model.Cell;
import joephysics62.co.uk.sudoku.model.Puzzle;
import joephysics62.co.uk.sudoku.model.PuzzleLayout;
import joephysics62.co.uk.sudoku.read.html.HTMLTableParser;
import joephysics62.co.uk.sudoku.read.html.TableParserHandler;

public class SudokuHTMLPuzzleReader implements PuzzleReader {

  private final ArrayPuzzleBuilder _sudokuBuilder;
  private final HTMLTableParser _tableParser;
  private final PuzzleLayout _layout;

  public SudokuHTMLPuzzleReader(final PuzzleLayout layout) {
    _layout = layout;
    _sudokuBuilder = new SudokuBuilder(layout);
    _tableParser = new HTMLTableParser(layout.getHeight(), layout.getWidth());
  }

  @Override
  public Puzzle read(final File input) throws IOException {
    final Integer[][] givens = new Integer[_layout.getHeight()][_layout.getWidth()];
    _tableParser.parseTable(input, new TableParserHandler() {
      @Override
      public void cell(String cellInput, int rowIndex, int colIndex) {
        givens[rowIndex][colIndex] = cellInput.isEmpty() ? null : Cell.fromString(cellInput, _layout.getInitialsSize());
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
