package joephysics62.co.uk.puzzle;

import java.io.File;
import java.io.PrintStream;

public interface Puzzle2D {
  void writeAnswer(PrintStream out);

  void writePuzzle(PrintStream out);

  void render(File htmlFile, int cellSize) throws Exception;

  PuzzleSolution<? extends Puzzle2D> solve();
}
