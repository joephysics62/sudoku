package joephysics62.co.uk.sudoku.solver;

import java.util.Collection;

import joephysics62.co.uk.sudoku.model.Cell;
import joephysics62.co.uk.sudoku.model.Restriction;
import joephysics62.co.uk.sudoku.standard.IntegerCell;
import joephysics62.co.uk.sudoku.standard.StandardPuzzle;

public class StandardPuzzleSolver {

  public void solve(final StandardPuzzle puzzle) {
    print(puzzle);

    // Initial sweep over all cells
    for (IntegerCell integerCell : puzzle.getAllCells()) {
      String identifier = integerCell.getIdentifier();
      recurse(puzzle.getRestrictions(identifier), puzzle);
    }

    recurse(puzzle.getAllRestrictions(), puzzle);
  }

  private void recurse(final Collection<Restriction<Integer>> restrictions, final StandardPuzzle puzzle) {
    for (Restriction<Integer> restriction : restrictions) {
      for (Cell<Integer> cell : restriction.eliminateValues()) {
        recurse(puzzle.getRestrictions(cell.getIdentifier()), puzzle);
      }
    }
    print(puzzle);
  }

  private void print(final StandardPuzzle puzzle) {
    int completeness = 0;
    for (IntegerCell integerCell : puzzle.getAllCells()) {
      completeness += integerCell.getCurrentValues().size();
      //System.out.println(integerCell.getIdentifier() + ": " + integerCell.getCurrentValues());
    }
    System.out.println(completeness);
    System.out.println("------------------------------------------------");
  }
}
