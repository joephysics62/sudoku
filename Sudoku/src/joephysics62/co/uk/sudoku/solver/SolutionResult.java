package joephysics62.co.uk.sudoku.solver;

public class SolutionResult {

  private final SolutionType _type;
  private final SolvedPuzzle _solution;
  private final long _timing;

  public SolutionResult(SolutionType type, SolvedPuzzle solution, final long timing) {
    _type = type;
    _solution = solution;
    _timing = timing;
  }

  public SolutionType getType() {
    return _type;
  }
  public SolvedPuzzle getSolution() {
    return _solution;
  }
  public long getTiming() {
    return _timing;
  }

}
