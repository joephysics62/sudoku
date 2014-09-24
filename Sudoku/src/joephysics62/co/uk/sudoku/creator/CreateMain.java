package joephysics62.co.uk.sudoku.creator;

import java.io.File;
import java.io.IOException;

import joephysics62.co.uk.sudoku.model.Puzzle;
import joephysics62.co.uk.sudoku.model.PuzzleLayout;
import joephysics62.co.uk.sudoku.solver.PuzzleSolver;
import joephysics62.co.uk.sudoku.write.HTMLPuzzleWriter;
import joephysics62.co.uk.sudoku.write.HTMLSudokuWriter;
import joephysics62.co.uk.sudoku.write.TextPuzzleWriter;
import freemarker.template.TemplateException;

public class CreateMain {
  public static void main(String[] args) throws IOException, TemplateException {
    TextPuzzleWriter writer = new TextPuzzleWriter(System.out);
    PuzzleSolver solver = new PuzzleSolver(RandomUnsolved.create());
    SudokuCreator creator = new SudokuCreator(solver);
    final int maxCluesToLeave = 25;
    PuzzleLayout layout = new PuzzleLayout(8, 8, 2, 4, 8);
    Puzzle puzzle = creator.create(layout, maxCluesToLeave);
    writer.write(puzzle);
    HTMLPuzzleWriter htmlPuzzleWriter = new HTMLSudokuWriter(puzzle);
    htmlPuzzleWriter.write(new File("sudoku.html"));
  }

}