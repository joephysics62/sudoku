package joephysics62.co.uk.sudoku;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BacktrackMain {
  public static void main(final String[] args) throws IOException {
    final Path inputFile = Paths.get("examples", "killer", "times-4699.txt");
    final KillerSudoku puzzle = KillerSudoku.readFile(inputFile);
    final long startTime = System.currentTimeMillis();
    for (final int[][] is : puzzle.solve()) {
      puzzle.printGrid(is);
    }
    System.out.println(System.currentTimeMillis() - startTime + "ms");
  }

}
