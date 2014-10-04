package joephysics62.co.uk.sudoku.main;

import java.io.File;
import java.io.IOException;

import joephysics62.co.uk.sudoku.model.Puzzle;
import joephysics62.co.uk.sudoku.model.PuzzleLayout;
import joephysics62.co.uk.sudoku.read.FutoshikiHTMLReader;
import joephysics62.co.uk.sudoku.read.PuzzleReader;
import joephysics62.co.uk.sudoku.read.SudokuHTMLPuzzleReader;
import joephysics62.co.uk.sudoku.solver.FirstClosestToSolved;
import joephysics62.co.uk.sudoku.solver.PuzzleSolver;
import joephysics62.co.uk.sudoku.solver.SolutionResult;
import joephysics62.co.uk.sudoku.solver.SolutionType;
import joephysics62.co.uk.sudoku.write.TextPuzzleWriter;

public class SolverMain {

  private static PuzzleReader readerForType(final String type) {
    if (type.equals("timesMini")) {
      return new SudokuHTMLPuzzleReader(PuzzleLayout.TIMES_MINI);
    }
    else if (type.equals("classic")) {
      return new SudokuHTMLPuzzleReader(PuzzleLayout.CLASSIC_SUDOKU);
    }
    else if (type.equals("futoshiki")) {
      return new FutoshikiHTMLReader(PuzzleLayout.FUTOSHIKI);
    }
    else if (type.equals("super")) {
      return new SudokuHTMLPuzzleReader(PuzzleLayout.SUPER_SUDOKU);
    }
    else {
      throw new IllegalArgumentException();
    }
  }

  public static void main(final String[] args) throws IOException {
    final String type = args[0];
    final File input = new File(args[1]);
    Puzzle puzzle = readerForType(type).read(input);
    TextPuzzleWriter puzzleWriter = new TextPuzzleWriter(System.out);
    System.out.println("INPUT = ");
    puzzleWriter.write(puzzle);
    PuzzleSolver solver = new PuzzleSolver(FirstClosestToSolved.create());
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
