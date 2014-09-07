package joephysics62.co.uk.sudoku.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import joephysics62.co.uk.sudoku.constraints.Constraint;

public class MapBackedPuzzle implements Puzzle {
  private final Map<Coord, List<Constraint>> _constraintsPerCell = new HashMap<>();
  private final int[][] _cells;
  private final List<Constraint> _allConstraints = new ArrayList<>();
  private final int _inits;
  private final int _possiblesSize;

  @Override
  public int getPuzzleSize() {
    return _possiblesSize;
  }

  private MapBackedPuzzle(MapBackedPuzzle old) {
    _cells = new int[old._cells.length][old._cells.length];
    int rowIndex = 0;
    for (int[] row : old._cells) {
      _cells[rowIndex] = row.clone();
      rowIndex++;
    }
    _constraintsPerCell.putAll(old._constraintsPerCell);
    _allConstraints.addAll(old._allConstraints);
    _possiblesSize = old._possiblesSize;
    _inits = old._inits;
  }

  @Override
  public int getCellValue(Coord coord) {
    return _cells[coord.getRow() - 1][coord.getCol() - 1];
  }

  @Override
  public void setCellValue(int cellValues, Coord coord) {
    _cells[coord.getRow() - 1][coord.getCol() - 1] = cellValues;
  }

  private MapBackedPuzzle(final int possiblesSize) {
    _possiblesSize = possiblesSize;
    _inits = (1 << possiblesSize) - 1;
    _cells = new int[possiblesSize][possiblesSize];
  }

  public static MapBackedPuzzle forPossiblesSize(final int possiblesSize) {
    return new MapBackedPuzzle(possiblesSize);
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
    return new MapBackedPuzzle(this);
  }

  @Override
  public List<Constraint> getAllConstraints() {
    return _allConstraints;
  }

  @Override
  public List<Constraint> getConstraints(final Coord coord) {
    return _constraintsPerCell.get(coord);
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
      if (!_constraintsPerCell.containsKey(cellCoord)) {
        _constraintsPerCell.put(cellCoord, new ArrayList<Constraint>());
      }
      _constraintsPerCell.get(cellCoord).add(constraint);
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
