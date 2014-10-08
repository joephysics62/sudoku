package joephysics62.co.uk.sudoku.solver;

import java.util.LinkedHashSet;
import java.util.Set;

import joephysics62.co.uk.sudoku.constraints.Constraint;
import joephysics62.co.uk.sudoku.model.Cell;
import joephysics62.co.uk.sudoku.model.Coord;
import joephysics62.co.uk.sudoku.model.Puzzle;
import joephysics62.co.uk.sudoku.model.Layout;

public class Solver {

  private final CellFilter _cellGuessingStrategy;

  public Solver(CellFilter cellGuessingStrategy) {
    _cellGuessingStrategy = cellGuessingStrategy;
  }

  public SolutionResult solve(final Puzzle puzzle) {
    final Set<Solution> solutions = new LinkedHashSet<>();
    final long start = System.currentTimeMillis();
    solve(puzzle, solutions, 0);
    final long timing = System.currentTimeMillis() - start;
    if (solutions.size() == 1) {
      return new SolutionResult(SolutionType.UNIQUE, solutions.iterator().next(), timing);
    }
    else if (solutions.size() > 1) {
      return new SolutionResult(SolutionType.MULTIPLE, solutions.iterator().next(), timing);
    }
    else {
      return new SolutionResult(SolutionType.NONE, null, timing);
    }
  }

  private void solve(final Puzzle puzzle, final Set<Solution> solutions, int recurseDepth) {
    if (solutions.size() > 1) {
      return;
    }
    analyticElimination(puzzle);
    if (puzzle.isUnsolveable()) {
      return;
    }
    if (puzzle.isSolved()) {
      addAsSolution(puzzle, solutions);
      return;
    }
    final Coord cellToGuess = _cellGuessingStrategy.apply(puzzle).get(0);
    char[] charArray = Integer.toBinaryString(puzzle.getCellValue(cellToGuess)).toCharArray();
    for (int i = 1; i <= charArray.length; i++) {
      if ('1' == charArray[charArray.length - i]) {
        Puzzle copy = puzzle.deepCopy();
        copy.setCellValue(Cell.cellValueAsBitwise(i), cellToGuess);
        solve(copy, solutions, recurseDepth + 1);
      }
    }
  }

  private void addAsSolution(final Puzzle puzzle, final Set<Solution> solutions) {
    Layout layout = puzzle.getLayout();
    final int[][] solutionMap = new int[layout.getHeight()][layout.getWidth()];
    for (int rowIndex = 0; rowIndex < puzzle.getLayout().getHeight(); rowIndex++) {
      for (int colIndex = 0; colIndex < puzzle.getLayout().getWidth(); colIndex++) {
        solutionMap[rowIndex][colIndex] = Cell.convertToNiceValue(puzzle.getCellValue(Coord.of(rowIndex + 1,  colIndex + 1)));
      }
    }
    solutions.add(new Solution(solutionMap, layout));
  }

  private void analyticElimination(final Puzzle puzzle) {
    for (int rowIndex = 0; rowIndex < puzzle.getLayout().getHeight(); rowIndex++) {
      for (int colIndex = 0; colIndex < puzzle.getLayout().getWidth(); colIndex++) {
        recursiveCellSolve(puzzle, Coord.of(rowIndex + 1, colIndex + 1));
      }
    }
    solveOnRestrictions(puzzle);
  }

  private void recursiveCellSolve(final Puzzle puzzle, final Coord coord) {
    final int value = puzzle.getCellValue(coord);
    if (Cell.isSolved(value)) {
      for (Constraint restriction : puzzle.getConstraints(coord)) {
        restriction.forSolvedCell(puzzle, value);
      }
    }
  }

  private boolean solveOnRestrictions(final Puzzle puzzle) {
    boolean changed = false;
    for (Constraint restriction : puzzle.getAllConstraints()) {
      if (restriction.eliminateValues(puzzle)) {
        changed = true;
      }
    }
    return changed;
  }

}