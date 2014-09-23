package joephysics62.co.uk.sudoku.read;

import java.io.File;
import java.io.IOException;

import joephysics62.co.uk.sudoku.builder.ArrayPuzzleBuilder;
import joephysics62.co.uk.sudoku.model.Cell;
import joephysics62.co.uk.sudoku.model.Coord;
import joephysics62.co.uk.sudoku.model.Puzzle;
import joephysics62.co.uk.sudoku.model.PuzzleLayout;
import joephysics62.co.uk.sudoku.read.html.HTMLTableParser;
import joephysics62.co.uk.sudoku.read.html.TableParserHandler;

public class SudokuHTMLPuzzleReader implements PuzzleReader {

  private final HTMLTableParser _tableParser;
  private final PuzzleLayout _layout;

  public SudokuHTMLPuzzleReader(final PuzzleLayout layout) {
    _layout = layout;
    _tableParser = new HTMLTableParser(layout.getHeight(), layout.getWidth());
  }

  @Override
  public Puzzle read(final File input) throws IOException {
    final ArrayPuzzleBuilder puzzleBuilder = new ArrayPuzzleBuilder(_layout);
    _tableParser.parseTable(input, new TableParserHandler() {
      @Override
      public void cell(String cellInput, int rowIndex, int colIndex) {
        if (!cellInput.isEmpty()) {
          puzzleBuilder.addGiven(Cell.fromString(cellInput, _layout.getInitialsSize()), Coord.of(rowIndex + 1, colIndex + 1));
        }
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
