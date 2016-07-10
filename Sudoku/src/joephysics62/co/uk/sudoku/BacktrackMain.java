package joephysics62.co.uk.sudoku;

import java.io.IOException;
import java.nio.file.Paths;

public class BacktrackMain {
  public static void main(final String[] args) throws IOException {
    solve(ClassicSudoku.readFile(Paths.get("examples", "sudoku", "classic", "times-7999"), 9, 3));

    //solve(KillerSudoku.readFile(Paths.get("examples", "killer", "times-4700.txt")));

  }

  private static void solve(final NumericBacktrackPuzzle puzzle) {
    final long startTime = System.currentTimeMillis();
    for (final int[][] is : puzzle.solve()) {
      puzzle.printGrid(is);
    }
    System.out.println(System.currentTimeMillis() - startTime + "ms");
  }

}
