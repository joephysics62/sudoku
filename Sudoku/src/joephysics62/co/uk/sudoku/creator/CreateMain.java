package joephysics62.co.uk.sudoku.creator;

import java.io.File;
import java.io.IOException;

import joephysics62.co.uk.sudoku.creator.util.RandomUnsolved;
import joephysics62.co.uk.sudoku.model.Puzzle;
import joephysics62.co.uk.sudoku.model.PuzzleLayout;
import joephysics62.co.uk.sudoku.solver.PuzzleSolver;
import joephysics62.co.uk.sudoku.write.HTMLFutoshikiWriter;
import joephysics62.co.uk.sudoku.write.HTMLPuzzleWriter;
import joephysics62.co.uk.sudoku.write.TextPuzzleWriter;
import freemarker.template.TemplateException;

public class CreateMain {
  public static void main(String[] args) throws IOException, TemplateException {
    TextPuzzleWriter writer = new TextPuzzleWriter(System.out);
    PuzzleSolver solver = new PuzzleSolver(RandomUnsolved.create());
    //SudokuCreator creator = new SudokuCreator(solver);
    PuzzleCreator creator = new FutoshikiCreator(solver);
    final int maxCluesToLeave = 4;
    final int maxVariableConstraints = 15;
    PuzzleLayout layout = PuzzleLayout.FUTOSHIKI;
    Puzzle puzzle = creator.create(layout, maxCluesToLeave, maxVariableConstraints);
    writer.write(puzzle);
    HTMLPuzzleWriter htmlPuzzleWriter = new HTMLFutoshikiWriter(puzzle);
    htmlPuzzleWriter.write(new File("sudoku.html"));
  }

}
