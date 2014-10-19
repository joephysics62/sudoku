package joephysics62.co.uk.sudoku.read;

import java.io.File;
import java.io.IOException;

import joephysics62.co.uk.grid.Coord;
import joephysics62.co.uk.grid.Grid;
import joephysics62.co.uk.sudoku.builder.ArrayBuilder;
import joephysics62.co.uk.sudoku.model.Cell;
import joephysics62.co.uk.sudoku.model.Puzzle;
import joephysics62.co.uk.sudoku.model.PuzzleLayout;
import joephysics62.co.uk.sudoku.read.html.HTMLTableParser;
import joephysics62.co.uk.sudoku.read.html.HTMLTableParser.InputCell;

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
    final Grid<InputCell> grid = _tableParser.parseTable(input);
    for (final Coord coord : grid) {
      InputCell inputCell = grid.get(coord);
      final String textValue = inputCell.getTextValue();
      if (!textValue.isEmpty()) {
        puzzleBuilder.addGiven(Cell.fromString(textValue, _layout.getInitialsSize()), coord);
      }
      if (!inputCell.getComplexValue().isEmpty()) {
        throw new RuntimeException();
      }
    }
    puzzleBuilder.addColumnUniquenessConstraints();
    puzzleBuilder.addRowUniquenessConstraints();
    puzzleBuilder.addSubTableUniquenessConstraints();
    return puzzleBuilder.build();
  }

}
