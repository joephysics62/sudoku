package joephysics62.co.uk.sudoku.creator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import joephysics62.co.uk.sudoku.builder.ArrayPuzzleBuilder;
import joephysics62.co.uk.sudoku.constraints.Constraint;
import joephysics62.co.uk.sudoku.creator.util.Solved;
import joephysics62.co.uk.sudoku.model.Cell;
import joephysics62.co.uk.sudoku.model.Coord;
import joephysics62.co.uk.sudoku.model.Puzzle;
import joephysics62.co.uk.sudoku.model.PuzzleLayout;
import joephysics62.co.uk.sudoku.solver.CellFilter;
import joephysics62.co.uk.sudoku.solver.PuzzleSolver;
import joephysics62.co.uk.sudoku.solver.SolutionResult;
import joephysics62.co.uk.sudoku.solver.SolutionType;

import org.apache.log4j.Logger;


public abstract class ArrayPuzzleCreator implements PuzzleCreator {

  private final PuzzleSolver _solver;
  private Puzzle _createdPuzzle = null;
  private static final Logger LOG = Logger.getLogger(ArrayPuzzleCreator.class);

  public ArrayPuzzleCreator(PuzzleSolver solver) {
    _solver = solver;
  }

  private final Set<Set<Coord>> _tried = new LinkedHashSet<>();
  private final Set<Set<Constraint>> _triedConstraints = new LinkedHashSet<>();

  @Override
  public Puzzle create(final PuzzleLayout layout, final int maxCluesToLeave, final int maxOptionalConstraints) {
    Puzzle completedNewPuzzle = createCompletedNewPuzzle(layout);
    findPuzzle(completedNewPuzzle, maxCluesToLeave, maxOptionalConstraints);
    return _createdPuzzle ;
  }

  public void findPuzzle(final Puzzle currentPuzzle, int maxCluesToLeave, int maxOptionalConstraints) {
    LOG.debug("New call to findPuzzle " + currentPuzzle.getVariableConstraints().size());
    if (_createdPuzzle != null) {
      return;
    }
    CellFilter solvedCellFilter = Solved.create();
    final List<Coord> solvedCells = solvedCellFilter.apply(currentPuzzle);
    final List<Constraint> variableConstraints = currentPuzzle.getVariableConstraints();
    boolean seenSolvedCellSet = !_tried.add(new LinkedHashSet<>(solvedCells));
    boolean seenVarConsSet = !_triedConstraints.add(new LinkedHashSet<>(variableConstraints));
    if (seenSolvedCellSet && seenVarConsSet) {
      return;
    }
    if (solvedCells.size() > maxCluesToLeave) {
      Collections.shuffle(solvedCells);
      for (Coord coord : solvedCells) {
        if (_createdPuzzle != null) {
          return;
        }
        Puzzle candidateToSolve = currentPuzzle.deepCopy();
        PuzzleLayout layout = candidateToSolve.getLayout();
        int init = (1 << layout.getInitialsSize()) - 1;
        candidateToSolve.setCellValue(init, coord);
        candidateToSolve.setCellValue(init, Coord.of(layout.getHeight() - coord.getRow() + 1, layout.getWidth() - coord.getCol() + 1));
        Puzzle candidateToKeep = candidateToSolve.deepCopy();
        SolutionResult solution = _solver.solve(candidateToSolve);
        if (solution.getType() == SolutionType.UNIQUE) {
          int numGivens = solvedCells.size() - 2;
          if (numGivens <= maxCluesToLeave && variableConstraints.size() <= maxOptionalConstraints) {
            _createdPuzzle = candidateToKeep;
          }
          else {
            findPuzzle(candidateToKeep, maxCluesToLeave, maxOptionalConstraints);
          }
        }
      }
    }
    else if (variableConstraints.size() > maxOptionalConstraints) {
      final List<Integer> cnums = new ArrayList<>();
      int varConsSize = currentPuzzle.getVariableConstraints().size();
      LOG.debug("Current number of variable constraints: " + varConsSize);
      for (int i = 0; i < varConsSize; i++) {
        cnums.add(i);
      }
      Collections.shuffle(cnums);
      LOG.debug("Trying to remove one of these...");
      for (int i = 0; i < varConsSize; i++) {
        if (_createdPuzzle != null) {
          return;
        }
        Puzzle candidateToSolve = currentPuzzle.deepCopy();
        final List<Constraint> varConstraintsInCandidate = candidateToSolve.getVariableConstraints();
        Constraint removed = varConstraintsInCandidate.remove((int) cnums.get(i));
        LOG.debug("Removing variable constraint " + removed);
        Puzzle candidateToKeep = candidateToSolve.deepCopy();
        SolutionResult solution = _solver.solve(candidateToSolve);
        LOG.debug("Removal leads to solution of type = " + solution.getType() + ", solved in " + solution.getTiming() + "ms");
        if (solution.getType() == SolutionType.UNIQUE) {
          int numGivens = solvedCells.size();
          if (numGivens <= maxCluesToLeave && varConstraintsInCandidate.size() <= maxOptionalConstraints) {
            _createdPuzzle = candidateToKeep;
            LOG.debug("Have found a puzzle with " + numGivens + " clues and " + varConstraintsInCandidate.size() + " var constraints.");
            return;
          }
          else {
            LOG.debug("Not reached " + maxCluesToLeave + " givens and " + maxOptionalConstraints + " constraints... continue...");
            findPuzzle(candidateToKeep, maxCluesToLeave, maxOptionalConstraints);
          }
        }
      }
      LOG.debug("Tried removing each one of these " + varConsSize + " variable constraints.");
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
        Coord coord = Coord.of(row, col);
        int niceValue = solutionResult.getSolution().getValue(coord);
        puzzle.setCellValue(Cell.cellValueAsBitwise(niceValue), coord);
      }
    }
    addVariableConstraints(puzzle);
    return puzzle;
  }

  protected abstract void addVariableConstraints(Puzzle puzzle);

  private Puzzle newPuzzle(final PuzzleLayout layout) {
    ArrayPuzzleBuilder puzzleBuilder = new ArrayPuzzleBuilder(layout);
    final List<Integer> aRow = new ArrayList<>();
    for (int i = 1; i <= layout.getWidth(); i++) {
      aRow.add(i);
    }
    Collections.shuffle(aRow);
    for (int i = 1; i <= layout.getWidth(); i++) {
      puzzleBuilder.addGiven(aRow.get(i - 1), Coord.of(1, i));
    }
    puzzleBuilder.addTitle("Joe's Sudoku");
    addGeometricConstraints(puzzleBuilder);
    return puzzleBuilder.build();
  }

  protected abstract void addGeometricConstraints(ArrayPuzzleBuilder puzzleBuilder);

}
