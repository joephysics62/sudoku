package joephysics62.co.uk.sudoku.solver;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import joephysics62.co.uk.sudoku.model.Cell;
import joephysics62.co.uk.sudoku.model.Coord;
import joephysics62.co.uk.sudoku.model.Puzzle;
import joephysics62.co.uk.sudoku.model.Restriction;

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
      final Map<Coord, T> solutionMap = new LinkedHashMap<>();
      for (Cell<T> cell : puzzle.getAllCells()) {
        solutionMap.put(cell.getIdentifier(), cell.getValue());
      }
      solutions.add(new SolvedPuzzle<T>(solutionMap));
    }
    else {
      if (puzzle.isUnsolveable()) {
        return;
      }
      Cell<T> cellToGuess = findCellToGuess(puzzle);
      for (T candidateValue : cellToGuess.getCurrentValues()) {
        Puzzle<T> copy = puzzle.deepCopy();
        Cell<T> cell = copy.getCell(cellToGuess.getIdentifier());
        final Set<T> removes = new LinkedHashSet<>(cell.getCurrentValues());
        removes.remove(candidateValue);
        cell.removeAll(removes);
        solveOnCells(Collections.singleton(cell), copy);
        solve(copy, solutions);
      }
    }
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
    boolean cellSolveChanged = solveOnCells(puzzle.getAllCells(), puzzle);
    boolean restrictSolveChanged = solveOnRestrictions(puzzle.getAllRestrictions(), puzzle);
    return cellSolveChanged || restrictSolveChanged;
  }

  private boolean solveOnCells(Set<Cell<T>> cells, final Puzzle<T> puzzle) {
    boolean stateChanged = false;
    for (Cell<T> cell : cells) {
      if (cell.canApplyElimination()) {
        stateChanged |= solveOnRestrictions(puzzle.getRestrictions(cell.getIdentifier()), puzzle);
        cell.setSolved();
      }
    }
    return stateChanged;
  }

  private boolean solveOnRestrictions(final Collection<Restriction<T>> restrictions, final Puzzle<T> puzzle) {
    boolean stateChanged = false;
    for (Restriction<T> restriction : restrictions) {
      Set<Cell<T>> changedCells = restriction.eliminateValues(puzzle);
      if (!changedCells.isEmpty()) {
        stateChanged = true;
      }
      solveOnCells(changedCells, puzzle);
    }
    return stateChanged;
  }

}
