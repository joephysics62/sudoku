package joephysics62.co.uk.kenken;

import java.util.Set;

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

  public Set<Coordinate> getCoords() { return _coords; }
  public void setCoords(final Set<Coordinate> coords) { _coords = coords; }

  public int getTarget() { return _target; }
  public void setTarget(final int target) { _target = target; }

  public Operator getOperator() { return _operator; }
  public void setOperator(final Operator operator) { _operator = operator; }
}