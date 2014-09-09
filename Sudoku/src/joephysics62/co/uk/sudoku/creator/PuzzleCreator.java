package joephysics62.co.uk.sudoku.creator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import joephysics62.co.uk.sudoku.builder.SudokuBuilder;
import joephysics62.co.uk.sudoku.model.Cell;
import joephysics62.co.uk.sudoku.model.Coord;
import joephysics62.co.uk.sudoku.model.Puzzle;
import joephysics62.co.uk.sudoku.solver.CellPickingStrategy;
import joephysics62.co.uk.sudoku.solver.PuzzleSolver;
import joephysics62.co.uk.sudoku.solver.SolutionResult;
import joephysics62.co.uk.sudoku.solver.SolutionType;
import joephysics62.co.uk.sudoku.write.PuzzleWriter;

public class PuzzleCreator {
  private final PuzzleSolver _solver;

  public PuzzleCreator(final PuzzleSolver solver) {
    _solver = solver;
  }

  public Puzzle create(final int puzzleSize, final int subTableHeight, final int subTableWidth) {
    Puzzle completedNewPuzzle = createCompletedNewPuzzle(puzzleSize, subTableHeight, subTableWidth);
    List<Coord> arrayList = new ArrayList<>();
    findPuzzle(completedNewPuzzle, arrayList);
    return completedNewPuzzle;
  }

  public void findPuzzle(final Puzzle completedPuzzle, final List<Coord> coordsToRemove) {
    Puzzle puzzleToTry = completedPuzzle.deepCopy();
    CellPickingStrategy cellGuessingStrategy = RandomSolved.create();
    int init = (1 << puzzleToTry.getPuzzleSize()) - 1;
    for (Coord coord : coordsToRemove) {
      puzzleToTry.setCellValue(init, coord);
    }
    Coord cellToBeCleaned = cellGuessingStrategy.cellToGuess(puzzleToTry);
    puzzleToTry.setCellValue(init, cellToBeCleaned);
    final Puzzle toPrint = puzzleToTry.deepCopy();
    SolutionResult solve = _solver.solve(puzzleToTry);
    if (solve.getType() == SolutionType.UNIQUE) {
      // OK
      System.out.println("Num clues = " + (puzzleToTry.getPuzzleSize() * puzzleToTry.getPuzzleSize() - coordsToRemove.size()));
      new PuzzleWriter().write(toPrint, System.out);
      coordsToRemove.add(cellToBeCleaned);
      findPuzzle(completedPuzzle, coordsToRemove);
    }
    else if (solve.getType() == SolutionType.MULTIPLE) {
      findPuzzle(completedPuzzle, coordsToRemove);
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
