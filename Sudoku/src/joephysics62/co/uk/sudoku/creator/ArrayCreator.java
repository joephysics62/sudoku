package joephysics62.co.uk.sudoku.creator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import joephysics62.co.uk.sudoku.builder.ArrayBuilder;
import joephysics62.co.uk.sudoku.constraints.Constraint;
import joephysics62.co.uk.sudoku.creator.util.Solved;
import joephysics62.co.uk.sudoku.model.Cell;
import joephysics62.co.uk.sudoku.model.Coord;
import joephysics62.co.uk.sudoku.model.Puzzle;
import joephysics62.co.uk.sudoku.model.Layout;
import joephysics62.co.uk.sudoku.solver.CellFilter;
import joephysics62.co.uk.sudoku.solver.Solver;
import joephysics62.co.uk.sudoku.solver.SolutionResult;
import joephysics62.co.uk.sudoku.solver.SolutionType;

import org.apache.log4j.Logger;


public abstract class ArrayCreator implements Creator {

  private final Solver _solver;
  private final CellFilter _solvedCellFilter = Solved.create();
  private Puzzle _createdPuzzle = null;
  private Puzzle _currentBestPuzzle = null;
  private int _callCount = 0;
  private static final Logger LOG = Logger.getLogger(ArrayCreator.class);
  private final Layout _layout;
  private final CreationSpec _creationSpec;

  public ArrayCreator(final Solver solver, final Layout layout, final CreationSpec creationSpec) {
    _solver = solver;
    _layout = layout;
    _creationSpec = creationSpec;
  }

  private final Set<Set<Coord>> _tried = new LinkedHashSet<>();
  private final Set<Set<Constraint>> _triedConstraints = new LinkedHashSet<>();

  @Override
  public Puzzle create() {
    Puzzle completedNewPuzzle = createCompletedNewPuzzle();
    findPuzzle(completedNewPuzzle);
    return _createdPuzzle ;
  }

  private boolean foundPuzzle() {
    return _createdPuzzle != null;
  }

  public void findPuzzle(final Puzzle currentPuzzle) {
    _callCount++;
    LOG.debug("Call " + _callCount + " to findPuzzle. Var constraints: " + currentPuzzle.getVariableConstraints().size());
    if (foundPuzzle()) {
      return;
    }
    final List<Coord> solvedCells = _solvedCellFilter.apply(currentPuzzle);
    LOG.debug("Currently puzzle has " + solvedCells.size() + " givens cells.");
    final List<Constraint> variableConstraints = currentPuzzle.getVariableConstraints();
    boolean seenSolvedCellSet = !_tried.add(new LinkedHashSet<>(solvedCells));
    boolean seenVarConsSet = !_triedConstraints.add(new LinkedHashSet<>(variableConstraints));
    if (seenSolvedCellSet && seenVarConsSet) {
      return;
    }
    if (solvedCells.size() > _creationSpec.getMaxGivens()) {
      Collections.shuffle(solvedCells);
      for (Coord coord : solvedCells) {
        if (foundPuzzle()) {
          return;
        }
        Puzzle candidateToSolve = currentPuzzle.deepCopy();
        Layout layout = candidateToSolve.getLayout();
        int init = (1 << layout.getInitialsSize()) - 1;
        candidateToSolve.setCellValue(init, coord);
        if (_creationSpec.isRemoveInSymmetricPairs()) {
          candidateToSolve.setCellValue(init, Coord.of(layout.getHeight() - coord.getRow() + 1, layout.getWidth() - coord.getCol() + 1));
        }
        LOG.debug("Puzzle now has " + _solvedCellFilter.apply(candidateToSolve).size() + " givens cells after removal");
        solveModifiedPuzzle(candidateToSolve);
      }
      LOG.debug("Tried removing each one of the solved cells");
    }
    else if (variableConstraints.size() > _creationSpec.getMaxVarConstraints()) {
      final List<Integer> cnums = new ArrayList<>();
      int varConsSize = currentPuzzle.getVariableConstraints().size();
      LOG.debug("Current number of variable constraints: " + varConsSize);
      for (int i = 0; i < varConsSize; i++) {
        cnums.add(i);
      }
      Collections.shuffle(cnums);
      LOG.debug("Trying to remove one of these...");
      for (int i = 0; i < varConsSize; i++) {
        if (foundPuzzle()) {
          return;
        }
        Puzzle candidateToSolve = currentPuzzle.deepCopy();
        final List<Constraint> varConstraintsInCandidate = candidateToSolve.getVariableConstraints();
        Constraint removed = varConstraintsInCandidate.remove((int) cnums.get(i));
        LOG.debug("Removing variable constraint " + removed);
        solveModifiedPuzzle(candidateToSolve);
      }
      LOG.debug("Tried removing each one of these " + varConsSize + " variable constraints.");
    }
  }

  private void solveModifiedPuzzle(final Puzzle candidateToSolve) {
    Puzzle candidateToKeep = candidateToSolve.deepCopy();
    SolutionResult solution = _solver.solve(candidateToSolve);
    LOG.debug("Removal leads to solution of type = " + solution.getType() + ", solved in " + solution.getTiming() + "ms");
    if (solution.getType() == SolutionType.UNIQUE) {
      checkPuzzleOrContinue(candidateToKeep);
    }
  }

  private void checkPuzzleOrContinue(final Puzzle candidateToKeep) {
    int numGivens = _solvedCellFilter.apply(candidateToKeep).size();
    List<Constraint> variableConstraints = candidateToKeep.getVariableConstraints();
    if (numGivens <= _creationSpec.getMaxGivens() && variableConstraints.size() <= _creationSpec.getMaxVarConstraints()) {
      LOG.debug("Have found a puzzle with " + numGivens + " clues and " + variableConstraints.size() + " var constraints.");
      _createdPuzzle = candidateToKeep;
    }
    else {
      LOG.debug("Not reached " + _creationSpec.getMaxGivens() + " givens and " + _creationSpec.getMaxVarConstraints() + " constraints... continue...");
      if (null == _currentBestPuzzle ||
          numGivens <= _solvedCellFilter.apply(_currentBestPuzzle).size() && variableConstraints.size() <= _currentBestPuzzle.getVariableConstraints().size()) {
        _currentBestPuzzle = candidateToKeep;
      }
      if (_callCount >= _creationSpec.getMaxDepth()) {
        LOG.debug("Have reached max recursive call count of " + _creationSpec.getMaxDepth()
            + ", returning current best puzzle which has " + _solvedCellFilter.apply(_currentBestPuzzle).size()
            + " givens and " + _currentBestPuzzle.getVariableConstraints().size() + " var constriants");
        _createdPuzzle = _currentBestPuzzle;
      }
      else {
        findPuzzle(candidateToKeep);
      }
    }
  }

  private Puzzle createCompletedNewPuzzle() {
    Puzzle puzzle = newPuzzle();
    SolutionResult solutionResult = _solver.solve(puzzle);
    if (solutionResult.getType() == SolutionType.NONE) {
      return null;
    }
    for (int row = 1; row <= _layout.getHeight(); row++) {
      for (int col = 1; col <= _layout.getWidth(); col++) {
        Coord coord = Coord.of(row, col);
        int niceValue = solutionResult.getSolution().getValue(coord);
        puzzle.setCellValue(Cell.cellValueAsBitwise(niceValue), coord);
      }
    }
    addVariableConstraints(puzzle);
    return puzzle;
  }

  protected abstract void addVariableConstraints(Puzzle puzzle);

  private Puzzle newPuzzle() {
    ArrayBuilder puzzleBuilder = new ArrayBuilder(_layout);
    final List<Integer> aRow = new ArrayList<>();
    for (int i = 1; i <= _layout.getWidth(); i++) {
      aRow.add(i);
    }
    Collections.shuffle(aRow);
    for (int i = 1; i <= _layout.getWidth(); i++) {
      puzzleBuilder.addGiven(aRow.get(i - 1), Coord.of(1, i));
    }
    puzzleBuilder.addTitle("Joe's Sudoku");
    addGeometricConstraints(puzzleBuilder);
    return puzzleBuilder.build();
  }

  protected abstract void addGeometricConstraints(ArrayBuilder puzzleBuilder);

}
