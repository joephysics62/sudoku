package joephysics62.co.uk.sudoku.builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import joephysics62.co.uk.sudoku.constraints.Uniqueness;
import joephysics62.co.uk.sudoku.model.ArrayPuzzle;
import joephysics62.co.uk.sudoku.model.Coord;
import joephysics62.co.uk.sudoku.model.Puzzle;

/**
 * Builds an empty sudoku, with constraints.
 */
public class SudokuBuilder implements ArrayPuzzleBuilder {

  private final int _outerSize;
  private final int _subTableHeight;
  private final int _subTableWidth;
  private Integer[][] _givenCells;

  public SudokuBuilder(final int outerSize, final int subTableHeight, final int subTableWidth) {
    _outerSize = outerSize;
    _subTableHeight = subTableHeight;
    _subTableWidth = subTableWidth;
  }

  @Override
  public void addCells(Integer[][] givenCells) {
    _givenCells = givenCells;
  }

  @Override
  public Puzzle build() {
    ArrayPuzzle sudoku = ArrayPuzzle.forPossiblesSize(_outerSize);
    final Coord[][] wholePuzzle = new Coord[_outerSize][_outerSize];
    for (int row = 0; row < _outerSize; row++) {
      for (int col = 0; col < _outerSize; col++) {
        wholePuzzle[row][col] = new Coord(row + 1, col + 1);
      }
    }

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
    if (_givenCells != null) {
      sudoku.addCells(_givenCells);
    }
    return sudoku;
  }

}
