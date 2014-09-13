package joephysics62.co.uk.sudoku.creator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import joephysics62.co.uk.sudoku.builder.SudokuBuilder;
import joephysics62.co.uk.sudoku.model.Cell;
import joephysics62.co.uk.sudoku.model.Coord;
import joephysics62.co.uk.sudoku.model.Puzzle;
import joephysics62.co.uk.sudoku.solver.CellFilter;
import joephysics62.co.uk.sudoku.solver.PuzzleSolver;
import joephysics62.co.uk.sudoku.solver.SolutionResult;
import joephysics62.co.uk.sudoku.solver.SolutionType;
import joephysics62.co.uk.sudoku.write.HTMLPuzzleWriter;
import joephysics62.co.uk.sudoku.write.PuzzleWriter;
import freemarker.template.TemplateException;

public class PuzzleCreator {
  private final PuzzleSolver _solver;
  private final Set<Set<Coord>> _seen = new LinkedHashSet<>();

  public PuzzleCreator(final PuzzleSolver solver) {
    _solver = solver;
  }

  public Puzzle create(final int puzzleSize, final int subTableHeight, final int subTableWidth, final int maxCluesToLeave) {
    if (!((subTableHeight < 0 && subTableWidth < 0) || (puzzleSize == subTableHeight * subTableWidth))) {
      throw new IllegalArgumentException();
    }
    _seen.clear();
    for (int i = 0; i < 10000; i++) {
      Puzzle completedNewPuzzle = createCompletedNewPuzzle(puzzleSize, subTableHeight, subTableWidth);
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
    int puzzleSize = puzzleToTry.getPuzzleSize();
    int init = (1 << puzzleSize) - 1;
    Collections.shuffle(solvedCells);
    int removesSize = puzzleSize * puzzleSize - cluesToLeave;

    Set<Coord> removes = new LinkedHashSet<>();
    for (Coord coord : solvedCells) {
      removes.add(coord);
      removes.add(new Coord(puzzleSize - coord.getRow() + 1, puzzleSize - coord.getCol() + 1));
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

  private Puzzle createCompletedNewPuzzle(final int puzzleSize, final int subTableHeight, final int subTableWidth) {
    Puzzle puzzle = newPuzzle(puzzleSize, subTableHeight, subTableWidth);
    SolutionResult solutionResult = _solver.solve(puzzle);
    if (solutionResult.getType() == SolutionType.NONE) {
      return null;
    }
    for (int row = 1; row <= puzzleSize; row++) {
      for (int col = 1; col <= puzzleSize; col++) {
        Coord coord = new Coord(row, col);
        int niceValue = solutionResult.getSolution().getValue(coord);
        puzzle.setCellValue(Cell.cellValueAsBitwise(niceValue), coord);
      }
    }
    return puzzle;
  }

  private Puzzle newPuzzle(final int puzzleSize, final int subTableHeight, final int subTableWidth) {
    SudokuBuilder sudokuBuilder = new SudokuBuilder(puzzleSize, subTableHeight, subTableWidth);
    Integer[][] givens = new Integer[puzzleSize][puzzleSize];
    for (Integer[] row : givens) {
      Arrays.fill(row, null);
    }
    final List<Integer> aRow = new ArrayList<>();
    for (int i = 1; i <= puzzleSize; i++) {
      aRow.add(i);
    }
    Collections.shuffle(aRow);
    givens[0] = aRow.toArray(new Integer[] {});
    sudokuBuilder.addGivens(givens);
    Puzzle puzzle = sudokuBuilder.build();
    return puzzle;
  }

  public static void main(String[] args) throws IOException, TemplateException {
    PuzzleWriter writer = new PuzzleWriter(System.out);
    PuzzleSolver solver = new PuzzleSolver(RandomUnsolved.create());
    PuzzleCreator creator = new PuzzleCreator(solver);
    final int maxCluesToLeave = 9;
    Puzzle puzzle = creator.create(6, 2, 3, maxCluesToLeave);
    writer.write(puzzle);
    HTMLPuzzleWriter htmlPuzzleWriter = new HTMLPuzzleWriter(puzzle);
    htmlPuzzleWriter.write(new File("sudoku.html"), "By Joe " + Calendar.getInstance().getTime().toLocaleString());
  }

}
