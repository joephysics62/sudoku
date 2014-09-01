package joephysics62.co.uk.sudoku.main;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import joephysics62.co.uk.sudoku.model.Puzzle;
import joephysics62.co.uk.sudoku.model.PuzzleSolution;
import joephysics62.co.uk.sudoku.solver.PuzzleSolver;
import joephysics62.co.uk.sudoku.standard.StandardSizeSudoku;
import joephysics62.co.uk.sudoku.standard.TimesThreeByTwoSudoku;

public class SolverMain {

  public static void main(final String[] args) throws IOException {
    final File input = new File(args[1]);
    final String type = args[0];
    Puzzle<Integer> puzzle;
    if (type.equals("timesMini")) {
      puzzle = new TimesThreeByTwoSudoku();
    }
    else if (type.equals("classic")) {
      puzzle = new StandardSizeSudoku();
    }
    else {
      throw new IllegalArgumentException();
    }
    puzzle.loadValues(input);
    puzzle.write(System.out);
    System.out.println("Initial completeness: " + puzzle.completeness());
    System.out.println();
    PuzzleSolver<Integer> solver = new PuzzleSolver<Integer>();
    long start = System.currentTimeMillis();
    Set<PuzzleSolution<Integer>> solve = solver.solve(puzzle);
    System.out.println("Found " + solve.size() + " solution(s)");
    for (PuzzleSolution<Integer> puzzleSolution : solve) {
      puzzleSolution.write(System.out);
    }
    long end = System.currentTimeMillis();
    System.err.println(end - start + " time ms");
  }

}
