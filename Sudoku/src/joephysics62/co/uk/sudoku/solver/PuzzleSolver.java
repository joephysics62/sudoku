package joephysics62.co.uk.sudoku.solver;

import java.util.LinkedHashSet;
import java.util.Set;

import joephysics62.co.uk.sudoku.constraints.Restriction;
import joephysics62.co.uk.sudoku.model.Cell;
import joephysics62.co.uk.sudoku.model.Coord;
import joephysics62.co.uk.sudoku.model.Puzzle;
import joephysics62.co.uk.sudoku.write.PuzzleWriter;

public class PuzzleSolver {

  public SolutionResult solve(final Puzzle puzzle) {
    final Set<SolvedPuzzle> solutions = new LinkedHashSet<>();
    final long start = System.currentTimeMillis();
    solve(puzzle, solutions);
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

  private void solve(final Puzzle puzzle, final Set<SolvedPuzzle> solutions) {
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
    final Coord cellToGuess = findCellToGuess(puzzle);
    char[] charArray = Integer.toBinaryString(puzzle.getCellValue(cellToGuess)).toCharArray();
    for (int i = 1; i <= charArray.length; i++) {
      if ('1' == charArray[charArray.length - i]) {
        Puzzle copy = puzzle.deepCopy();
        copy.setCellValue(Cell.cellValueAsBitwise(i), cellToGuess);
        solve(copy, solutions);
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
        solutionMap[rowIndex][colIndex] = PuzzleWriter.convertToNiceValue(value);
        colIndex++;
      }
      rowIndex++;
    }
    solutions.add(new SolvedPuzzle(solutionMap));
  }

  private Coord findCellToGuess(final Puzzle puzzle) {
    int minPossibles = Integer.MAX_VALUE;
    Coord minCell = null;
    int rowNum = 1;
    for (int[] row : puzzle.getAllCells()) {
      int colNum = 1;
      for (int value : row) {
        if (!Cell.isSolved(value)) {
          int possiblesSize = Integer.bitCount(value);
          if (possiblesSize == 2) {
            return new Coord(rowNum, colNum);
          }
          else if (possiblesSize < minPossibles) {
            minPossibles = possiblesSize;
            minCell = new Coord(rowNum, colNum);
          }
        }
        colNum++;
      }
      rowNum++;
    }
    return minCell;
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
      for (Restriction restriction : puzzle.getRestrictions(coord)) {
        for (Coord solvedCell : restriction.forSolvedCell(puzzle, value)) {
          recursiveCellSolve(puzzle, solvedCell);
        }
      }
    }
  }

  private boolean solveOnRestrictions(final Puzzle puzzle) {
    boolean changed = false;
    for (Restriction restriction : puzzle.getAllRestrictions()) {
      if (restriction.eliminateValues(puzzle)) {
        changed = true;
      }
    }
    return changed;
  }

}
