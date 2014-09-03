package joephysics62.co.uk.sudoku.main;

import java.io.File;
import java.io.IOException;

import joephysics62.co.uk.sudoku.model.Puzzle;
import joephysics62.co.uk.sudoku.puzzleBuilders.FutoshikiBuilder;
import joephysics62.co.uk.sudoku.puzzleBuilders.PuzzleBuilder;
import joephysics62.co.uk.sudoku.puzzleBuilders.SudokuBuilder;
import joephysics62.co.uk.sudoku.solver.PuzzleSolver;
import joephysics62.co.uk.sudoku.solver.SolutionResult;
import joephysics62.co.uk.sudoku.solver.SolutionType;

public class SolverMain {

  public static void main(final String[] args) throws IOException {
    final File input = new File(args[1]);
    final String type = args[0];
    PuzzleBuilder<Integer> sudokuBuilder;
    if (type.equals("timesMini")) {
      sudokuBuilder = new SudokuBuilder(2, 3, 6);
    }
    else if (type.equals("classic")) {
      sudokuBuilder = new SudokuBuilder(3, 3, 9);
    }
    else if (type.equals("futoshiki")) {
      sudokuBuilder = new FutoshikiBuilder(5);
    }
    else {
      throw new IllegalArgumentException();
    }
    Puzzle<Integer> puzzle = sudokuBuilder.read(input);
    puzzle.write(System.out);
    PuzzleSolver<Integer> solver = new PuzzleSolver<Integer>();
    SolutionResult<Integer> result = solver.solve(puzzle);
    if (SolutionType.UNIQUE == result.getType()) {
      System.out.println("Found a unique solution solution(s)");
      result.getSolution().write(System.out);
    }
    System.out.println(result.getTiming() + " time ms");
  }

}
