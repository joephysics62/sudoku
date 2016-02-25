package joephysics62.co.uk.hidato;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

  private static final Path EXAMPLE = Paths.get("examples", "hidato", "wikipedia.txt");

  public static void main(final String[] args) throws IOException {
    final Hidato puzzle = Hidato.read(EXAMPLE);

    final Hidato solution = puzzle.solve();
    solution.writeToPrintStream(System.out);
  }
}
