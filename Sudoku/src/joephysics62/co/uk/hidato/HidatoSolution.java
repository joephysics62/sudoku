package joephysics62.co.uk.hidato;

import java.io.PrintStream;
import java.util.Optional;

public class HidatoSolution {

  private final Optional<Hidato> _solution;
  private final SolutionType _solutionType;

  public HidatoSolution(final Optional<Hidato> solution, final SolutionType solutionType) {
    _solution = solution;
    _solutionType = solutionType;
  }

  public void write(final PrintStream out) {
    out.println("Solution Type: " + _solutionType);
    _solution.ifPresent(h -> h.write(out));
  }

}
