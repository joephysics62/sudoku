package joephysics62.co.uk.sudoku.creator;

import java.util.List;

import joephysics62.co.uk.sudoku.builder.ArrayBuilder;
import joephysics62.co.uk.sudoku.constraints.Constraint;
import joephysics62.co.uk.sudoku.constraints.GreaterThan;
import joephysics62.co.uk.sudoku.model.Coord;
import joephysics62.co.uk.sudoku.model.Puzzle;
import joephysics62.co.uk.sudoku.model.Layout;
import joephysics62.co.uk.sudoku.solver.Solver;

public class FutoshikiCreator extends ArrayCreator {

  public FutoshikiCreator(final Solver solver, final Layout layout, final CreationSpec creationSpec) {
    super(solver, layout, creationSpec);
  }

  @Override
  protected void addGeometricConstraints(ArrayBuilder puzzleBuilder) {
    puzzleBuilder.addColumnUniquenessConstraints();
    puzzleBuilder.addRowUniquenessConstraints();
  }

  @Override
  protected void addVariableConstraints(Puzzle puzzle) {
    int[][] allCells = puzzle.getAllCells();
    List<Constraint> variableConstraints = puzzle.getVariableConstraints();
    for (int rowNum = 1; rowNum <= puzzle.getLayout().getHeight(); rowNum++) {
      for (int colNum = 1; colNum <= puzzle.getLayout().getWidth(); colNum++) {
        Coord thisCoord = Coord.of(rowNum, colNum);
        Coord toRight = Coord.of(rowNum, colNum + 1);
        Coord below = Coord.of(rowNum + 1, colNum);
        int thisValue = allCells[rowNum - 1][colNum - 1];
        if (colNum < puzzle.getLayout().getWidth()) {
          int valueToRight = allCells[rowNum - 1][colNum];
          variableConstraints.add(thisValue > valueToRight ? GreaterThan.of(thisCoord, toRight) : GreaterThan.of(toRight, thisCoord));
        }
        if (rowNum < puzzle.getLayout().getHeight()) {
          int valueBelow = allCells[rowNum][colNum - 1];
          variableConstraints.add(thisValue > valueBelow ? GreaterThan.of(thisCoord, below) : GreaterThan.of(below, thisCoord));
        }
      }
    }
  }

}
