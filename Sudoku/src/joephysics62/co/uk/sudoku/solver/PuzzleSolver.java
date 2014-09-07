package joephysics62.co.uk.sudoku.solver;

import java.util.Collection;
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
        copy.setCellValue(Cell.cellValueAsBitwise(i), cellToGuess.getRow(), cellToGuess.getCol());
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
        if (Integer.bitCount(value) != 1) {
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
    recursiveCellSolve(puzzle, null);
    solveOnRestrictions(puzzle);
  }

  private void recursiveCellSolve(final Puzzle puzzle, final Collection<Coord> cells) {
    if (puzzle.isUnsolveable() || puzzle.isSolved()) {
      return;
    }
    final Set<Coord> forElimination = new LinkedHashSet<>();
    if (null == cells) {
      int rowNum = 1;
      for (int[] row : puzzle.getAllCells()) {
        int colNum = 1;
        for (int value : row) {
          Coord coord = new Coord(rowNum, colNum);
          doStuff(puzzle, forElimination, value, coord);
          colNum++;
        }
        rowNum++;
      }
    }
    else {
      for (Coord coord : cells) {
        final int value = puzzle.getCellValue(coord);
        doStuff(puzzle, forElimination, value, coord);
      }
    }
    if (!forElimination.isEmpty()) {
      recursiveCellSolve(puzzle, forElimination);
    }
  }

  private void doStuff(final Puzzle puzzle, final Set<Coord> forElimination, int value, Coord coord) {
    if (Integer.bitCount(value) == 1) {
      for (Restriction restriction : puzzle.getRestrictions(coord)) {
        forElimination.addAll(restriction.forSolvedCell(puzzle, value));
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
