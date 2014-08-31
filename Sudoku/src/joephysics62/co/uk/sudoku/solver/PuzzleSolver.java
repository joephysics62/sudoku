package joephysics62.co.uk.sudoku.solver;

import java.util.Collection;
import java.util.Set;

import joephysics62.co.uk.sudoku.model.Cell;
import joephysics62.co.uk.sudoku.model.Puzzle;
import joephysics62.co.uk.sudoku.model.Restriction;

public class PuzzleSolver {

  public <T> void solve(final Puzzle<T> puzzle) {
    print(puzzle);

    while (elim(puzzle)) {
      // do
    }
    print(puzzle);
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
        stateChanged |= solveOnRestrictions(puzzle.getRestrictions(cell.getIdentifier()), puzzle);
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

  private <T> void print(final Puzzle<T> puzzle) {
    int completeness = 0;
    for (Cell<T> cell : puzzle.getAllCells()) {
      completeness += cell.getCurrentValues().size();
      System.out.println(cell.getIdentifier() + ": " + cell.getCurrentValues());
    }
    System.out.println(completeness);
    System.out.println("------------------------------------------------");
  }
}
