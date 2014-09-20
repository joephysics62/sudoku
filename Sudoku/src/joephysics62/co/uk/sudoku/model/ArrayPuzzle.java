package joephysics62.co.uk.sudoku.model;

import java.util.ArrayList;
import java.util.List;

import joephysics62.co.uk.sudoku.constraints.Constraint;

public class ArrayPuzzle implements Puzzle {
  private final ConstraintList[][] _constraintsPerCell;
  private final int[][] _cells;
  private final ConstraintList _allConstraints;
  private final int _inits;
  private final int _possiblesSize;
  private final int _subTableWidth;
  private final int _subTableHeight;
  private final String _title;

  @Override public int getPuzzleSize() { return _possiblesSize; }
  @Override public int getSubTableHeight() { return _subTableHeight; }
  @Override public int getSubTableWidth() { return _subTableWidth; }

  @SuppressWarnings("serial")
  private static class ConstraintList extends ArrayList<Constraint> {
    // for arrays.
  }

  private ArrayPuzzle(ArrayPuzzle old) {
    int rows = old._cells.length;
    int cols = old._cells.length;
    _cells = new int[rows][cols];
    for (int rowIndex = 0; rowIndex < rows; rowIndex++) {
      _cells[rowIndex] = old._cells[rowIndex].clone();
    }
    _constraintsPerCell = old._constraintsPerCell;
    _allConstraints = old._allConstraints;
    _possiblesSize = old._possiblesSize;
    _subTableHeight = old._subTableHeight;
    _subTableWidth = old._subTableWidth;
    _inits = old._inits;
    _title = old._title;
  }

  @Override
  public int getCellValue(Coord coord) {
    return _cells[coord.getRow() - 1][coord.getCol() - 1];
  }

  @Override
  public void setCellValue(int cellValues, Coord coord) {
    _cells[coord.getRow() - 1][coord.getCol() - 1] = cellValues;
  }

  @Override
  public String getTitle() {
    return _title;
  }


  private ArrayPuzzle(final String title, final int possiblesSize, final int subTableHeight, final int subTableWidth) {
    _title = title;
    _possiblesSize = possiblesSize;
    _subTableHeight = subTableHeight;
    _subTableWidth = subTableWidth;
    _inits = (1 << possiblesSize) - 1;
    _cells = new int[possiblesSize][possiblesSize];
    _constraintsPerCell = new ConstraintList[possiblesSize][possiblesSize];
    _allConstraints = new ConstraintList();
  }

  public static ArrayPuzzle forPossiblesSize(final String title, final int possiblesSize, final int subTableHeight, final int subTableWidth) {
    return new ArrayPuzzle(title, possiblesSize, subTableHeight, subTableWidth);
  }

  @Override
  public final int[][] getAllCells() {
    return _cells;
  }

  public int getInits() {
    return _inits;
  }

  @Override
  public Puzzle deepCopy() {
    return new ArrayPuzzle(this);
  }

  @Override
  public List<Constraint> getAllConstraints() {
    return _allConstraints;
  }

  @Override
  public List<Constraint> getConstraints(final Coord coord) {
    return _constraintsPerCell[coord.getRow() - 1][coord.getCol() - 1];
  }

  @Override
  public boolean isSolved() {
    for (int[] cell : _cells) {
      for (int value : cell) {
        if (!Cell.isSolved(value)) {
          return false;
        }
      }
    }
    return true;
  }

  @Override
  public boolean isUnsolveable() {
    for (int[] cell : _cells) {
      for (int value : cell) {
        if (value == 0) {
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public int completeness() {
    int completeness = 0;
    for (int[] cell : _cells) {
      for (int value : cell) {
        completeness += Integer.bitCount(value);
      }
    }
    return completeness;
  }

  public void addConstraint(Constraint constraint) {
    _allConstraints.add(constraint);
    for (Coord cellCoord : constraint.getCells()) {
      if (getConstraints(cellCoord) == null) {
        _constraintsPerCell[cellCoord.getRow() - 1][cellCoord.getCol() - 1] = new ConstraintList();
      }
      getConstraints(cellCoord).add(constraint);
    }
  }

  public void addCells(Integer[][] givenValues) {
    for (int rowIndex = 0; rowIndex < givenValues.length; rowIndex++) {
      Integer[] row = givenValues[rowIndex];
      for (int colIndex = 0; colIndex < row.length; colIndex++) {
        final Integer value = givenValues[rowIndex][colIndex];
        _cells[rowIndex][colIndex] = value == null ? _inits : Cell.cellValueAsBitwise(value);
      }
    }
  }


}
