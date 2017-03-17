package com.fenton.suguru;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import com.fenton.puzzle.constraint.Group;
import com.fenton.puzzle.grid.RectangularCoord;

public class Suguru {

  private final int _height;
  private final int _width;
  private final int[][] initState;
  private final int[][] _givens;
  private final Group[][] _groupByCell;

  public Suguru(final int[][] givens, final int[][] groups) {
    _givens = givens;
    _height = _givens.length;
    _width = _givens[0].length;
    _groupByCell = new Group[_height][_width];
    buildGroups(groups);
    initState = new int[_height][_width];
    setFromGivens(initState);
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

  private void setFromGivens(final int[][] state) {
    for (int row = 0; row < _height; row++) {
      for (int col = 0; col < _width; col++) {
        final int given = _givens[row][col];
        state[row][col] = given > 0 ? (1 << (given - 1)) : (1 << (_groupByCell[row][col].size())) - 1;
      }
    }
  }

  public Solution solve() {
    final int[][] currentState = ArrayUtils.clone(initState);
    final List<int[][]> solutions = new ArrayList<>();

    solveInner(solutions, currentState);

    if (solutions.isEmpty()) {
      return new Solution(SolutionType.NONE, Optional.empty());
    }
    final int[][] solution = solutions.get(0);
    return new Solution(solutions.size() > 1 ? SolutionType.MULTIPLE : SolutionType.UNIQUE, Optional.of(solution));
  }

  private void solveInner(final List<int[][]> solutions, final int[][] currentState) {
    if (solutions.size() > 1) {
      return; // stop when found multiple solns
    }
    EliminationResult result = null;
    while (result == null || result == EliminationResult.UNSOLVED_UPDATED) {
      result = eliminate(currentState);
      if (result == EliminationResult.SOLVED) {
        solutions.add(currentState);
      }
      else if (result == EliminationResult.UNSOLVED) {
        final RectangularCoord bestUnsolved = findBestUnsolved(currentState);
        final int unsolvedBitVal = currentState[bestUnsolved.row][bestUnsolved.col];
        for (final int guessedBitValue : decomposed(unsolvedBitVal)) {
          final int[][] cloned = ArrayUtils.clone(currentState);
          cloned[bestUnsolved.row][bestUnsolved.col] = guessedBitValue;
          solveInner(solutions, cloned);
        }
      }
    }
  }

  private List<Integer> decomposed(final int bitValue) {
    final List<Integer> powers = new ArrayList<>();
    int n = bitValue; // something > 0
    int power = 0;
    while (n != 0) {
        if ((n & 1) != 0) {
            powers.add(1 << power);
        }
        ++power;
        n >>>= 1;
    }
    return powers;
  }

  private RectangularCoord findBestUnsolved(final int[][] currentState) {
    int bestBitcount = Integer.MAX_VALUE;
    RectangularCoord bestCoord = null;
    for (int row = 0; row < _height; row++) {
      for (int col = 0; col < _width; col++) {
        final int bitCount = Integer.bitCount(currentState[row][col]);
        if (bitCount < 2) {
          continue;
        }
        if (bitCount < bestBitcount) {
          bestCoord = RectangularCoord.of(row, col);
          if (bitCount == 2) {
            return bestCoord;
          }
          bestBitcount = bitCount;
        }
      }
    }
    return bestCoord;
  }

  public EliminationResult eliminate(final int[][] state) {
    boolean stateChanged = false;
    boolean allSolved = true;

    for (int row = 0; row < _height; row++) {
      for (int col = 0; col < _width; col++) {
        final Integer bitValue = state[row][col];
        final int bitCount = Integer.bitCount(bitValue);
        if (bitCount == 0) {
          return EliminationResult.INCONSISTENT;
        }
        if (bitCount != 1) {
          allSolved = false;
          continue;
        }
        final RectangularCoord current = RectangularCoord.of(row, col);

        // Eliminate adjacents
        final Set<RectangularCoord> coordsToApplyElimination = new LinkedHashSet<>();

        // Add surrounds
        for (int r = Math.max(0, row - 1); r <= Math.min(_height - 1, row + 1); r++) {
          for (int c = Math.max(0, col - 1); c <= Math.min(_width - 1, col + 1); c++) {
            coordsToApplyElimination.add(RectangularCoord.of(r, c));
          }
        }
        // Eliminate by group
        final Group group = _groupByCell[row][col];
        coordsToApplyElimination.addAll(group.coords());

        for (final RectangularCoord coord : coordsToApplyElimination) {
          if (coord.equals(current)) {
            continue;
          }
          final int maskChange = state[coord.row][coord.col] & bitValue;
          if (maskChange > 0) {
            state[coord.row][coord.col] -= maskChange;
            stateChanged = true;
          }
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
