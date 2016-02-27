package joephysics62.co.uk.hidato;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

  private static final Path EXAMPLE = Paths.get("examples", "hidato", "online-irregular.txt");

  public static void main(final String[] args) throws Exception {
//    final Hidato puzzle = Hidato.Reader.create().read(EXAMPLE);
//    puzzle.renderPuzzle(new File("out.html"), 50);
//    final PuzzleSolution<Puzzle2D> solution = puzzle.solve();
//    solution.write(System.out);

    final Hidato newHidato = Hidato.create(5, 7);
    newHidato.writePuzzle(System.out);
  }
}
