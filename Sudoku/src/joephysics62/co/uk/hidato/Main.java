package joephysics62.co.uk.hidato;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import joephysics62.co.uk.puzzle.PuzzleSolution;

public class Main {

  private static final Path EXAMPLE = Paths.get("examples", "hidato", "online-irregular.txt");

  public static void main(final String[] args) throws Exception {
    final Hidato puzzle = Hidato.read(EXAMPLE);
    puzzle.renderPuzzle(new File("out.html"), 50);
    final PuzzleSolution<Hidato> solution = puzzle.solve();
    solution.write(System.out);
  }
}
