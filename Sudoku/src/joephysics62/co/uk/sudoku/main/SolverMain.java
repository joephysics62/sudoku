package joephysics62.co.uk.sudoku.main;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import joephysics62.co.uk.sudoku.model.Puzzle;
import joephysics62.co.uk.sudoku.model.PuzzleSolution;
import joephysics62.co.uk.sudoku.solver.PuzzleSolver;
import joephysics62.co.uk.sudoku.standard.StandardParser;

public class SolverMain {

  public static void main(final String[] args) throws IOException {
    final File input = new File(args[0]);
    StandardParser parser = new StandardParser();
    Puzzle<Integer> standardPuzzle = parser.parse(input);
    PuzzleSolver solver = new PuzzleSolver();
    long start = System.currentTimeMillis();
    Set<PuzzleSolution<Integer>> solve = solver.solve(standardPuzzle);
    System.out.println("Found " + solve.size() + " solution(s)");
    for (PuzzleSolution<Integer> puzzleSolution : solve) {
      puzzleSolution.write(System.out);
    }
    long end = System.currentTimeMillis();
    System.err.println(end - start + " time ms");
  }

}
