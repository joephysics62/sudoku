package joephysics62.co.uk.sudoku.main;

import java.io.File;
import java.io.IOException;

import joephysics62.co.uk.sudoku.model.Puzzle;
import joephysics62.co.uk.sudoku.read.FutoshikiReader;
import joephysics62.co.uk.sudoku.read.PuzzleReader;
import joephysics62.co.uk.sudoku.read.SudokuReader;
import joephysics62.co.uk.sudoku.solver.PuzzleSolver;
import joephysics62.co.uk.sudoku.solver.SolutionResult;
import joephysics62.co.uk.sudoku.solver.SolutionType;
import joephysics62.co.uk.sudoku.write.PuzzleWriter;

public class SolverMain {

  public static void main(final String[] args) throws IOException {
    final File input = new File(args[1]);
    final String type = args[0];
    PuzzleReader<Integer> sudokuBuilder;
    if (type.equals("timesMini")) {
      sudokuBuilder = new SudokuReader(2, 3, 6);
    }
    else if (type.equals("classic")) {
      sudokuBuilder = new SudokuReader(3, 3, 9);
    }
    else if (type.equals("futoshiki")) {
      sudokuBuilder = new FutoshikiReader(5);
    }
    else {
      throw new IllegalArgumentException();
    }
    Puzzle<Integer> puzzle = sudokuBuilder.read(input);
    new PuzzleWriter<>(puzzle).write(System.out);
    PuzzleSolver<Integer> solver = new PuzzleSolver<Integer>();
    SolutionResult<Integer> result = solver.solve(puzzle);
    if (SolutionType.UNIQUE == result.getType()) {
      System.out.println("Found a unique solution solution(s)");
      result.getSolution().write(System.out);
    }
    else {
      System.err.println("No unique solution: " + result.getType());
    }
    System.out.println(result.getTiming() + " time ms");
  }

}
