package joephysics62.co.uk.puzzle;

import java.io.File;
import java.io.PrintStream;

public interface Puzzle2D {
  void write(PrintStream out);

  void render(File htmlFile) throws Exception;

  PuzzleSolution<? extends Puzzle2D> solve();
}
