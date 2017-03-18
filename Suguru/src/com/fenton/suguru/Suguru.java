package com.fenton.suguru;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import com.fenton.puzzle.EliminationResult;
import com.fenton.puzzle.PuzzleState;
import com.fenton.puzzle.Solution;
import com.fenton.puzzle.SolutionType;
import com.fenton.puzzle.constraint.Group;
import com.fenton.puzzle.grid.RectangularCoord;

public class Suguru {

  private final int _height;
  private final int _width;
  private final int[][] _givens;
  private final Group[][] _groupByCell;
  private final PuzzleState _initState;

  public Suguru(final int[][] givens, final int[][] groups) {
    _givens = givens;
    _height = _givens.length;
    _width = _givens[0].length;
    _groupByCell = new Group[_height][_width];
    buildGroups(groups);
    _initState = new PuzzleState(_height, _width);
    for (int row = 0; row < _height; row++) {
      for (int col = 0; col < _width; col++) {
        final RectangularCoord coord = RectangularCoord.of(row, col);
        final int given = _givens[row][col];
        if (given > 0) {
          _initState.setGiven(coord, given);
        }
        else {
          _initState.initializeBitmask(coord, _groupByCell[row][col].size());
        }
      }
    }
  }

  private void buildGroups(final int[][] groupIds) {
    final Map<Integer, Group> groupMap = new LinkedHashMap<>();
    for (int row = 0; row < _height; row++) {
      for (int col = 0; col < _width; col++) {
        final int groupId = groupIds[row][col];
        if (!groupMap.containsKey(groupId)) {
          groupMap.put(groupId, new Group());
        }
        final Group group = groupMap.get(groupId);
        group.addCell(row, col);
        _groupByCell[row][col] = group;
      }
    }
  }

  private int _recurseCount = 0;

  public Solution solve() {
    final PuzzleState currentState = _initState.clone();
    final List<PuzzleState> solutions = new ArrayList<>();

    _recurseCount = 0;
    solveInner(solutions, currentState);

    if (solutions.isEmpty()) {
      return new Solution(SolutionType.NONE, Optional.empty(), _recurseCount);
    }
    final PuzzleState solution = solutions.get(0);
    return new Solution(
        solutions.size() > 1 ? SolutionType.MULTIPLE : SolutionType.UNIQUE,
        Optional.of(solution),
        _recurseCount);
  }

  private void solveInner(final List<PuzzleState> solutions, final PuzzleState currentState) {
    if (solutions.size() > 1) {
      return; // stop when found multiple solns
    }
    _recurseCount++;
    EliminationResult result = null;
    while (result == null || result == EliminationResult.UNSOLVED_UPDATED) {
      result = eliminate(currentState);
      if (result == EliminationResult.SOLVED) {
        solutions.add(currentState);
      }
      else if (result == EliminationResult.UNSOLVED) {
        final RectangularCoord bestUnsolved = findBestUnsolved(currentState);
        for (final Integer possible : currentState.possibles(bestUnsolved)) {
          final PuzzleState cloned = currentState.clone();
          cloned.setGiven(bestUnsolved, possible);
          solveInner(solutions, cloned);
        }
      }
    }
  }

  private RectangularCoord findBestUnsolved(final PuzzleState currentState) {
    int bestBitcount = Integer.MAX_VALUE;
    RectangularCoord bestCoord = null;
    for (int row = 0; row < _height; row++) {
      for (int col = 0; col < _width; col++) {
        final int possiblesCount = currentState.possiblesCount(RectangularCoord.of(row, col));
        if (possiblesCount < 2) {
          continue;
        }
        if (possiblesCount < bestBitcount) {
          bestCoord = RectangularCoord.of(row, col);
          if (possiblesCount == 2) {
            return bestCoord;
          }
          bestBitcount = possiblesCount;
        }
      }
    }
    return bestCoord;
  }

  private Set<RectangularCoord> forCoord(final RectangularCoord current) {
    // Eliminate adjacents
    final Set<RectangularCoord> coordsToApplyElimination = new LinkedHashSet<>();

    // Add surrounds
    for (int r = Math.max(0, current.row - 1); r <= Math.min(_height - 1, current.row + 1); r++) {
      for (int c = Math.max(0, current.col - 1); c <= Math.min(_width - 1, current.col + 1); c++) {
        coordsToApplyElimination.add(RectangularCoord.of(r, c));
      }
    }
    // Eliminate by group
    final Group group = _groupByCell[current.row][current.col];
    coordsToApplyElimination.addAll(group.coords());
    return coordsToApplyElimination;
  }

  public EliminationResult eliminate(final PuzzleState state) {
    boolean stateChanged = false;
    boolean allSolved = true;

    for (int row = 0; row < _height; row++) {
      for (int col = 0; col < _width; col++) {
        final RectangularCoord fromCoord = RectangularCoord.of(row, col);
        final int possiblesCount = state.possiblesCount(fromCoord);
        if (possiblesCount == 0) {
          return EliminationResult.INCONSISTENT;
        }
        if (possiblesCount != 1) {
          allSolved = false;
          continue;
        }

        for (final RectangularCoord toCoord : forCoord(fromCoord)) {
          if (toCoord.equals(fromCoord)) {
            continue;
          }
          stateChanged |= state.applyFilter(fromCoord, toCoord);
        }
      }
    }
    if (allSolved) {
      return EliminationResult.SOLVED;
    }
    return stateChanged ? EliminationResult.UNSOLVED_UPDATED : EliminationResult.UNSOLVED;
  }

  public static <T> Predicate<T> not(final Predicate<T> t) {
    return t.negate();
  }

  public int getWidth() {
    return _width;
  }

  public int getHeight() {
    return _height;
  }

}
