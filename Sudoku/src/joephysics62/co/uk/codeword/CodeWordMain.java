package joephysics62.co.uk.codeword;

import java.io.File;

import joephysics62.co.uk.puzzle.PuzzleSolution;

public class CodeWordMain {

  private static final String FILE = "examples\\codeword\\codeWordTimes2635.txt";

  public static void main(final String[] args) throws Exception {
    final String file = FILE;
    final CodeWord puzzle = CodeWord.readFromFile(file);
    final PuzzleSolution<CodeWord> solved = puzzle.solve();
    solved.write(System.out);

    solved.getSolution().get().render(new File("out2.html"), 30);
  }

}
