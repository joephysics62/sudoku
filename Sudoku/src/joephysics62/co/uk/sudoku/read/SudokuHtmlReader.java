package joephysics62.co.uk.sudoku.read;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import joephysics62.co.uk.grid.Coord;
import joephysics62.co.uk.sudoku.builder.ArrayBuilder;
import joephysics62.co.uk.sudoku.model.Cell;
import joephysics62.co.uk.sudoku.model.Puzzle;
import joephysics62.co.uk.sudoku.model.PuzzleLayout;
import joephysics62.co.uk.sudoku.read.html.HTMLTableParser;
import joephysics62.co.uk.sudoku.read.html.TableParserHandler;

public class SudokuHtmlReader implements PuzzleHtmlReader {

  private final HTMLTableParser _tableParser;
  private final PuzzleLayout _layout;

  public SudokuHtmlReader(final PuzzleLayout layout) {
    _layout = layout;
    _tableParser = new HTMLTableParser(layout);
  }

  @Override
  public Puzzle read(final File input) throws IOException {
    final ArrayBuilder puzzleBuilder = new ArrayBuilder(_layout);
    _tableParser.parseTable(input, new TableParserHandler() {
      @Override
      public void cell(String cellInput, Set<String> classValues, int rowIndex, int colIndex) {
        if (!cellInput.isEmpty()) {
          puzzleBuilder.addGiven(Cell.fromString(cellInput, _layout.getInitialsSize()), Coord.of(rowIndex + 1, colIndex + 1));
        }
      }

      @Override
      public void cell(final Map<String, String> complexCellInput, Set<String> classValues, int rowIndex, int colIndex) {
        throw new UnsupportedOperationException();
      }

      @Override
      public void title(String title) {
        puzzleBuilder.addTitle(title);
      }
    });
    puzzleBuilder.addColumnUniquenessConstraints();
    puzzleBuilder.addRowUniquenessConstraints();
    puzzleBuilder.addSubTableUniquenessConstraints();
    return puzzleBuilder.build();
  }

}
