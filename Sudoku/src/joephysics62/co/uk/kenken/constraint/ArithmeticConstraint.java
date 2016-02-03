package joephysics62.co.uk.kenken.constraint;

import java.util.Set;

import joephysics62.co.uk.kenken.Operator;
import joephysics62.co.uk.kenken.PuzzleAnswer;
import joephysics62.co.uk.kenken.grid.Coordinate;

public class ArithmeticConstraint implements Constraint {
  private Set<Coordinate> _coords;
  private int _target;
  private Operator _operator;

  public ArithmeticConstraint() {}

  public ArithmeticConstraint(final Set<Coordinate> coords, final int target, final Operator operator) {
    _coords = coords;
    _target = target;
    _operator = operator;
  }

  @Override
  public boolean isSatisfiedBy(final PuzzleAnswer answer) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void applyConstraint(final PuzzleAnswer answer) {
    // TODO Auto-generated method stub

  }

  @Override
  public Set<Coordinate> getCoords() { return _coords; }
  public void setCoords(final Set<Coordinate> coords) { _coords = coords; }

  public int getTarget() { return _target; }
  public void setTarget(final int target) { _target = target; }

  public Operator getOperator() { return _operator; }
  public void setOperator(final Operator operator) { _operator = operator; }
}