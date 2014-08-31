package joephysics62.co.uk.sudoku.main;

import java.io.File;
import java.io.IOException;

import joephysics62.co.uk.sudoku.model.Puzzle;
import joephysics62.co.uk.sudoku.solver.PuzzleSolver;
import joephysics62.co.uk.sudoku.standard.StandardParser;

public class SolverMain {

  public static void main(final String[] args) throws IOException {
    final File input = new File(args[0]);
    StandardParser parser = new StandardParser();
    Puzzle<Integer> standardPuzzle = parser.parse(input);
    PuzzleSolver solver = new PuzzleSolver();
    long start = System.currentTimeMillis();
    solver.solve(standardPuzzle);
    long end = System.currentTimeMillis();
    System.err.println(end - start + " time ms");
  }

}
