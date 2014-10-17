package joephysics62.co.uk.sudoku.creator;

import java.util.List;

import joephysics62.co.uk.constraints.Constraint;
import joephysics62.co.uk.constraints.GreaterThan;
import joephysics62.co.uk.grid.Coord;
import joephysics62.co.uk.sudoku.builder.ArrayBuilder;
import joephysics62.co.uk.sudoku.model.Puzzle;
import joephysics62.co.uk.sudoku.model.PuzzleLayout;
import joephysics62.co.uk.sudoku.solver.Solver;

public class FutoshikiCreator extends ArrayPuzzleCreator {

  public FutoshikiCreator(final Solver solver, final PuzzleLayout layout, final CreationSpec creationSpec) {
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
    for (Coord coord : puzzle) {
      int rowNum = coord.getRow();
      int colNum = coord.getCol();
      Coord toRight = Coord.of(rowNum, colNum + 1);
      Coord below = Coord.of(rowNum + 1, colNum);
      int thisValue = allCells[rowNum - 1][colNum - 1];
      if (colNum < puzzle.getLayout().getWidth()) {
        int valueToRight = allCells[rowNum - 1][colNum];
        variableConstraints.add(thisValue > valueToRight ? GreaterThan.of(coord, toRight) : GreaterThan.of(toRight, coord));
      }
      if (rowNum < puzzle.getLayout().getHeight()) {
        int valueBelow = allCells[rowNum][colNum - 1];
        variableConstraints.add(thisValue > valueBelow ? GreaterThan.of(coord, below) : GreaterThan.of(below, coord));
      }
    }
  }

}
