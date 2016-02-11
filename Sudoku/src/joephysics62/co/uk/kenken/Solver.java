package joephysics62.co.uk.kenken;

public class Solver {
  public Answer solve(final Puzzle puzzle) {
    return new Answer(puzzle.getCoords(), puzzle.getHeight());
  }
}
