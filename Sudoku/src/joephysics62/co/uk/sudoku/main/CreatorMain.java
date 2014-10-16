package joephysics62.co.uk.sudoku.main;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import joephysics62.co.uk.sudoku.creator.CreationSpec;
import joephysics62.co.uk.sudoku.creator.PuzzleCreator;
import joephysics62.co.uk.sudoku.creator.FutoshikiCreator;
import joephysics62.co.uk.sudoku.creator.SudokuCreator;
import joephysics62.co.uk.sudoku.creator.util.Unsolved;
import joephysics62.co.uk.sudoku.model.Layout;
import joephysics62.co.uk.sudoku.model.Puzzle;
import joephysics62.co.uk.sudoku.solver.Solver;
import joephysics62.co.uk.sudoku.write.FutoshikiHtmlWriter;
import joephysics62.co.uk.sudoku.write.SudokuHtmlWriter;
import joephysics62.co.uk.sudoku.write.PuzzleHtmlWriter;
import freemarker.template.TemplateException;

public class CreatorMain {
  private static final int MAX_DEPTH = 500;

  public static void main(String[] args) throws IOException, TemplateException, URISyntaxException {
    final String type = args[0];
    final File output = new File(args[1]);
    final Puzzle puzzle = buildPuzzleCreator(type).create();
    PuzzleHtmlWriter htmlPuzzleWriter = type.equals("futoshiki") ? new FutoshikiHtmlWriter(puzzle) : new SudokuHtmlWriter(puzzle);
    htmlPuzzleWriter.write(output);
  }

  private static PuzzleCreator buildPuzzleCreator(final String type) {
    Solver solver = new Solver(Unsolved.create());
    if (type.equals("timesMini")) {
      return new SudokuCreator(solver, Layout.TIMES_MINI, new CreationSpec(10, 0, MAX_DEPTH, true));
    }
    else if (type.equals("classic")) {
      return new SudokuCreator(solver, Layout.CLASSIC_SUDOKU, new CreationSpec(25, 0, MAX_DEPTH, true));
    }
    else if (type.equals("futoshiki")) {
      return new FutoshikiCreator(solver, Layout.FUTOSHIKI, new CreationSpec(2, 6, MAX_DEPTH, false));
    }
    else if (type.equals("super")) {
      return new SudokuCreator(solver, Layout.SUPER_SUDOKU, new CreationSpec(50, 0, 70, true));
    }
    else {
      throw new IllegalArgumentException();
    }
  }

}
