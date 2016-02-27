package joephysics62.co.uk.puzzle;

import java.io.File;
import java.io.PrintStream;

public interface Puzzle2D {
  void writeAnswer(PrintStream out);

  void writePuzzle(PrintStream out);

  void renderAnswer(File htmlFile, int cellSize) throws Exception;

  void renderPuzzle(File htmlFile, int cellSize) throws Exception;

  PuzzleSolution<? extends Puzzle2D> solve();
}
