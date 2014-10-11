package joephysics62.co.uk.sudoku.builder;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import joephysics62.co.uk.sudoku.constraints.Constraint;
import joephysics62.co.uk.sudoku.constraints.AllValuesUniqueness;
import joephysics62.co.uk.sudoku.model.ArrayPuzzle;
import joephysics62.co.uk.sudoku.model.Coord;
import joephysics62.co.uk.sudoku.model.Layout;

public class ArrayBuilder implements Builder {

  private final Layout _layout;
  private Integer[][] _givenCells = null;
  private String _title;
  private final List<Constraint> _constraints = new ArrayList<>();

  public ArrayBuilder(final Layout layout) {
    _layout = layout;
  }

  @Override
  public final void addGiven(Integer value, Coord coord) {
    if (null == _givenCells) {
      _givenCells = new Integer[_layout.getHeight()][_layout.getWidth()];
    }
    _givenCells[coord.getRow() - 1][coord.getCol() -1] = value;
  }

  @Override
  public final void addTitle(final String title) {
    _title = title;
  }
  public Layout getLayout() {
    return _layout;
  }

  @Override
  public final void addConstraint(Constraint constraint) {
    _constraints.add(constraint);
  }

  @Override
  public final ArrayPuzzle build() {
    ArrayPuzzle arrayPuzzle = ArrayPuzzle.forPossiblesSize(_title, _layout, _constraints);
    if (_givenCells != null) {
      arrayPuzzle.addCells(_givenCells);
    }
    return arrayPuzzle;
  }

  public void addRowUniquenessConstraints() {
    for (int rowNum = 1; rowNum <= _layout.getHeight(); rowNum++) {
      final List<Coord> row = new ArrayList<>();
      for (int colNum = 1; colNum <= _layout.getWidth(); colNum++) {
        row.add(Coord.of(rowNum, colNum));
      }
      addConstraint(AllValuesUniqueness.of(row));
    }
  }

  public void addColumnUniquenessConstraints() {
    for (int colNum = 1; colNum <= _layout.getWidth(); colNum++) {
      final List<Coord> column = new ArrayList<>();
      for (int rowNum = 1; rowNum <= _layout.getHeight(); rowNum++) {
        column.add(Coord.of(rowNum, colNum));
      }
      addConstraint(AllValuesUniqueness.of(column));
    }
  }

  public void addSubTableUniquenessConstraints() {
    int subTableHeight = _layout.getSubTableHeight();
    int subTableWidth = _layout.getSubTableWidth();
    for (int subTableRowNum = 1; subTableRowNum <= _layout.getHeight() / subTableHeight; subTableRowNum++) {
      for (int subTableColNum = 1; subTableColNum <= _layout.getWidth() / subTableWidth; subTableColNum++) {
        final Set<Coord> subTableCells = new LinkedHashSet<>();
        for (int rowNumInSubTable = 1; rowNumInSubTable <= subTableHeight; rowNumInSubTable++) {
          for (int colNumInSubTable = 1; colNumInSubTable <= subTableWidth; colNumInSubTable++) {
            int row = (subTableRowNum - 1) * subTableHeight + rowNumInSubTable;
            int col = (subTableColNum - 1) * subTableWidth + colNumInSubTable;
            subTableCells.add(Coord.of(row, col));
          }
        }
        addConstraint(AllValuesUniqueness.of(subTableCells));
      }
    }
  }
}
