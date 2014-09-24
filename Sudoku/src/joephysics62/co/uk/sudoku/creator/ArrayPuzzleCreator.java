package joephysics62.co.uk.sudoku.creator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import joephysics62.co.uk.sudoku.builder.ArrayPuzzleBuilder;
import joephysics62.co.uk.sudoku.model.Cell;
import joephysics62.co.uk.sudoku.model.Coord;
import joephysics62.co.uk.sudoku.model.Puzzle;
import joephysics62.co.uk.sudoku.model.PuzzleLayout;
import joephysics62.co.uk.sudoku.solver.CellFilter;
import joephysics62.co.uk.sudoku.solver.PuzzleSolver;
import joephysics62.co.uk.sudoku.solver.SolutionResult;
import joephysics62.co.uk.sudoku.solver.SolutionType;


public abstract class ArrayPuzzleCreator implements PuzzleCreator {

  private final PuzzleSolver _solver;

  public ArrayPuzzleCreator(PuzzleSolver solver) {
    _solver = solver;
  }

  @Override
  public Puzzle create(final PuzzleLayout layout, final int maxCluesToLeave) {
    for (int i = 0; i < 10000; i++) {
      Puzzle completedNewPuzzle = createCompletedNewPuzzle(layout);
      for (int j = 0; j < 100; j++) {
        Puzzle puzzle = findPuzzle(completedNewPuzzle, maxCluesToLeave);
        if (null != puzzle) {
          return puzzle;
        }
      }
    }
    return null;
  }

  public Puzzle findPuzzle(final Puzzle completedPuzzle, final int cluesToLeave) {
    Puzzle puzzleToTry = completedPuzzle.deepCopy();
    CellFilter solvedCellFilter = Solved.create();
    final List<Coord> solvedCells = solvedCellFilter.apply(puzzleToTry);
    PuzzleLayout layout = puzzleToTry.getLayout();
    int init = (1 << layout.getInitialsSize()) - 1;
    Collections.shuffle(solvedCells);
    int removesSize = layout.getWidth() * layout.getHeight() - cluesToLeave;

    Set<Coord> removes = new LinkedHashSet<>();
    for (Coord coord : solvedCells) {
      removes.add(coord);
      removes.add(Coord.of(layout.getHeight() - coord.getRow() + 1, layout.getWidth() - coord.getCol() + 1));
      if (removes.size() >= removesSize) {
        break;
      }
    }
    for (Coord coord : removes) {
      puzzleToTry.setCellValue(init, coord);
    }
    Puzzle toPrint = puzzleToTry.deepCopy();
    SolutionResult solve = _solver.solve(puzzleToTry);
    if (solve.getType() == SolutionType.UNIQUE) {
        return toPrint;
    }
    else {
      return null;
    }
  }

  private Puzzle createCompletedNewPuzzle(final PuzzleLayout layout) {
    Puzzle puzzle = newPuzzle(layout);
    SolutionResult solutionResult = _solver.solve(puzzle);
    if (solutionResult.getType() == SolutionType.NONE) {
      return null;
    }
    for (int row = 1; row <= layout.getHeight(); row++) {
      for (int col = 1; col <= layout.getWidth(); col++) {
        Coord coord = Coord.of(row, col);
        int niceValue = solutionResult.getSolution().getValue(coord);
        puzzle.setCellValue(Cell.cellValueAsBitwise(niceValue), coord);
      }
    }
    return puzzle;
  }

  private Puzzle newPuzzle(final PuzzleLayout layout) {
    ArrayPuzzleBuilder puzzleBuilder = new ArrayPuzzleBuilder(layout);
    final List<Integer> aRow = new ArrayList<>();
    for (int i = 1; i <= layout.getWidth(); i++) {
      aRow.add(i);
    }
    Collections.shuffle(aRow);
    for (int i = 1; i <= layout.getWidth(); i++) {
      puzzleBuilder.addGiven(aRow.get(i - 1), Coord.of(1, i));
    }
    puzzleBuilder.addTitle("Joe's Sudoku");
    addGeometricConstraints(puzzleBuilder);
    return puzzleBuilder.build();
  }

  protected abstract void addGeometricConstraints(ArrayPuzzleBuilder puzzleBuilder);

}
