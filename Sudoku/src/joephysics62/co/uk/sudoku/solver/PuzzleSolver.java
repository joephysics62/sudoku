package joephysics62.co.uk.sudoku.solver;

import java.util.Collection;
import java.util.Set;

import joephysics62.co.uk.sudoku.model.Cell;
import joephysics62.co.uk.sudoku.model.Puzzle;
import joephysics62.co.uk.sudoku.model.Restriction;

public class PuzzleSolver {

  public <T> void solve(final Puzzle<T> puzzle) {
    print(puzzle);

    // Initial sweep over all cells
    for (Cell<T> cell : puzzle.getAllCells()) {
      solveForCell(puzzle, cell);
    }

    while (recursivelyEliminate(puzzle.getAllRestrictions(), puzzle)) {
      // do
    }
    print(puzzle);
  }

  private <T> void solveForCell(final Puzzle<T> puzzle, Cell<T> cell) {
    if (!cell.isSolved() && cell.getCurrentValues().size() == 1) {
      recursivelyEliminate(puzzle.getRestrictions(cell.getIdentifier()), puzzle);
      cell.setSolved();
    }
  }

  private <T> boolean recursivelyEliminate(final Collection<Restriction<T>> restrictions, final Puzzle<T> puzzle) {
    boolean stateChanged = false;
    for (Restriction<T> restriction : restrictions) {
      Set<Cell<T>> changedCells = restriction.eliminateValues();
      if (!changedCells.isEmpty()) {
        stateChanged = true;
      }
      for (Cell<T> cell : changedCells) {
        solveForCell(puzzle, cell);
      }
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
