package joephysics62.co.uk.puzzle;

import java.io.PrintStream;

public interface Puzzle2D {
  void write(PrintStream out);

  PuzzleSolution<? extends Puzzle2D> solve();
}
