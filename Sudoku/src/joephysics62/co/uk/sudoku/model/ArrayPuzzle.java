package joephysics62.co.uk.sudoku.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import joephysics62.co.uk.sudoku.constraints.Constraint;

public class ArrayPuzzle implements Puzzle {
  private final int[][] _cells;
  private final List<Constraint> _fixedConstraints;
  private final List<Constraint> _variableConstraints;
  private final int _inits;
  private final String _title;
  private final PuzzleLayout _layout;

  @Override
  public PuzzleLayout getLayout() {
    return _layout;
  }

  private ArrayPuzzle(ArrayPuzzle old) {
    _cells = new int[old.getLayout().getHeight()][old.getLayout().getWidth()];
    for (int rowIndex = 0; rowIndex < old.getLayout().getHeight(); rowIndex++) {
      _cells[rowIndex] = old._cells[rowIndex].clone();
    }
    _fixedConstraints = old._fixedConstraints;
    _variableConstraints = new ArrayList<>();
    _variableConstraints.addAll(old._variableConstraints);
    _layout = old._layout;
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

  private ArrayPuzzle(final String title, final PuzzleLayout layout, final List<Constraint> fixedConstraints) {
    _title = title;
    _inits = (1 << layout.getInitialsSize()) - 1;
    _cells = new int[layout.getHeight()][layout.getWidth()];
    _fixedConstraints = Collections.unmodifiableList(fixedConstraints);
    _variableConstraints = new ArrayList<>();
    _layout = layout;
  }

  public static ArrayPuzzle forPossiblesSize(final String title, final PuzzleLayout layout, final List<Constraint> constraints) {
    return new ArrayPuzzle(title, layout, constraints);
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
    ArrayList<Constraint> allConstraints = new ArrayList<>(_fixedConstraints);
    allConstraints.addAll(_variableConstraints);
    return allConstraints;
  }

  @Override
  public List<Constraint> getVariableConstraints() {
    return _variableConstraints;
  }

  @Override
  public List<Constraint> getConstraints(final Coord coord) {
    List<Constraint> out = new ArrayList<>();
    for (Constraint constraint : getAllConstraints()) {
      if (constraint.getCells().contains(coord)) {
        out.add(constraint);
      }
    }
    return out;
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
