package joephysics62.co.uk.puzzle;

import java.io.PrintStream;
import java.util.Optional;

public class PuzzleSolution<T extends Puzzle2D> {

  private final Optional<T> _solution;
  private final SolutionType _solutionType;

  public PuzzleSolution(final Optional<T> solution, final SolutionType solutionType) {
    _solution = solution;
    _solutionType = solutionType;
  }

  public Optional<T> getSolution() {
    return _solution;
  }

  public void write(final PrintStream out) {
    out.println("Solution Type: " + _solutionType);
    _solution.ifPresent(h -> h.write(out));
  }

}
