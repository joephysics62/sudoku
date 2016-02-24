package joephysics62.co.uk.old.sudoku.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import joephysics62.co.uk.old.constraints.Constraint;
import joephysics62.co.uk.old.grid.Coord;
import joephysics62.co.uk.old.grid.Grid;
import joephysics62.co.uk.old.grid.arrays.IntegerArrayGrid;

import org.apache.log4j.Logger;

public class ArrayPuzzle extends IntegerArrayGrid implements Puzzle {
  private final List<Constraint> _fixedConstraints;
  private final List<Constraint> _variableConstraints;
  private final int _inits;
  private final String _title;
  private final PuzzleLayout _layout;

  private static final Logger LOG = Logger.getLogger(ArrayPuzzle.class);

  @Override
  public PuzzleLayout getLayout() {
    return _layout;
  }

  private ArrayPuzzle(final ArrayPuzzle old) {
    super(old);
    _fixedConstraints = old._fixedConstraints;
    _variableConstraints = new ArrayList<>();
    _variableConstraints.addAll(old._variableConstraints);
    _layout = old._layout;
    _inits = old._inits;
    _title = old._title;
  }

  @Override
  public String getTitle() {
    return _title;
  }

  private ArrayPuzzle(final String title, final PuzzleLayout layout, final List<Constraint> fixedConstraints) {
    super(layout);
    _title = title;
    _inits = layout.getInitialValue();
    _fixedConstraints = Collections.unmodifiableList(fixedConstraints);
    _variableConstraints = new ArrayList<>();
    _layout = layout;
  }

  public static ArrayPuzzle forPossiblesSize(final String title, final PuzzleLayout layout, final List<Constraint> constraints) {
    return new ArrayPuzzle(title, layout, constraints);
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
    final ArrayList<Constraint> allConstraints = new ArrayList<>(_fixedConstraints);
    allConstraints.addAll(_variableConstraints);
    return allConstraints;
  }

  @Override
  public List<Constraint> getVariableConstraints() {
    return _variableConstraints;
  }

  @Override
  public List<Constraint> getConstraints(final Coord coord) {
    final List<Constraint> out = new ArrayList<>();
    for (final Constraint constraint : getAllConstraints()) {
      if (constraint.getCells().contains(coord)) {
        out.add(constraint);
      }
    }
    return out;
  }

  @Override
  public boolean isSolved() {
    for (final Coord coord : this) {
      if (!Cell.isSolved(get(coord))) {
        LOG.debug("Puzzle not solved as cell at " + coord + " is not solved");
        return false;
      }
    }
    for (final Constraint constraint : getAllConstraints()) {
      if (!constraint.isSatisfied(this)) {
        LOG.debug("Puzzle not solved as constraint " + constraint.toString() + " not satisfied.");
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean isUnsolveable() {
    for (final Coord coord : this) {
      if (get(coord) == 0) {
        LOG.debug("Puzzle not solveable as cell at " + coord + " has no possible values.");
        return true;
      }
    }
    for (final Constraint constraint : getAllConstraints()) {
      if (!constraint.isSatisfied(this)) {
        LOG.debug("Puzzle not solveable as constraint " + constraint + " is not satisfied.");
        return true;
      }
    }
    return false;
  }

  public void addCells(final Grid<Integer> givenValues) {
    for (final Coord coord : givenValues) {
      final Integer value = givenValues.get(coord);
      set(value == null ? _inits : Cell.cellValueAsBitwise(value), coord);
    }
  }
}
