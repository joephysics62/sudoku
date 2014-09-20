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
import joephysics62.co.uk.sudoku.model.PuzzleLayout;

/**
 * Builds an empty sudoku, with constraints.
 */
public class SudokuBuilder implements ArrayPuzzleBuilder {

  private Integer[][] _givenCells;
  private String _title;
  private final PuzzleLayout _layout;

  public SudokuBuilder(PuzzleLayout layout) {
    _layout = layout;
  }

  @Override
  public void addGivens(Integer[][] givenCells) {
    _givenCells = givenCells;
  }

  @Override
  public void addTitle(final String title) {
    _title = title;
  }

  @Override
  public Puzzle build() {
    ArrayPuzzle sudoku = ArrayPuzzle.forPossiblesSize(_title, _layout);
    int height = _layout.getHeight();
    int width = _layout.getWidth();
    final Coord[][] wholePuzzle = new Coord[height][width];
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        wholePuzzle[row][col] = new Coord(row + 1, col + 1);
      }
    }

    for (Coord[] rowArray : wholePuzzle) {
      sudoku.addConstraint(Uniqueness.of(Arrays.asList(rowArray)));
    }
    for (int col = 0; col < height; col++) {
      List<Coord> colCells = new ArrayList<>();
      for (int row = 0; row < width; row++) {
        colCells.add(wholePuzzle[row][col]);
      }
      sudoku.addConstraint(Uniqueness.of(colCells));
    }
    for (int i = 0; i < height / _layout.getSubTableHeight(); i++) {
      for (int j = 0; j < width / _layout.getSubTableWidth(); j++) {
        final Set<Coord> subTableCells = new LinkedHashSet<>();
        for (int ii = 0; ii < _layout.getSubTableHeight(); ii++) {
          for (int jj = 0; jj < _layout.getSubTableWidth(); jj++) {
            int row = i * _layout.getSubTableHeight() + ii;
            int col = j * _layout.getSubTableHeight() + jj;
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
