package joephysics62.co.uk.sudoku.standard;

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
import joephysics62.co.uk.sudoku.parse.TableValueParser;

public class Sudoku<T extends Comparable<T>> extends MapBackedPuzzle<T> {
  private final int _subTableHeight;
  private final int _subTableWidth;

  public Sudoku(final Set<T> inits, final int subTableHeight, final int subTableWidth) {
    super(inits);
    if (subTableHeight >= inits.size() || inits.size() % subTableHeight != 0) {
      throw new IllegalArgumentException(subTableHeight + " is not an appropriate subtable height for outer size " + inits.size());
    }
    if (subTableWidth >= inits.size() || inits.size() % subTableWidth != 0) {
      throw new IllegalArgumentException(subTableWidth + " is not an appropriate subtable width for outer size " + inits.size());
    }
    _subTableHeight = subTableHeight;
    _subTableWidth = subTableWidth;
  }

  protected int getSubTableHeight() {
    return _subTableHeight;
  }
  protected int getSubTableWidth() {
    return _subTableWidth;
  }

  public static <T extends Comparable<T>> Puzzle<T> loadValues(final File file, final Set<T> inits, final int subTableHeight, final int subTableWidth, TableValueParser<T> parser) throws IOException {
    List<List<T>> tableInts = parser.parse(file);
    if (tableInts.size() != inits.size()) {
      throw new IllegalArgumentException("Error: number of rows is " + tableInts.size() + " but inits size is " + inits.size());
    }
    final Coord[][] wholePuzzle = new Coord[inits.size()][inits.size()];
    for (int row = 0; row < inits.size(); row++) {
      for (int col = 0; col < inits.size(); col++) {
        wholePuzzle[row][col] = new Coord(row + 1, col + 1);
      }
    }
    Sudoku<T> sudoku = new Sudoku<T>(inits, subTableHeight, subTableWidth);
    sudoku.addCells(tableInts);

    for (Coord[] rowArray : wholePuzzle) {
      sudoku.addConstraint(Uniqueness.<T>of(Arrays.asList(rowArray)));
    }
    for (int col = 0; col < inits.size(); col++) {
      List<Coord> colCells = new ArrayList<>();
      for (int row = 0; row < inits.size(); row++) {
        colCells.add(wholePuzzle[row][col]);
      }
      sudoku.addConstraint(Uniqueness.<T>of(colCells));
    }
    for (int i = 0; i < inits.size() / subTableHeight; i++) {
      for (int j = 0; j < inits.size() / subTableWidth; j++) {
        final Set<Coord> subTableCells = new LinkedHashSet<>();
        for (int ii = 0; ii < subTableHeight; ii++) {
          for (int jj = 0; jj < subTableWidth; jj++) {
            int row = i * subTableHeight + ii;
            int col = j * subTableWidth + jj;
            subTableCells.add(wholePuzzle[row][col]);
          }
        }
        sudoku.addConstraint(Uniqueness.<T>of(subTableCells));
      }
    }
    return sudoku;
  }



}
