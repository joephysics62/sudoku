package joephysics62.co.uk.sudoku.creator;

import java.io.File;
import java.io.IOException;

import joephysics62.co.uk.sudoku.model.Puzzle;
import joephysics62.co.uk.sudoku.model.PuzzleLayout;
import joephysics62.co.uk.sudoku.solver.PuzzleSolver;
import joephysics62.co.uk.sudoku.write.HTMLPuzzleWriter;
import joephysics62.co.uk.sudoku.write.PuzzleWriter;
import freemarker.template.TemplateException;

public class CreateMain {
  public static void main(String[] args) throws IOException, TemplateException {
    PuzzleWriter writer = new PuzzleWriter(System.out);
    PuzzleSolver solver = new PuzzleSolver(RandomUnsolved.create());
    PuzzleCreator creator = new PuzzleCreator(solver);
    final int maxCluesToLeave = 27;
    Puzzle puzzle = creator.create(PuzzleLayout.CLASSIC_SUDOKU, maxCluesToLeave);
    writer.write(puzzle);
    HTMLPuzzleWriter htmlPuzzleWriter = new HTMLPuzzleWriter(puzzle);
    htmlPuzzleWriter.write(new File("sudoku.html"));
  }

}
