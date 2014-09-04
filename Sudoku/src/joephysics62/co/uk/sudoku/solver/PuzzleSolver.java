package joephysics62.co.uk.sudoku.solver;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import joephysics62.co.uk.sudoku.constraints.Restriction;
import joephysics62.co.uk.sudoku.model.Cell;
import joephysics62.co.uk.sudoku.model.Coord;
import joephysics62.co.uk.sudoku.model.Puzzle;

public class PuzzleSolver<T extends Comparable<T>> {

  public SolutionResult<T> solve(final Puzzle<T> puzzle) {
    final Set<SolvedPuzzle<T>> solutions = new LinkedHashSet<>();
    final long start = System.currentTimeMillis();
    solve(puzzle, solutions);
    final long timing = System.currentTimeMillis() - start;
    if (solutions.size() == 1) {
      return new SolutionResult<>(SolutionType.UNIQUE, solutions.iterator().next(), timing);
    }
    else if (solutions.size() > 1) {
      return new SolutionResult<>(SolutionType.MULTIPLE, solutions.iterator().next(), timing);
    }
    else {
      return new SolutionResult<>(SolutionType.NONE, null, timing);
    }
  }

  private void solve(final Puzzle<T> puzzle, final Set<SolvedPuzzle<T>> solutions) {
    if (solutions.size() > 1) {
      return;
    }
    while (elim(puzzle)) {
      if (puzzle.isUnsolveable()) {
        return;
      }
    }
    if (puzzle.isSolved()) {
      addAsSolution(puzzle, solutions);
    }
    else {
      if (puzzle.isUnsolveable()) {
        return;
      }
      final Cell<T> cellToGuess = findCellToGuess(puzzle);
      for (T candidateValue : cellToGuess.getCurrentValues()) {
        Puzzle<T> copy = puzzle.deepCopy();
        Cell<T> cell = copy.getCell(cellToGuess.getCoord());
        cell.fixValue(candidateValue);
        solve(copy, solutions);
      }
    }
  }

  private void addAsSolution(final Puzzle<T> puzzle, final Set<SolvedPuzzle<T>> solutions) {
    final Map<Coord, T> solutionMap = new LinkedHashMap<>();
    for (Cell<T> cell : puzzle.getAllCells()) {
      solutionMap.put(cell.getCoord(), cell.getValue());
    }
    solutions.add(new SolvedPuzzle<T>(solutionMap));
  }

  private Cell<T> findCellToGuess(final Puzzle<T> puzzle) {
    int minPossibles = Integer.MAX_VALUE;
    Cell<T> minCell = null;
    for (Cell<T> cell : puzzle.getAllCells()) {
      if (!cell.isSolved()) {
        int possiblesSize = cell.getCurrentValues().size();
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

  private boolean elim(final Puzzle<T> puzzle) {
    boolean cellSolveChanged = checkForCellsToSetSolved(puzzle);
    boolean restrictSolveChanged = solveOnRestrictions(puzzle);
    return cellSolveChanged || restrictSolveChanged;
  }

  private boolean checkForCellsToSetSolved(final Puzzle<T> puzzle) {
    boolean changed = false;
    for (Cell<T> cell : puzzle.getAllCells()) {
      if (cell.isUnsolveable()) {
        return false;
      }
      if (cell.canApplyElimination()) {
        cell.setSolved();
        changed = true;
      }
    }
    return changed;
  }

  private boolean solveOnRestrictions(final Puzzle<T> puzzle) {
    boolean changed = false;
    for (Restriction<T> restriction : puzzle.getAllRestrictions()) {
      if (restriction.eliminateValues(puzzle)) {
        changed = true;
      }
    }
    return changed;
  }

}
