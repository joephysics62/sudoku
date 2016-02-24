package joephysics62.co.uk.old.sudoku.builder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import joephysics62.co.uk.old.constraints.AllValuesUniqueness;
import joephysics62.co.uk.old.constraints.Constraint;
import joephysics62.co.uk.old.grid.Coord;
import joephysics62.co.uk.old.grid.Grid;
import joephysics62.co.uk.old.grid.arrays.IntegerArrayGrid;
import joephysics62.co.uk.old.grid.map.MapGrid;
import joephysics62.co.uk.old.sudoku.model.ArrayPuzzle;
import joephysics62.co.uk.old.sudoku.model.PuzzleLayout;

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
  public void addNonValueCell(final Coord coord) {
    _nonValueCells.add(coord);
  }

  @Override
  public final void addGiven(final Integer value, final Coord coord) {
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
  public final void addConstraint(final Constraint constraint) {
    _constraints.add(constraint);
  }

  @Override
  public final ArrayPuzzle build() {
    final ArrayPuzzle arrayPuzzle = ArrayPuzzle.forPossiblesSize(_title, _layout, _constraints);
    if (_givenCells != null) {
      arrayPuzzle.addCells(_givenCells);
    }
    for (final Coord coord : _nonValueCells) {
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

  private int getColAsId(final PuzzleLayout layout, final Coord coord) {
    return coord.getCol();
  }

  private int getRowAsId(final PuzzleLayout layout, final Coord coord) {
    return coord.getRow();
  }

  public int getSubtableId(final PuzzleLayout layout, final Coord coord) {
    final int subTableCol = 1 + (coord.getCol() - 1) / layout.getSubTableWidth();
    final int subTableRow = 1 + (coord.getRow() - 1) / layout.getSubTableHeight();
    final int subTableId = (subTableRow - 1) * (_layout.getWidth() / layout.getSubTableWidth()) + subTableCol;
    return subTableId;
  }

  private List<Constraint> addGeometricConstraints(final IdProvider idProvider) {
    final List<Constraint> addedConstraints = new ArrayList<>();
    final Multimap<Integer, Coord> groupsById = ArrayListMultimap.create();
    for (final Coord coord : new IntegerArrayGrid(_layout)) {
      groupsById.put(idProvider.getId(_layout, coord), coord);
    }
    for (final Collection<Coord> group : groupsById.asMap().values()) {
      addedConstraints.add(AllValuesUniqueness.of(group));
    }
    for (final Constraint constraint : addedConstraints) {
      addConstraint(constraint);
    }
    return Collections.unmodifiableList(addedConstraints);
  }
}
