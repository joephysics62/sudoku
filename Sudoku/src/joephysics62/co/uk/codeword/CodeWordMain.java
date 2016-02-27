package joephysics62.co.uk.codeword;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import joephysics62.co.uk.puzzle.Puzzle2D;
import joephysics62.co.uk.puzzle.PuzzleSolution;

public class CodeWordMain {

  private static final Path FILE = Paths.get("examples", "codeword", "codeWordTimes2635.txt");

  public static void main(final String[] args) throws Exception {
    final CodeWord puzzle = CodeWord.Reader.create().read(FILE);
    puzzle.renderPuzzle(new File("out.html"), 30);
    final PuzzleSolution<? extends Puzzle2D> solved = puzzle.solve();
    solved.write(System.out);

    solved.getSolution().get().renderAnswer(new File("out2.html"), 30);
  }

}
