package joephysics62.co.uk.sudoku.solver;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import joephysics62.co.uk.sudoku.model.Cell;
import joephysics62.co.uk.sudoku.model.Coord;
import joephysics62.co.uk.sudoku.model.Puzzle;
import joephysics62.co.uk.sudoku.model.PuzzleSolution;
import joephysics62.co.uk.sudoku.model.Restriction;

public class PuzzleSolver {

  public <T> Set<PuzzleSolution<T>> solve(final Puzzle<T> puzzle) {
    while (elim(puzzle)) {
      puzzle.write(System.out);
      System.out.println("Completeness =  " + puzzle.completeness());
      System.out.println();
    }
    if (puzzle.isSolved()) {
      final Map<Coord, T> solutionMap = new LinkedHashMap<>();
      for (Cell<T> cell : puzzle.getAllCells()) {
        solutionMap.put(cell.getIdentifier(), cell.getValue());
      }
      return Collections.singleton(new PuzzleSolution<T>(solutionMap));
    }
    else {
      return Collections.emptySet();
    }
  }

  private <T> boolean elim(final Puzzle<T> puzzle) {
    boolean cellSolveChanged = solveOnCells(puzzle.getAllCells(), puzzle);
    boolean restrictSolveChanged = solveOnRestrictions(puzzle.getAllRestrictions(), puzzle);
    return cellSolveChanged || restrictSolveChanged;
  }

  private <T> boolean solveOnCells(Set<Cell<T>> cells, final Puzzle<T> puzzle) {
    boolean stateChanged = false;
    for (Cell<T> cell : cells) {
      if (cell.canApplyElimination()) {
        stateChanged |= solveOnRestrictions(puzzle.getRestrictions(cell), puzzle);
        cell.setSolved();
      }
    }
    return stateChanged;
  }

  private <T> boolean solveOnRestrictions(final Collection<Restriction<T>> restrictions, final Puzzle<T> puzzle) {
    boolean stateChanged = false;
    for (Restriction<T> restriction : restrictions) {
      Set<Cell<T>> changedCells = restriction.eliminateValues();
      if (!changedCells.isEmpty()) {
        stateChanged = true;
      }
      solveOnCells(changedCells, puzzle);
    }
    return stateChanged;
  }

}
