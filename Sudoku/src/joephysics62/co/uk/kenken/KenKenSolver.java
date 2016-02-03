package joephysics62.co.uk.kenken;

public class KenKenSolver {
  public PuzzleAnswer solve(final KenKen puzzle) {
    return new PuzzleAnswer(puzzle.getCoords(), puzzle.getHeight());
  }
}
