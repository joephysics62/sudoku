package joephysics62.co.uk.sudoku.creator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import joephysics62.co.uk.sudoku.builder.ArrayPuzzleBuilder;
import joephysics62.co.uk.sudoku.creator.util.Solved;
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
  private Puzzle _createdPuzzle = null;

  public ArrayPuzzleCreator(PuzzleSolver solver) {
    _solver = solver;
  }

  private final Set<Set<Coord>> _tried = new LinkedHashSet<>();

  @Override
  public Puzzle create(final PuzzleLayout layout, final int maxCluesToLeave, final int maxOptionalConstraints) {
    Puzzle completedNewPuzzle = createCompletedNewPuzzle(layout);
    findPuzzle(completedNewPuzzle, maxCluesToLeave, maxOptionalConstraints);
    return _createdPuzzle ;
  }

  public void findPuzzle(final Puzzle currentPuzzle, int maxCluesToLeave, int maxOptionalConstraints) {
    if (_createdPuzzle != null) {
      return;
    }
    CellFilter solvedCellFilter = Solved.create();
    final List<Coord> solvedCells = solvedCellFilter.apply(currentPuzzle);
    if (!_tried.add(new LinkedHashSet<>(solvedCells))) {
      return;
    }
    Collections.shuffle(solvedCells);
    for (Coord coord : solvedCells) {
      Puzzle puzzleToTry = currentPuzzle.deepCopy();
      PuzzleLayout layout = puzzleToTry.getLayout();
      int init = (1 << layout.getInitialsSize()) - 1;
      puzzleToTry.setCellValue(init, coord);
      puzzleToTry.setCellValue(init, Coord.of(layout.getHeight() - coord.getRow() + 1, layout.getWidth() - coord.getCol() + 1));
      Puzzle toKeep = puzzleToTry.deepCopy();
      SolutionResult solve = _solver.solve(puzzleToTry);
      if (solve.getType() == SolutionType.UNIQUE) {
        int numGivens = solvedCells.size() - 2;
        if (numGivens <= maxCluesToLeave) {
          _createdPuzzle = toKeep;
        }
        else {
          findPuzzle(toKeep, maxCluesToLeave, maxOptionalConstraints);
        }
      }
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
    addVariableConstraints(puzzle);
    return puzzle;
  }

  protected abstract void addVariableConstraints(Puzzle puzzle);

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
