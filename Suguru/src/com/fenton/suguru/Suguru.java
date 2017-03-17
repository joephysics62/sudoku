package com.fenton.suguru;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fenton.puzzle.constraint.Group;

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
    final int height = state.length;
    final int width = state[0].length;
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        final int given = _givens[row][col];
        state[row][col] = given > 0 ? (1 << (given - 1)) : (1 << (_groupByCell[row][col].size())) - 1;
      }
    }
  }

  public Solution solve() {
    final int[][] currentState = ArrayUtils.clone(initState);
    printState(currentState);

    boolean continueElim = true;
    while (continueElim) {
      final EliminationResult eliminationResult = eliminate(currentState);
      continueElim = eliminationResult == EliminationResult.UNSOLVED_UPDATED;
      printState(currentState);
    }
    return new Solution(SolutionType.NONE, Optional.empty());
  }

  private void printState(final int[][] currentState) {
    for (final int[] row : currentState) {
      final String rowStr = Arrays.stream(row).boxed()
            .map(Integer::toBinaryString)
            .map(s -> String.format("%1$6s", s))
            .collect(Collectors.joining(", "));
      System.out.println(rowStr);
    }
    System.out.println();
  }

  public EliminationResult eliminate(final int[][] state) {
    final int height = state.length;
    final int width = state[0].length;
    boolean stateChanged = false;
    boolean allSolved = true;

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        final Integer bitValue = state[row][col];
        final int bitCount = Integer.bitCount(bitValue);
        if (bitCount == 0) {
          return EliminationResult.INCONSISTENT;
        }
        if (bitCount != 1) {
          allSolved = false;
          continue;
        }
        // Eliminate adjacents
        for (int r = Math.max(0, row - 1); r <= Math.min(height - 1, row + 1); r++) {
          for (int c = Math.max(0, col - 1); c <= Math.min(width - 1, col + 1); c++) {
            if (r == row && c == col) {
              continue;
            }
            final int maskChange = state[r][c] & bitValue;
            if (maskChange > 0) {
              state[r][c] -= maskChange;
              stateChanged = true;
            }
          }
        }
        // Eliminate by group
        final Group group = _groupByCell[row][col];
        for (final int[] coord : group.getCoords()) {
          final int r = coord[0];
          final int c = coord[1];
          if (r == row && c == col) {
            continue;
          }
          final int maskChange = state[r][c] & bitValue;
          if (maskChange > 0) {
            state[r][c] -= maskChange;
            stateChanged = true;
          }
        }
      }
    }
    if (allSolved) {
      return EliminationResult.SOLVED;
    }
    return stateChanged ? EliminationResult.UNSOLVED_UPDATED : EliminationResult.SOLVED;
  }

  public int getWidth() {
    return _width;
  }

  public int getHeight() {
    return _height;
  }

}
