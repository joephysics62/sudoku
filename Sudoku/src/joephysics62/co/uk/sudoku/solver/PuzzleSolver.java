package joephysics62.co.uk.sudoku.solver;

import java.util.LinkedHashSet;
import java.util.Set;

import joephysics62.co.uk.sudoku.constraints.Constraint;
import joephysics62.co.uk.sudoku.model.Cell;
import joephysics62.co.uk.sudoku.model.Coord;
import joephysics62.co.uk.sudoku.model.Puzzle;

public class PuzzleSolver {

  private final CellPickingStrategy _cellGuessingStrategy;

  public PuzzleSolver(CellPickingStrategy cellGuessingStrategy) {
    _cellGuessingStrategy = cellGuessingStrategy;
  }

  public SolutionResult solve(final Puzzle puzzle) {
    final Set<SolvedPuzzle> solutions = new LinkedHashSet<>();
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

  private void solve(final Puzzle puzzle, final Set<SolvedPuzzle> solutions, int recurseDepth) {
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
    final Coord cellToGuess = _cellGuessingStrategy.cellToGuess(puzzle);
    char[] charArray = Integer.toBinaryString(puzzle.getCellValue(cellToGuess)).toCharArray();
    for (int i = 1; i <= charArray.length; i++) {
      if ('1' == charArray[charArray.length - i]) {
        Puzzle copy = puzzle.deepCopy();
        copy.setCellValue(Cell.cellValueAsBitwise(i), cellToGuess);
        solve(copy, solutions, recurseDepth + 1);
      }
    }
  }

  private void addAsSolution(final Puzzle puzzle, final Set<SolvedPuzzle> solutions) {
    final int[][] solutionMap = new int[puzzle.getPuzzleSize()][puzzle.getPuzzleSize()];
    int[][] allCells = puzzle.getAllCells();
    int rowIndex = 0;
    for (int[] row : allCells) {
      int colIndex = 0;
      for (int value : row) {
        solutionMap[rowIndex][colIndex] = Cell.convertToNiceValue(value);
        colIndex++;
      }
      rowIndex++;
    }
    solutions.add(new SolvedPuzzle(solutionMap, puzzle.getSubTableHeight(), puzzle.getSubTableWidth()));
  }

  private void analyticElimination(final Puzzle puzzle) {
    final int[][] allCells = puzzle.getAllCells();
    for (int rowIndex = 0; rowIndex < allCells.length; rowIndex++) {
      int[] row = allCells[rowIndex];
      for (int colIndex = 0; colIndex < row.length; colIndex++) {
        Coord coord = new Coord(rowIndex + 1, colIndex + 1);
        recursiveCellSolve(puzzle, coord);
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
