package joephysics62.co.uk.old.sudoku.solver;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import joephysics62.co.uk.hidato.SolutionType;
import joephysics62.co.uk.old.constraints.Constraint;
import joephysics62.co.uk.old.grid.Coord;
import joephysics62.co.uk.old.grid.arrays.IntegerArrayGrid;
import joephysics62.co.uk.old.sudoku.model.Cell;
import joephysics62.co.uk.old.sudoku.model.Puzzle;
import joephysics62.co.uk.old.sudoku.model.PuzzleLayout;

import org.apache.log4j.Logger;


public class Solver {

  private final CellFilter _cellGuessingStrategy;
  private static final Logger LOG = Logger.getLogger(Solver.class);

  public Solver(final CellFilter cellGuessingStrategy) {
    _cellGuessingStrategy = cellGuessingStrategy;
  }

  public SolutionResult solve(final Puzzle puzzle) {
    final Set<Solution> solutions = new LinkedHashSet<>();
    final long start = System.currentTimeMillis();
    solve(puzzle, solutions, 0);
    final long timing = System.currentTimeMillis() - start;
    if (solutions.size() == 1) {
      return new SolutionResult(SolutionType.UNIQUE, solutions.iterator().next(), timing);
    }
    else if (solutions.size() > 1) {
      return new SolutionResult(SolutionType.MULTIPLE, solutions.iterator().next(), timing);
    }
    else {
      return new SolutionResult(SolutionType.NONE, null, timing);
    }
  }

  private void solve(final Puzzle puzzle, final Set<Solution> solutions, final int recurseDepth) {
    if (solutions.size() > 1) {
      LOG.info("More than one solution found... return from solving");
      return;
    }
    analyticElimination(puzzle);
    if (puzzle.isUnsolveable()) {
      LOG.info("Puzzle is unsolveable.. return from solving");
      return;
    }
    if (puzzle.isSolved()) {
      addAsSolution(puzzle, solutions);
      LOG.info("Found a solution.. return from solving");
      return;
    }
    final List<Coord> cellsToGuess = _cellGuessingStrategy.apply(puzzle);
    if (cellsToGuess.isEmpty()) {
      return;
    }
    final Coord cellToGuess = cellsToGuess.get(0);
    LOG.debug("Guessing on cell " + cellsToGuess);
    final char[] charArray = Integer.toBinaryString(puzzle.get(cellToGuess)).toCharArray();
    for (int i = 1; i <= charArray.length; i++) {
      if ('1' == charArray[charArray.length - i]) {
        final Puzzle copy = puzzle.deepCopy();
        copy.set(Cell.cellValueAsBitwise(i), cellToGuess);
        LOG.debug("Guessing value " + i + " on cell " + cellsToGuess);
        solve(copy, solutions, recurseDepth + 1);
      }
    }
  }

  private void addAsSolution(final Puzzle puzzle, final Set<Solution> solutions) {
    final PuzzleLayout layout = puzzle.getLayout();
    final IntegerArrayGrid solutionMap = new IntegerArrayGrid(layout);
    for (final Coord coord : puzzle) {
      solutionMap.set(Cell.toNumericValue(puzzle.get(coord)), coord);
    }
    solutions.add(new Solution(solutionMap, layout));
  }

  private void analyticElimination(final Puzzle puzzle) {
    for (final Coord coord : puzzle) {
      recursiveCellSolve(puzzle, coord);
    }
    solveOnRestrictions(puzzle);
  }

  private void recursiveCellSolve(final Puzzle puzzle, final Coord coord) {
    final int value = puzzle.get(coord);
    if (Cell.isSolved(value)) {
      for (final Constraint restriction : puzzle.getConstraints(coord)) {
        restriction.forKnownValue(puzzle, coord);
      }
    }
  }

  private boolean solveOnRestrictions(final Puzzle puzzle) {
    boolean changed = false;
    for (final Constraint restriction : puzzle.getAllConstraints()) {
      if (restriction.eliminateValues(puzzle)) {
        changed = true;
      }
    }
    return changed;
  }

}
