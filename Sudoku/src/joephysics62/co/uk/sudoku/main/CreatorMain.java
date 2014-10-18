package joephysics62.co.uk.sudoku.main;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import joephysics62.co.uk.grid.maths.FourColourSolver;
import joephysics62.co.uk.sudoku.creator.CreationSpec;
import joephysics62.co.uk.sudoku.creator.FutoshikiCreator;
import joephysics62.co.uk.sudoku.creator.KillerSudokuCreator;
import joephysics62.co.uk.sudoku.creator.PuzzleCreator;
import joephysics62.co.uk.sudoku.creator.SudokuCreator;
import joephysics62.co.uk.sudoku.creator.util.Unsolved;
import joephysics62.co.uk.sudoku.model.Puzzle;
import joephysics62.co.uk.sudoku.model.PuzzleLayout;
import joephysics62.co.uk.sudoku.solver.Solver;
import joephysics62.co.uk.sudoku.write.FutoshikiHtmlWriter;
import joephysics62.co.uk.sudoku.write.KillerSudokuHtmlWriter;
import joephysics62.co.uk.sudoku.write.PuzzleHtmlWriter;
import joephysics62.co.uk.sudoku.write.SudokuHtmlWriter;
import freemarker.template.TemplateException;

public class CreatorMain {
  private static final int MAX_DEPTH = 500;

  public static void main(String[] args) throws IOException, TemplateException, URISyntaxException {
    final String type = args[0];
    final File output = new File(args[1]);
    final Puzzle puzzle = buildPuzzleCreator(type).create();
    final PuzzleHtmlWriter htmlPuzzleWriter;
    if (type.equals("futoshiki")) {
      htmlPuzzleWriter =  new FutoshikiHtmlWriter(puzzle);
    }
    else if (type.equals("killer")) {
      htmlPuzzleWriter =  new KillerSudokuHtmlWriter(puzzle, new FourColourSolver());
    }
    else {
      htmlPuzzleWriter = new SudokuHtmlWriter(puzzle);
    }
    htmlPuzzleWriter.write(output);
  }

  private static PuzzleCreator buildPuzzleCreator(final String type) {
    Solver solver = new Solver(Unsolved.create());
    if (type.equals("timesMini")) {
      return new SudokuCreator(solver, PuzzleLayout.TIMES_MINI, new CreationSpec(10, 0, MAX_DEPTH, true));
    }
    else if (type.equals("classic")) {
      return new SudokuCreator(solver, PuzzleLayout.CLASSIC_SUDOKU, new CreationSpec(25, 0, MAX_DEPTH, true));
    }
    else if (type.equals("futoshiki")) {
      return new FutoshikiCreator(solver, PuzzleLayout.FUTOSHIKI, new CreationSpec(2, 6, MAX_DEPTH, false));
    }
    else if (type.equals("super")) {
      return new SudokuCreator(solver, PuzzleLayout.SUPER_SUDOKU, new CreationSpec(50, 0, 70, true));
    }
    else if (type.equals("killer")) {
      return new KillerSudokuCreator(solver, PuzzleLayout.CLASSIC_SUDOKU, 33, MAX_DEPTH);
    }
    else {
      throw new IllegalArgumentException();
    }
  }

}
