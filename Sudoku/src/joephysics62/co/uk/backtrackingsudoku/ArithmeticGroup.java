package joephysics62.co.uk.backtrackingsudoku;

import java.util.List;

import joephysics62.co.uk.grid.Coordinate;

public class ArithmeticGroup {
  private final Operator _op;
  private final List<Coordinate> _cells;
  private final int _target;

  public ArithmeticGroup(final Operator op, final int target, final List<Coordinate> cells) {
    _op = op;
    _cells = cells;
    _target = target;
  }

  public List<Coordinate> getCells() { return _cells; }
  public Operator getOp() { return _op; }
  public int getTarget() { return _target; }
}