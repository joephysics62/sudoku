package joephysics62.co.uk.sudoku.puzzleBuilders;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import joephysics62.co.uk.sudoku.model.Coord;
import joephysics62.co.uk.sudoku.model.MapBackedPuzzle;
import joephysics62.co.uk.sudoku.model.Puzzle;
import joephysics62.co.uk.sudoku.model.Uniqueness;

public class SudokuBuilder implements PuzzleBuilder<Integer> {
  private final int _subTableHeight;
  private final int _subTableWidth;
  private final int _outerSize;
  private TableValueParser<Integer> _tableValueParser;

  public SudokuBuilder(final int subTableHeight, final int subTableWidth, final int outerSize) {
    _subTableHeight = subTableHeight;
    _subTableWidth = subTableWidth;
    _outerSize = outerSize;
    _tableValueParser = new TableValueParser<>(outerSize, new CellValueReader<Integer>() {
      @Override
      public Integer parseCellValue(String value) {
        if (value.isEmpty()) {
          return null;
        }
        return Integer.valueOf(value);
      }
    });
  }

  @Override
  public Puzzle<Integer> read(final File file) throws IOException {
    List<List<Integer>> tableInts = _tableValueParser.parse(file);
    if (tableInts.size() != _outerSize) {
      throw new IllegalArgumentException("Error: number of rows is " + tableInts.size() + " but inits size is " + _outerSize);
    }
    final Coord[][] wholePuzzle = new Coord[_outerSize][_outerSize];
    for (int row = 0; row < _outerSize; row++) {
      for (int col = 0; col < _outerSize; col++) {
        wholePuzzle[row][col] = new Coord(row + 1, col + 1);
      }
    }
    List<Integer> inits = new ArrayList<>();
    for (int i = 1; i <= _outerSize; i++) {
      inits.add(i);
    }
    MapBackedPuzzle<Integer> sudoku = MapBackedPuzzle.forInits(inits);
    sudoku.addCells(tableInts);

    for (Coord[] rowArray : wholePuzzle) {
      sudoku.addConstraint(Uniqueness.<Integer>of(Arrays.asList(rowArray)));
    }
    for (int col = 0; col < inits.size(); col++) {
      List<Coord> colCells = new ArrayList<>();
      for (int row = 0; row < inits.size(); row++) {
        colCells.add(wholePuzzle[row][col]);
      }
      sudoku.addConstraint(Uniqueness.<Integer>of(colCells));
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
        sudoku.addConstraint(Uniqueness.<Integer>of(subTableCells));
      }
    }
    return sudoku;
  }



}
