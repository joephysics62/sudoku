package joephysics62.co.uk.sudoku.creator;

import java.io.File;
import java.io.IOException;

import joephysics62.co.uk.sudoku.model.Puzzle;
import joephysics62.co.uk.sudoku.model.PuzzleLayout;
import joephysics62.co.uk.sudoku.solver.FirstClosestToSolved;
import joephysics62.co.uk.sudoku.solver.PuzzleSolver;
import joephysics62.co.uk.sudoku.write.HTMLFutoshikiWriter;
import joephysics62.co.uk.sudoku.write.HTMLPuzzleWriter;
import joephysics62.co.uk.sudoku.write.TextPuzzleWriter;
import freemarker.template.TemplateException;

public class CreateMain {
  public static void main(String[] args) throws IOException, TemplateException {
    TextPuzzleWriter writer = new TextPuzzleWriter(System.out);
    PuzzleSolver solver = new PuzzleSolver(FirstClosestToSolved.create());
    //SudokuCreator creator = new SudokuCreator(solver);
    PuzzleCreator creator = new FutoshikiCreator(solver);
    final int maxCluesToLeave = 2;
    final int maxVariableConstraints = 9;
    Puzzle puzzle = creator.create(PuzzleLayout.FUTOSHIKI, maxCluesToLeave, maxVariableConstraints);
    writer.write(puzzle);
    HTMLPuzzleWriter htmlPuzzleWriter = new HTMLFutoshikiWriter(puzzle);
    htmlPuzzleWriter.write(new File("sudoku.html"));
  }

}
