package joephysics62.co.uk.kenken;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {

  private static final String EXAMPLE = "examples\\kenken\\times-3627.csv";

  public static void main(final String[] args) throws IOException {
    final Puzzle puzzle = new Reader().read(new File(EXAMPLE));

    final long start = System.currentTimeMillis();
    final List<Answer> solvedUnique = puzzle.solvedUnique();
    System.out.println("Solution time : " + (System.currentTimeMillis() - start) + "ms");
    for (final Answer answer : solvedUnique) {
      answer.writeAsGrid(System.out);
      System.out.println();
    }
  }

}
