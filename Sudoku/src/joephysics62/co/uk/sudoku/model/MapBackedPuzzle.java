package joephysics62.co.uk.sudoku.model;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import joephysics62.co.uk.sudoku.constraints.Restriction;

public class MapBackedPuzzle implements Puzzle {
  private final Map<Coord, Set<Restriction>> _constraints = new TreeMap<>();
  private final int[][] _cells;
  private final Set<Restriction> _restrictions = new LinkedHashSet<>();
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
    _constraints.putAll(old._constraints);
    _restrictions.addAll(old._restrictions);
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
  public Set<Restriction> getAllRestrictions() {
    return _restrictions;
  }

  @Override
  public Set<Restriction> getRestrictions(final Coord coord) {
    return Collections.unmodifiableSet(_constraints.get(coord));
  }

  @Override
  public boolean isSolved() {
    for (int[] cell : _cells) {
      for (int value : cell) {
        if (Integer.bitCount(value) > 1) {
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

  public void addConstraint(Restriction restriction) {
    _restrictions.add(restriction);
    for (Coord cellCoord : restriction.getCells()) {
      if (!_constraints.containsKey(cellCoord)) {
        _constraints.put(cellCoord, new LinkedHashSet<Restriction>());
      }
      _constraints.get(cellCoord).add(restriction);
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
