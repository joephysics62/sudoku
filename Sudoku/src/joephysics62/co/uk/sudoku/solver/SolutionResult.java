package joephysics62.co.uk.sudoku.solver;

public class SolutionResult<T extends Comparable<T>> {

  private final SolutionType _type;
  private final SolvedPuzzle<T> _solution;
  private final long _timing;

  public SolutionResult(SolutionType type, SolvedPuzzle<T> solution, final long timing) {
    _type = type;
    _solution = solution;
    _timing = timing;
  }

  public SolutionType getType() {
    return _type;
  }
  public SolvedPuzzle<T> getSolution() {
    return _solution;
  }
  public long getTiming() {
    return _timing;
  }

}
