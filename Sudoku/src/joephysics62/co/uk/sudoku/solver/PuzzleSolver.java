package joephysics62.co.uk.sudoku.solver;

import java.util.Collection;

import joephysics62.co.uk.sudoku.model.Cell;
import joephysics62.co.uk.sudoku.model.Puzzle;
import joephysics62.co.uk.sudoku.model.Restriction;

public class PuzzleSolver {

  public <T> void solve(final Puzzle<T> puzzle) {
    print(puzzle);

    // Initial sweep over all cells
    for (Cell<T> cell : puzzle.getAllCells()) {
      String identifier = cell.getIdentifier();
      if (!cell.isSolved() && cell.getCurrentValues().size() == 1) {
        recurse(puzzle.getRestrictions(identifier), puzzle);
        cell.setSolved();
      }
    }

    recurse(puzzle.getAllRestrictions(), puzzle);
  }

  private <T> void recurse(final Collection<Restriction<T>> restrictions, final Puzzle<T> puzzle) {
    for (Restriction<T> restriction : restrictions) {
      for (Cell<T> cell : restriction.eliminateValues()) {
        if (!cell.isSolved() && cell.getCurrentValues().size() == 1) {
          recurse(puzzle.getRestrictions(cell.getIdentifier()), puzzle);
          cell.setSolved();
        }
      }
    }
    print(puzzle);
  }

  private <T> void print(final Puzzle<T> puzzle) {
    int completeness = 0;
    for (Cell<T> cell : puzzle.getAllCells()) {
      completeness += cell.getCurrentValues().size();
      //System.out.println(integerCell.getIdentifier() + ": " + integerCell.getCurrentValues());
    }
    System.out.println(completeness);
    System.out.println("------------------------------------------------");
  }
}
