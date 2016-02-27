package joephysics62.co.uk.old.sudoku.main;

import java.io.File;
import java.io.IOException;

import joephysics62.co.uk.old.sudoku.model.GridUniqueness;
import joephysics62.co.uk.old.sudoku.model.Puzzle;
import joephysics62.co.uk.old.sudoku.model.PuzzleLayout;
import joephysics62.co.uk.old.sudoku.read.FutoshikiHtmlReader;
import joephysics62.co.uk.old.sudoku.read.KakuroHtmlReader;
import joephysics62.co.uk.old.sudoku.read.KillerSudokuHtmlReader;
import joephysics62.co.uk.old.sudoku.read.PuzzleReader;
import joephysics62.co.uk.old.sudoku.read.SudokuHtmlReader;
import joephysics62.co.uk.old.sudoku.solver.FirstClosestToSolved;
import joephysics62.co.uk.old.sudoku.solver.SolutionResult;
import joephysics62.co.uk.old.sudoku.solver.Solver;
import joephysics62.co.uk.old.sudoku.write.PuzzleTextWriter;
import joephysics62.co.uk.puzzle.SolutionType;

public class SolverMain {

  private static PuzzleReader readerForType(final String type) {
    if (type.equals("timesMini")) {
      return new SudokuHtmlReader(PuzzleLayout.TIMES_MINI);
    }
    else if (type.equals("classic")) {
      return new SudokuHtmlReader(PuzzleLayout.CLASSIC_SUDOKU);
    }
    else if (type.equals("futoshiki")) {
      return new FutoshikiHtmlReader(PuzzleLayout.FUTOSHIKI);
    }
    else if (type.equals("super")) {
      return new SudokuHtmlReader(PuzzleLayout.SUPER_SUDOKU);
    }
    else if (type.equals("kakuro")) {
      return new KakuroHtmlReader(new PuzzleLayout(10, 10, 0, 0, 9, GridUniqueness.CUSTOM)); // TODO this obviously isn't generic enough.
    }
    else if (type.equals("killer")) {
      return new KillerSudokuHtmlReader(PuzzleLayout.CLASSIC_SUDOKU);
    }
    else {
      throw new IllegalArgumentException();
    }
  }

  public static void main(final String[] args) throws IOException {
    final String type = args[0];
    final File input = new File(args[1]);
    Puzzle puzzle = readerForType(type).read(input);
    PuzzleTextWriter puzzleWriter = new PuzzleTextWriter(System.out);
    System.out.println("INPUT = ");
    puzzleWriter.write(puzzle);
    Solver solver = new Solver(FirstClosestToSolved.create());
    SolutionResult result = solver.solve(puzzle);

    if (SolutionType.NONE == result.getType()) {
      System.err.println("No unique solution: " + result.getType());
    }
    else {
      if (SolutionType.UNIQUE == result.getType()) {
        System.out.println("Found a unique solution solution");
      }
      else {
        System.out.println("Found multiple solutions. Example:");
      }
      result.getSolution().write(System.out);
    }
    System.out.println(result.getTiming() + " time ms");
  }

}
