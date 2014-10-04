package joephysics62.co.uk.sudoku.main;

import java.io.File;
import java.io.IOException;

import joephysics62.co.uk.sudoku.creator.CreationSpec;
import joephysics62.co.uk.sudoku.creator.FutoshikiCreator;
import joephysics62.co.uk.sudoku.creator.PuzzleCreator;
import joephysics62.co.uk.sudoku.creator.SudokuCreator;
import joephysics62.co.uk.sudoku.creator.util.RandomUnsolved;
import joephysics62.co.uk.sudoku.model.Puzzle;
import joephysics62.co.uk.sudoku.model.PuzzleLayout;
import joephysics62.co.uk.sudoku.solver.PuzzleSolver;
import joephysics62.co.uk.sudoku.write.HTMLFutoshikiWriter;
import joephysics62.co.uk.sudoku.write.HTMLPuzzleWriter;
import joephysics62.co.uk.sudoku.write.HTMLSudokuWriter;
import freemarker.template.TemplateException;

public class CreatorMain {
  private static final int MAX_DEPTH = 500;

  public static void main(String[] args) throws IOException, TemplateException {
    final String type = args[0];
    final File output = new File(args[1]);
    final Puzzle puzzle = buildPuzzleCreator(type).create();
    HTMLPuzzleWriter htmlPuzzleWriter = type.equals("futoshiki") ? new HTMLFutoshikiWriter(puzzle) : new HTMLSudokuWriter(puzzle);
    htmlPuzzleWriter.write(output);
  }

  private static PuzzleCreator buildPuzzleCreator(final String type) {
    PuzzleSolver solver = new PuzzleSolver(RandomUnsolved.create());
    if (type.equals("timesMini")) {
      return new SudokuCreator(solver, PuzzleLayout.TIMES_MINI, new CreationSpec(10, 0, MAX_DEPTH, true));
    }
    else if (type.equals("classic")) {
      return new SudokuCreator(solver, PuzzleLayout.CLASSIC_SUDOKU, new CreationSpec(25, 0, MAX_DEPTH, true));
    }
    else if (type.equals("futoshiki")) {
      return new FutoshikiCreator(solver, PuzzleLayout.FUTOSHIKI, new CreationSpec(2, 12, MAX_DEPTH, false));
    }
    else if (type.equals("super")) {
      return new SudokuCreator(solver, PuzzleLayout.SUPER_SUDOKU, new CreationSpec(50, 0, 70, true));
    }
    else {
      throw new IllegalArgumentException();
    }
  }

}
