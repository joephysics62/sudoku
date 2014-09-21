package joephysics62.co.uk.sudoku.builder;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import joephysics62.co.uk.sudoku.constraints.Constraint;
import joephysics62.co.uk.sudoku.constraints.Uniqueness;
import joephysics62.co.uk.sudoku.model.ArrayPuzzle;
import joephysics62.co.uk.sudoku.model.Coord;
import joephysics62.co.uk.sudoku.model.Puzzle;
import joephysics62.co.uk.sudoku.model.PuzzleLayout;

/**
 * Builds an empty sudoku, with constraints.
 */
public class SudokuBuilder implements PuzzleBuilder {

  private Integer[][] _givenCells = null;
  private String _title;
  private final PuzzleLayout _layout;

  public SudokuBuilder(PuzzleLayout layout) {
    _layout = layout;
  }

  @Override
  public void addConstraint(Constraint constraint) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void addGiven(Integer value, Coord coord) {
    if (null == _givenCells) {
      _givenCells = new Integer[_layout.getHeight()][_layout.getWidth()];
    }
    _givenCells[coord.getRow() - 1][coord.getCol() -1] = value;
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

    for (int rowNum = 1; rowNum <= height; rowNum++) {
      final List<Coord> row = new ArrayList<>();
      for (int colNum = 1; colNum <= width; colNum++) {
        row.add(Coord.of(rowNum, colNum));
      }
      sudoku.addConstraint(Uniqueness.of(row));
    }

    for (int colNum = 1; colNum <= width; colNum++) {
      final List<Coord> column = new ArrayList<>();
      for (int rowNum = 1; rowNum <= height; rowNum++) {
        column.add(Coord.of(rowNum, colNum));
      }
      sudoku.addConstraint(Uniqueness.of(column));
    }

    for (int subTableRowNum = 1; subTableRowNum <= height / _layout.getSubTableHeight(); subTableRowNum++) {
      for (int subTableColNum = 1; subTableColNum <= width / _layout.getSubTableWidth(); subTableColNum++) {
        final Set<Coord> subTableCells = new LinkedHashSet<>();
        for (int rowNumInSubTable = 1; rowNumInSubTable <= _layout.getSubTableHeight(); rowNumInSubTable++) {
          for (int colNumInSubTable = 1; colNumInSubTable <= _layout.getSubTableWidth(); colNumInSubTable++) {
            int row = (subTableRowNum - 1) * _layout.getSubTableHeight() + rowNumInSubTable;
            int col = (subTableColNum - 1) * _layout.getSubTableWidth() + colNumInSubTable;
            subTableCells.add(Coord.of(row, col));
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
