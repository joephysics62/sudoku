package joephysics62.co.uk.sudoku.read;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import joephysics62.co.uk.sudoku.constraints.Uniqueness;
import joephysics62.co.uk.sudoku.model.Coord;
import joephysics62.co.uk.sudoku.model.ArrayPuzzle;
import joephysics62.co.uk.sudoku.model.Puzzle;

public class SudokuReader implements PuzzleReader {
  private final int _subTableHeight;
  private final int _subTableWidth;
  private final int _outerSize;
  private final TableValueParser _tableValueParser;

  public SudokuReader(final int subTableHeight, final int subTableWidth, final int outerSize) {
    _subTableHeight = subTableHeight;
    _subTableWidth = subTableWidth;
    _outerSize = outerSize;
    _tableValueParser = new TableValueParser(outerSize);
  }

  @Override
  public Puzzle read(final File file) throws IOException {
    Integer[][] tableInts = _tableValueParser.parse(file);
    if (tableInts.length != _outerSize) {
      throw new IllegalArgumentException("Error: number of rows is " + tableInts.length + " but inits size is " + _outerSize);
    }
    final Coord[][] wholePuzzle = new Coord[_outerSize][_outerSize];
    for (int row = 0; row < _outerSize; row++) {
      for (int col = 0; col < _outerSize; col++) {
        wholePuzzle[row][col] = new Coord(row + 1, col + 1);
      }
    }
    ArrayPuzzle sudoku = ArrayPuzzle.forPossiblesSize(_outerSize);
    sudoku.addCells(tableInts);

    for (Coord[] rowArray : wholePuzzle) {
      sudoku.addConstraint(Uniqueness.of(Arrays.asList(rowArray)));
    }
    for (int col = 0; col < _outerSize; col++) {
      List<Coord> colCells = new ArrayList<>();
      for (int row = 0; row < _outerSize; row++) {
        colCells.add(wholePuzzle[row][col]);
      }
      sudoku.addConstraint(Uniqueness.of(colCells));
    }
    for (int i = 0; i < _outerSize / _subTableHeight; i++) {
      for (int j = 0; j < _outerSize / _subTableWidth; j++) {
        final Set<Coord> subTableCells = new LinkedHashSet<>();
        for (int ii = 0; ii < _subTableHeight; ii++) {
          for (int jj = 0; jj < _subTableWidth; jj++) {
            int row = i * _subTableHeight + ii;
            int col = j * _subTableWidth + jj;
            subTableCells.add(wholePuzzle[row][col]);
          }
        }
        sudoku.addConstraint(Uniqueness.of(subTableCells));
      }
    }
    return sudoku;
  }



}
