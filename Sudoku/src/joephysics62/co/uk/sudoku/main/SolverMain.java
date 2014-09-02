package joephysics62.co.uk.sudoku.main;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import joephysics62.co.uk.sudoku.model.Puzzle;
import joephysics62.co.uk.sudoku.model.PuzzleSolution;
import joephysics62.co.uk.sudoku.parse.CellValueReader;
import joephysics62.co.uk.sudoku.parse.TableValueParser;
import joephysics62.co.uk.sudoku.solver.PuzzleSolver;
import joephysics62.co.uk.sudoku.standard.Sudoku;

public class SolverMain {

  private static Set<Integer> upTo(final int max) {
    final Set<Integer> inits = new LinkedHashSet<>();
    for (int i = 1; i <= max; i++) {
      inits.add(i);
    }
    return Collections.unmodifiableSet(inits);
  }

  public static void main(final String[] args) throws IOException {
    final File input = new File(args[1]);
    final String type = args[0];
    Puzzle<Integer> puzzle;
    CellValueReader<Integer> cellValueReader = new CellValueReader<Integer>() {
      @Override
      public Integer parseCellValue(String value) {
        if (value.isEmpty()) {
          return null;
        }
        return Integer.valueOf(value);
      }
    };
    if (type.equals("timesMini")) {
      final TableValueParser<Integer> parser = new TableValueParser<Integer>(6, cellValueReader);
      puzzle = Sudoku.loadValues(input, upTo(6), 2, 3, parser);
    }
    else if (type.equals("classic")) {
      final TableValueParser<Integer> parser = new TableValueParser<Integer>(9, cellValueReader);
      puzzle = Sudoku.loadValues(input, upTo(9), 3, 3, parser);
    }
    else {
      throw new IllegalArgumentException();
    }
    puzzle.write(System.out);
    System.out.println("Initial completeness: " + puzzle.completeness());
    System.out.println();
    PuzzleSolver<Integer> solver = new PuzzleSolver<Integer>();
    long start = System.currentTimeMillis();
    PuzzleSolution<Integer> solution = solver.solve(puzzle);
    if (null != solution) {
      System.out.println("Found a unique solution solution(s)");
      solution.write(System.out);
    }
    long end = System.currentTimeMillis();
    System.err.println(end - start + " time ms");
  }

}
