package joephysics62.co.uk.sudoku.creator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import joephysics62.co.uk.sudoku.builder.SudokuBuilder;
import joephysics62.co.uk.sudoku.model.Cell;
import joephysics62.co.uk.sudoku.model.Coord;
import joephysics62.co.uk.sudoku.model.Puzzle;
import joephysics62.co.uk.sudoku.model.PuzzleLayout;
import joephysics62.co.uk.sudoku.solver.CellFilter;
import joephysics62.co.uk.sudoku.solver.PuzzleSolver;
import joephysics62.co.uk.sudoku.solver.SolutionResult;
import joephysics62.co.uk.sudoku.solver.SolutionType;

public class PuzzleCreator {
  private final PuzzleSolver _solver;
  private final Set<Set<Coord>> _seen = new LinkedHashSet<>();

  public PuzzleCreator(final PuzzleSolver solver) {
    _solver = solver;
  }

  public Puzzle create(final PuzzleLayout layout, final int maxCluesToLeave) {
    _seen.clear();
    for (int i = 0; i < 10000; i++) {
      Puzzle completedNewPuzzle = createCompletedNewPuzzle(layout);
      for (int j = 0; j < 100; j++) {
        Puzzle puzzle = findPuzzle(completedNewPuzzle, maxCluesToLeave);
        if (null != puzzle) {
          return puzzle;
        }
      }
    }
    return null;
  }

  public Puzzle findPuzzle(final Puzzle completedPuzzle, final int cluesToLeave) {
    Puzzle puzzleToTry = completedPuzzle.deepCopy();
    CellFilter solvedCellFilter = Solved.create();
    final List<Coord> solvedCells = solvedCellFilter.apply(puzzleToTry);
    PuzzleLayout layout = puzzleToTry.getLayout();
    int init = (1 << layout.getInitialsSize()) - 1;
    Collections.shuffle(solvedCells);
    int removesSize = layout.getWidth() * layout.getHeight() - cluesToLeave;

    Set<Coord> removes = new LinkedHashSet<>();
    for (Coord coord : solvedCells) {
      removes.add(coord);
      removes.add(new Coord(layout.getHeight() - coord.getRow() + 1, layout.getWidth() - coord.getCol() + 1));
      if (removes.size() >= removesSize) {
        break;
      }
    }
    for (Coord coord : removes) {
      puzzleToTry.setCellValue(init, coord);
    }
    Puzzle toPrint = puzzleToTry.deepCopy();
    SolutionResult solve = _solver.solve(puzzleToTry);
    if (solve.getType() == SolutionType.UNIQUE) {
        return toPrint;
    }
    else {
      return null;
    }
  }

  private Puzzle createCompletedNewPuzzle(final PuzzleLayout layout) {
    Puzzle puzzle = newPuzzle(layout);
    SolutionResult solutionResult = _solver.solve(puzzle);
    if (solutionResult.getType() == SolutionType.NONE) {
      return null;
    }
    for (int row = 1; row <= layout.getHeight(); row++) {
      for (int col = 1; col <= layout.getWidth(); col++) {
        Coord coord = new Coord(row, col);
        int niceValue = solutionResult.getSolution().getValue(coord);
        puzzle.setCellValue(Cell.cellValueAsBitwise(niceValue), coord);
      }
    }
    return puzzle;
  }

  private Puzzle newPuzzle(final PuzzleLayout layout) {
    SudokuBuilder sudokuBuilder = new SudokuBuilder(layout);
    Integer[][] givens = new Integer[layout.getHeight()][layout.getWidth()];
    for (Integer[] row : givens) {
      Arrays.fill(row, null);
    }
    final List<Integer> aRow = new ArrayList<>();
    for (int i = 1; i <= layout.getHeight(); i++) {
      aRow.add(i);
    }
    Collections.shuffle(aRow);
    givens[0] = aRow.toArray(new Integer[] {});
    sudokuBuilder.addGivens(givens);
    sudokuBuilder.addTitle("Joe's Sudoku");
    return sudokuBuilder.build();
  }

}
