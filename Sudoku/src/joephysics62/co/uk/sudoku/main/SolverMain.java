package joephysics62.co.uk.sudoku.main;

import java.io.File;
import java.io.IOException;

import joephysics62.co.uk.sudoku.model.Puzzle;
import joephysics62.co.uk.sudoku.read.FutoshikiReader;
import joephysics62.co.uk.sudoku.read.PuzzleReader;
import joephysics62.co.uk.sudoku.read.SudokuHTMLPuzzleReader;
import joephysics62.co.uk.sudoku.solver.FirstClosestToSolved;
import joephysics62.co.uk.sudoku.solver.PuzzleSolver;
import joephysics62.co.uk.sudoku.solver.SolutionResult;
import joephysics62.co.uk.sudoku.solver.SolutionType;
import joephysics62.co.uk.sudoku.write.PuzzleWriter;

public class SolverMain {

  public static void main(final String[] args) throws IOException {
    final File input = new File(args[1]);
    final String type = args[0];
    PuzzleReader puzzleReader;
    if (type.equals("timesMini")) {
      puzzleReader = new SudokuHTMLPuzzleReader(2, 3, 6);
    }
    else if (type.equals("classic")) {
      puzzleReader = new SudokuHTMLPuzzleReader(3, 3, 9);
    }
    else if (type.equals("futoshiki")) {
      puzzleReader = new FutoshikiReader(5);
    }
    else if (type.equals("super")) {
      puzzleReader = new SudokuHTMLPuzzleReader(4, 4, 16);
    }
    else {
      throw new IllegalArgumentException();
    }
    Puzzle puzzle = puzzleReader.read(input);
    PuzzleWriter puzzleWriter = new PuzzleWriter(System.out);
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
