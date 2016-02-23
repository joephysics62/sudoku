package joephysics62.co.uk.codeword;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class CodeWordMain {

  private static final String FILE = "examples\\codeword\\codeWordTimes2635.csv";

  public static void main(final String[] args) throws IOException, URISyntaxException {
    final String file = FILE;
    final CodeWord puzzle = CodeWord.readFromFile(file);
    final CodewordSolver codewordSolver = new CodewordSolver();
    final List<CodeWord> solvedPuzzles = codewordSolver.solve(puzzle);
    System.out.println(solvedPuzzles.get(0).getKey());
    System.out.println(new CodeWord(puzzle.getGrid(), solvedPuzzles.get(0).getKey()));
  }

}
