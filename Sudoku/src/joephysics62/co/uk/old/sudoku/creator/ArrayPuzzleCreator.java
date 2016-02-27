package joephysics62.co.uk.old.sudoku.creator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import joephysics62.co.uk.old.constraints.Constraint;
import joephysics62.co.uk.old.grid.Coord;
import joephysics62.co.uk.old.sudoku.builder.ArrayBuilder;
import joephysics62.co.uk.old.sudoku.creator.util.Solved;
import joephysics62.co.uk.old.sudoku.model.Cell;
import joephysics62.co.uk.old.sudoku.model.Puzzle;
import joephysics62.co.uk.old.sudoku.model.PuzzleLayout;
import joephysics62.co.uk.old.sudoku.solver.CellFilter;
import joephysics62.co.uk.old.sudoku.solver.SolutionResult;
import joephysics62.co.uk.old.sudoku.solver.Solver;
import joephysics62.co.uk.puzzle.SolutionType;

import org.apache.log4j.Logger;


public abstract class ArrayPuzzleCreator implements PuzzleCreator {

  private final Solver _solver;
  private final CellFilter _solvedCellFilter = Solved.create();
  private Puzzle _createdPuzzle = null;
  private Puzzle _currentBestPuzzle = null;
  private int _callCount = 0;
  private static final Logger LOG = Logger.getLogger(ArrayPuzzleCreator.class);
  private final PuzzleLayout _layout;
  private final CreationSpec _creationSpec;

  public ArrayPuzzleCreator(final Solver solver, final PuzzleLayout layout, final CreationSpec creationSpec) {
    _solver = solver;
    _layout = layout;
    _creationSpec = creationSpec;
  }

  private final Set<Set<Coord>> _tried = new LinkedHashSet<>();
  private final Set<Set<Constraint>> _triedConstraints = new LinkedHashSet<>();

  @Override
  public Puzzle create() {
    final Puzzle completedNewPuzzle = createCompletedNewPuzzle();
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
    final boolean seenSolvedCellSet = !_tried.add(new LinkedHashSet<>(solvedCells));
    final boolean seenVarConsSet = !_triedConstraints.add(new LinkedHashSet<>(variableConstraints));
    if (seenSolvedCellSet && seenVarConsSet) {
      return;
    }
    if (solvedCells.size() > _creationSpec.getMaxGivens()) {
      Collections.shuffle(solvedCells);
      for (final Coord coord : solvedCells) {
        if (foundPuzzle()) {
          return;
        }
        final Puzzle candidateToSolve = currentPuzzle.deepCopy();
        final PuzzleLayout layout = candidateToSolve.getLayout();
        final int init = layout.getInitialValue();
        candidateToSolve.set(init, coord);
        if (_creationSpec.isRemoveInSymmetricPairs()) {
          candidateToSolve.set(init, Coord.of(layout.getHeight() - coord.getRow() + 1, layout.getWidth() - coord.getCol() + 1));
        }
        LOG.debug("Puzzle now has " + _solvedCellFilter.apply(candidateToSolve).size() + " givens cells after removal");
        solveModifiedPuzzle(candidateToSolve);
      }
      LOG.debug("Tried removing each one of the solved cells");
    }
    else if (variableConstraints.size() > _creationSpec.getMaxVarConstraints()) {
      final List<Constraint> copyConstraints = new ArrayList<>(currentPuzzle.getVariableConstraints());
      Collections.shuffle(copyConstraints);
      postShuffleReorder(copyConstraints);
      LOG.debug("Trying to remove one of these...");
      for (final Constraint toRemove : copyConstraints) {
        if (foundPuzzle()) {
          return;
        }
        final Puzzle candidateToSolve = currentPuzzle.deepCopy();
        final boolean removeSuccessful = removeVariable(toRemove, candidateToSolve);
        if (removeSuccessful) {
          solveModifiedPuzzle(candidateToSolve);
        }
      }
      LOG.debug("Tried removing each one of these " + copyConstraints.size() + " variable constraints.");
    }
  }

  protected abstract boolean removeVariable(final Constraint constraint, Puzzle candidateToSolve);

  protected abstract void postShuffleReorder(final List<Constraint> varConstraints);

  private void solveModifiedPuzzle(final Puzzle candidateToSolve) {
    final Puzzle candidateToKeep = candidateToSolve.deepCopy();
    final SolutionResult solution = _solver.solve(candidateToSolve);
    LOG.debug("Removal leads to solution of type = " + solution.getType() + ", solved in " + solution.getTiming() + "ms");
    if (solution.getType() == SolutionType.UNIQUE) {
      checkPuzzleOrContinue(candidateToKeep);
    }
  }

  private void checkPuzzleOrContinue(final Puzzle candidateToKeep) {
    final int numGivens = _solvedCellFilter.apply(candidateToKeep).size();
    final List<Constraint> variableConstraints = candidateToKeep.getVariableConstraints();
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
    final Puzzle puzzle = newPuzzle();
    final SolutionResult solutionResult = _solver.solve(puzzle);
    if (solutionResult.getType() == SolutionType.NONE) {
      return null;
    }
    for (final Coord coord : puzzle) {
      final int niceValue = solutionResult.getSolution().getValue(coord);
      puzzle.set(Cell.cellValueAsBitwise(niceValue), coord);
    }
    addVariableConstraints(puzzle);
    return puzzle;
  }

  protected abstract void addVariableConstraints(Puzzle puzzle);

  protected abstract void addGeometricConstraints(ArrayBuilder puzzleBuilder);

  private Puzzle newPuzzle() {
    final ArrayBuilder puzzleBuilder = new ArrayBuilder(_layout);
    final List<Integer> aRow = new ArrayList<>();
    for (int i = 1; i <= _layout.getWidth(); i++) {
      aRow.add(i);
    }
    Collections.shuffle(aRow);
    for (int i = 1; i <= _layout.getWidth(); i++) {
      puzzleBuilder.addGiven(aRow.get(i - 1), Coord.of(1, i));
    }
    final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    puzzleBuilder.addTitle("Joe's Puzzle " + dateFormat.format(Calendar.getInstance().getTime()));
    addGeometricConstraints(puzzleBuilder);
    return puzzleBuilder.build();
  }


}
