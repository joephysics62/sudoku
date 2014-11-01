package joephysics62.co.uk.sudoku.read;

import java.io.File;
import java.io.IOException;

import joephysics62.co.uk.grid.Coord;
import joephysics62.co.uk.grid.Grid;
import joephysics62.co.uk.grid.GridLayout;
import joephysics62.co.uk.sudoku.builder.ArrayBuilder;
import joephysics62.co.uk.sudoku.model.Puzzle;
import joephysics62.co.uk.sudoku.model.PuzzleLayout;
import joephysics62.co.uk.sudoku.read.html.HTMLTableParser;
import joephysics62.co.uk.sudoku.read.html.HTMLTableParser.InputCell;

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
    if (hasColumnUniqueness()) {
      puzzleBuilder.addColumnUniquenessConstraints();
    }
    if (hasRowUniqueness()) {
      puzzleBuilder.addRowUniquenessConstraints();
    }
    if (hasSubtableUniqueness()) {
      puzzleBuilder.addSubTableUniquenessConstraints();
    }
    return puzzleBuilder.build();
  }

  protected abstract void handleInputCell(InputCell inputCell, Coord coord, ArrayBuilder puzzleBuilder);
  protected abstract PuzzleLayout getPuzzleLayout();
  protected abstract GridLayout getInputTableLayout();
  protected abstract boolean hasColumnUniqueness();
  protected abstract boolean hasRowUniqueness();
  protected abstract boolean hasSubtableUniqueness();

}
