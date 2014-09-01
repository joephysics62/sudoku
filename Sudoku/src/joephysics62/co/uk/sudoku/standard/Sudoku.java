package joephysics62.co.uk.sudoku.standard;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import joephysics62.co.uk.sudoku.model.Cell;
import joephysics62.co.uk.sudoku.model.Coord;
import joephysics62.co.uk.sudoku.model.MapBackedPuzzle;
import joephysics62.co.uk.sudoku.model.Uniqueness;
import joephysics62.co.uk.sudoku.parse.CellValueReader;
import joephysics62.co.uk.sudoku.parse.TableValueParser;

public abstract class Sudoku extends MapBackedPuzzle<Integer> {
  private final int _outerSize;
  private final int _subTableHeight;
  private final int _subTableWidth;
  private final Set<Integer> _inits;

  public Sudoku(final int outerSize, final int subTableHeight, final int subTableWidth) {
    if (subTableHeight >= outerSize || outerSize % subTableHeight != 0) {
      throw new IllegalArgumentException(subTableHeight + " is not an appropriate subtable height for outer size " + outerSize);
    }
    if (subTableWidth >= outerSize || outerSize % subTableWidth != 0) {
      throw new IllegalArgumentException(subTableWidth + " is not an appropriate subtable width for outer size " + outerSize);
    }
    _outerSize = outerSize;
    _subTableHeight = subTableHeight;
    _subTableWidth = subTableWidth;
    final Set<Integer> inits = new LinkedHashSet<>();
    for (int i = 1; i <= _outerSize; i++) {
      inits.add(i);
    }
    _inits = Collections.unmodifiableSet(inits);
  }

  @Override
  protected Set<Integer> getInits() {
    return _inits;
  }

  protected Integer getMaxValue() {
    return _outerSize;
  }
  protected int getSubTableHeight() {
    return _subTableHeight;
  }
  protected int getSubTableWidth() {
    return _subTableWidth;
  }

  @Override
  public void loadValues(final File file) throws IOException {
    TableValueParser<Integer> parser = new TableValueParser<Integer>(getMaxValue(), new CellValueReader<Integer>() {
      @Override
      public Integer parseCellValue(String value) {
        if (value.isEmpty()) {
          return null;
        }
        else {
          return Integer.valueOf(value);
        }
      }
    });
    List<List<Integer>> tableInts = parser.parse(file);
    if (tableInts.size() != getMaxValue()) {
      throw new IllegalArgumentException();
    }
    int rowNum = 0;
    final List<List<Cell<Integer>>> wholePuzzle = new ArrayList<>(getMaxValue());
    for (List<Integer> row : tableInts) {
      rowNum++;
      if (row.size() != getMaxValue()) {
        throw new IllegalArgumentException();
      }
      final List<Cell<Integer>> rowCells = new ArrayList<Cell<Integer>>(getMaxValue());
      int colNum = 0;
      for (Integer integer : row) {
        Coord id = new Coord(rowNum, ++colNum);
        if (integer == null) {
          rowCells.add(new IntegerCell(getInits(), id));
        }
        else {
          if (integer < 1 || integer > getMaxValue()) {
            throw new IllegalArgumentException("Bad init: " + integer);
          }
          rowCells.add(new IntegerCell(integer, id));
        }
      }
      wholePuzzle.add(rowCells);
    }
    addCells(wholePuzzle);
    for (List<Cell<Integer>> row : wholePuzzle) {
      final List<Coord> rowCoords = new ArrayList<>();
      for (Cell<Integer> cell : row) {
        rowCoords.add(cell.getIdentifier());
      }
      addConstraint(Uniqueness.<Integer>of(rowCoords));
    }
    for (int i = 0; i < getMaxValue(); i++) {
      final Set<Coord> colCells = new LinkedHashSet<>();
      for (List<Cell<Integer>> row : wholePuzzle) {
        Cell<Integer> cell = row.get(i);
        colCells.add(cell.getIdentifier());
      }
      addConstraint(Uniqueness.<Integer>of(colCells));
    }
    for (int i = 0; i < getMaxValue() / getSubTableHeight(); i++) {
      for (int j = 0; j < getMaxValue() / getSubTableWidth(); j++) {
        final Set<Coord> subTableCells = new LinkedHashSet<>();
        for (int ii = 0; ii < getSubTableHeight(); ii++) {
          for (int jj = 0; jj < getSubTableWidth(); jj++) {
            int row = i * getSubTableHeight() + ii;
            int col = j * getSubTableWidth() + jj;
            Cell<Integer> cell = wholePuzzle.get(row).get(col);
            subTableCells.add(cell.getIdentifier());
          }
        }
        addConstraint(Uniqueness.<Integer>of(subTableCells));
      }
    }
  }



}
