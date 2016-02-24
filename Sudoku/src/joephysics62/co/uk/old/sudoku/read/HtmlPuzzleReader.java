package joephysics62.co.uk.old.sudoku.read;

import java.io.File;
import java.io.IOException;

import joephysics62.co.uk.old.grid.Coord;
import joephysics62.co.uk.old.grid.Grid;
import joephysics62.co.uk.old.grid.GridLayout;
import joephysics62.co.uk.old.sudoku.builder.ArrayBuilder;
import joephysics62.co.uk.old.sudoku.model.GridUniqueness;
import joephysics62.co.uk.old.sudoku.model.Puzzle;
import joephysics62.co.uk.old.sudoku.model.PuzzleLayout;
import joephysics62.co.uk.old.sudoku.read.html.HTMLTableParser;
import joephysics62.co.uk.old.sudoku.read.html.HTMLTableParser.InputCell;

public abstract class HtmlPuzzleReader implements PuzzleReader {

  @Override
  public final Puzzle read(File input) throws IOException {
    HTMLTableParser tableParser = new HTMLTableParser(getInputTableLayout());
    final ArrayBuilder puzzleBuilder = new ArrayBuilder(getPuzzleLayout());
    Grid<InputCell> table = tableParser.parseTable(input);
    for (final Coord coord : table) {
      InputCell inputCell = table.get(coord);
      handleInputCell(inputCell, coord, puzzleBuilder);
    }
    if (getPuzzleLayout().getGridUniqueness() == GridUniqueness.ROWS_COLUMNS) {
      puzzleBuilder.addRowUniquenessConstraints();
      puzzleBuilder.addColumnUniquenessConstraints();
    }
    else if (getPuzzleLayout().getGridUniqueness() == GridUniqueness.ROWS_COLUMNS_SUBTABLES) {
      puzzleBuilder.addRowUniquenessConstraints();
      puzzleBuilder.addColumnUniquenessConstraints();
      puzzleBuilder.addSubTableUniquenessConstraints();
    }

    return puzzleBuilder.build();
  }

  protected abstract void handleInputCell(InputCell inputCell, Coord coord, ArrayBuilder puzzleBuilder);
  protected abstract PuzzleLayout getPuzzleLayout();
  protected abstract GridLayout getInputTableLayout();

}
