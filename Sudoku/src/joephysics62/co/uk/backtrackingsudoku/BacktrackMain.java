package joephysics62.co.uk.backtrackingsudoku;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class BacktrackMain {
  public static void main(final String[] args) throws IOException {
    final Path inputFile = Paths.get("examples", "sudoku", "classic", "times-7999");
    final BSudoku puzzle = BSudoku.readPuzzle(inputFile, 9, 3);
    final long startTime = System.currentTimeMillis();
    for (final int[][] is : puzzle.solve()) {
      printGrid(is);
    }
    System.out.println(System.currentTimeMillis() - startTime + "ms");
  }

  private static void printGrid(final int[][] answer) {
    for (int i = 0; i < answer.length; i++) {
      System.out.println(Arrays.toString(answer[i]));
    }
    System.out.println();
  }

}
