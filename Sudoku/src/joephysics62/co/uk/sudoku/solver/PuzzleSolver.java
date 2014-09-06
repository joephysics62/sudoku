package joephysics62.co.uk.sudoku.solver;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
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
    final Cell cellToGuess = findCellToGuess(puzzle);
    char[] charArray = Integer.toBinaryString(cellToGuess.getCurrentValues()).toCharArray();
    for (int i = 1; i <= charArray.length; i++) {
      if ('1' == charArray[charArray.length - i]) {
        Puzzle copy = puzzle.deepCopy();
        Cell cell = copy.getCell(cellToGuess.getCoord());
        cell.fixValue(i);
        solve(copy, solutions);
      }
    }
  }

  private void addAsSolution(final Puzzle puzzle, final Set<SolvedPuzzle> solutions) {
    final Map<Coord, Integer> solutionMap = new LinkedHashMap<>();
    for (Cell cell : puzzle.getAllCells()) {
      solutionMap.put(cell.getCoord(), PuzzleWriter.convertToNiceValue(cell));
    }
    solutions.add(new SolvedPuzzle(solutionMap));
  }

  private Cell findCellToGuess(final Puzzle puzzle) {
    int minPossibles = Integer.MAX_VALUE;
    Cell minCell = null;
    for (Cell cell : puzzle.getAllCells()) {
      if (!cell.isSolved()) {
        int possiblesSize = Integer.bitCount(cell.getCurrentValues());
        if (possiblesSize == 2) {
          return cell;
        }
        if (possiblesSize < minPossibles) {
          minPossibles = possiblesSize;
          minCell = cell;
        }
      }
    }
    return minCell;
  }

  private void analyticElimination(final Puzzle puzzle) {
    while (recursiveCellSolve(puzzle, puzzle.getAllCoords())) {
      if (puzzle.isSolved()) {
        return;
      }
      solveOnRestrictions(puzzle);
    }
  }

  private boolean recursiveCellSolve(final Puzzle puzzle, final Collection<Coord> cells) {
    if (puzzle.isUnsolveable() || puzzle.isSolved()) {
      return false;
    }
    boolean cellsWereSolved = false;
    Set<Coord> forElimination = new LinkedHashSet<>();
    for (Coord coord : cells) {
      final Cell cell = puzzle.getCell(coord);
      if (cell.canApplyElimination()) {
        cell.setSolved();
        cellsWereSolved = true;
        for (Restriction restriction : puzzle.getRestrictions(cell.getCoord())) {
          forElimination.addAll(restriction.forSolvedCell(puzzle, cell));
        }
      }
    }
    if (!forElimination.isEmpty()) {
      recursiveCellSolve(puzzle, forElimination);
    }
    return cellsWereSolved;
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
