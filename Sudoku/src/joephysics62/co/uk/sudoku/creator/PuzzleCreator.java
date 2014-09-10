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
import joephysics62.co.uk.sudoku.solver.CellFilter;
import joephysics62.co.uk.sudoku.solver.PuzzleSolver;
import joephysics62.co.uk.sudoku.solver.SolutionResult;
import joephysics62.co.uk.sudoku.solver.SolutionType;
import joephysics62.co.uk.sudoku.write.PuzzleWriter;

public class PuzzleCreator {
  private final PuzzleSolver _solver;
  private final Set<Set<Coord>> _seenCoords = new LinkedHashSet<>();
  private Puzzle _toPrint;

  public PuzzleCreator(final PuzzleSolver solver) {
    _solver = solver;
  }

  public Puzzle create(final int puzzleSize, final int subTableHeight, final int subTableWidth) {
       Puzzle completedNewPuzzle = createCompletedNewPuzzle(puzzleSize, subTableHeight, subTableWidth);
      _seenCoords.clear();
      List<Coord> coordsToRemove = new ArrayList<>();
      findPuzzle(completedNewPuzzle, coordsToRemove, false);
      int numClues = puzzleSize * puzzleSize - coordsToRemove.size();
      new PuzzleWriter().write(_toPrint, System.out);
      System.out.println("NUM CLUES = " + numClues);
      return completedNewPuzzle;
  }

  public void findPuzzle(final Puzzle completedPuzzle, final List<Coord> coordsToRemove, final boolean backtracked) {
    Puzzle puzzleToTry = completedPuzzle.deepCopy();
    CellFilter cellGuessingStrategy = Solved.create();
    int init = (1 << puzzleToTry.getPuzzleSize()) - 1;
    for (Coord coord : coordsToRemove) {
      puzzleToTry.setCellValue(init, coord);
    }
    List<Coord> solvedCells = cellGuessingStrategy.apply(puzzleToTry);
    Collections.shuffle(solvedCells);
    Coord cellToBeCleaned = solvedCells.get(0);
    puzzleToTry.setCellValue(init, cellToBeCleaned);
    _toPrint = puzzleToTry.deepCopy();
    SolutionResult solve = _solver.solve(puzzleToTry);
    if (solve.getType() == SolutionType.UNIQUE) {
      // OK
      coordsToRemove.add(cellToBeCleaned);
      if (backtracked || _seenCoords.add(new LinkedHashSet<>(coordsToRemove))) {
        findPuzzle(completedPuzzle, coordsToRemove, false);
      }
    }
    else if (solve.getType() == SolutionType.MULTIPLE) {
      coordsToRemove.remove(coordsToRemove.size() - 1);
      findPuzzle(completedPuzzle, coordsToRemove, true);
    }
    else {
      // Broken
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
    assert puzzleSize == subTableHeight * subTableWidth || subTableHeight < 0 && subTableWidth < 0;
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

  public static void main(String[] args) {
    PuzzleWriter writer = new PuzzleWriter();
    PuzzleSolver solver = new PuzzleSolver(RandomUnsolved.create());
    PuzzleCreator creator = new PuzzleCreator(solver);
    Puzzle puzzle = creator.create(9, 3, 3);
    writer.write(puzzle, System.out);
  }

}
