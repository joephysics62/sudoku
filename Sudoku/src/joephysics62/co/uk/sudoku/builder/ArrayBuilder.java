package joephysics62.co.uk.sudoku.builder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import joephysics62.co.uk.constraints.AllValuesUniqueness;
import joephysics62.co.uk.constraints.Constraint;
import joephysics62.co.uk.grid.Coord;
import joephysics62.co.uk.grid.Grid;
import joephysics62.co.uk.grid.arrays.IntegerArrayGrid;
import joephysics62.co.uk.grid.map.MapGrid;
import joephysics62.co.uk.sudoku.model.ArrayPuzzle;
import joephysics62.co.uk.sudoku.model.PuzzleLayout;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

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
    return addGeometricConstraints(this::getRowAsId);
  }

  public List<Constraint> addColumnUniquenessConstraints() {
    return addGeometricConstraints(this::getColAsId);
  }

  public List<Constraint> addSubTableUniquenessConstraints() {
    return addGeometricConstraints(this::getSubtableId);
  }

  private interface IdProvider {
    int getId(PuzzleLayout layout, Coord coord);
  }

  private int getColAsId(PuzzleLayout layout, Coord coord) {
    return coord.getCol();
  }

  private int getRowAsId(PuzzleLayout layout, Coord coord) {
    return coord.getRow();
  }

  public int getSubtableId(PuzzleLayout layout, Coord coord) {
    int subTableCol = 1 + (coord.getCol() - 1) / layout.getSubTableWidth();
    int subTableRow = 1 + (coord.getRow() - 1) / layout.getSubTableHeight();
    int subTableId = (subTableRow - 1) * (_layout.getWidth() / layout.getSubTableWidth()) + subTableCol;
    return subTableId;
  }

  private List<Constraint> addGeometricConstraints(IdProvider idProvider) {
    final List<Constraint> addedConstraints = new ArrayList<>();
    final Multimap<Integer, Coord> groupsById = ArrayListMultimap.create();
    for (Coord coord : new IntegerArrayGrid(_layout)) {
      groupsById.put(idProvider.getId(_layout, coord), coord);
    }
    for (Collection<Coord> group : groupsById.asMap().values()) {
      addedConstraints.add(AllValuesUniqueness.of(group));
    }
    for (Constraint constraint : addedConstraints) {
      addConstraint(constraint);
    }
    return Collections.unmodifiableList(addedConstraints);
  }
}
