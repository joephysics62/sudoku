package joephysics62.co.uk.sudoku.builder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import joephysics62.co.uk.constraints.AllValuesUniqueness;
import joephysics62.co.uk.constraints.Constraint;
import joephysics62.co.uk.grid.Coord;
import joephysics62.co.uk.grid.Grid;
import joephysics62.co.uk.grid.arrays.IntegerArrayGrid;
import joephysics62.co.uk.grid.map.MapGrid;
import joephysics62.co.uk.sudoku.model.ArrayPuzzle;
import joephysics62.co.uk.sudoku.model.PuzzleLayout;

public class ArrayBuilder implements Builder {

  private final PuzzleLayout _layout;
  private Grid<Integer> _givenCells = null;
  private String _title;
  private final List<Constraint> _constraints = new ArrayList<>();
  private final Set<Coord> _nonValueCells = new LinkedHashSet<>();

  public ArrayBuilder(final PuzzleLayout layout) {
    _layout = layout;
  }

  @Override
  public void addNonValueCell(Coord coord) {
    _nonValueCells.add(coord);
  }

  @Override
  public final void addGiven(Integer value, Coord coord) {
    if (null == _givenCells) {
      _givenCells = new MapGrid<>(_layout);
    }
    _givenCells.set(value, coord);
  }

  @Override
  public final void addTitle(final String title) {
    _title = title;
  }
  public PuzzleLayout getLayout() {
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
    for (Coord coord : _nonValueCells) {
      arrayPuzzle.set(-1, coord);
    }
    return arrayPuzzle;
  }

  public List<Constraint> addRowUniquenessConstraints() {
    final List<Constraint> addedConstraints = new ArrayList<>();

    final Map<Integer, List<Coord>> rows = new LinkedHashMap<>();
    for (Coord coord : new IntegerArrayGrid(_layout)) {
      int rowNum = coord.getRow();
      if (!rows.containsKey(rowNum)) {
        rows.put(rowNum, new ArrayList<>());
      }
      rows.get(rowNum).add(coord);
    }
    for (List<Coord> row : rows.values()) {
      addedConstraints.add(AllValuesUniqueness.of(row));
    }
    for (Constraint constraint : addedConstraints) {
      addConstraint(constraint);
    }
    return Collections.unmodifiableList(addedConstraints);
  }

  public List<Constraint> addColumnUniquenessConstraints() {
    final List<Constraint> addedConstraints = new ArrayList<>();
    final Map<Integer, List<Coord>> cols = new LinkedHashMap<>();
    for (Coord coord : new IntegerArrayGrid(_layout)) {
      int colNum = coord.getCol();
      if (!cols.containsKey(colNum)) {
        cols.put(colNum, new ArrayList<>());
      }
      cols.get(colNum).add(coord);
    }
    for (List<Coord> col : cols.values()) {
      addedConstraints.add(AllValuesUniqueness.of(col));
    }
    for (Constraint constraint : addedConstraints) {
      addConstraint(constraint);
    }
    return Collections.unmodifiableList(addedConstraints);
  }

  public List<Constraint> addSubTableUniquenessConstraints() {
    final List<Constraint> addedConstraints = new ArrayList<>();
    int subTableHeight = _layout.getSubTableHeight();
    int subTableWidth = _layout.getSubTableWidth();
    final Map<Integer, List<Coord>> subtables = new LinkedHashMap<>();
    for (Coord coord : new IntegerArrayGrid(_layout)) {
      int subTableCol = 1 + (coord.getCol() - 1) / subTableWidth;
      int subTableRow = 1 + (coord.getRow() - 1) / subTableHeight;
      int subTableId = (subTableRow - 1) * (_layout.getWidth() / subTableWidth) + subTableCol;
      if (!subtables.containsKey(subTableId)) {
        subtables.put(subTableId, new ArrayList<>());
      }
      subtables.get(subTableId).add(coord);
    }
    for (List<Coord> subtable : subtables.values()) {
      addedConstraints.add(AllValuesUniqueness.of(subtable));
    }
    for (Constraint constraint : addedConstraints) {
      addConstraint(constraint);
    }
    return Collections.unmodifiableList(addedConstraints);
  }
}
