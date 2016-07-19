package joephysics62.co.uk.sudoku;

import java.io.IOException;
import java.nio.file.Paths;

public class BacktrackMain {
  public static void main(final String[] args) throws IOException {
    solve(ClassicSudoku.readFile(Paths.get("examples", "sudoku", "classic", "times-7999"), 9, 3));

    //final KillerSudoku killerSudoku = KillerSudoku.readFile(Paths.get("examples", "killer", "times-4699.txt"));
    //solve(killerSudoku);

  }

  private static void solve(final NumericBacktrackPuzzle puzzle) {
    final long startTime = System.currentTimeMillis();
//    for (int i = 0; i < 1000000; i++) {
//      puzzle.solve();
//    }
    for (final int[][] is : puzzle.solve()) {
      puzzle.printGrid(is);
    }
    System.out.println(System.currentTimeMillis() - startTime + "ms");
  }

}
