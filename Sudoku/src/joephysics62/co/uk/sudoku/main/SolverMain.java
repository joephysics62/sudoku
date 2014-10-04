package joephysics62.co.uk.sudoku.main;

import java.io.File;
import java.io.IOException;

import joephysics62.co.uk.sudoku.model.Puzzle;
import joephysics62.co.uk.sudoku.model.Layout;
import joephysics62.co.uk.sudoku.read.FutoshikiHTMLReader;
import joephysics62.co.uk.sudoku.read.Reader;
import joephysics62.co.uk.sudoku.read.SudokuHTMLReader;
import joephysics62.co.uk.sudoku.solver.FirstClosestToSolved;
import joephysics62.co.uk.sudoku.solver.Solver;
import joephysics62.co.uk.sudoku.solver.SolutionResult;
import joephysics62.co.uk.sudoku.solver.SolutionType;
import joephysics62.co.uk.sudoku.write.TextWriter;

public class SolverMain {

  private static Reader readerForType(final String type) {
    if (type.equals("timesMini")) {
      return new SudokuHTMLReader(Layout.TIMES_MINI);
    }
    else if (type.equals("classic")) {
      return new SudokuHTMLReader(Layout.CLASSIC_SUDOKU);
    }
    else if (type.equals("futoshiki")) {
      return new FutoshikiHTMLReader(Layout.FUTOSHIKI);
    }
    else if (type.equals("super")) {
      return new SudokuHTMLReader(Layout.SUPER_SUDOKU);
    }
    else {
      throw new IllegalArgumentException();
    }
  }

  public static void main(final String[] args) throws IOException {
    final String type = args[0];
    final File input = new File(args[1]);
    Puzzle puzzle = readerForType(type).read(input);
    TextWriter puzzleWriter = new TextWriter(System.out);
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
