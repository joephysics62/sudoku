package joephysics62.co.uk.old.sudoku.solver;

import joephysics62.co.uk.puzzle.SolutionType;

public class SolutionResult {

  private final SolutionType _type;
  private final Solution _solution;
  private final long _timing;

  public SolutionResult(SolutionType type, Solution solution, final long timing) {
    _type = type;
    _solution = solution;
    _timing = timing;
  }

  public SolutionType getType() {
    return _type;
  }
  public Solution getSolution() {
    return _solution;
  }
  public long getTiming() {
    return _timing;
  }

}
