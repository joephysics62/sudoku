package joephysics62.co.uk.sudoku.creator;

import java.io.File;
import java.io.IOException;
import java.util.List;

import joephysics62.co.uk.sudoku.builder.ArrayPuzzleBuilder;
import joephysics62.co.uk.sudoku.constraints.Constraint;
import joephysics62.co.uk.sudoku.constraints.GreaterThan;
import joephysics62.co.uk.sudoku.model.Coord;
import joephysics62.co.uk.sudoku.model.Puzzle;
import joephysics62.co.uk.sudoku.solver.PuzzleSolver;
import joephysics62.co.uk.sudoku.write.HTMLFutoshikiWriter;
import freemarker.template.TemplateException;

public class FutoshikiCreator extends ArrayPuzzleCreator {

  public FutoshikiCreator(PuzzleSolver solver) {
    super(solver);
  }

  @Override
  protected void addGeometricConstraints(ArrayPuzzleBuilder puzzleBuilder) {
    puzzleBuilder.addColumnUniquenessConstraints();
    puzzleBuilder.addRowUniquenessConstraints();
  }

  @Override
  protected void addVariableConstraints(Puzzle puzzle) {
    int[][] allCells = puzzle.getAllCells();
    List<Constraint> variableConstraints = puzzle.getVariableConstraints();
    for (int rowNum = 1; rowNum < puzzle.getLayout().getHeight(); rowNum++) {
      for (int colNum = 1; colNum < puzzle.getLayout().getWidth(); colNum++) {
        Coord thisCoord = Coord.of(rowNum, colNum);
        Coord toRight = Coord.of(rowNum, colNum + 1);
        Coord below = Coord.of(rowNum + 1, colNum);
        int thisValue = allCells[rowNum - 1][colNum - 1];
        int valueToRight = allCells[rowNum - 1][colNum];
        int valueBelow = allCells[rowNum][colNum - 1];
        variableConstraints.add(thisValue > valueToRight ? GreaterThan.of(thisCoord, toRight) : GreaterThan.of(toRight, thisCoord));
        variableConstraints.add(thisValue > valueBelow ? GreaterThan.of(thisCoord, below) : GreaterThan.of(below, thisCoord));
      }
    }
    try {
      new HTMLFutoshikiWriter(puzzle).write(new File("tmp.html"));
    }
    catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch (TemplateException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

}
