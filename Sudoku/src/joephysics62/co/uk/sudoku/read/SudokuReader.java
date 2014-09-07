package joephysics62.co.uk.sudoku.read;

import java.io.File;
import java.io.IOException;

import joephysics62.co.uk.sudoku.builder.SudokuBuilder;
import joephysics62.co.uk.sudoku.model.Puzzle;

public class SudokuReader implements PuzzleReader {
  private final int _outerSize;
  private final TableValueParser _tableValueParser;
  private final int _subTableHeight;
  private final int _subTableWidth;

  public SudokuReader(final int subTableHeight, final int subTableWidth, final int outerSize) {
    _subTableHeight = subTableHeight;
    _subTableWidth = subTableWidth;
    _tableValueParser = new TableValueParser(outerSize);
    _outerSize = outerSize;
  }

  @Override
  public Puzzle read(final File file) throws IOException {
    Integer[][] tableInts = _tableValueParser.parse(file);
    if (tableInts.length != _outerSize) {
      throw new IllegalArgumentException("Error: number of rows is " + tableInts.length + " but inits size is " + _outerSize);
    }
    SudokuBuilder builder = new SudokuBuilder(_outerSize, _subTableHeight, _subTableWidth);
    builder.addCells(tableInts);
    return builder.build();
  }

}
